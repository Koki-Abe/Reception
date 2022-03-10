package jp.reception.soarest.controller;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

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

import jp.reception.soarest.domain.dto.LoginUserSearchDto;
import jp.reception.soarest.domain.dto.LoginUserSearchResultDto;
import jp.reception.soarest.enums.MessageEnum;
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

    // ロガー
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    HttpSession session; 

    @Autowired
    HttpServletRequest request;

    @Autowired
    // アカウント関連サービスクラス
    LoginService loginService;
    
    // ログインURL
    private final String LOGIN = "/login";

     /*
      * ログイン処理
      * 
      * @param form ログイン用フォームクラス 
      * @param result BindingResult
      * @param redirectAttributes リダイレクトアトリビュート
      * 
      */
    @RequestMapping(value = LOGIN, method = RequestMethod.POST)
    public String login(@Validated LoginForm form, BindingResult result, 
        RedirectAttributes redirectAttributes) throws NoSuchAlgorithmException {

        // 開始ログ
        logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + " : START");

        // 入力値の保持
        loginService.saveWord(form, redirectAttributes);

        // エラー格納用リスト
        List<String> errorList = new ArrayList<String>();

        // 入力チェック
        if(!loginService.inputCheck(form, result, redirectAttributes, errorList)) {
            // 終了ログ
            logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + " : END");
            return "redirect:/";
        }

        // ログインユーザー検索
        LoginUserSearchResultDto loginUser=  loginService.searchLoginUser(form, new LoginUserSearchDto(), redirectAttributes);

        // 検索結果が0件の場合
        if (null == loginUser) {
            errorList.add(MessageEnum.MSG_A01_W_008.getMsg("validation"));
            // ※flashAttributeにすると、URLにパラメータが表示されない
            redirectAttributes.addFlashAttribute("errorList", errorList);

            // 終了ログ
            logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + " : END");

            return "redirect:/";
        } else {
            // セッション開始(引数がtrueの場合、sessionがないと生成して返却する)
            session = request.getSession();
            // 日時のハイフンをスラッシュに置換
            loginUser.setLastLoginDate(loginUser.getLastLoginDate().replace("-", "/"));

            redirectAttributes.addFlashAttribute("loginUser", loginUser);
            // セッション情報の保持
            session.setAttribute("loginUser", loginUser);

        }
        // 終了ログ
        logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + " : END");

        return "redirect:/top";
    }

    /*
     * ログアウト
     * 
     * @return ログイン画面
     */    
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        // 開始ログ
        logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + " : START");

        // セッションを破棄
        session.invalidate();

        // 終了ログ
        logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + " : END");

        // ログインページへ遷移
        return "redirect:/";
    }
}
