package jp.reception.soarest.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
    @Size(min=6, max=10, message="{MSG-A01-W-003}")
//    @Pattern(regexp = "^(?=.*[a-z][A-Z][0-9])[A-Z0-9]|[a-z0-9]|[a-zA-Z0-9]{6,20}$", message="この形式でオナシャス")
    // 正規表現あんま自信ない
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9])..*|(?=.*[A-Z])(?=.*[0-9])..*$", message="{MSG-A01-W-002}")
    private String userId;

    // パスワード
    @NotBlank(message = "{MSG-A01-W-004}")
    @Size(min=8, max=20, message="{MSG-A01-W-006}")
    private String password;

}
