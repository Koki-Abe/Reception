package jp.reception.soarest.repository;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import jp.reception.soarest.domain.dto.MeetingSearchResultDto;

/*
 * ログイン関連 リポジトリ実装クラス
 * 
 * @author k.abe
 * @version 1.0
 */
public class ShanaiMonitorRepositoryImpl implements ShanaiMonitorRepository{

    @Autowired
    private ShanaiMonitorRepository shanaiMonitorRepository;

    // 打ち合わせ情報検索
    public Set<MeetingSearchResultDto> searchMtgList(Map<String, String> dateInfo) {
        return shanaiMonitorRepository.searchMtgList(dateInfo);
    }

}
