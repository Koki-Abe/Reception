package jp.reception.soarest.repository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import jp.reception.soarest.domain.dto.LoginUserSearchDto;
import jp.reception.soarest.domain.dto.LoginUserSearchResultDto;

/*
 * ログイン関連 リポジトリ実装クラス
 * 
 * @author k.abe
 * @version 1.0
 */
public class LoginRepositoryImpl implements LoginRepository{
    
    @Autowired
    private LoginRepository loginRepository;
    
    /*
     * ログインユーザー 検索
     * 
     * @param searchDto ログインユーザー情報 検索用DTO
     * @return 検索結果
     */
    public LoginUserSearchResultDto searchLoginUser(LoginUserSearchDto searchDto) {
        return loginRepository.searchLoginUser(searchDto);
    }

    /*
     * 最終ログイン日時更新
     * 
     * @param userId ユーザーID
     * @param lastLoginDate 最終ログイン日時
     * @return 更新日時
     */
    @Override
    public int updLastLoginDate(Map<String, String> loginInfo) {
        return loginRepository.updLastLoginDate(loginInfo);
    }


}
