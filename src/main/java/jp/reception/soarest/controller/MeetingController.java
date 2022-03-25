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
import org.springframework.web.bind.annotation.RequestParam;

import jp.reception.soarest.common.utils.CommonUtils;
import jp.reception.soarest.domain.dto.LoginUserSearchResultDto;
import jp.reception.soarest.domain.dto.MeetingSearchDto;
import jp.reception.soarest.enums.CharEnum;
import jp.reception.soarest.enums.MessageEnum;
import jp.reception.soarest.enums.UrlEnum;
import jp.reception.soarest.form.MeetingSearchForm;
import jp.reception.soarest.form.MeetingUpdateForm;
import jp.reception.soarest.service.MeetingService;

/*
 * 打ち合わせ情報 コントローラー
 * 
 * @author m.shigesawa
 * @version 1.0
 */
@Controller
public class MeetingController {

    // 打ち合わせ関連サービスクラス
    @Autowired
    MeetingService meetingService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpSession session; 

    // ロガー
    private final Logger logger = LoggerFactory.getLogger(MeetingController.class);

    /*
     * 打ち合わせ情報一覧 初期表示
     * 
     * @param model モデル
     * @return 打ち合わせ情報一覧画面
     */
    @RequestMapping(value = "/mtg_list", method = RequestMethod.GET)
    private String init(Model model) {
        // 開始ログ
        logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.START.getChar());

        // セッションの取得
        session = request.getSession(false);

        // セッション情報のチェック
        if (null == session || null == (LoginUserSearchResultDto)session.getAttribute("loginUser")) {
            // 終了ログ
            logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());
            // ログイン画面へリダイレクト
            return CharEnum.REDIRECT.getChar() + UrlEnum.LOGIN.getUrl();
        }

        // セッションから表示情報を取得
        model.addAttribute("loginUser", session.getAttribute("loginUser"));
        
        // 初期処理
        try {
            meetingService.init(model);
        } catch (SQLException e) {
            CommonUtils.outputErrLog(logger, e, MessageEnum.MSG_C01_E_001.getMsg(null));
            return UrlEnum.SYSTEM_ERROR.getPass();
        } catch (Exception e) {
            CommonUtils.outputErrLog(logger, e, MessageEnum.MSG_E_001.getMsg(null));
            return UrlEnum.SYSTEM_ERROR.getPass();
        }
        

        // 終了ログ
        logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());

        // return "mtg_list";
        return "meeting/mtg_list";
    }

    /*
     * 打ち合わせ情報一覧 検索処理
     * 
     * @param form 打ち合わせ情報一覧 フォームクラス 
     * @param model モデル
     * @return 打ち合わせ情報一覧画面
     */
   @RequestMapping(value = "/mtg_search", method = RequestMethod.GET)
   private String searchMeetingList(@Validated MeetingSearchForm form, Model model) {

       // 開始ログ
       logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.START.getChar());

       // セッション存在チェック
       session = request.getSession(false);
       if (null == session || null == (LoginUserSearchResultDto)session.getAttribute("loginUser")) {
           // 終了ログ
           logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());
           // ログイン画面へリダイレクト
           return CharEnum.REDIRECT.getChar() + UrlEnum.LOGIN.getUrl();
       }

       // 検索値を入力欄に保持
       meetingService.saveWord(form, model);

       // 入力チェック
       if(!meetingService.inputCheck(form, model)) {
           // 終了ログ
           logger.warn(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());
           return CharEnum.FORWARD.getChar() + UrlEnum.MEETING_LIST.getUrl();
       }

       try {
           // 検索処理
           meetingService.searchMtgList(form, new MeetingSearchDto(), model);
       } catch (SQLException e) {
           CommonUtils.outputErrLog(logger, e, MessageEnum.MSG_C01_E_002.getMsg(null));
           return UrlEnum.SYSTEM_ERROR.getPass();
       } catch (Exception e) {
           CommonUtils.outputErrLog(logger, e, MessageEnum.MSG_E_001.getMsg(null));
           return UrlEnum.SYSTEM_ERROR.getPass();
       }
       // 終了ログ
       logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());

       // ※forwardがないとプルダウンが表示されない。また、リダイレクトだとURLがaccount_listの
       // ままになるが、URLにパラメータが表示されないことに加え、検索結果も表示されない。
       // (redirectの場合、redirectAttributesにsetしないと連携できない)
       // return "redirect:/account_list";
       return CharEnum.FORWARD.getChar() + UrlEnum.MEETING_LIST.getUrl();
    }

   /*
    * 打ち合わせ情報一覧 更新確認処理
    * 
    * @param form 打ち合わせ情報一覧 フォームクラス 
    * @param model モデル
    * @return 打ち合わせ情報更新確認画面
    */
  @RequestMapping(value = "/mtg_update", method = RequestMethod.POST)
  private String isUpdateMtg(MeetingUpdateForm form, Model model) {

      // 開始ログ
      logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.START.getChar());

      // セッション存在チェック
      session = request.getSession(false);
      if (null == session || null == (LoginUserSearchResultDto)session.getAttribute("loginUser")) {
          // 終了ログ
          logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());
          // ログイン画面へリダイレクト
          return CharEnum.REDIRECT.getChar() + UrlEnum.LOGIN.getUrl();
      }

      // 終了ログ
      logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());

      // ※forwardがないとプルダウンが表示されない。また、リダイレクトだとURLがaccount_listの
      // ままになるが、URLにパラメータが表示されないことに加え、検索結果も表示されない。
      // (redirectの場合、redirectAttributesにsetしないと連携できない)
      // return "redirect:/account_list";
      return CharEnum.FORWARD.getChar() + UrlEnum.MEETING_LIST.getUrl();
   }

    /*
     * コメント表示
     * 
     * @param model モデル
     * @return コメント表示画面
     */
    @RequestMapping(value = "/comment", method = RequestMethod.GET)
    public String showComment(Model model, @RequestParam("comment") String comment){
        model.addAttribute("comment", comment);
        return "meeting/comment";
    }
}
