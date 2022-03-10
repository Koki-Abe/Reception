package jp.reception.soarest.repository;

import org.springframework.beans.factory.annotation.Autowired;

import jp.reception.soarest.domain.dto.LoginUserSearchDto;
import jp.reception.soarest.domain.dto.LoginUserSearchResultDto;

/*
 * ログイン関連 リポジトリ実装クラス
 * 
 * 
 */
public class LoginRepositoryImpl implements LoginRepository{
	
	@Autowired
	private LoginRepository loginRepository;
	
	/*
	 * ログインユーザー 検索
	 * 
	 * @param searchDto ログインユーザー情報 検索用DTO
	 * @author k.abe
	 * @return 検索結果
	 */
	public LoginUserSearchResultDto searchLoginUser(LoginUserSearchDto searchDto) {
		return loginRepository.searchLoginUser(searchDto);
	}


}
