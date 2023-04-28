package jp.reception.soarest.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jp.reception.soarest.common.utils.CommonUtils;
import jp.reception.soarest.domain.dto.MeetingRoomSearchResultDto;
import jp.reception.soarest.domain.dto.MeetingSearchDto;
import jp.reception.soarest.domain.dto.MeetingSearchResultDto;
import jp.reception.soarest.domain.dto.PurposeSearchResultDto;
import jp.reception.soarest.domain.dto.StaffSearchResultDto;
import jp.reception.soarest.enums.CharEnum;
import jp.reception.soarest.enums.MessageEnum;
import jp.reception.soarest.form.MeetingRegisterForm;
import jp.reception.soarest.form.MeetingSearchForm;
import jp.reception.soarest.repository.CommonRepository;
import jp.reception.soarest.repository.MeetingRepository;


@Service
/*
 * 打ち合わせ情報一覧 サービス実装クラス
 * 
 * @author k.abe
 * @version 1.0
 */
public class MeetingServiceImpl implements MeetingService {

    // 打ち合わせ関連 リポジトリ
    @Autowired
    private MeetingRepository meetingRepository;

    // 共通 リポジトリ
    @Autowired
    CommonRepository commonRepository;

    // エラーメッセージ
    private final String ERR_MSG = "errMsg";

    // 検索結果件数
    private final String SEARCH_COUNT = "searchCount";

    // 打ち合わせリスト
    private final String MTG_LIST = "mtgList";

    // ユーザーID
    private final String USER_ID = "userId";

    // サブユーザーID
    private final String SUB_USER_ID = "subUserId";

    // 相手会社名
    private final String CLIENT_COMP_NAME = "clientCompName";

    // 相手氏名
    private final String CLIENT_NAME = "clientName";

    // 予定日
    private final String SCHEDULED_DATE = "scheduledDate";

    // 予定時刻
    private final String SCHEDULED_TIME = "scheduledTime";

    // 会議室No
    private final String ROOM_ID = "roomId";

    // その他打ち合わせ場所
    private final String MTG_PLACE = "mtgPlace";

    // 目的
    private final String MTG_ID = "mtgId";

    // コメント
    private final String COMMENT = "comment";

    /*
     * 打ち合わせ情報一覧 初期処理
     * 
     * @param model モデル
     */
    @Override
    public void init(Model model) throws SQLException {
        try {
            // 主担当、副担当プルダウンの取得
            List<StaffSearchResultDto> staffList = commonRepository.searchStaffList();

            // 会議室プルダウンの取得
            List<MeetingRoomSearchResultDto> roomList = commonRepository.searchRoomList();
            
            // 目的プルダウンの取得
            List<PurposeSearchResultDto> purposeList = commonRepository.searchPurposeList();

            // プルダウン生成
            CommonUtils.makePulldown(model, staffList, new StaffSearchResultDto());
            CommonUtils.makePulldown(model, roomList, new MeetingRoomSearchResultDto());
            CommonUtils.makePulldown(model, purposeList, new PurposeSearchResultDto());
           
        } catch (Exception e) {
            // SQLの例外の場合
            if (e.getCause() instanceof SQLException) {
                throw new SQLException(e);
            } else {
                throw e;
            }
        }
    }

    /*
     * 打ち合わせ情報一覧 検索
     * 
     * @param form 打ち合わせ情報一覧 フォームクラス 
     * @param searchDto 打ち合わせ情報一覧 検索用DTO
     * @param model モデル
     * @return mtgList 検索結果
     */
    @Override
    public List<MeetingSearchResultDto> searchMtgList(MeetingSearchForm form, 
        MeetingSearchDto searchDto, Model model) throws SQLException {
        
        // beanの内容を詰め替え
        BeanUtils.copyProperties(form, searchDto);
        // プロパティ名が異なるものは別途設定
        searchDto.setMtgId(form.getPurpose());

        // 検索結果格納用リスト
        List<MeetingSearchResultDto> mtgList = new ArrayList<MeetingSearchResultDto>();
        try {
            // 検索処理を実行
            mtgList = meetingRepository.searchMtgList(searchDto);
            
            // 検索結果が0件の場合
            if (0 == mtgList.size()) {
                // エラーメッセージを画面に返却
                model.addAttribute(ERR_MSG, MessageEnum.MSG_D01_W_001.getMsg(CharEnum.VALIDATION.getChar()));
                // 検索結果件数を設定
                // model.addAttribute(SEARCH_COUNT, mtgList.size());
            } else {
                
                int cnt = 0;
                for(MeetingSearchResultDto rs : mtgList) {
                    // 日付のハイフンを削除
                    mtgList.get(cnt).setScheduledDate(rs.getScheduledDate()
                        .replace(CharEnum.HYPHEN.getChar(), CharEnum.SLASH.getChar()));

                    // 時間の秒を削除
                    mtgList.get(cnt).setScheduledTime(rs.getScheduledTime().substring(0,5));
                    
                    // コメントの改行を置換
                    mtgList.get(cnt).setComment(rs.getComment().replaceAll("\r\n|\r|\n", "<br>"));

                    ++cnt;
                }
                // 検索結果を格納
                model.addAttribute(MTG_LIST, mtgList);
                // 検索結果件数を設定
                model.addAttribute(SEARCH_COUNT, mtgList.size());
            }
        } catch (Exception e) {
            if (e.getCause() instanceof SQLException) {
                throw new SQLException(e);
            } else {
                throw e;
            }
        }
        // 検索結果を返却
        return mtgList;
    }

