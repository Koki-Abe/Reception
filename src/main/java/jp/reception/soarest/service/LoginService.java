package jp.reception.soarest.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.reception.soarest.form.LoginForm;

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
	 * ログイン 入力値保持
	 * 
	 * @param form ログイン用フォームクラス 
	 * @param model モデル
	 */
	void saveWord(LoginForm form, RedirectAttributes redirectAttributes);

}
