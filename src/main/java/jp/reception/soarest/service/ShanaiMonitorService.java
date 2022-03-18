package jp.reception.soarest.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import jp.reception.soarest.domain.dto.MeetingSearchResultDto;
/*
 * 社内モニター サービスインターフェース
 * 
 * @author k.abe
 * @version 1.0
 */
@Service
public interface ShanaiMonitorService {

    /*
     * 開始10分前 打ち合わせ情報取得
     * 
     * @param 
     * @param 
     * @param 
     * @param 
     * @return 
     */
    public Set<MeetingSearchResultDto> searchMtgList();


}
