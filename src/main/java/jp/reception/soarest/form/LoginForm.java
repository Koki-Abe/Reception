package jp.reception.soarest.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/* 
 * ログイン用フォームクラス 
 * 
 * @author k.abe
 * @version 1.0
 */
@Data
public class LoginForm {

    // ユーザーID
    @NotBlank(message = "{MSG-A01-W-001}")
    private String userId;

    // パスワード
    @NotBlank(message = "{MSG-A01-W-004}")
    private String password;

}
