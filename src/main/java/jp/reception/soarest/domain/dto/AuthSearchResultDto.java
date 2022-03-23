package jp.reception.soarest.domain.dto;

import lombok.Data;

/*
 * 権限情報 検索結果格納用DTO
 * 
 * author k.abe
 * version 1.0
 */
@Data
public class AuthSearchResultDto {
	// 権限ID
	private int authId;
	
	// 権限名
	private String authName;
}
