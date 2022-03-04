package jp.reception.soarest.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.reception.soarest.form.LoginForm;

@Service
public class LoginServiceImpl implements LoginService {

	/*
	 * ログイン 入力チェック
	 * 
	 * @param form ログイン用フォームクラス 
	 * @param model モデル
	 * @param errorList エラーメッセージ格納リスト
	 */
	public boolean inputCheck(LoginForm form, BindingResult result, 
        RedirectAttributes redirectAttributes, List<String> errorList) {
		
		// 入力チェックに該当する場合
		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				errorList.add(error.getDefaultMessage());
			}
			//model.addAttribute("validationError", errorList);
			// ※リダイレクトにしないとURLが変わってしまうため
			redirectAttributes.addFlashAttribute("validationError", errorList);
			
			return false;
        }
        return true;
	}
	/*
	 * ログイン 入力値保持
	 * 
	 * @param form ログイン用フォームクラス 
	 * @param model モデル
	 */
	public void saveWord(LoginForm form, RedirectAttributes redirectAttributes) {
		

        // 入力値を保持(※addFlashAttributeにしないと、何故かURLにパラメータが付与される)
		redirectAttributes.addFlashAttribute("userId", form.getUserId());
		// ※パスワードは保存しない
//		redirectAttributes.addFlashAttribute("password", form.getPassword());
		
	}

}
