package jp.reception.soarest.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@PropertySource("classpath:properties/message.properties")
@Controller
public class IndexController {

    @Value("${MSG-A01-W-001}")
    public String msg;
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String init() {

        return "login/login";
    }

}
