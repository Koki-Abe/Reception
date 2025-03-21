package jp.reception.soarest.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import jp.reception.soarest.domain.dto.MeetingDeleteDto;
import jp.reception.soarest.domain.dto.MeetingRegisterDto;
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

	/*
	 * 打ち合わせ情報登録 打ち合わせIDの取得
	 * 
	 * @return 最大の打ち合わせID
	 */
	public String getScheduleId() {
		return getScheduleId();
	}
	
	/*
	 * 打ち合わせ情報登録 登録
	 * 
	 * @param AccountRegisterDto 打ち合わせ情報登録用DTO
	 * @return 打ち合わせ登録件数
	 */
	public int registerMtg(MeetingRegisterDto registerDto) {
		return registerMtg(registerDto);
	}
	
	/*
	 * 打ち合わせ情報削除 削除対象の最終アップデート時間を取得
	 * 
	 * @param delDto 打ち合わせ情報削除用DTO
	 * @return 最終アップデート時間
	 */
	public String getDeleteDate(MeetingDeleteDto delDto) {
		return meetingRepository.getDeleteDate(delDto);
	}
	
	/*
	 * 打ち合わせ情報削除 削除対象のデータをチェック
	 * 
	 * @param delDto 打ち合わせ情報削除用DTO
	 * @return 最終アップデート時間
	 */
	public int checkDeleteData(MeetingDeleteDto delDto) {
		return meetingRepository.checkDeleteData(delDto);
	}
	
	/*
	 * 打ち合わせ情報削除 削除
	 * 
	 * @param MeetingDeleteDto 打ち合わせ情報登録用DTO
	 * @return 打ち合わせ登録件数
	 */
	public int deleteMtg(MeetingDeleteDto delDto) {
		return meetingRepository.deleteMtg(delDto);
	}
}