    /*
     * 打ち合わせ情報一覧 入力チェック
     * 
     * @param form 打ち合わせ情報一覧 フォームクラス 
     * @param model モデル
     */
    @Override
    public boolean inputCheck(MeetingSearchForm form, Model model) {
        // 会議室名がその他、かつ打ち合わせ場所がNULLまたは空文字の場合
        if (form.getRoomId() == 9999 && (null == form.getMtgPlace() || "" == form.getMtgPlace())) {
            model.addAttribute(ERR_MSG, MessageEnum.MSG_D01_W_004.getMsg(CharEnum.VALIDATION.getChar()));
            return false;
        }

        return true;
    }

    /*
     * 打ち合わせ情報一覧 入力値保持
     * 
     * @param form 打ち合わせ情報一覧 フォームクラス 
     * @param model モデル
     */
    @Override
    public void saveWord(MeetingSearchForm form, Model model) {
        // 検索値を入力欄に保持
        model.addAttribute(USER_ID, form.getUserId());
        model.addAttribute(SUB_USER_ID, form.getSubUserId());
        model.addAttribute(CLIENT_COMP_NAME, form.getClientCompName());
        model.addAttribute(CLIENT_NAME, form.getClientName());
        model.addAttribute(SCHEDULED_DATE, form.getScheduledDate());
        model.addAttribute(SCHEDULED_TIME, form.getScheduledTime());
        model.addAttribute(ROOM_ID, form.getRoomId());
        model.addAttribute(MTG_PLACE, form.getMtgPlace());
        model.addAttribute(MTG_ID, form.getPurpose()); // 何か知らんが、"purpose"にすると値が保持されん
        model.addAttribute(COMMENT, form.getComment());
    }
    
    /*
     * 打ち合わせ情報登録 初期処理
     * 
     * @param model モデル
     */
    @Override
    public void meetingRegister (Model model) throws SQLException {
        try {
            // 主担当、副担当プルダウンの取得
            List<StaffSearchResultDto> staffList = commonRepository.searchStaffList();

            // 会議室プルダウンの取得
            List<MeetingRoomSearchResultDto> roomList = commonRepository.searchRoomList();
            
            // 目的プルダウンの取得
            List<PurposeSearchResultDto> purposeList = commonRepository.searchPurposeList();

            // プルダウン生成
            CommonUtils.makePulldown(model, staffList, new StaffSearchResultDto());
            CommonUtils.makePulldown(model, roomList, new MeetingRoomSearchResultDto());
            CommonUtils.makePulldown(model, purposeList, new PurposeSearchResultDto());
           
        } catch (Exception e) {
            // SQLの例外の場合
            if (e.getCause() instanceof SQLException) {
                throw new SQLException(e);
            } else {
                throw e;
            }
        }
    }
    
    /*
     * 打ち合わせ情報登録 入力チェック
     * 
     * @param form 打ち合わせ情報一覧 フォームクラス 
     * @param model モデル
     */
    @Override
    public void registerCheck(MeetingSearchForm form, Model model) {
    	// 検索値を入力欄に保持
        model.addAttribute(USER_ID, form.getUserId());
        model.addAttribute(SUB_USER_ID, form.getSubUserId());
        model.addAttribute(CLIENT_COMP_NAME, form.getClientCompName());
        model.addAttribute(CLIENT_NAME, form.getClientName());
        model.addAttribute(SCHEDULED_DATE, form.getScheduledDate());
        model.addAttribute(SCHEDULED_TIME, form.getScheduledTime());
        model.addAttribute(ROOM_ID, form.getRoomId());
        model.addAttribute(MTG_PLACE, form.getMtgPlace());
        model.addAttribute(MTG_ID, form.getPurpose()); // 何か知らんが、"purpose"にすると値が保持されん
        model.addAttribute(COMMENT, form.getComment());

        
    }

	@Override
	public boolean registerCheck(MeetingRegisterForm form, Model model) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	/*
     * 打ち合わせ情報一覧 初期処理
     * 
     * @param model モデル
     */
    @Override
    public void init1(Model model) throws SQLException {
        try {
            // 主担当、副担当プルダウンの取得
            List<StaffSearchResultDto> staffList = commonRepository.searchStaffList();

            // 会議室プルダウンの取得
            List<MeetingRoomSearchResultDto> roomList = commonRepository.searchRoomList();
            
            // 目的プルダウンの取得
            List<PurposeSearchResultDto> purposeList = commonRepository.searchPurposeList();

            // プルダウン生成
            CommonUtils.makePulldown(model, staffList, new StaffSearchResultDto());
            CommonUtils.makePulldown(model, roomList, new MeetingRoomSearchResultDto());
            CommonUtils.makePulldown(model, purposeList, new PurposeSearchResultDto());
           
        } catch (Exception e) {
            // SQLの例外の場合
            if (e.getCause() instanceof SQLException) {
                throw new SQLException(e);
            } else {
                throw e;
            }
        }
    
		
	}

}
