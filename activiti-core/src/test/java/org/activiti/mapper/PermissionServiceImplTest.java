package org.activiti.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)//指定spring测试环境
@ContextConfiguration({"/spring-base.xml"})
public class PermissionServiceImplTest {

    @Autowired
    Environment env;
    @Test
    public void test(){
        System.out.println("===================");
        System.out.println(env.getProperty("admin.group"));
    }

}
