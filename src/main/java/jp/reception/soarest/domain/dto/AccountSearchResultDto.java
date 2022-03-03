package jp.reception.soarest.domain.dto;

import lombok.Data;

/*
 * アカウント情報一覧 検索結果格納用DTO
 * 
 * 
 */
@Data
public class AccountSearchResultDto {
	// 担当者ID
	private String staffId;
		
	// 担当者名
	private String staffName;
	
	// 部署名
	private String depName;
	
	// 権限ID
	private int authId;
	
	// 権限名
	private String authName;
	
	// 最終ログイン日(開始)
	private String loginDateStart;
	
	// 最終ログイン日(終了)
	private String loginDateEnd;
	
	// 検索取得件数
	private int searchCount;
	
	// エラーメッセージ
	private String errMsg;
}
