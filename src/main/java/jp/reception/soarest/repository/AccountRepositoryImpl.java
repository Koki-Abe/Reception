package jp.reception.soarest.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import jp.reception.soarest.domain.dto.AccountDeleteDto;
import jp.reception.soarest.domain.dto.AccountRegisterDto;
import jp.reception.soarest.domain.dto.AccountSearchDto;
import jp.reception.soarest.domain.dto.AccountSearchResultDto;
import jp.reception.soarest.domain.dto.AccountUpdateDto;

/*
 * アカウント関連 リポジトリ実装クラス
 * 
 * @author k.abe
 * @version 1.0
 */
public class AccountRepositoryImpl implements AccountRepository{
	
	@Autowired
	private AccountRepository accountRepository;
	
	/*
	 * アカウント情報一覧 検索
	 * 
	 * @param searchDto アカウント情報一覧 検索用DTO
	 * @author k.abe
	 * @return 検索結果
	 */
	public List<AccountSearchResultDto> searchAccountList(AccountSearchDto searchDto) {
		return accountRepository.searchAccountList(searchDto);
	}

	/*
	 * アカウント情報変更 変更
	 * 
	 * @param AccountUpdateDto アカウント情報変更用DTO
	 * @return アカウント変更件数
	 */
	public int updateAccount(AccountUpdateDto updDto) {
		return accountRepository.updateAccount(updDto);
	}
	
	/*
	 * アカウント情報変更 変更対象の最終アップデート時間を取得
	 * 
	 * @param upDto アカウント情報変更用DTO
	 * @return 最終アップデート時間
	 */
	public String getUpdateDate(AccountUpdateDto upDto) {
		return accountRepository.getUpdateDate(upDto);
	}
	
	/*
	 * アカウント情報変更 変更対象のデータをチェック
	 * 
	 * @param upDto アカウント情報変更用DTO
	 * @return 最終アップデート時間
	 */
	public int checkUpdateData(AccountUpdateDto upDto) {
		return accountRepository.checkUpdateData(upDto);
	}
	
	/*
	 * アカウント情報登録 登録
	 * 
	 * @param AccountRegisterDto アカウント情報登録用DTO
	 * @return アカウント登録件数
	 */
	public int registerAccount(AccountRegisterDto registerDto) {
		return accountRepository.registerAccount(registerDto);
	}
	
	/*
	 * アカウント情報削除 削除
	 * 
	 * @param delDto アカウント情報削除用DTO
	 * @return アカウント削除件数
	 */
	public int deleteAccount(AccountDeleteDto delDto) {
		return accountRepository.deleteAccount(delDto);
	}
	
	/*
	 * アカウント情報削除 削除対象の最終アップデート時間を取得
	 * 
	 * @param delDto アカウント情報削除用DTO
	 * @return 最終アップデート時間
	 */
	public String getDeleteDate(AccountDeleteDto delDto) {
		return accountRepository.getDeleteDate(delDto);
	}
	
	/*
	 * アカウント情報削除 削除対象のデータをチェック
	 * 
	 * @param delDto アカウント情報削除用DTO
	 * @return 最終アップデート時間
	 */
	public int checkDeleteData(AccountDeleteDto delDto) {
		return accountRepository.checkDeleteData(delDto);
	}
	
}
