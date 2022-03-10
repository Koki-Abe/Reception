package jp.reception.soarest.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.reception.soarest.domain.dto.LoginUserSearchResultDto;
import jp.reception.soarest.enums.UrlEnum;

/*
 * 初期画面(ログイン)表示コントローラー
 * 
 * @author k.abe
 * @version 1.0
 */
//@PropertySource("classpath:properties/message.properties")
@Controller
public class IndexController {

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpSession session; 
    
    // 初期画面URL
    private final String INIT = "";

//    @Value("${MSG-A01-W-001}")
//    public String msg;
    /*
     * 初期処理
     * 
     * @author k.abe
     * @return ログイン状態：管理画面TOP
     *         未ログイン状態：ログイン画面
     */
    @RequestMapping(value = INIT, method = RequestMethod.GET)
    public String init(Model model) {

        /* ※ここをコメントアウトにすると、ログアウト後にログイン処理が走り、管理画面TOPに遷移しない*/
        // →判定条件にloginUser有無を加え、解決
        session = request.getSession(false);

        // セッション情報が存在する場合、管理画面TOPへ遷移する
        if (session != null && (LoginUserSearchResultDto)session.getAttribute("loginUser") != null) {
            // セッションから表示情報を取得(最初この処理が無かったので、タブを閉じてもログイン情報が画面に出なかった)
            model.addAttribute("loginUser", session.getAttribute("loginUser"));

            // 管理画面TOPへ遷移
            return UrlEnum.TOP.getPass();
        }

        // ログイン画面へ遷移
        return UrlEnum.LOGIN.getPass();
    }

}
