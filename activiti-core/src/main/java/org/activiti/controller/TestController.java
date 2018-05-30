package org.activiti.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public Object test() {
        try {
            String str = null;
            str.equals("");

        }catch (Exception e){
            logger.info("--------"+e);
            logger.error("++++++++++", e);
            logger.error("++++++++++" + e);
//            e.printStackTrace();
        }
        return "";
    }
}
