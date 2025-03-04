package jp.reception.soarest.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import jp.reception.soarest.common.utils.CommonUtils;
import jp.reception.soarest.domain.dto.AccountRegisterDto;
import jp.reception.soarest.domain.dto.AccountSearchDto;
import jp.reception.soarest.domain.dto.AccountSearchResultDto;
import jp.reception.soarest.domain.dto.AccountUpdateDto;
import jp.reception.soarest.domain.dto.AuthSearchResultDto;
import jp.reception.soarest.domain.dto.DepartmentSearchResultDto;
import jp.reception.soarest.domain.dto.LoginUserSearchResultDto;
import jp.reception.soarest.enums.CharEnum;
import jp.reception.soarest.enums.MessageEnum;
import jp.reception.soarest.form.AccountRegisterForm;
import jp.reception.soarest.form.AccountSearchForm;
import jp.reception.soarest.form.AccountUpdateForm;
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

    // 変更前ユーザーID
    private final String OLD_USER_ID = "oldUserId";
    
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
    
 // ログイン日(終了)
    private final String PASSWORD = "password";

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
     * アカウント情報変更 変更
     * 
     * @param form アカウント情報変更 フォームクラス 
     * @param updDto アカウント情報変更 検索用DTO
     * @param model モデル
     * @return 検索結果
     */
    @Override
    public void updateAccount(AccountUpdateForm form, 
    		AccountUpdateDto updDto, Model model, String staffID) throws SQLException {
        
        try {
        	// beanの内容を詰め替え
            BeanUtils.copyProperties(form, updDto);
            // プロパティ名が異なるものは別途設定
            updDto.setDepId(form.getDepartment());
            updDto.setAuthId(form.getRole());
            updDto.setUpdatedDate(CommonUtils.getSysdate());
            updDto.setUpdatedUserId(staffID);
            
            // 登録処理を実行
            int updatenum = accountRepository.updateAccount(updDto);
            
            // 登録件数が0件の場合
            if (0 == updatenum) {
                // エラーメッセージを画面に返却
                model.addAttribute(ERR_MSG, MessageEnum.MSG_C01_W_003.getMsg(CharEnum.VALIDATION.getChar()));
            }
            
        } catch (Exception e) {
        	// ハッシュ生成時の例外の場合
            if (e.getCause() instanceof SQLException) {
                throw new SQLException(e);
            } else {
                throw e;
            }
        }
    }
    
    /*
     * アカウント情報登録 登録
     * 
     * @param form アカウント情報登録 フォームクラス 
     * @param registerDto アカウント情報登録 検索用DTO
     * @param model モデル
     * @return 検索結果
     */
    @Override
    public void registerAccount(AccountRegisterForm form, 
    		AccountRegisterDto registerDto, Model model, String staffID) throws SQLException {
        
        try {
        	// beanの内容を詰め替え
            BeanUtils.copyProperties(form, registerDto);
            // プロパティ名が異なるものは別途設定
            registerDto.setDepId(form.getDepartment());
            registerDto.setAuthId(form.getRole());
            registerDto.setCreatedUserId(staffID);
            String pass = CommonUtils.makeHash(form.getPassword());
            registerDto.setPassword(pass);
            registerDto.setCreatedDate(CommonUtils.getSysdate());
            registerDto.setCreatedUserId(staffID);
            
            // 登録処理を実行
            int registernum = accountRepository.registerAccount(registerDto);

            // 登録件数が0件の場合
            if (0 == registernum) {
                // エラーメッセージを画面に返却
                model.addAttribute(ERR_MSG, MessageEnum.MSG_C01_W_002.getMsg(CharEnum.VALIDATION.getChar()));
            }
            
        } catch (Exception e) {
        	// ハッシュ生成時の例外の場合
            if (e.getCause() instanceof SQLException) {
                throw new SQLException(e);
            }
        }
    }
    
    
    /*
     * アカウント情報一覧 入力チェック
     * 
     * @param form アカウント情報一覧 フォームクラス 
     * @param model モデル
     */
    @Override
    public boolean inputCheck(AccountSearchForm form, Model model) {
        // ログイン日時(開始)、ログイン日時(終了)がNULLまたは空文字の場合
        if (null != form.getLoginDateStart() && "" != form.getLoginDateStart()
                && null != form.getLoginDateEnd() && "" != form.getLoginDateEnd()) {
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
     * アカウント情報変更 入力チェック
     * 
     * @param form アカウント情報変更 フォームクラス 
     * @param result フォームのバリデーションチェック
     * @param model モデル
     * @return 入力チェック結果
     */
    @Override
    public boolean inputCheck(AccountUpdateForm form, BindingResult result, 
    		Model model, List<String> errorList) {
    	
    	// 初期状態のt機入力チェックはスルー
    	if(form.getUserId() == null && form.getUserName() == null && form.getDepartment() == 0 &&
    			form.getRole() == 0){
    		return false;
    	}
    	
    	// 入力チェックに該当する場合
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
            	result.getFieldError();
                errorList.add(error.getDefaultMessage());
            }
            // ※リダイレクトにしないとURLが変わってしまうため
            model.addAttribute(ERR_MSG, errorList);

            return false;
        }
        return true;
    }
    
    /*
     * アカウント情報登録 入力チェック
     * 
     * @param form アカウント情報登録 フォームクラス 
     * @param result フォームのバリデーションチェック
     * @param model モデル
     * @return 入力チェック結果
     */
    @Override
    public boolean inputCheck(AccountRegisterForm form, BindingResult result, 
    		Model model, List<String> errorList) {
    	
    	// 初期状態のt機入力チェックはスルー
    	if(form.getUserId() == null && form.getUserName() == null && form.getDepartment() == 0 &&
    			form.getRole() == 0 && form.getPassword() == null){
    		return false;
    	}
    	
    	// 入力チェックに該当する場合
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
            	result.getFieldError();
                errorList.add(error.getDefaultMessage());
            }
            // ※リダイレクトにしないとURLが変わってしまうため
            model.addAttribute(ERR_MSG, errorList);

            return false;
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
    
    /*
     * アカウント情報変更 入力値保持
     * 
     * @param form アカウント情報変更 フォームクラス 
     * @param model モデル
     */
    @Override
    public void saveWord(AccountUpdateForm form, Model model) {
    	// 検索値を入力欄に保持
    	model.addAttribute(OLD_USER_ID, form.getOldUserId());
        model.addAttribute(USER_ID, form.getUserId());
        model.addAttribute(USER_NAME, form.getUserName());
        model.addAttribute(DEPARTMENT, form.getDepartment());
        model.addAttribute(ROLE, form.getRole());
    }
    
    /*
     * アカウント情報登録 入力値保持
     * 
     * @param form アカウント情報登録 フォームクラス 
     * @param model モデル
     */
    @Override
    public void saveWord(AccountRegisterForm form, Model model) {
    	// 検索値を入力欄に保持
        model.addAttribute(USER_ID, form.getUserId());
        model.addAttribute(USER_NAME, form.getUserName());
        model.addAttribute(DEPARTMENT, form.getDepartment());
        model.addAttribute(ROLE, form.getRole());
        model.addAttribute(PASSWORD, form.getPassword());
    }
    
    /*
     * アカウント情報変更 セッション情報更新
     * 
     * @param form アカウント情報変更 フォームクラス
     * @param loginUser アカウント情報変更 更新用DTO 
     * @return 更新データ
     */
    public LoginUserSearchResultDto setNewSessionData(AccountUpdateForm form, LoginUserSearchResultDto loginUser) {
    	loginUser.setStaffId(form.getUserId());
        loginUser.setStaffName(form.getUserName());
        loginUser.setAuthId(form.getRole());
        // 権限プルダウンの取得
        List<AuthSearchResultDto> authList = commonRepository.searchAuthList();
        for(AuthSearchResultDto d : authList) {
        	if(d.getAuthId() == form.getRole()) loginUser.setAuthName(d.getAuthName());
        }
    	return loginUser;
    }
}
