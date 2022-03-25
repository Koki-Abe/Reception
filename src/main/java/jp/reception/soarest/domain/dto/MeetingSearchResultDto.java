package jp.reception.soarest.domain.dto;

import lombok.Data;

/*
 * 打ち合わせ情報検索結果格納用DTO
 * 
 * author k.abe
 * version 1.0
 */
@Data
public class MeetingSearchResultDto {
    
    // 打ち合わせID
    private String scheduleId;

    // ユーザーID
    private String staffId;

    // サブユーザーID
    private String subStaffId;

    // ユーザー名
    private String staffName;

    // サブユーザー名
    private String subStaffName;

    // 相手会社名
    private String clientCompName;

    // 相手氏名
    private String clientName;

    // 予定日
    private String scheduledDate;

    // 予定時刻
    private String scheduledTime;

    // 会議室番号
    private String roomId;

    // その他打ち合わせ場所
    private String mtgPlace;

    // 打ち合わせID
    private String mtgId;

    // 打ち合わせ名称
    private String mtgName;

    // コメント
    private String comment;
}
