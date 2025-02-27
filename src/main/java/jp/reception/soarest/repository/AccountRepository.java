package jp.reception.soarest.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.reception.soarest.domain.dto.AccountRegisterDto;
import jp.reception.soarest.domain.dto.AccountSearchDto;
import jp.reception.soarest.domain.dto.AccountSearchResultDto;

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
	 * アカウント情報登録 登録
	 * 
	 * @param AccountRegisterDto アカウント情報登録用DTO
	 * @return アカウント登録件数
	 */
	int registerAccount(AccountRegisterDto registerDto);

}
