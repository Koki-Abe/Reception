package jp.reception.soarest.form;

import lombok.Data;

/* 
 * アカウント情報変更 フォーム
 * 
 * @author k_hagiwara
 * @version 1.0
 */
@Data
public class MeetingDeleteForm {
	
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
    private int purpose;
    
    // コメント
    private String comment;
    
    // 最終変更日時
  	private String lastUpdateDate;
}
