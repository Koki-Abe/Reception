package jp.reception.soarest.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.reception.soarest.domain.dto.MeetingSearchResultDto;

/*
 * テスト Restコントローラー
 * 
 * @author k.abe
 * @version 1.0
 */
//@PropertySource("classpath:properties/message.properties")
@RestController
public class TestRestController {

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpSession session; 
    
    // 初期画面URL
    private final String INIT = "";
    
    // ログインユーザー
    private final String LOGIN_USER = "loginUser";

//    @Value("${MSG-A01-W-001}")
//    public String msg;
    /*
     * 初期処理
     * 
     * @author k.abe
     * @return ログイン状態：管理画面TOP
     *         未ログイン状態：ログイン画面
     */
    @RequestMapping(value = "/test_rest", method = RequestMethod.GET)
//    private Map<String, List<MeetingSearchResultDto>> init() {
    private Map<String, List<MeetingSearchResultDto>> init(@RequestParam("scheduleId") String scheduleId) {
    	
    	List<MeetingSearchResultDto> res = new ArrayList<>();
    	MeetingSearchResultDto dto = new MeetingSearchResultDto();
    	
    	dto.setClientCompName("株式会社テストテスト");
    	dto.setClientName("クライアント名");
    	dto.setScheduleId(scheduleId);
    	res.add(dto);
    	dto = new MeetingSearchResultDto();
    	dto.setScheduleId(scheduleId);
    	dto.setClientCompName("株式会社ああああああ");
    	dto.setClientName("田中太郎");
    	res.add(dto);
    	Map<String, List<MeetingSearchResultDto>> map = new LinkedHashMap<>();
    	map.put("meeting", res);
    	
        // jsonを返却
        return map;
    }

}
