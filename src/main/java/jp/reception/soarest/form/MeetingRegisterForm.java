package jp.reception.soarest.form;

import java.util.List;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.thymeleaf.util.StringUtils;

import jp.reception.soarest.domain.dto.MeetingRoomSearchResultDto;
import jp.reception.soarest.enums.NumEnum;
import lombok.Data;

/* 
 * アカウント情報一覧 フォーム
 * 
 * @author k.abe
 * @version 1.0
 */
@Data
public class MeetingRegisterForm {
	
	// ユーザーID
	@NotBlank(message = "{MSG-D02-W-001}")
	@Size(min=6, max=10, message="{MSG-D02-W-003}")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[0-9])..*|(?=.*[A-Z])(?=.*[0-9])..*$", message="{MSG-D02-W-002}")
    private String userId;

    // サブユーザーID
    private String subUserId;
	
	@AssertTrue(message="{MSG-D02-W-005}")
    public boolean isSubUserIdSize() {
		if(!StringUtils.isEmpty(subUserId) && (subUserId.length() < 6 || subUserId.length() >10)) {
			return false;
		}
        return true;
    }
	
	@AssertTrue(message="{MSG-D02-W-004}")
    public boolean isSubUserIdPattern() {
		if(!StringUtils.isEmpty(subUserId) && !subUserId.matches("^(?=.*[a-z])(?=.*[0-9])..*|(?=.*[A-Z])(?=.*[0-9])..*$") ) {
			return false;
		}
        return true;
    }

    // 相手会社名
    private String clientCompName;
    
    @AssertTrue(message="{MSG-D02-W-006}")
    public boolean isClientCopNameSize() {
    	if(!StringUtils.isEmpty(clientCompName)  && (clientCompName.length() < 1 || clientCompName.length() >100)) {
			return false;
		}
    	return true;
    }

    // 相手氏名
    @NotBlank(message = "{MSG-D02-W-007}")
	@Size(min=1, max=50, message="{MSG-D02-W-008}")
    private String clientName;

    // 予定日
    @NotBlank(message = "{MSG-D02-W-009}")
    private String scheduledDate;
    
    // 予定時刻
    @NotBlank(message = "{MSG-D02-W-010}")
    private String scheduledTime;

    // 会議室番号
    private int roomId;
    @AssertTrue(message="{MSG-D02-W-012}")
    public boolean isRoomId() {
    	return roomId != NumEnum.PULLDOWN.getNum();
    }
    
    // 会議室名
    private List<MeetingRoomSearchResultDto> roomList;

    // その他打ち合わせ場所
    private String mtgPlace;
    @AssertTrue(message="{MSG-D02-W-013}")
    public boolean isMtgPlaceSize() {
    	if(!StringUtils.isEmpty(clientCompName)  && (clientCompName.length() < 1 || clientCompName.length() >50)) {
			return false;
		}
    	return true;
    }
    
    // 目的
    @Max(value = 998, message="{MSG-D02-W-015}")
    private int purpose;
    
    // コメント
    private String comment;
    
    @AssertTrue(message="{MSG-D02-W-016}")
    public boolean isCommentSize() {
		if(!StringUtils.isEmpty(comment) && (comment.length() < 1 || comment.length() >200)) {
			return false;
		}
        return true;
    }
    
}



