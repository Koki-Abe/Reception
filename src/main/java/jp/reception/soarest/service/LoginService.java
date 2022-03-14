package jp.reception.soarest.service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.reception.soarest.domain.dto.LoginUserSearchDto;
import jp.reception.soarest.domain.dto.LoginUserSearchResultDto;
import jp.reception.soarest.form.LoginForm;
/*
 * ログイン サービスインターフェース
 * 
 * @author k.abe
 * @version 1.0
 */
@Service
public interface LoginService {

    /*
     * ログイン 入力チェック
     * 
     * @param form アカウント情報一覧 フォームクラス
     * @param result 入力チェック結果
     * @param redirectAttributes リダイレクトアトリビュート
     * @param errorList エラーメッセージ格納リスト
     * @return 入力チェック結果
     */
    public boolean inputCheck(LoginForm form, BindingResult result, 
        RedirectAttributes redirectAttributes, List<String> errorList);

    /*
     * ログインユーザー 検索
     * 
     * @param form アカウント情報一覧 フォームクラス
     * @param redirectAttributes リダイレクトアトリビュート
     * @param errorList エラーメッセージ格納リスト
     * @return ログインユーザー
     */
    public LoginUserSearchResultDto searchLoginUser(LoginForm form, LoginUserSearchDto searchDto, 
        RedirectAttributes redirectAttributes) throws NoSuchAlgorithmException , SQLException;
    
    /*
     * 最終ログイン日時 更新
     * 
     * @param userId ユーザーID
     * @param lastLoginDate 最終ログイン日時
     */
    public int updLastLoginDate(String lastLoginDate, LoginUserSearchResultDto loginUser);
    
    /*
     * ログイン 入力値保持
     * 
     * @param form ログイン用フォームクラス 
     * @param redirectAttributes リダイレクトアトリビュート
     */
    void saveWord(LoginForm form, RedirectAttributes redirectAttributes);

}
