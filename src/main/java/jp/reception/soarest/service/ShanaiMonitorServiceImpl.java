package jp.reception.soarest.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.reception.soarest.common.utils.CommonUtils;
import jp.reception.soarest.domain.dto.MeetingSearchResultDto;
import jp.reception.soarest.repository.ShanaiMonitorRepository;
/*
 * 社内モニター サービス実装クラス
 * 
 * @author k.abe
 * @version 1.0
 */
@Service
public class ShanaiMonitorServiceImpl implements ShanaiMonitorService {

	@Autowired
	ShanaiMonitorRepository repository;
    /*
     * 開始10分前 打ち合わせ情報取得
     * 
     * @param 
     * @param 
     * @param 
     * @param 
     * @return 
     */
    public Set<MeetingSearchResultDto> searchMtgList() {
    	
    	Map<String, String> dateInfo = new LinkedHashMap<>();
    	System.out.println(CommonUtils.getSysDay());
    	System.out.println(CommonUtils.getSysTime());

    	
    	dateInfo.put("scheduledDate", CommonUtils.getSysDay());
    	dateInfo.put("scheduledTime", CommonUtils.getSysTime());
    	
    	Set<MeetingSearchResultDto> mtgList = repository.searchMtgList(dateInfo);
    	
		return mtgList;
    	
    }


}
