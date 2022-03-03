package jp.reception.soarest.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.reception.soarest.form.LoginForm;


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


		// エラー格納用リスト
		List<String> errorList = new ArrayList<String>();
		
		// 入力チェックに該当する場合
		if (result.hasErrors()) {
			
			for (ObjectError error : result.getAllErrors()) {
				errorList.add(error.getDefaultMessage());
			}
			//model.addAttribute("validationError", errorList);
			// ※リダイレクトにしないとURLが変わってしまうため
			redirectAttributes.addFlashAttribute("validationError", errorList);
//			return "login/login";
			return "redirect:/";
        }
		// TODO DBから取得したパスワードを復号化
		
		
		
		//model.addAttribute("name", "nameです");
		redirectAttributes.addFlashAttribute("name", "nameです");
		return "redirect:/top";
    }
}
