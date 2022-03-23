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
    @Size(min=6, max=10, message="ユーザーIDは、6字以上10字以内で入力してください。")
//    @Pattern(regexp = "^(?=.*[a-z][A-Z][0-9])[A-Z0-9]|[a-z0-9]|[a-zA-Z0-9]{6,20}$", message="この形式でオナシャス")
    // 正規表現あんま自信ない
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9])..*|(?=.*[A-Z])(?=.*[0-9])..*$", message="この形式でオナシャス")
    private String userId;

    // パスワード
    @NotBlank(message = "{MSG-A01-W-004}")
    @Size(min=8, max=20, message="パスワードは、8字以上20字以内で入力してください。")
    private String password;

}
