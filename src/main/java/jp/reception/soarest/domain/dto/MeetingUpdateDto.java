package jp.reception.soarest.domain.dto;

import lombok.Data;

/*
 * 打ち合わせ情報登録用DTO
 * 
 * author k.hagiwara
 * version 1.0
 */
@Data
public class MeetingUpdateDto {
	// 打ち合わせID
	private String scheduleId;
	
    // ユーザーID
    private String userId;

    // サブユーザーID
    private String subUserId;

    // 相手会社名
    private String clientCompName;

    // 相手氏名
    private String clientName;

    // 予定日
    private String scheduledDate;

    // 予定時刻
    private String scheduledTime;

    // 会議室番号
    private int roomId;

    // その他打ち合わせ場所
    private String mtgPlace;

    // 目的
    private int mtgId;
    
    // コメント
    private String comment;
    
    // 変更日
    private String updatedDate;
    
    // 変更者
    private String updatedUserId;
    
    // 最重アップデート日時
    private String lastUpdateDate;
    
}
