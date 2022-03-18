package jp.reception.soarest.controller;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.reception.soarest.common.utils.CommonUtils;
import jp.reception.soarest.domain.dto.MeetingSearchResultDto;
import jp.reception.soarest.service.ShanaiMonitorService;

/*
 * 社内モニター コントローラー
 * 
 * @author k.abe
 * @version 1.0
 */
@Controller
public class ShanaiMonitorController {
    
    @Autowired
    ShanaiMonitorService service;

    // 社内モニター画面URL
    private final String MONITOR_SHANAI = "/monitor_shanai";
    
    private Set<MeetingSearchResultDto> mtgDispList = new LinkedHashSet<>();

    /*
     * 初期処理
     * 
     * @author k.abe
     * @return 社内モニター画面
     */
    @RequestMapping(value = MONITOR_SHANAI, method = RequestMethod.GET)
    private String init(Model model) {
        
        
        
        
        Set<MeetingSearchResultDto> mtgList = service.searchMtgList();
        mtgDispList.addAll(mtgList);
        model.addAttribute("mtgList", mtgDispList);
        


        // 社内モニター画面へ遷移
        return "monitor/monitor_shanai";
    }

    /*
     * 1分ごとに処理
     * 
     * @author k.abe
     * @return 社内モニター画面
     */
    @RequestMapping(value = "per_minutes", method = RequestMethod.GET)
    @ResponseBody
    private Set<MeetingSearchResultDto> perMinutes(Model model) {
                
        
    	mtgDispList = service.searchMtgList();
        //mtgDispList.addAll(mtgList);
        // 初期化
        // mtgList = new LinkedHashSet<>();
        // 画面返却値に設定
        model.addAttribute("mtgList", mtgDispList);
        
        System.out.println(CommonUtils.getSysdate());

        // 社内モニター画面へ遷移
        return mtgDispList;
    }


}
