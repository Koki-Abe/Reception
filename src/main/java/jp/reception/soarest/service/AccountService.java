package jp.reception.soarest.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import jp.reception.soarest.domain.dto.AccountRegisterDto;
import jp.reception.soarest.domain.dto.AccountSearchDto;
import jp.reception.soarest.domain.dto.AccountSearchResultDto;
import jp.reception.soarest.domain.dto.AccountUpdateDto;
import jp.reception.soarest.domain.dto.LoginUserSearchResultDto;
import jp.reception.soarest.form.AccountRegisterForm;
import jp.reception.soarest.form.AccountSearchForm;
import jp.reception.soarest.form.AccountUpdateForm;

/*
 * アカウント情報一覧 サービスインターフェース
 * 
 * @author k.abe
 * @version 1.0
 */
@Service
public interface AccountService {

    /*
     * アカウント情報一覧 初期処理
     * 
     * @param model モデル
     */
    void init(Model model) throws SQLException;

    /*
     * アカウント情報一覧 検索
     * 
     * @param form アカウント情報一覧 フォームクラス 
     * @param searchDto アカウント情報一覧 検索用DTO
     * @param model モデル
     * @return 検索結果
     */
    List<AccountSearchResultDto> searchAccountList(AccountSearchForm form, 
        AccountSearchDto searchDto, Model model) throws SQLException;
    
    /*
     * アカウント情報変更 変更
     * 
     * @param form アカウント情報変更 フォームクラス 
     * @param AccountUpdateDto アカウント情報変更 変更用DTO
     * @param model モデル
     * @return 検索結果
     */
    void updateAccount(AccountUpdateForm form, 
    		AccountUpdateDto updDto, Model model, String staffID) throws SQLException;
    
    /*
     * アカウント情報登録 登録
     * 
     * @param form アカウント情報登録 フォームクラス 
     * @param AccountRegisterDto アカウント情報登録 登録用DTO
     * @param model モデル
     * @return 検索結果
     */
    void registerAccount(AccountRegisterForm form, 
    		AccountRegisterDto registerDto, Model model, String staffID) throws SQLException;
    
    /*
     * アカウント情報一覧 入力チェック
     * 
     * @param form アカウント情報一覧 フォームクラス 
     * @param model モデル
     * @return 入力チェック結果
     */
    boolean inputCheck(AccountSearchForm form, Model model);
    
    /*
     * アカウント情報変更 入力チェック
     * 
     * @param form アカウント情報変更 フォームクラス 
     * @param result フォームのバリデーションチェック
     * @param model モデル
     * @return 入力チェック結果
     */
    boolean inputCheck(AccountUpdateForm form, BindingResult result, 
    		Model model, List<String> errorList);
    
    /*
     * アカウント情報登録 入力チェック
     * 
     * @param form アカウント情報登録 フォームクラス 
     * @param result フォームのバリデーションチェック
     * @param model モデル
     * @return 入力チェック結果
     */
    boolean inputCheck(AccountRegisterForm form, BindingResult result, 
    		Model model, List<String> errorList);
    
    /*
     * アカウント情報一覧 入力値保持
     * 
     * @param form アカウント情報一覧 フォームクラス 
     * @param model モデル
     */
    void saveWord(AccountSearchForm form, Model model);
    
    /*
     * アカウント情報変更 入力値保持
     * 
     * @param form アカウント情報変更 フォームクラス 
     * @param model モデル
     */
    void saveWord(AccountUpdateForm form, Model model);
    
    /*
     * アカウント情報登録 入力値保持
     * 
     * @param form アカウント情報登録 フォームクラス 
     * @param model モデル
     */
    void saveWord(AccountRegisterForm form, Model model);
    
    /*
     * アカウント情報変更 セッション情報更新
     * 
     * @param form アカウント情報変更 フォームクラス
     * @param loginUser アカウント情報変更 更新用DTO 
     * @return 更新データ
     */
    LoginUserSearchResultDto setNewSessionData(AccountUpdateForm form, LoginUserSearchResultDto loginUser);
    
}
