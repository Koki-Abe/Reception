package jp.reception.soarest.repository;

import org.apache.ibatis.annotations.Mapper;

import jp.reception.soarest.domain.dto.LoginUserSearchDto;
import jp.reception.soarest.domain.dto.LoginUserSearchResultDto;

/*
 * ログイン関連 リポジトリインターフェース
 * 
 * @author k.abe
 * @version 1.0
 */
@Mapper
public interface LoginRepository {
	
	LoginUserSearchResultDto searchLoginUser(LoginUserSearchDto searchDto);

}
