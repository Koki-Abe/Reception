package jp.reception.soarest.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import jp.reception.soarest.domain.dto.MeetingSearchDto;
import jp.reception.soarest.domain.dto.MeetingSearchResultDto;

/*
 * 打ち合わせ関連 リポジトリ実装クラス
 * 
 * @author k.abe
 * @version 1.0
 */
public class MeetingRepositoryImpl implements MeetingRepository{
	
	@Autowired
	private MeetingRepository meetingRepository;
	
	/*
	 * 打ち合わせ情報一覧 検索
	 * 
	 * @param searchDto 打ち合わせ情報一覧 検索用DTO
	 * @author k.abe
	 * @return 検索結果
	 */
	public List<MeetingSearchResultDto> searchMtgList(MeetingSearchDto searchDto) {
		return meetingRepository.searchMtgList(searchDto);
	}


}
