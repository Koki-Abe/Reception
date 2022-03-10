package jp.reception.soarest.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.reception.soarest.common.utils.CommonUtils;
import jp.reception.soarest.domain.dto.LoginUserSearchDto;
import jp.reception.soarest.domain.dto.LoginUserSearchResultDto;
import jp.reception.soarest.form.LoginForm;
import jp.reception.soarest.repository.LoginRepository;

@Service
public class LoginServiceImpl implements LoginService {
    
    // アカウント関連 リポジトリ
    @Autowired
    private LoginRepository loginRepository;

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
            redirectAttributes.addFlashAttribute("errorList", errorList);

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
        RedirectAttributes redirectAttributes) throws NoSuchAlgorithmException {

        // パスワードをハッシュ化し、検索条件に設定
        String pass = CommonUtils.makeHash(form.getPassword());
        searchDto.setUserId(form.getUserId());
        searchDto.setPassword(pass);

        // 検索処理を実行
        LoginUserSearchResultDto loginUser = loginRepository.searchLoginUser(searchDto);
        
        // ログインユーザーの情報を返却
        return loginUser;
    }
    
    /*
     * ログイン 入力値保持
     * 
     * @param form ログイン用フォームクラス 
     * @param model モデル
     */
    @Override
    public void saveWord(LoginForm form, RedirectAttributes redirectAttributes) {

        // 入力値を保持(※addFlashAttributeにしないと、何故かURLにパラメータが付与される)
        redirectAttributes.addFlashAttribute("userId", form.getUserId());
        // ※パスワードは保存しない
//        redirectAttributes.addFlashAttribute("password", form.getPassword());
    }
}
