package jp.reception.soarest.domain.dto;

import java.io.Serializable;

import lombok.Data;

/*
 * ログインユーザー 検索結果格納用DTO
 * 
 * @author k.abe
 * @version 1.0
 */
@Data
public class LoginUserSearchResultDto implements Serializable {
	// 担当者ID
	private String staffId;
		
	// 担当者名
	private String staffName;
	
	// 部署名
	private String depName;
	
	// 権限名
	private String authName;
	
	// 最終ログイン日
	private String lastLoginDate;

	// 検索取得件数
	private int searchCount;
	
	// エラーメッセージ
	private String errMsg;
}
