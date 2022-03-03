package jp.reception.soarest.domain.dto;

import lombok.Data;

/*
 * アカウント情報一覧 検索用DTO
 * 
 * 
 */
@Data
public class AccountSearchDto {
	// ユーザーID
	private String userId;
		
	// ユーザー名
	private String userName;
	
	// 部署ID
	private int depId;
	
	// 権限ID
	private int authId;
	
	// 最終ログイン日(開始)
	// TODO 正規表現で日付形式チェック
	private String loginDateStart;
	
	// 最終ログイン日(終了)
	// TODO 正規表現で日付形式チェック
	private String loginDateEnd;
}
