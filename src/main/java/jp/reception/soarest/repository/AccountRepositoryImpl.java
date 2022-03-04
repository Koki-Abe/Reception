package jp.reception.soarest.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import jp.reception.soarest.domain.dto.AccountSearchDto;
import jp.reception.soarest.domain.dto.AccountSearchResultDto;

/*
 * アカウント関連 リポジトリ実装クラス
 * 
 * 
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


}
