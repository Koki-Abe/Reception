package jp.reception.soarest.domain.dto;

import lombok.Data;

/*
 * 部署情報 検索結果格納用DTO
 * 
 */
@Data
public class DepartmentSearchResultDto {
	// 部署ID
	private int depId;
	
	// 部署名
	private String depName;
}
