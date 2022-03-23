package jp.reception.soarest.domain.dto;

import lombok.Data;

/*
 * 打ち合わせ目的情報 検索結果格納用DTO
 * 
 * author k.abe
 * version 1.0
 */
@Data
public class PurposeSearchResultDto {
	// 打ち合わせ目的ID
	private int mtgId;
	
	// 打ち合わせ名
	private String mtgName;
}
