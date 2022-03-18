package jp.reception.soarest.domain.dto;

import lombok.Data;

/*
 * 社内モニター 打ち合わせ情報検索用DTO
 * 
 * 
 */
@Data
public class MeetingSearchDto {
	// ユーザーID
	private String staffId;
		
	// サブユーザーID
	private String subStaffId;
	
	// 相手会社名
	private String clientCompName;
	
	// 相手氏名
	private String clientName;

}
