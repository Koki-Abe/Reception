package jp.reception.soarest.repository;

import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;

import jp.reception.soarest.domain.dto.MeetingSearchResultDto;

/*
 * 社内モニター リポジトリインターフェース
 * 
 * @author k.abe
 * @version 1.0
 */
@Mapper
public interface ShanaiMonitorRepository {
	
	// 打ち合わせ情報検索
	Set<MeetingSearchResultDto> searchMtgList(Map<String, String> dateInfo);
	
}
