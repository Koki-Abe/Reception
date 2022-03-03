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
public class AccountServiceImpl implements AccountService {
	
	// マッパー
	@Autowired
	private AccountRepository accountRepository;
	

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
    		// redirectAttributes.addFlashAttribute("errMsg", "検索結果が見つかりませんでした。");
    		model.addAttribute("errMsg", "検索結果が見つかりませんでした。");
    		// 検索結果件数を設定
    		// redirectAttributes.addFlashAttribute("searchCount", accList.size());
    		model.addAttribute("searchCount", accList.size());
    	} else {
    		// 検索結果を格納
    		// redirectAttributes.addFlashAttribute("accList", accList);
    		model.addAttribute("accList", accList);
    		// 検索結果件数を設定
    		// redirectAttributes.addFlashAttribute("searchCount", accList.size());
    		model.addAttribute("searchCount", accList.size());
    	}
    	// 検索ワードをテキストに保持
    	model.addAttribute("userId", form.getUserId());
    	model.addAttribute("userName", form.getUserName());
    	model.addAttribute("department", form.getDepartment());
    	model.addAttribute("role", form.getRole());
    	model.addAttribute("loginDateStart", form.getLoginDateStart());
    	model.addAttribute("loginDateEnd", form.getLoginDateEnd());
		
		return accList;
		
	}
}
