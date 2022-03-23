package jp.reception.soarest.domain.dto;

import lombok.Data;

/*
 * ログインユーザー情報 検索用DTO
 * 
 * author k.abe
 * version 1.0
 */
@Data
public class LoginUserSearchDto {
	// ユーザーID
	private String userId;
		
	// パスワード
	private String password;
}
