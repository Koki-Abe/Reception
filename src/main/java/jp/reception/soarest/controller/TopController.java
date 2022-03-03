package jp.reception.soarest.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@PropertySource("classpath:properties/message.properties")
@Controller
public class TopController {

    @Value("${MSG-A01-W-001}")	
    public String msg;
    
    @RequestMapping(value = "/top", method = RequestMethod.GET)
    public String init(Model model) {
    	System.out.println(model.getAttribute("name"));
        return "top/top";
    }

}
