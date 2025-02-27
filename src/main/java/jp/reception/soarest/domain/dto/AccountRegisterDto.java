package jp.reception.soarest.domain.dto;

import lombok.Data;

/*
 * アカウント情報登録 登録用DTO
 * 
 * author k.hagiwara
 * version 1.0
 */
@Data
public class AccountRegisterDto {
    // ユーザーID
    private String userId;

    // ユーザー名
    private String userName;

    // 部署ID
    private int depId;

    // 権限ID
    private int authId;

    // パスワード
    private String password;
    
    // 最終ログイン日(開始)
    private String loginDateStart;

    // 最終ログイン日(終了)
    private String loginDateEnd;
    
    // 作成日
    private String createddate;
    
    // 作成者
    private String createduserId;
    
    // 作成日
    private String updateddate;
    
    // 作成者
    private String updateduserId;
}
