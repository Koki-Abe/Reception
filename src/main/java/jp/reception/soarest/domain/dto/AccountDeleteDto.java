package jp.reception.soarest.domain.dto;

import lombok.Data;

/*
 * アカウント情報変更 変更用DTO
 * 
 * author k.hagiwara
 * version 1.0
 */
@Data
public class AccountDeleteDto {
    // ユーザーID
    private String userId;

    // ユーザー名
    private String userName;

    // 部署ID
    private int depId;

    // 権限ID
    private int authId;
    
    // 変更前ユーザーID
    private String oldUserId;
	private String oldUserName;
	private int oldDepId;
	private int oldAuthId;
	private String lastUpdateDate;
}
