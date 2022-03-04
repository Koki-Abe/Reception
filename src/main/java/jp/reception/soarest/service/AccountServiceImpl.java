package jp.reception.soarest.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jp.reception.soarest.domain.dto.AccountSearchDto;
import jp.reception.soarest.domain.dto.AccountSearchResultDto;
import jp.reception.soarest.form.AccountSearchForm;
import jp.reception.soarest.repository.AccountRepository;


@Service
/*
 * アカウント情報一覧 サービス実装クラス
 * 
 */
public class AccountServiceImpl implements AccountService {
	
	// アカウント関連 リポジトリ
	@Autowired
	private AccountRepository accountRepository;

	/*
	 * アカウント情報一覧 検索
	 * 
	 */
	@Override
	public List<AccountSearchResultDto> searchAccountList(AccountSearchForm form, 
			AccountSearchDto searchDto, Model model) {
		
		// beanの内容を詰め替え
		BeanUtils.copyProperties(form, searchDto);
		// プロパティ名が異なるものは別途設定
		searchDto.setDepId(form.getDepartment());
		searchDto.setAuthId(form.getRole());
	
		// 検索処理を実行
		List<AccountSearchResultDto> accList = accountRepository.searchAccountList(searchDto);
		
    	// 検索結果が0件の場合
    	if (0 == accList.size()) {
    		// エラーメッセージを画面に返却
    		model.addAttribute("errMsg", "検索結果が見つかりませんでした。");
    		// 検索結果件数を設定
    		model.addAttribute("searchCount", accList.size());
    	} else {
    		// 検索結果を格納
    		model.addAttribute("accList", accList);
    		// 検索結果件数を設定
    		model.addAttribute("searchCount", accList.size());
    	}
        
        // 検索結果を返却
	    return accList;
	}

	
	/*
	 * アカウント情報一覧 入力チェック
	 * 
	 */
	@Override
	public boolean inputCheck(AccountSearchForm form, Model model) {
		// 日付の相関チェック
		String start = form.getLoginDateStart();
		String end = form.getLoginDateEnd();

		// 開始日が終了日より未来日の場合
		if(start.compareTo(end) > 0) {
            model.addAttribute("errMsg", "入力された日付の範囲が不正です。");
	        return false;
	    }

	    return true;
	}

	/*
	 * アカウント情報一覧 入力値保持
	 * 
	 */
	@Override
	public void saveWord(AccountSearchForm form, Model model) {
        // 検索値を入力欄に保持
        model.addAttribute("userId", form.getUserId());
        model.addAttribute("userName", form.getUserName());
        model.addAttribute("department", form.getDepartment());
        model.addAttribute("role", form.getRole());
        model.addAttribute("loginDateStart", form.getLoginDateStart());
        model.addAttribute("loginDateEnd", form.getLoginDateEnd());
    }
}
