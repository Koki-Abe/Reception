package jp.reception.soarest.form;

import lombok.Data;

/* 
 * アカウント情報変更 フォーム
 * 
 * @author k_hagiwara
 * @version 1.0
 */
@Data
public class AccountDeleteForm {
	
    // ユーザーID
    private String userId;

    // ユーザー名
    private String userName;

    // 部署ID
    private int department;

    // 権限ID
    private int role;
    
    // 変更前のデータ
 	private String oldUserId;
 	private String oldUserName;
 	private int oldDepartment;
 	private int oldRole;
 	private String lastUpdateDate;
}
