package jp.reception.soarest.repository;

import java.util.Map;

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
	
	// ログインユーザー検索
	LoginUserSearchResultDto searchLoginUser(LoginUserSearchDto searchDto);
	
	// 最終ログイン日時更新
	int updLastLoginDate(Map<String, String> updInfo);

}
