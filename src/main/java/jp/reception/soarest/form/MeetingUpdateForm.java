package jp.reception.soarest.form;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import jp.reception.soarest.domain.dto.MeetingRoomSearchResultDto;
import jp.reception.soarest.validator.CanEmptyPattern;
import jp.reception.soarest.validator.CanEmptySize;
import jp.reception.soarest.validator.NotBlankPullDown;
import lombok.Data;

/*
 * 打ち合わせ情報一覧 更新用フォーム
 *
 * @author k.abe
 * @version 1.0
 */
@Data
public class MeetingUpdateForm {

	// スケジュールID
	private String scheduleId;

	// ユーザーID
	@NotBlank(message = "{MSG-D02-W-001}")
	@Size(min=6, max=10, message="{MSG-D02-W-003}")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9])..*|(?=.*[A-Z])(?=.*[0-9])..*$", message="{MSG-D02-W-002}")
	private String userId;

	// サブユーザーID
	@CanEmptySize(min = 6, max = 10, message = "{MSG-D02-W-005}") // 独自アノテーション Empty許可の文字数制限
	@CanEmptyPattern(regexp = "^(?=.*[a-z])(?=.*[0-9])..*|(?=.*[A-Z])(?=.*[0-9])..*$", message="{MSG-D02-W-004}")// 独自アノテーション Empty許可の文字列パターン制限
    private String subUserId;

	// 相手会社名
	@CanEmptySize(min = 1, max = 100, message = "{MSG-D02-W-006}") // 独自アノテーション Empty許可の文字数制限
    private String clientCompName;

	// 相手氏名
	@NotBlank(message = "{MSG-D02-W-007}")
	@Size(min=1, max=50, message="{MSG-D02-W-008}")
	private String clientName;

	// 予定日
	@NotNull(message = "{MSG-D02-W-009}")
	private String scheduledDate;

	// 予定時刻
	@NotBlank(message = "{MSG-D02-W-010}")
	private String scheduledTime;

	// 会議室番号
	@NotBlankPullDown(message="{MSG-D02-W-012}") // 独自アノテーション プルダウンの選択を確認
    private int roomId;

	// 会議室名
	private List<MeetingRoomSearchResultDto> roomList;

	// その他打ち合わせ場所
	@CanEmptySize(min = 1, max = 50, message = "{MSG-D02-W-013}") // 独自アノテーション Empty許可の文字数制限
    private String mtgPlace;

	// 目的
	@Max(value = 998, message="{MSG-D02-W-015}")
	private int purpose;

	// コメント
	@CanEmptySize(min = 1, max = 200, message = "{MSG-D02-W-016}") // 独自アノテーション Empty許可の文字数制限
    private String comment;

	// 変更前データ
	private String oldUserId;
    private String oldSubUserId;
    private String oldClientCompName;
    private String oldClientName;
    private String oldScheduledDate;
    private String oldScheduledTime;
    private int oldRoomId;
    private String oldMtgPlace;
    private int oldMtgId;
    private String oldComment;

	// 最重アップデート日時
	private String lastUpdateDate;
}
