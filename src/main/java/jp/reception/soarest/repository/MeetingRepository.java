package jp.reception.soarest.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.reception.soarest.domain.dto.MeetingDeleteDto;
import jp.reception.soarest.domain.dto.MeetingRegisterDto;
import jp.reception.soarest.domain.dto.MeetingSearchDto;
import jp.reception.soarest.domain.dto.MeetingSearchResultDto;
import jp.reception.soarest.domain.dto.MeetingUpdateDto;

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
	
	/*
	 * 打ち合わせ情報変更 変更対象の最終アップデート時間を取得
	 * 
	 * @param updDto 打ち合わせ情報変更用DTO
	 * @return 最終アップデート時間
	 */
	String getUpdateDate(MeetingUpdateDto updDto);
	
	/*
	 * 打ち合わせ情報削除 削除対象の最終アップデート時間を取得
	 * 
	 * @param delDto 打ち合わせ情報削除用DTO
	 * @return 最終アップデート時間
	 */
	String getDeleteDate(MeetingDeleteDto delDto);
	
	/*
	 * 打ち合わせ情報変更 変更対象のデータをチェック
	 * 
	 * @param updDto 打ち合わせ情報変更用DTO
	 * @return 最終アップデート時間
	 */
	int checkUpdateData(MeetingUpdateDto updDto);
	
	/*
	 * 打ち合わせ情報削除 削除対象のデータをチェック
	 * 
	 * @param delDto 打ち合わせ情報削除用DTO
	 * @return 最終アップデート時間
	 */
	int checkDeleteData(MeetingDeleteDto delDto);
	
	/*
	 * 打ち合わせ情報削除 削除
	 * 
	 * @param MeetingDeleteDto 打ち合わせ情報登録用DTO
	 * @return 打ち合わせ登録件数
	 */
	int deleteMtg(MeetingDeleteDto delDto);
}
