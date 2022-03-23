package jp.reception.soarest.form;

import lombok.Data;

/* 
 * アカウント情報一覧 フォーム
 * 
 * @author k.abe
 * @version 1.0
 */
@Data
public class AccountSearchForm {

    // ユーザーID
    private String userId;

    // ユーザー名
    private String userName;

    // 部署ID
    private int department;

    // 権限ID
    private int role;

    // 最終ログイン日(開始)
    private String loginDateStart;

    // 最終ログイン日(終了)
    private String loginDateEnd;
}
