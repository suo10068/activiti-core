package com.dianer.login;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/app")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @ResponseBody
    @RequestMapping(value = "/authentication")
    public Object login(HttpServletRequest request, HttpServletResponse response){
        System.out.println("==============");
        logger.info("j_username:"+request.getParameter("j_username"));
        logger.info("j_password:"+request.getParameter("j_password"));
        Map<String, String> result = new HashMap<>();
        result.put("data","aaaaa");
        result.put("model","YES");
        return result;
    }

}
