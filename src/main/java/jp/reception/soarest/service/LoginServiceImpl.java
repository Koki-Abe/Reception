package jp.reception.soarest.service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.reception.soarest.common.utils.CommonUtils;
import jp.reception.soarest.domain.dto.LoginUserSearchDto;
import jp.reception.soarest.domain.dto.LoginUserSearchResultDto;
import jp.reception.soarest.form.LoginForm;
import jp.reception.soarest.repository.LoginRepository;

/*
 * ログイン サービス実装クラス
 * 
 * @author k.abe
 * @version 1.0
 */
@Service
public class LoginServiceImpl implements LoginService {
    
    // アカウント関連 リポジトリ
    @Autowired
    private LoginRepository loginRepository;
    
    // エラーリスト
    private final String ERROR_LIST = "errorList";
    
    // ユーザーID
    private final String USER_ID = "userId";

    // 最終ログイン日時
    private final String LAST_LOGIN_DATE = "lastLoginDate";

    // 最終更新日時
    private final String UPDATED_DATE = "updatedDate";

    /*
     * ログイン 入力チェック
     * 
     * @param form ログイン用フォームクラス 
     * @param model モデル
     * @param errorList エラーメッセージ格納リスト
     */
    @Override
    public boolean inputCheck(LoginForm form, BindingResult result, 
        RedirectAttributes redirectAttributes, List<String> errorList) {

        // 入力チェックに該当する場合
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                result.getFieldError();
                errorList.add(error.getDefaultMessage());
            }
            //model.addAttribute("validationError", errorList);
            // ※リダイレクトにしないとURLが変わってしまうため
            redirectAttributes.addFlashAttribute(ERROR_LIST, errorList);

            return false;
        }
        return true;
    }
    /*
     * ログイン ユーザー検索
     * 
     * @param form ログイン用フォームクラス 
     * @param searchDto ログインユーザー情報 検索用DTO
     * @param redirectAttributes リダイレクトアトリビュート
     * @return loginUser ログインユーザー情報
     */
    @Override
    public LoginUserSearchResultDto searchLoginUser(LoginForm form, LoginUserSearchDto searchDto, 
        RedirectAttributes redirectAttributes) throws NoSuchAlgorithmException, SQLException {

        // 検索結果格納用DTO
        LoginUserSearchResultDto loginUser = new LoginUserSearchResultDto();
        try {
            // パスワードをハッシュ化し、検索条件に設定
            String pass = CommonUtils.makeHash(form.getPassword());
            searchDto.setUserId(form.getUserId());
            searchDto.setPassword(pass);

            // 検索処理を実行
            loginUser = loginRepository.searchLoginUser(searchDto);

        // 例外処理
        } catch (Exception e) {
           // ハッシュ生成時の例外の場合
           if (e.getCause() instanceof NoSuchAlgorithmException) {
               throw new NoSuchAlgorithmException(e);
           // SQL例外の場合
           } else if (e.getCause() instanceof SQLSyntaxErrorException) {
               throw new SQLException(e);
           } else {
               throw e;
           }
        }

        // ログインユーザーの情報を返却
        return loginUser;
    }

    /*
     * ログイン 最終ログイン日時更新
     * 
     * @param lastLoginDate 最終ログイン日時
     * @param loginUser ログインユーザー 検索結果格納用DTO
     * @return upd 更新件数
     */
    @Override
    @Transactional(rollbackFor=Exception.class) // 検査例外でもロールバックしてくれる
    public int updLastLoginDate(String lastLoginDate, LoginUserSearchResultDto loginUser) {
        // 更新用Mapの設定
        Map<String, String> loginInfo = new LinkedHashMap<String, String>();
        String userId = loginUser.getStaffId();
        loginInfo.put(USER_ID, userId);
        loginInfo.put(LAST_LOGIN_DATE, lastLoginDate);
        loginInfo.put(UPDATED_DATE, CommonUtils.getSysdate());

        // 最終ログイン日時更新
        return loginRepository.updLastLoginDate(loginInfo);
    }

    /*
     * ログイン 入力値保持
     * 
     * @param form ログイン用フォームクラス 
     * @param redirectAttributes リダイレクトアトリビュート
     */
    @Override
    public void saveWord(LoginForm form, RedirectAttributes redirectAttributes) {

        // 入力値を保持(※addFlashAttributeにしないと、何故かURLにパラメータが付与される)
        redirectAttributes.addFlashAttribute(USER_ID, form.getUserId());
        // ※パスワードは保存しない
//        redirectAttributes.addFlashAttribute("password", form.getPassword());
    }

}
