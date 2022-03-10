package jp.reception.soarest.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jp.reception.soarest.domain.dto.AccountSearchDto;
import jp.reception.soarest.domain.dto.AccountSearchResultDto;
import jp.reception.soarest.domain.dto.AuthSearchResultDto;
import jp.reception.soarest.domain.dto.DepartmentSearchResultDto;
import jp.reception.soarest.enums.MessageEnum;
import jp.reception.soarest.form.AccountSearchForm;
import jp.reception.soarest.repository.AccountRepository;
import jp.reception.soarest.repository.CommonRepository;


@Service
/*
 * アカウント情報一覧 サービス実装クラス
 * 
 * @author k.abe
 * @version 1.0
 */
public class AccountServiceImpl implements AccountService {

    // アカウント関連 リポジトリ
    @Autowired
    private AccountRepository accountRepository;

    // 共通 リポジトリ
    @Autowired
    CommonRepository commonRepository;
    
    // 部署リスト
    private final String DEP_LIST = "depList";
    // 権限リスト
    private final String AUTH_LIST = "authList";

    
    /*
     * アカウント情報一覧 初期処理
     * 
     * @param model モデル
     */
    @Override
    public void init(Model model) {
        // 部署プルダウンの取得
        List<DepartmentSearchResultDto> depList = commonRepository.searchDepList();

        // 権限プルダウンの取得
        List<AuthSearchResultDto> authList = commonRepository.searchAuthList();

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
        model.addAttribute(DEP_LIST, depList);
        model.addAttribute(AUTH_LIST, authList);
    }

    /*
     * アカウント情報一覧 検索
     * 
     * @param form アカウント情報一覧 フォームクラス 
     * @param searchDto アカウント情報一覧 検索用DTO
     * @param model モデル
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
            model.addAttribute("errMsg", MessageEnum.MSG_C01_W_002.getMsg("validation"));
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
     * @param form アカウント情報一覧 フォームクラス 
     * @param model モデル
     */
    @Override
    public boolean inputCheck(AccountSearchForm form, Model model) {
        // 日付の相関チェック
        String start = form.getLoginDateStart();
        String end = form.getLoginDateEnd();

        // 開始日が終了日より未来日の場合
        if(start.compareTo(end) > 0) {
            model.addAttribute("errMsg", MessageEnum.MSG_C01_W_001.getMsg("validation"));
            return false;
        }

        return true;
    }

    /*
     * アカウント情報一覧 入力値保持
     * 
     * @param form アカウント情報一覧 フォームクラス 
     * @param model モデル
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
