package jp.reception.soarest.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.reception.soarest.form.LoginForm;
import jp.reception.soarest.service.LoginService;


/*
 * ログインコントローラ
 * 
 * 
 */
 @Controller
public class LoginController {

	  @Autowired
	  HttpSession session; 

	  @Autowired
	  HttpServletRequest request;
	  
	  @Autowired
	  // アカウント関連サービスクラス
	  LoginService loginService;
	  

	 /*
	  * ログインチェック
	  * 
	  * 
	  */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@Validated LoginForm form, BindingResult result, 
    		RedirectAttributes redirectAttributes) {

		/* セッションの動作確認 */
//		System.out.println(request.getSession(false));
//		session.invalidate();
//		System.out.println(form.getUserId());
//		System.out.println(form.getPassword());
//		session = request.getSession();
//		System.out.println(session);
//		System.out.println("セッションの開始：" + request.getSession(false));
//		System.out.println(session);
		
	
		// 入力値の保持
		loginService.saveWord(form, redirectAttributes);
		
		

		// エラー格納用リスト
		List<String> errorList = new ArrayList<String>();
		
		// 入力チェック
		if(!loginService.inputCheck(form, result, redirectAttributes, errorList)) {
		    return "redirect:/";
		}
		// パスワードの暗号化
//		String password = passwordEncoder.encode(form.getPassword());
//		System.out.println(password);
		
		// TODO DBから取得したパスワードを復号化

		return "redirect:/top";
    }
}
