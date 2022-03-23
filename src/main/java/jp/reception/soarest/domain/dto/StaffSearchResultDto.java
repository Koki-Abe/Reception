package jp.reception.soarest.domain.dto;

import lombok.Data;

/*
 * 担当者情報 検索結果格納用DTO
 * 
 * author k.abe
 * version 1.0
 */
@Data
public class StaffSearchResultDto {
	// 担当者ID
	private String staffId;
	
	// 担当者名
	private String staffName;
}
