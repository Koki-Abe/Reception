package jp.reception.soarest.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.reception.soarest.domain.dto.AccountDeleteDto;
import jp.reception.soarest.domain.dto.AccountRegisterDto;
import jp.reception.soarest.domain.dto.AccountSearchDto;
import jp.reception.soarest.domain.dto.AccountSearchResultDto;
import jp.reception.soarest.domain.dto.AccountUpdateDto;

/*
 * アカウント関連 リポジトリインターフェース
 * 
 * @author k.abe
 * @version 1.0
 */
@Mapper
public interface AccountRepository {
	/*
	 * アカウント情報一覧 検索
	 * 
	 * @param searchDto アカウント情報一覧 検索用DTO
	 * @return アカウント情報一覧
	 */
	List<AccountSearchResultDto> searchAccountList(AccountSearchDto searchDto);
	
	/*
	 * アカウント情報変更 変更
	 * 
	 * @param updDto アカウント情報変更用DTO
	 * @return アカウント変更件数
	 */
	int updateAccount(AccountUpdateDto updDto);
	
	/*
	 * アカウント情報変更 変更対象の最終アップデート時間を取得
	 * 
	 * @param upDto アカウント情報変更用DTO
	 * @return 最終アップデート時間
	 */
	String getUpdatedDate(AccountUpdateDto upDto);
	
	/*
	 * アカウント情報変更 変更対象のデータをチェック
	 * 
	 * @param upDto アカウント情報変更用DTO
	 * @return 最終アップデート時間
	 */
	int checkData(AccountUpdateDto upDto);
	
	/*
	 * アカウント情報登録 登録
	 * 
	 * @param AccountRegisterDto アカウント情報登録用DTO
	 * @return アカウント登録件数
	 */
	int registerAccount(AccountRegisterDto registerDto);
	
	/*
	 * アカウント情報削除 削除
	 * 
	 * @param delDto アカウント情報削除用DTO
	 * @return アカウント削除件数
	 */
	int deleteAccount(AccountDeleteDto delDto);

	
}
