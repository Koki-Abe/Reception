package jp.reception.soarest.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.thymeleaf.util.StringUtils;

import jp.reception.soarest.common.utils.CommonUtils;
import jp.reception.soarest.domain.dto.MeetingDeleteDto;
import jp.reception.soarest.domain.dto.MeetingRegisterDto;
import jp.reception.soarest.domain.dto.MeetingRoomSearchResultDto;
import jp.reception.soarest.domain.dto.MeetingSearchDto;
import jp.reception.soarest.domain.dto.MeetingSearchResultDto;
import jp.reception.soarest.domain.dto.MeetingUpdateDto;
import jp.reception.soarest.domain.dto.PurposeSearchResultDto;
import jp.reception.soarest.domain.dto.StaffSearchResultDto;
import jp.reception.soarest.enums.CharEnum;
import jp.reception.soarest.enums.MessageEnum;
import jp.reception.soarest.form.MeetingDeleteForm;
import jp.reception.soarest.form.MeetingRegisterForm;
import jp.reception.soarest.form.MeetingSearchForm;
import jp.reception.soarest.form.MeetingUpdateForm;
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

    // スケジュールID
    private final String SCHEDULE_ID = "scheduleId";
    
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
    
    // 会議室リスト
    private final String ROOM_LIST = "roomList";

    // その他打ち合わせ場所
    private final String MTG_PLACE = "mtgPlace";

    // 目的
    private final String MTG_ID = "mtgId";

    // コメント
    private final String COMMENT = "comment";
    
    private final String OTHERS = "その他";
    
    // 変更前アップデート日時
    private String LAST_UPDATE_DATE = "lastUpdateDate";

    /*
     * 打ち合わせ情報一覧 初期処理
     * 
     * @param model モデル
     */
    @Override
    public void init(Model model){
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
        MeetingSearchDto searchDto, Model model) {
        
        // beanの内容を詰め替え
        BeanUtils.copyProperties(form, searchDto);
        // プロパティ名が異なるものは別途設定
        searchDto.setMtgId(form.getPurpose());

        // 検索結果格納用リスト
        List<MeetingSearchResultDto> mtgList = new ArrayList<MeetingSearchResultDto>();
        
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
        
        // 検索結果を返却
        return mtgList;
    }
    
    /*
     * 打ち合わせ情報登録 登録
     * 
     * @param form 打ち合わせ情報登録 フォームクラス 
     * @param registerDto 打ち合わせ情報登録 検索用DTO
     * @param model モデル
     * @return 検索結果
     */
    public int registerMtg(MeetingRegisterForm form, 
    		MeetingRegisterDto registerDto, Model model, String staffID){
    	
    	int registernum = 0;
    	String maxScheduleId = meetingRepository.getScheduleId();
    	int scheduleId = Integer.parseInt(maxScheduleId.substring(3, 7)) + 1;
    	String newScheduleId = maxScheduleId.substring(0, 3) + scheduleId;
    	// beanの内容を詰め替え
        BeanUtils.copyProperties(form, registerDto);
        // プロパティ名が異なるものは別途設定
        registerDto.setScheduleId(newScheduleId);
        registerDto.setMtgId(form.getPurpose());
        registerDto.setRoomId(form.getRoomId()); // プロパティ名は同じだと思われるが詰め替えされない
        
        // その他以外のルームIDの場合はROOM_NAMEをmtgPlaceに設定する
        if(form.getRoomId() != 0 && form.getRoomId() != 9999) {
        	if(form.getRoomList() != null) {
    	    	for(MeetingRoomSearchResultDto room : form.getRoomList()) {
    	    		if(room.getRoomId() == form.getRoomId()) {
    	    			registerDto.setMtgPlace(room.getRoomName());
    	    		}
    	    	}
        	}
        }
        registerDto.setMtgId(form.getPurpose());
        registerDto.setCreatedDate(CommonUtils.getSysdate());
        registerDto.setCreatedUserId(staffID);
        
        // 登録処理を実行
        registernum = meetingRepository.registerMtg(registerDto);

        // 登録件数が0件の場合
        if (0 == registernum) {
            // エラーメッセージを画面に返却
            model.addAttribute(ERR_MSG, MessageEnum.MSG_C01_W_002.getMsg(CharEnum.VALIDATION.getChar()));
        }
        
        return registernum;
    }
    
    /*
     * 打ち合わせ情報削除 削除
     * 
     * @param form 打ち合わせ情報削除 フォームクラス 
     * @param searchDto 打ち合わせ情報削除 削除用DTO
     * @param model モデル
     * @return 検索結果
     */
    public int deletehMtg(MeetingDeleteForm form, MeetingDeleteDto deleteDto, Model model){
    	// beanの内容を詰め替え
        BeanUtils.copyProperties(form, deleteDto);
        
        // プロパティ名が異なるものは別途設定
        deleteDto.setMtgId(form.getPurpose());
    	if(deleteDto.getLastUpdateDate() == "") deleteDto.setLastUpdateDate(null);
    	
        // 削除処理を実行
        int deletenum = meetingRepository.deleteMtg(deleteDto);
        
        return  deletenum;
    }
    
    /*
     * 打ち合わせ情報変更 変更対象の最終アップデート時間を取得
     * @param form 打ち合わせ情報変更 フォームクラス 
     * @param model モデル
     */
    public void getLastDate(MeetingUpdateForm form, Model model){
    	MeetingUpdateDto updDto = new MeetingUpdateDto();
    	BeanUtils.copyProperties(form, updDto);
        // プロパティ名が異なるものは別途設定
    	updDto.setMtgId(form.getPurpose());
    	
    	// 変更対象の最重アップデート日時を取得	
    	String lastDate = meetingRepository.getUpdateDate(updDto);
    	form.setLastUpdateDate(lastDate);
    	model.addAttribute(LAST_UPDATE_DATE, lastDate);
    }
    
    /*
     * 打ち合わせ情報削除 削除対象の最終アップデート時間を取得
     * @param form 打ち合わせ情報削除 フォームクラス 
     * @param model モデル
     */
    public void getLastDate(MeetingDeleteForm form, Model model){
    	MeetingDeleteDto delDto = new MeetingDeleteDto();
    	BeanUtils.copyProperties(form, delDto);
        // プロパティ名が異なるものは別途設定
    	delDto.setMtgId(form.getPurpose());
    	
    	// 削除対象の最重アップデート日時を取得
    	String lastDate = meetingRepository.getDeleteDate(delDto);
    	form.setLastUpdateDate(lastDate);
    	model.addAttribute(LAST_UPDATE_DATE, lastDate);
    }
    
    /*
     * 打ち合わせ情報変更 変更対象のデータをチェック
     * 
     * @param form 打ち合わせ情報変更 フォームクラス 
     * @param model モデル
     */
    public int checkData(MeetingUpdateForm form, Model model){
    	MeetingUpdateDto updDto = new MeetingUpdateDto();
    	BeanUtils.copyProperties(form, updDto);
        // プロパティ名が異なるものは別途設定
    	updDto.setMtgId(form.getPurpose());
    	if(updDto.getLastUpdateDate() == "") updDto.setLastUpdateDate(null);
        
    	// 完全一致するデータを数える
    	int count = meetingRepository.checkUpdateData(updDto);
        return count;
    }
    
    /*
     * 打ち合わせ情報削除 削除対象のデータをチェック
     * 
     * @param form 打ち合わせ情報削除 フォームクラス 
     * @param model モデル
     */
    public int checkData(MeetingDeleteForm form, Model model){
    	MeetingDeleteDto delDto = new MeetingDeleteDto();
    	BeanUtils.copyProperties(form, delDto);
        // プロパティ名が異なるものは別途設定
    	delDto.setMtgId(form.getPurpose());
    	if(delDto.getLastUpdateDate() == "") delDto.setLastUpdateDate(null);
        
    	int count = meetingRepository.checkDeleteData(delDto);
        return count;
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
     * 打ち合わせ情報変更 入力チェック
     * 
     * @param form 打ち合わせ情報変更 フォームクラス 
     * @param result フォームのバリデーションチェック
     * @param model モデル
     * @param errorList エラーリスト
     * @return 入力チェック結果
     */
    @Override
    public boolean inputCheck(MeetingUpdateForm form, BindingResult result, 
    		Model model, List<String> errorList){
    	
    	Boolean is_error = false;
    	
    	// 会議室が「その他」の場合、mtgPlaceがnullの時バリデーションエラー
    	if(form.getRoomList() != null) {
	    	for(MeetingRoomSearchResultDto room : form.getRoomList()) {
	    		if(room.getRoomId() == form.getRoomId()) {
	    			if( room.getRoomName().equals(OTHERS) && StringUtils.isEmpty(form.getMtgPlace()) ) {
	    				model.addAttribute(ERR_MSG, MessageEnum.MSG_D02_W_014.getMsg(CharEnum.VALIDATION.getChar()));
	    				is_error = true;
	    			}
	    		}
	    	}
    	}
    	
    	// 打ち合わせ予定日、予定時刻のバリデーションチェック
    	if(!StringUtils.isEmpty(form.getScheduledTime())) {
    		// 現在の日時の取得
	    	LocalDate nowDate = LocalDate.now();
	    	LocalTime nowTime = LocalTime.now();
	    	
	    	// 予定日、予定時刻の取得
	    	DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
	    	LocalTime time = LocalTime.parse(form.getScheduledTime(), timeFormat);
	    	
	    	// 予定日、予定時刻が現在の日時より過去になっていた場合バリデーションエラー
	    	if(form.getScheduledDate().isBefore(nowDate)){
	    		 model.addAttribute(ERR_MSG, MessageEnum.MSG_D02_W_011.getMsg(CharEnum.VALIDATION.getChar()));
 				is_error = true;
	    	}else if(form.getScheduledDate().equals(nowDate)) {
	    		if(time.isBefore(nowTime)) {
	    			model.addAttribute(ERR_MSG, MessageEnum.MSG_D02_W_011.getMsg(CharEnum.VALIDATION.getChar()));
    				is_error = true;
	    		}
	    	}
    	}
    	
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
            	result.getFieldError();
                errorList.add(error.getDefaultMessage());
            }
            // ※リダイレクトにしないとURLが変わってしまうため
            model.addAttribute(ERR_MSG, errorList);

            is_error = true;
        }
        
        if(is_error == true) return false;
        else					return true;
	}

    /*
     * 打ち合わせ情報登録 入力チェック
     * 
     * @param form 打ち合わせ情報登録 フォームクラス 
     * @param result フォームのバリデーションチェック
     * @param model モデル
     * @param errorList エラーリスト
     * @return 入力チェック結果
     */
    @Override
	public boolean inputCheck(MeetingRegisterForm form, BindingResult result, 
    		Model model, List<String> errorList){
    	
    	Boolean is_error = false;
    	
    	// 会議室が「その他」の場合、mtgPlaceがnullの時バリデーションエラー
    	if(form.getRoomList() != null) {
	    	for(MeetingRoomSearchResultDto room : form.getRoomList()) {
	    		if(room.getRoomId() == form.getRoomId()) {
	    			if( room.getRoomName().equals(OTHERS) && StringUtils.isEmpty(form.getMtgPlace()) ) {
	    				model.addAttribute(ERR_MSG, MessageEnum.MSG_D02_W_014.getMsg(CharEnum.VALIDATION.getChar()));
	    				is_error = true;
	    			}
	    		}
	    	}
    	}
    	
    	// 打ち合わせ予定日、予定時刻のバリデーションチェック
    	if(!StringUtils.isEmpty(form.getScheduledDate()) && !StringUtils.isEmpty(form.getScheduledTime())) {
    		// 現在の日時の取得
	    	LocalDate nowDate = LocalDate.now();
	    	LocalTime nowTime = LocalTime.now();
	    	
	    	// 予定日、予定時刻の取得
	    	DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    	LocalDate date = LocalDate.parse(form.getScheduledDate(), dateFormat);
	    	DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
	    	LocalTime time = LocalTime.parse(form.getScheduledTime(), timeFormat);
	    	
	    	// 予定日、予定時刻が現在の日時より過去になっていた場合バリデーションエラー
	    	if(date.isBefore(nowDate)){
	    		 model.addAttribute(ERR_MSG, MessageEnum.MSG_D02_W_011.getMsg(CharEnum.VALIDATION.getChar()));
 				is_error = true;
	    	}else if(date.equals(nowDate)) {
	    		if(time.isBefore(nowTime)) {
	    			model.addAttribute(ERR_MSG, MessageEnum.MSG_D02_W_011.getMsg(CharEnum.VALIDATION.getChar()));
    				is_error = true;
	    		}
	    	}
    	}
    	
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
            	result.getFieldError();
                errorList.add(error.getDefaultMessage());
            }
            // ※リダイレクトにしないとURLが変わってしまうため
            model.addAttribute(ERR_MSG, errorList);

            is_error = true;
        }
        
        if(is_error == true) return false;
        else					return true;
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
     * 打ち合わせ登録 入力値保持
     * 
     * @param form 打ち合わせ情報登録 フォームクラス 
     * @param model モデル
     */
    @Override
    public void saveWord(MeetingRegisterForm form, Model model) {
    	// 検索値を入力欄に保持
        model.addAttribute(USER_ID, form.getUserId());
        model.addAttribute(SUB_USER_ID, form.getSubUserId());
        model.addAttribute(CLIENT_COMP_NAME, form.getClientCompName());
        model.addAttribute(CLIENT_NAME, form.getClientName());
        model.addAttribute(SCHEDULED_DATE, form.getScheduledDate());
        model.addAttribute(SCHEDULED_TIME, form.getScheduledTime());
        model.addAttribute(ROOM_ID, form.getRoomId());
        model.addAttribute(ROOM_LIST, form.getRoomList());
        model.addAttribute(MTG_PLACE, form.getMtgPlace());
        model.addAttribute(MTG_ID, form.getPurpose()); // 何か知らんが、"purpose"にすると値が保持されん
        model.addAttribute(COMMENT, form.getComment());
    }
    
    /*
     * 打ち合わせ情報変更 入力値保持
     * 
     * @param form 打ち合わせ情報変更 フォームクラス 
     * @param model モデル
     */
    @Override
    public void saveWord(MeetingUpdateForm form, Model model) {
        // 検索値を入力欄に保持
    	model.addAttribute(SCHEDULE_ID, form.getScheduleId());
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
     * 打ち合わせ情報削除 入力値保持
     * 
     * @param form 打ち合わせ情報一覧 フォームクラス 
     * @param model モデル
     */
    @Override
    public void saveWord(MeetingDeleteForm form, Model model) {
        // 検索値を入力欄に保持
    	model.addAttribute(SCHEDULE_ID, form.getScheduleId());
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
}
