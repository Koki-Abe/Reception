package jp.reception.soarest.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.reception.soarest.domain.dto.MeetingRegisterDto;
import jp.reception.soarest.domain.dto.MeetingSearchDto;
import jp.reception.soarest.domain.dto.MeetingSearchResultDto;

/*
 * 打ち合わせ関連 リポジトリインターフェース
 * 
 * @author k.abe
 * @version 1.0
 */
@Mapper
public interface MeetingRepository {
	
	/*
	 * 打ち合せ情報一覧 検索
	 * 
	 * @param searchDto 打ち合わせ情報一覧 検索用DTO
	 * @return 打ち合わせ情報一覧
	 */
	List<MeetingSearchResultDto> searchMtgList(MeetingSearchDto searchDto);

	/*
	 * 打ち合わせ情報登録 打ち合わせIDの取得
	 * 
	 * @return 最大の打ち合わせID
	 */
	String getScheduleId();
	
	
	/*
	 * 打ち合わせ情報登録 登録
	 * 
	 * @param AccountRegisterDto 打ち合わせ情報登録用DTO
	 * @return 打ち合わせ登録件数
	 */
	int registerMtg(MeetingRegisterDto registerDto);
	
}
