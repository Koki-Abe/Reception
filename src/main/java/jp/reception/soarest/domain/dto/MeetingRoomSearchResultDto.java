package jp.reception.soarest.domain.dto;

import lombok.Data;

/*
 * 会議室情報 検索結果格納用DTO
 * 
 * author k.abe
 * version 1.0
 */
@Data
public class MeetingRoomSearchResultDto {
	// 会議室No
	private int roomId;
	
	// 会議室名
	private String roomName;
}
