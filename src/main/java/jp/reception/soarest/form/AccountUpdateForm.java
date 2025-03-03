package jp.reception.soarest.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

/* 
 * アカウント情報変更 フォーム
 * 
 * @author k_hagiwara
 * @version 1.0
 */
@Data
public class AccountUpdateForm {
	
	// 変更前ユーザーID
	private String oldUserId;
	
    // ユーザーID
	@NotBlank(message = "{MSG-C02-W-001}")
	@Size(min=6, max=10, message="{MSG-C02-W-003}")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9])..*|(?=.*[A-Z])(?=.*[0-9])..*$", message="{MSG-C02-W-002}")
    private String userId;

    // ユーザー名
	@NotBlank(message = "{MSG-C02-W-004}")
	@Size(min=1, max=50, message="{MSG-C02-W-005}")
    private String userName;

    // 部署ID
	@Max(value = 998, message = "{MSG-C02-W-006}")
    private int department;

    // 権限ID
	@Max(value = 998, message = "{MSG-C02-W-007}")
    private int role;
}
