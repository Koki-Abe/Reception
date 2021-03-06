package jp.reception.soarest.domain.dto;

import lombok.Data;

/*
 * 打ち合わせ情報検索用DTO
 * 
 * author k.abe
 * version 1.0
 */
@Data
public class MeetingSearchDto {
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

}
