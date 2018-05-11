package com.dianer.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)//指定spring测试环境
@ContextConfiguration({"/spring-base.xml"})
public class PermissionServiceImplTest {

    @Test
    public void test(){
        System.out.println("===================");
    }

}
