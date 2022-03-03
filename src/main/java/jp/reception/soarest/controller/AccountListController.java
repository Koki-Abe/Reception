package jp.reception.soarest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.reception.soarest.domain.dto.AccountSearchDto;
import jp.reception.soarest.domain.dto.AuthSearchResultDto;
import jp.reception.soarest.domain.dto.DepartmentSearchResultDto;
import jp.reception.soarest.form.AccountSearchForm;
import jp.reception.soarest.repository.CommonRepository;
import jp.reception.soarest.service.AccountService;


/*
 * アカウント情報一覧 コントローラー
 * 
 * 
 */
 @Controller
public class AccountListController {

    @Autowired
    // アカウント関連サービスクラス
    AccountService accountService;
    
    @Autowired
    CommonRepository commonRepository;

	 /*
	  * アカウント情報一覧 初期表示
	  * 
	  * 
	  */
	@RequestMapping(value = "/account_list", method = RequestMethod.GET)
    public String init(Model model) {
		
		// 部署プルダウンの取得
		List<DepartmentSearchResultDto> depList = commonRepository.searchDepList();
		
		// 権限プルダウンの取得
		List<AuthSearchResultDto> authList = commonRepository.searchAuthList();
		
		/* 画面返却値の設定 */
		// 部署プルダウンの初めにブランクを設定
		DepartmentSearchResultDto dep = new DepartmentSearchResultDto();
		dep.setDepId(999);
		dep.setDepName("");
		// 最初にブランクを表示させるため、要素の最初に挿入
		depList.add(0, dep);

		// 権限プルダウンの初めにブランクを設定
		AuthSearchResultDto auth = new AuthSearchResultDto();
		auth.setAuthId(999);
		auth.setAuthName("");
		
        // 最初にブランクを表示させるため、要素の最初に挿入
		authList.add(0, auth);
		
		// 画面返却値の設定
		model.addAttribute("depList", depList);
		model.addAttribute("authList", authList);

        return "account/account_list";
    }

	 /*
	  * アカウント情報一覧 検索処理
	  * 
	  * @param form
	  * @param result
	  * @param redirect
	  */
    @RequestMapping(value = "/account_search", method = RequestMethod.GET)
    public String searchAccount(@Validated AccountSearchForm form, BindingResult result, 
    		RedirectAttributes redirectAttributes, Model model) {
        // TODO ※最初に入力チェック

        // 検索処理
        accountService.searchAccountList(form, new AccountSearchDto(), model);

        // ※forwardがないとプルダウンが表示されない。また、リダイレクトだとURLがaccount_listの
        // ままになるが、URLにパラメータが表示されない。
        return "forward:/account_list";
//      return "redirect:/account_list";

   }
}
