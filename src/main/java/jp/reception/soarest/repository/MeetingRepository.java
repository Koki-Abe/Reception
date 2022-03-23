package jp.reception.soarest.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

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
	
	List<MeetingSearchResultDto> searchMtgList(MeetingSearchDto searchDto);

}
