package jp.reception.soarest.controller;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.reception.soarest.common.utils.CommonUtils;
import jp.reception.soarest.domain.dto.LoginUserSearchDto;
import jp.reception.soarest.domain.dto.LoginUserSearchResultDto;
import jp.reception.soarest.enums.CharEnum;
import jp.reception.soarest.enums.MessageEnum;
import jp.reception.soarest.enums.UrlEnum;
import jp.reception.soarest.form.LoginForm;
import jp.reception.soarest.service.LoginService;

/*
 * ログインコントローラー
 * 
 * @author k.abe
 * @version 1.0
 */
 @Controller
public class LoginController {


    @Autowired
    HttpSession session; 

    @Autowired
    HttpServletRequest request;

    @Autowired
    // アカウント関連サービスクラス
    LoginService loginService;

    // ロガー
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    // ログインURL
    private final String LOGIN_URL = "/login";

    // ログアウトURL
    private final String LOGOUT_URL = "/logout";

    // ログインユーザー
    private final String LOGIN_USER = "loginUser";

    // 更新用 最終ログイン日時
    private final String UPD_LAST_LOGIN_DATE = "forUpdLoginDate";

    // エラーリスト
    private final String ERROR_LIST = "errorList";

     /*
      * ログイン処理
      * 
      * @param form ログイン用フォームクラス 
      * @param result BindingResult
      * @param redirectAttributes リダイレクトアトリビュート
      */
    @RequestMapping(value = LOGIN_URL, method = RequestMethod.POST)
    private String login(@Validated LoginForm form, BindingResult result, 
        RedirectAttributes redirectAttributes) {

        // 開始ログ
        logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.START.getChar());

        // 入力値の保持
        loginService.saveWord(form, redirectAttributes);

        // エラー格納用リスト
        List<String> errorList = new ArrayList<String>();

        // 入力チェック
        if(!loginService.inputCheck(form, result, redirectAttributes, errorList)) {
            // 終了ログ
            logger.warn(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());

            // ログイン画面へ遷移("redirect:/")
            return CharEnum.REDIRECT.getChar() + UrlEnum.LOGIN.getUrl();
        }

        // 検索結果格納用DTO
        LoginUserSearchResultDto loginUser = new LoginUserSearchResultDto();

        try {
            // ログインユーザー検索処理
            loginUser=  loginService.searchLoginUser(form, new LoginUserSearchDto(), redirectAttributes);
            // ハッシュ生成時の例外
        } catch (NoSuchAlgorithmException e) {
            CommonUtils.outputErrLog(logger, e, MessageEnum.MSG_E_002.getMsg(null));
            return UrlEnum.SYSTEM_ERROR.getPass();
            // SQLの例外
        } catch (SQLException e) {
            CommonUtils.outputErrLog(logger, e, MessageEnum.MSG_A01_E_001.getMsg(null));
            return UrlEnum.SYSTEM_ERROR.getPass();
        } catch (Exception e) {
            CommonUtils.outputErrLog(logger, e, MessageEnum.MSG_E_001.getMsg(null));
            return UrlEnum.SYSTEM_ERROR.getPass();
        }

        // 検索結果が0件の場合
        if (Objects.isNull(loginUser)) {
            errorList.add(MessageEnum.MSG_A01_W_008.getMsg(null));
            // ※flashAttributeにすると、URLにパラメータが表示されない
            redirectAttributes.addFlashAttribute(ERROR_LIST, errorList);

            // 終了ログ
            logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());

            // ログイン画面へ遷移
            return CharEnum.REDIRECT.getChar() + UrlEnum.LOGIN.getUrl();
        } else {
            // セッション開始(引数がtrueの場合、sessionがないと生成して返却する)
            session = request.getSession();

            // システム日時を設定
            String sysDate = CommonUtils.getSysdate();
            redirectAttributes.addFlashAttribute(UPD_LAST_LOGIN_DATE, sysDate);

            // 日時のハイフンをスラッシュに置換
            String lastLoginDate = loginUser.getLastLoginDate();
            if (null != lastLoginDate) {
                loginUser.setLastLoginDate(loginUser.getLastLoginDate()
                        .replace(CharEnum.HYPHEN.getChar(), CharEnum.SLASH.getChar()));
            }
            // ログインユーザー情報を設定
            redirectAttributes.addFlashAttribute(LOGIN_USER, loginUser);

            // セッション情報の保持
            session.setAttribute(LOGIN_USER, loginUser);
            session.setAttribute(UPD_LAST_LOGIN_DATE, sysDate);
        }

        // 終了ログ
        logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());

        // 管理画面TOPへ遷移
        return CharEnum.REDIRECT.getChar() + UrlEnum.TOP.getUrl();
    }

    /*
     * ログアウト
     * 
     * @return ログイン画面
     * @throws Exception
     */
    @RequestMapping(value = LOGOUT_URL, method = RequestMethod.GET)
    private String logout() throws Exception {
        // 開始ログ
        logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.START.getChar());

        // セッション存在チェック
        session = request.getSession(false);
        if (null != session) {
            // セッションより情報を取得
            String lastLoginDate = (String) session.getAttribute(UPD_LAST_LOGIN_DATE);
            LoginUserSearchResultDto loginUser = (LoginUserSearchResultDto)session.getAttribute("loginUser");

            // 最終ログイン日時更新処理
            if(0 == loginService.updLastLoginDate(lastLoginDate, loginUser)) {
                CommonUtils.outputErrLog(logger, new Exception(), MessageEnum.MSG_E_001.getMsg(null));
                throw new Exception();
            }
            // セッションを破棄
            session.invalidate();
        }
        // 終了ログ
        logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());

        // ログインページへ遷移
        return CharEnum.REDIRECT.getChar() + UrlEnum.LOGIN.getUrl();
    }
}
