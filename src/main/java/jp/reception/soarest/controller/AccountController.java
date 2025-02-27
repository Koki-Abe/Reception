package jp.reception.soarest.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.reception.soarest.common.utils.CommonUtils;
import jp.reception.soarest.domain.dto.AccountRegisterDto;
import jp.reception.soarest.domain.dto.AccountSearchDto;
import jp.reception.soarest.domain.dto.LoginUserSearchResultDto;
import jp.reception.soarest.enums.CharEnum;
import jp.reception.soarest.enums.MessageEnum;
import jp.reception.soarest.enums.UrlEnum;
import jp.reception.soarest.form.AccountRegisterForm;
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

    // アカウント関連サービスクラス
    @Autowired
    AccountService accountService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpSession session;

    // ロガー
    private final Logger logger = LoggerFactory.getLogger(AccountController.class);

    // アカウント情報一覧URL
    private final String ACCOUNT_LIST = "/account_list";

    // アカウント情報一覧 検索URL
    private final String ACCOUNT_SEARCH = "/account_search";
    
    // アカウント情報登録URL
    private final String ACCOUNT_REGISTER = "/account_register";
    
    // アカウント情報登録確認URL
    private final String ACCOUNT_REGISTER_CONFIRM = "/account_register_conf";
    
    // アカウント情報登録完了URL
    private final String ACCOUNT_REGISTER_COMPLETE = "/account_register_comp";

    // ログインユーザー
    private final String LOGIN_USER = "loginUser";

     /*
      * アカウント情報一覧 初期表示
      * 
      * @param model モデル
      * @return アカウント情報一覧画面
      */
    @RequestMapping(value = ACCOUNT_LIST, method = RequestMethod.GET)
    private String init(Model model) {
        // 開始ログ
        logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.START.getChar());
        // セッション存在チェック
        session = request.getSession(false);
        if (null == session || null == (LoginUserSearchResultDto)session.getAttribute(LOGIN_USER)) {
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
        // 終了ログ
        logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());

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
        if (null == session || null == (LoginUserSearchResultDto)session.getAttribute(LOGIN_USER)) {
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
        return CharEnum.FORWARD.getChar() + UrlEnum.ACCOUNT_LIST.getUrl();
   }
    
    /*
     * アカウント情報登録 初期表示
     * 
     * @param form アカウント情報一覧 フォームクラス
     * @param result フォームのバリデーションチェック
     * @param model モデル
     * @return アカウント情報登録画面
     */
   @RequestMapping(value = ACCOUNT_REGISTER)
   private String registerAccount(@Validated AccountRegisterForm form, BindingResult result,Model model) {
	   // 開始ログ
       logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.START.getChar());

       // セッション存在チェック
       session = request.getSession(false);
       if (null == session || null == (LoginUserSearchResultDto)session.getAttribute(LOGIN_USER)) {
           // 終了ログ
           logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());
           // ログイン画面へリダイレクト
           return CharEnum.REDIRECT.getChar() + UrlEnum.LOGIN.getUrl();
       }

       // セッションから表示情報を取得
       model.addAttribute(LOGIN_USER, session.getAttribute(LOGIN_USER));
       
       // 検索値を入力欄に保持
       accountService.saveWord(form, model);
       
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
       
       // 終了ログ
       logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());

       // アカウント情報登録画面へ遷移
       return UrlEnum.ACCOUNT_REGISTER.getPass();
   }
   
   /*
    * アカウント情報登録確認 初期表示
    * 
    * @param form アカウント情報一覧 フォームクラス
    * @param result フォームのバリデーションチェック
    * @param model モデル
    * @return アカウント情報登録画面
    */
   
  @RequestMapping(value = ACCOUNT_REGISTER_CONFIRM, method = RequestMethod.POST)
  private String checkRegisterAccountConf(@Validated AccountRegisterForm form, BindingResult result,
		  Model model) {
	   // 開始ログ
      logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.START.getChar());

      // セッション存在チェック
      session = request.getSession(false);
      if (null == session || null == (LoginUserSearchResultDto)session.getAttribute(LOGIN_USER)) {
          // 終了ログ
          logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());
          // ログイン画面へリダイレクト
          return CharEnum.REDIRECT.getChar() + UrlEnum.LOGIN.getUrl();
      }
      // セッションから表示情報を取得
      model.addAttribute(LOGIN_USER, session.getAttribute(LOGIN_USER));
      // エラー格納用リスト
      List<String> errorList = new ArrayList<String>();
      
      // 入力チェック
      if(accountService.inputCheck(form, result, model, errorList)) {
          // 終了ログ
          logger.warn(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());
          
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
          
          // 検索値を入力欄に保持
	      accountService.saveWord(form, model);
          
          // アカウント情報登録確認画面へ遷移("redirect:/")
          //return CharEnum.REDIRECT.getChar() + UrlEnum.ACCOUNT_REGISTER_CONFIRM.getUrl();
          return UrlEnum.ACCOUNT_REGISTER_CONFIRM.getPass();
      }
      
      // 検索値を入力欄に保持
      accountService.saveWord(form, model);
      
      // 終了ログ
      logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());

      // アカウント情報登録画面へ遷移
      // ※forwardがないとプルダウンが表示されない。また、リダイレクトだとURLがaccount_listの
      // ままになるが、URLにパラメータが表示されないことに加え、検索結果も表示されない。
      // (redirectの場合、redirectAttributesにsetしないと連携できない)
      return CharEnum.FORWARD.getChar() +UrlEnum.ACCOUNT_REGISTER.getUrl();
  }
  /*
  /*
   * アカウント情報登録確認 リダイレクト
   * 
   * @param form アカウント情報一覧 フォームクラス
   * @param result フォームのバリデーションチェック
   * @param model モデル
   * @return アカウント情報登録画面
   */
	 @RequestMapping(value = ACCOUNT_REGISTER_CONFIRM, method = RequestMethod.GET)
	 private String redirectRegisterAccountConf(@Validated AccountRegisterForm form, BindingResult result,Model model) {
		 // 開始ログ
	     logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.START.getChar());
	     // セッション存在チェック
	      session = request.getSession(false);
	      if (null == session || null == (LoginUserSearchResultDto)session.getAttribute(LOGIN_USER)) {
	          // 終了ログ
	          logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());
	          // ログイン画面へリダイレクト
	          return CharEnum.REDIRECT.getChar() + UrlEnum.LOGIN.getUrl();
	      } 
	     // 終了ログ
	      logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());
	   // アカウント情報登録画面へリダイレクト
	      return CharEnum.REDIRECT.getChar() +UrlEnum.ACCOUNT_REGISTER.getUrl();
	  }
	 
	 /*
	    * アカウント情報登録確認 戻る
	    * 
	    * @param form アカウント情報一覧 フォームクラス
	    * @param result フォームのバリデーションチェック
	    * @param model モデル
	    * @return アカウント情報登録画面
	    */
	 @RequestMapping(value = ACCOUNT_REGISTER_CONFIRM, params="backreg", method = RequestMethod.POST)
	  private String backRegisterAccountConf(@Validated AccountRegisterForm form, Model model) {
		   // 開始ログ
	      logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.START.getChar());

	      // セッション存在チェック
	      session = request.getSession(false);
	      if (null == session || null == (LoginUserSearchResultDto)session.getAttribute(LOGIN_USER)) {
	          // 終了ログ
	          logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());
	          // ログイン画面へリダイレクト
	          return CharEnum.REDIRECT.getChar() + UrlEnum.LOGIN.getUrl();
	      }
	      // セッションから表示情報を取得
	      model.addAttribute(LOGIN_USER, session.getAttribute(LOGIN_USER));
	      
	      // 終了ログ
	      logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());

	      // アカウント情報登録画面へ遷移
	      // ※forwardがないとプルダウンが表示されない。また、リダイレクトだとURLがaccount_listの
	      // ままになるが、URLにパラメータが表示されないことに加え、検索結果も表示されない。
	      // (redirectの場合、redirectAttributesにsetしないと連携できない)
	      return CharEnum.FORWARD.getChar() +UrlEnum.ACCOUNT_REGISTER.getUrl();
	  }
	 /*
	    * アカウント情報登録確認 登録
	    * 
	    * @param form アカウント情報一覧 フォームクラス
	    * @param result フォームのバリデーションチェック
	    * @param model モデル
	    * @return アカウント情報登録画面
	    */
	 @RequestMapping(value = ACCOUNT_REGISTER_CONFIRM, params="reg", method = RequestMethod.POST)
	  private String registerAccountConf(@Validated AccountRegisterForm form, BindingResult result,
			  Model model) {
		   // 開始ログ
	      logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.START.getChar());

	      // セッション存在チェック
	      session = request.getSession(false);
	      if (null == session || null == (LoginUserSearchResultDto)session.getAttribute(LOGIN_USER)) {
	          // 終了ログ
	          logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());
	          // ログイン画面へリダイレクト
	          return CharEnum.REDIRECT.getChar() + UrlEnum.LOGIN.getUrl();
	      }
	      try {
	            // 検索処理
	    	  String staffID = ((LoginUserSearchResultDto)session.getAttribute(LOGIN_USER)).getStaffId();
	            accountService.registerAccount(form, new AccountRegisterDto(), model, staffID);
	        } catch (SQLException e) {
	            CommonUtils.outputErrLog(logger, e, MessageEnum.MSG_C03_E_001.getMsg(null));
	            return UrlEnum.SYSTEM_ERROR.getPass();
	        } catch (Exception e) {
	            CommonUtils.outputErrLog(logger, e, MessageEnum.MSG_E_001.getMsg(null));
	            return UrlEnum.SYSTEM_ERROR.getPass();
	        }
	      
	      // 終了ログ
	      logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());

	      // アカウント情報登録画面へ遷移
	      // ※forwardがないとプルダウンが表示されない。また、リダイレクトだとURLがaccount_listの
	      // ままになるが、URLにパラメータが表示されないことに加え、検索結果も表示されない。
	      // (redirectの場合、redirectAttributesにsetしないと連携できない)
	      return UrlEnum.ACCOUNT_REGISTER_COMPLETE.getPass();
	  }
	 
	 /*
	  /*
	   * アカウント情報登録完了 リダイレクト
	   * 
	   * @param form アカウント情報一覧 フォームクラス
	   * @param model モデル
	   * @return アカウント情報登録画面
	   */
		 @RequestMapping(value = ACCOUNT_REGISTER_COMPLETE, method = RequestMethod.GET)
		 private String RegisterAccountConmplete(@Validated AccountRegisterForm form,Model model) {
			 // 開始ログ
		     logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.START.getChar());
		     // セッション存在チェック
		      session = request.getSession(false);
		      if (null == session || null == (LoginUserSearchResultDto)session.getAttribute(LOGIN_USER)) {
		          // 終了ログ
		          logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());
		          // ログイン画面へリダイレクト
		          return CharEnum.REDIRECT.getChar() + UrlEnum.LOGIN.getUrl();
		      } 
		     // 終了ログ
		      logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());
		   // アカウント情報登録画面へリダイレクト
		      return CharEnum.REDIRECT.getChar() +UrlEnum.ACCOUNT_REGISTER.getUrl();
		  }
}
