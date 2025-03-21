package jp.reception.soarest.controller;


import java.security.NoSuchAlgorithmException;
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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.reception.soarest.domain.dto.LoginUserSearchResultDto;
import jp.reception.soarest.domain.dto.MeetingDeleteDto;
import jp.reception.soarest.domain.dto.MeetingRegisterDto;
import jp.reception.soarest.domain.dto.MeetingSearchDto;
import jp.reception.soarest.enums.CharEnum;
import jp.reception.soarest.enums.MessageEnum;
import jp.reception.soarest.enums.UrlEnum;
import jp.reception.soarest.form.MeetingDeleteForm;
import jp.reception.soarest.form.MeetingRegisterForm;
import jp.reception.soarest.form.MeetingSearchForm;
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

    // 打ち合わせ情報一覧URL
    private final String MTG_LIST_URL = "/mtg_list";

    // 打ち合わせ情報一覧 検索URL
    private final String MTG_SEARCH_URL = "/mtg_search";

    // 打ち合わせ情報一覧 更新確認URL
    private final String MTG_UPDATE_URL = "/mtg_update";
    
    // 打ち合わせ情報登録　URL
    private final String MTG_REGISTER_URL = "/mtg_register";

    // 打ち合わせ情報登録確認　URL
    private final String MTG_REGISTER_CONFIRM_URL = "/mtg_register_conf";
    
    // 打ち合わせ情報登録完了　URL
    private final String MTG_REGISTER_COMPLETE_URL = "/mtg_register_comp";
    
    // 打ち合わせ情報削除確認　URL
    private final String MTG_DELETE_CONFIRM_URL = "/mtg_delete_conf";
    
    // 打ち合わせ情報削除完了　URL
    private final String MTG_DELETE_COMPLETE_URL = "/mtg_delete_comp";
    
    // コメントURL
    private final String COMMENT_URL = "/comment";

    // ログインユーザー
    private final String LOGIN_USER = "loginUser";

    // メッセージ
    private final String MESSAGE = "message";
    
    // コメント
    private final String COMMENT = "comment";
    
    // エラーメッセージ
    static private String errMsg = "";

    /*
     * 打ち合わせ情報一覧 初期表示
     * 
     * @param model モデル
     * @return 打ち合わせ情報一覧画面
     */
    @RequestMapping(value = MTG_LIST_URL, method = RequestMethod.GET)
    private String init(Model model) {
        // 開始ログ
        logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.START.getChar());

        // セッションの取得
        session = request.getSession(false);

        // セッション情報のチェック
        if (null == session || null == (LoginUserSearchResultDto)session.getAttribute(LOGIN_USER)) {
            // 終了ログ
            logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());
            // ログイン画面へリダイレクト
            return CharEnum.REDIRECT.getChar() + UrlEnum.LOGIN.getUrl();
        }

        // セッションから表示情報を取得
        model.addAttribute(LOGIN_USER, session.getAttribute(LOGIN_USER));
        
        // エラーメッセージ
    	errMsg = MessageEnum.MSG_D01_E_001.getMsg(null);
    	
        // 初期処理
        meetingService.init(model);

        // 終了ログ
        logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());

        // return "mtg_list";
        return UrlEnum.MEETING_LIST.getPass();
    }

    /*
     * 打ち合わせ情報一覧 検索処理
     * 
     * @param form 打ち合わせ情報一覧 フォームクラス 
     * @param model モデル
     * @return 打ち合わせ情報一覧画面
     */
   @RequestMapping(value = MTG_SEARCH_URL, method = RequestMethod.GET)
   private String searchMeetingList(@Validated MeetingSearchForm form, Model model) {

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
       meetingService.saveWord(form, model);

       // 入力チェック
       if(!meetingService.inputCheck(form, model)) {
           // 終了ログ
           logger.warn(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());
           return CharEnum.FORWARD.getChar() + UrlEnum.MEETING_LIST.getUrl();
       }

       // エラーメッセージ
       errMsg = MessageEnum.MSG_D01_E_002.getMsg(null);
       
       // 検索処理
       meetingService.searchMtgList(form, new MeetingSearchDto(), model);
       
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
   
   @RequestMapping(value = MTG_UPDATE_URL, method = RequestMethod.POST)
   private String isUpdateMtg(MeetingSearchForm form, BindingResult result, Model model) {
	  
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
      
      // エラーメッセージ
      errMsg = MessageEnum.MSG_D01_E_001.getMsg(null);
  	
      // 初期処理
      meetingService.init(model);
      
      // 検索値を入力欄に保持
      meetingService.saveWord(form, model);
      
      // 入力チェック
      if(!meetingService.inputCheck(form, model)) {
          // 終了ログ
          logger.warn(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());
          return CharEnum.FORWARD.getChar() + UrlEnum.MEETING_LIST.getUrl();
      }
      
      // 終了ログ
      logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());
      return UrlEnum.MEETING_UPDATE.getPass();
   }
  
    /*
     * コメント表示
     * 
     * @param model モデル
     * @return コメント表示画面
     */
    @RequestMapping(value = COMMENT_URL, method = RequestMethod.GET)
    public String showComment(Model model, @RequestParam(COMMENT) String comment){
        model.addAttribute(COMMENT, comment);
        return UrlEnum.MEETING_COMMENT.getPass();    
        // return "meeting/comment";
    }
    
    /*
     * 新規登録ボタンの押下、打ち合わせ情報登録 初期表示
     * 
     * @param form 打ち合わせ情報登録 フォームクラス
     * @param model モデル
     * @return 打ち合わせ情報一覧画面
     */
    @RequestMapping(value = MTG_REGISTER_URL, method = RequestMethod.POST)
    private String registerMtg(MeetingRegisterForm form, Model model){
    	// 開始ログ
        logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.START.getChar());

        // セッションの取得
        session = request.getSession(false);

        // セッション情報のチェック
        if (null == session || null == (LoginUserSearchResultDto)session.getAttribute(LOGIN_USER)) {
            // 終了ログ
            logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());
            // ログイン画面へリダイレクト
            return CharEnum.REDIRECT.getChar() + UrlEnum.LOGIN.getUrl();
        } 
        // セッションから表示情報を取得
        model.addAttribute(LOGIN_USER, session.getAttribute(LOGIN_USER));
        
        // 検索値を入力欄に保持
        meetingService.saveWord(form , model);

        // エラーメッセージ
    	errMsg = MessageEnum.MSG_D01_E_001.getMsg(null);
    	
        // 初期処理
        meetingService.init(model);
    	
        // 終了ログ
        logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());

        // return "mtg_register_confirm";
        return UrlEnum.MEETING_REGISTER.getPass();      
	}
    
    /*
     * 打ち合わせ情報登録 入力確認
     * 
     * @param form 打ち合わせ情報登録 フォームクラス
     * @param result フォームのバリデーションチェック
     * @param model モデル
     * @return 打ち合わせ情報登録画面
     */
    @RequestMapping(value = MTG_REGISTER_CONFIRM_URL, method = RequestMethod.POST)
    private String registerMtgConf(@Validated MeetingRegisterForm form, BindingResult result, Model model) {
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

    	// 登録内容を入力欄に保持
    	meetingService.saveWord(form, model);
    	
    	// 入力チェック
    	if(meetingService.inputCheck(form, result, model, errorList)) {
    		
    		// エラーメッセージ
	    	errMsg = MessageEnum.MSG_D02_E_001.getMsg(null);
	    	
	    	// 初期処理
			meetingService.init(model);
			
    		// 画面上部メッセージ部分
    		model.addAttribute(MESSAGE, MessageEnum.MSG_C03_I_001.getMsg(null));

    		// 終了ログ
    		logger.warn(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());
    		
    		// 打ち合わせ情報登録確認画面へ遷移
    		return UrlEnum.MEETING_REGISTER_CONFIRM.getPass();
    	}

    	// 終了ログ
    	logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());

    	// 打ち合わせ情報登録画面へ遷移
    	// ※forwardがないとプルダウンが表示されない。また、リダイレクトだとURLがaccount_listの
    	// ままになるが、URLにパラメータが表示されないことに加え、検索結果も表示されない。
    	// (redirectの場合、redirectAttributesにsetしないと連携できない)
    	return CharEnum.FORWARD.getChar() +UrlEnum.MEETING_REGISTER.getUrl();
    }
    
    /*
     * 打ち合わせ情報登録 登録
     * 
     * @param form 打ち合わせ情報登録 フォームクラス
     * @param result フォームのバリデーションチェック
     * @param model モデル
     * @return 打ち合わせ情報登録完了画面
     */
    @RequestMapping(value = MTG_REGISTER_COMPLETE_URL, method = RequestMethod.POST)
    private String registerMtgConmp(@Validated MeetingRegisterForm form, BindingResult result, Model model) {

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
    	
    	// エラーメッセージ
    	errMsg = MessageEnum.MSG_D03_E_002.getMsg(null);
    	
    	// 会議情報の登録
    	String staffID = ((LoginUserSearchResultDto)session.getAttribute(LOGIN_USER)).getStaffId();
    	int count = meetingService.registerMtg(form, new MeetingRegisterDto(), model, staffID);
		
    	// 登録情報がない場合
		if(count == 0) {
			return UrlEnum.SYSTEM_ERROR.getPass();
		}
		
    	// 画面上部メッセージ部分
		model.addAttribute(MESSAGE, MessageEnum.MSG_D04_I_001.getMsg(null));

    	// 終了ログ
    	logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());

    	// 打ち合わせ情報登録完了画面へ遷移
    	// ※forwardがないとプルダウンが表示されない。また、リダイレクトだとURLがaccount_listの
    	// ままになるが、URLにパラメータが表示されないことに加え、検索結果も表示されない。
    	// (redirectの場合、redirectAttributesにsetしないと連携できない)
    	return UrlEnum.MEETING_REGISTER_COMPLETE.getPass();
    }
    
    /*
     * 打ち合わせ情報削除確認 確認
     * 
     * @param form 打ち合わせ情報削除 フォームクラス
     * @param result フォームのバリデーションチェック
     * @param model モデル
     * @return 打ち合わせ情報削除画面
     */
    
    @RequestMapping(value = MTG_DELETE_CONFIRM_URL, method = RequestMethod.POST)
    private String deleteMtgConf(MeetingDeleteForm form, BindingResult result, Model model) {
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
    	
    	// エラーメッセージ
    	errMsg = MessageEnum.MSG_D01_E_001.getMsg(null);
    	
		// 初期処理
    	meetingService.init(model);
    	
		// 画面上部メッセージ部分
		model.addAttribute(MESSAGE, MessageEnum.MSG_C08_I_001.getMsg(null));
		
		// 対象アカウント情報を保持
		meetingService.saveWord(form, model);

		// エラーメッセージ
    	errMsg = MessageEnum.MSG_E01_I_002.getMsg(null);
    	
		// 削除対象のデータを保持
    	meetingService.getLastDate(form, model);
		int count = meetingService.checkData(form, model);
		if(count == 0) {
			// 変更予定のデータに操作が加えられていた場合
			return UrlEnum.SYSTEM_ERROR.getPass();
		}
    	
		// 終了ログ
		logger.warn(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());
		
		// アカウント情報削除確認画面へ遷移
		return UrlEnum.MEETING_DELETE_CONFIRM.getPass();
    }
    
    /*
     * 打ち合わせ情報削除確認 削除
     * 
     * @param form 打ち合わせ情報削除 フォームクラス
     * @param result フォームのバリデーションチェック
     * @param model モデル
     * @return 打ち合わせ情報登録削除画面
     */
    @RequestMapping(value = MTG_DELETE_COMPLETE_URL, method = RequestMethod.POST)
    private String deleteMtgConmp(MeetingDeleteForm form, BindingResult result, Model model) {

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
    	
    	// エラーメッセージ
    	errMsg = MessageEnum.MSG_E01_I_002.getMsg(null);
    	
    	// 削除対象のデータをチェック
    	int count = meetingService.checkData(form, model);
		if(count == 0) {
			// 変更予定のデータに操作が加えられていた場合
			return UrlEnum.SYSTEM_ERROR.getPass();
		}
    	
		// エラーメッセージ
    	errMsg = MessageEnum.MSG_C08_E_001.getMsg(null);
    	
		// 削除処理
		int deleteAccountCount = meetingService.deletehMtg(form, new MeetingDeleteDto(), model);
		// 対象の打ち合わせ情報がない場合
		if(deleteAccountCount == 0) {
			return UrlEnum.SYSTEM_ERROR.getPass();
		}
    	
    	// 画面上部メッセージ部分
		model.addAttribute(MESSAGE, MessageEnum.MSG_C09_I_001.getMsg(null));

    	// 終了ログ
    	logger.info(new Object(){}.getClass().getEnclosingMethod().getName() + CharEnum.END.getChar());

    	// アカウント情報削除完了画面へ遷移
    	// ※forwardがないとプルダウンが表示されない。また、リダイレクトだとURLがaccount_listの
    	// ままになるが、URLにパラメータが表示されないことに加え、検索結果も表示されない。
    	// (redirectの場合、redirectAttributesにsetしないと連携できない)
    	return UrlEnum.MEETING_DELETE_COMPLETE.getPass();
    }
    
    /*
     * 例外ハンドリング
     * 
     * @param e 例外
     * @param model モデル
     * @return エラー画面
     */
    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e, Model model) {
    	
    	if (e.getCause() instanceof SQLException) {
    		model.addAttribute("errMsg", errMsg);
    	}else if(e.getCause() instanceof NoSuchAlgorithmException) {
    		model.addAttribute("errMsg", MessageEnum.MSG_E_002.getMsg(null));
        }else {
        	model.addAttribute("errMsg", MessageEnum.MSG_E_001.getMsg(null));
        }
    	return UrlEnum.SYSTEM_ERROR.getPass();
    }
}
