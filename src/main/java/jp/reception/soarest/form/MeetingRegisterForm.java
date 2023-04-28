package jp.reception.soarest.form;

import lombok.Data;

/* 
 * アカウント情報一覧 フォーム
 * 
 * @author k.abe
 * @version 1.0
 */
@Data
public class MeetingRegisterForm {

    // ユーザーID
    private String userId;

    // ユーザー名
    private String userName;

    // 部署ID
    private int department;
    
    // 部署名
    private int departmentName;
    
    // 権限ID
    private int role;
    
    // 権限名
    private int roleName;
    
    // パスワード
    private int password;
    
}



