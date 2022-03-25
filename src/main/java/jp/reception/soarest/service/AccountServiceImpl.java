package jp.reception.soarest.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.h2.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jp.reception.soarest.common.utils.CommonUtils;
import jp.reception.soarest.domain.dto.AccountSearchDto;
import jp.reception.soarest.domain.dto.AccountSearchResultDto;
import jp.reception.soarest.domain.dto.AuthSearchResultDto;
import jp.reception.soarest.domain.dto.DepartmentSearchResultDto;
import jp.reception.soarest.enums.CharEnum;
import jp.reception.soarest.enums.MessageEnum;
import jp.reception.soarest.form.AccountSearchForm;
import jp.reception.soarest.repository.AccountRepository;
import jp.reception.soarest.repository.CommonRepository;


@Service
/*
 * アカウント情報一覧 サービス実装クラス
 * 
 * @author k.abe
 * @version 1.0
 */
public class AccountServiceImpl implements AccountService {

    // アカウント関連 リポジトリ
    @Autowired
    private AccountRepository accountRepository;

    // 共通 リポジトリ
    @Autowired
    CommonRepository commonRepository;

    // エラーメッセージ
    private final String ERR_MSG = "errMsg";

    // 検索結果件数
    private final String SEARCH_COUNT = "searchCount";

    // アカウントリスト
    private final String ACC_LIST = "accList";

    // ユーザーID
    private final String USER_ID = "userId";

    // ユーザー名
    private final String USER_NAME = "userName";

    // 部署
    private final String DEPARTMENT = "department";

    // ロール
    private final String ROLE = "role";

    // ログイン日(開始)
    private final String LOGIN_DATE_START = "loginDateStart";

    // ログイン日(終了)
    private final String LOGIN_DATE_END = "loginDateEnd";

    /*
     * アカウント情報一覧 初期処理
     * 
     * @param model モデル
     */
    @Override
    public void init(Model model) throws SQLException {
        try {
            // 部署プルダウンの取得
            List<DepartmentSearchResultDto> depList = commonRepository.searchDepList();

            // 権限プルダウンの取得
            List<AuthSearchResultDto> authList = commonRepository.searchAuthList();

            // プルダウン生成
            CommonUtils.makePulldown(model, depList, new DepartmentSearchResultDto());
            CommonUtils.makePulldown(model, authList, new AuthSearchResultDto());
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
     * アカウント情報一覧 検索
     * 
     * @param form アカウント情報一覧 フォームクラス 
     * @param searchDto アカウント情報一覧 検索用DTO
     * @param model モデル
     * @return accList 検索結果
     */
    @Override
    public List<AccountSearchResultDto> searchAccountList(AccountSearchForm form, 
            AccountSearchDto searchDto, Model model) throws SQLException {
        
        // beanの内容を詰め替え
        BeanUtils.copyProperties(form, searchDto);
        // プロパティ名が異なるものは別途設定
        searchDto.setDepId(form.getDepartment());
        searchDto.setAuthId(form.getRole());

        // 検索結果格納用リスト
        List<AccountSearchResultDto> accList = new ArrayList<AccountSearchResultDto>();
        try {
            // 検索処理を実行
            accList = accountRepository.searchAccountList(searchDto);

            // 検索結果が0件の場合
            if (0 == accList.size()) {
                // エラーメッセージを画面に返却
                model.addAttribute(ERR_MSG, MessageEnum.MSG_C01_W_002.getMsg(CharEnum.VALIDATION.getChar()));
            } else {
                // 検索結果を格納
                model.addAttribute(ACC_LIST, accList);
                // 検索結果件数を設定
                model.addAttribute(SEARCH_COUNT, accList.size());
            }
        } catch (Exception e) {
            if (e.getCause() instanceof SQLException) {
                throw new SQLException(e);
            } else {
                throw e;
            }
        }
        // 検索結果を返却
        return accList;
    }

    
    /*
     * アカウント情報一覧 入力チェック
     * 
     * @param form アカウント情報一覧 フォームクラス 
     * @param model モデル
     */
    @Override
    public boolean inputCheck(AccountSearchForm form, Model model) {
        // ログイン日時(開始)、ログイン日時(終了)がNULLまたは空文字でない場合
        if (StringUtils.isNullOrEmpty(form.getLoginDateStart()) 
                && StringUtils.isNullOrEmpty(form.getLoginDateEnd())) {
            // 日付の相関チェック
            String start = form.getLoginDateStart();
            String end = form.getLoginDateEnd();

            // 開始日が終了日より未来日の場合
            if(start.compareTo(end) > 0) {
                model.addAttribute(ERR_MSG, MessageEnum.MSG_C01_W_001.getMsg(CharEnum.VALIDATION.getChar()));
                return false;
            }
        }
        return true;
    }

    /*
     * アカウント情報一覧 入力値保持
     * 
     * @param form アカウント情報一覧 フォームクラス 
     * @param model モデル
     */
    @Override
    public void saveWord(AccountSearchForm form, Model model) {
        // 検索値を入力欄に保持
        model.addAttribute(USER_ID, form.getUserId());
        model.addAttribute(USER_NAME, form.getUserName());
        model.addAttribute(DEPARTMENT, form.getDepartment());
        model.addAttribute(ROLE, form.getRole());
        model.addAttribute(LOGIN_DATE_START, form.getLoginDateStart());
        model.addAttribute(LOGIN_DATE_END, form.getLoginDateEnd());
    }

}
