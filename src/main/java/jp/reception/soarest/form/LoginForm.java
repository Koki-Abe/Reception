package jp.reception.soarest.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/* 
 * ログイン用フォームクラス 
 * 
 */
@Data
public class LoginForm {
		
	// ユーザーID
	@NotBlank(message="IDを入力してください")
	private String userId;
		
	// パスワード
	@NotBlank(message="パスを入力してください")
	private String password;

}
