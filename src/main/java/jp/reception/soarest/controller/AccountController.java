package jp.reception.soarest.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.reception.soarest.common.utils.CommonUtils;
import jp.reception.soarest.domain.dto.AccountSearchDto;
import jp.reception.soarest.domain.dto.LoginUserSearchResultDto;
import jp.reception.soarest.enums.CharEnum;
import jp.reception.soarest.enums.MessageEnum;
import jp.reception.soarest.enums.UrlEnum;
import jp.reception.soarest.form.AccountSearchForm;
import jp.reception.soarest.service.AccountService;


/*
 * アカウント情報 コントローラー
 * 
 * @author k.abe
 * @version 1.0
 */
@Controller
public class AccountController {

    @Autowired
    // アカウント関連サービスクラス
    AccountService accountService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpSession session;

    // ロガー
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    // アカウント情報一覧URL
    private final String ACCOUNT_LIST = "/account_list";

    // アカウント情報検索結果URL
    private final String ACCOUNT_SEARCH = "/account_search";

    // ログインユーザー
    private final String LOGIN_USER = "loginUser";

     /*
      * アカウント情報一覧 初期表示
      * 
      * @param model モデル
      * @param session セッション
      * @return アカウント情報一覧画面
      */
    @RequestMapping(value = ACCOUNT_LIST, method = RequestMethod.GET)
    private String init(Model model) {

        // セッション存在チェック
        session = request.getSession(false);
        if (null == session|| null == (LoginUserSearchResultDto)session.getAttribute(LOGIN_USER)) {
            return CharEnum.REDIRECT.getChar() + UrlEnum.LOGIN.getUrl();
        }

        // セッションから表示情報を取得
        model.addAttribute(LOGIN_USER, session.getAttribute(LOGIN_USER));

        // 初期処理
        try {
            accountService.init(model);
        } catch (SQLException e) {
            CommonUtils.outputErrLog(logger, e, MessageEnum.MSG_C01_E_001.getMsg(null));
            return UrlEnum.SYSTEM_ERROR.getPass();
        } catch (Exception e) {
            CommonUtils.outputErrLog(logger, e, MessageEnum.MSG_E_001.getMsg(null));
            return UrlEnum.SYSTEM_ERROR.getPass();
        }

        // アカウント情報一覧画面へ遷移
        return UrlEnum.ACCOUNT_LIST.getPass();
    }

     /*
      * アカウント情報一覧 検索処理
      * 
      * @param form アカウント情報一覧 フォームクラス 
      * @param model モデル
      * @return アカウント情報一覧画面
      */
    @RequestMapping(value = ACCOUNT_SEARCH, method = RequestMethod.GET)
    private String searchAccountList(@Validated AccountSearchForm form, Model model) {

        // 開始ログ
        logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.START.getChar());

        // セッション存在チェック
        session = request.getSession(false);
        if (null == session|| null == (LoginUserSearchResultDto)session.getAttribute(LOGIN_USER)) {
            // 終了ログ
            logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());
            // ログイン画面へリダイレクト
            return CharEnum.REDIRECT.getChar() + UrlEnum.LOGIN.getUrl();
        }

        // 検索値を入力欄に保持
        accountService.saveWord(form, model);

        // 入力チェック
        if(!accountService.inputCheck(form, model)) {
            // 終了ログ
            logger.warn(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());
            return CharEnum.FORWARD.getChar() + UrlEnum.ACCOUNT_LIST.getUrl();
        }

        try {
            // 検索処理
            accountService.searchAccountList(form, new AccountSearchDto(), model);
        } catch (SQLException e) {
            CommonUtils.outputErrLog(logger, e, MessageEnum.MSG_C01_E_002.getMsg(null));
            // session.invalidate(); ←セッションを破棄するかは要相談
            return UrlEnum.SYSTEM_ERROR.getPass();
        } catch (Exception e) {
            CommonUtils.outputErrLog(logger, e, MessageEnum.MSG_E_001.getMsg(null));
            // session.invalidate(); ←セッションを破棄するかは要相談
            return UrlEnum.SYSTEM_ERROR.getPass();
        }
        // 終了ログ
        logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());

        // ※forwardがないとプルダウンが表示されない。また、リダイレクトだとURLがaccount_listの
        // ままになるが、URLにパラメータが表示されないことに加え、検索結果も表示されない。
        // (redirectの場合、redirectAttributesにsetしないと連携できない)
        // return "redirect:/account_list";
        return CharEnum.FORWARD.getChar() + UrlEnum.ACCOUNT_LIST.getUrl();

   }
}
