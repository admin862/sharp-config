package com.day.config;

import com.dafy.config.utils.CommonUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/ApplicationContext.xml","classpath:spring/ApplicationContext-mvc.xml"})
@WebAppConfiguration("src/main/resources")
public class BaseTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Before
    public void init(){
        executeSqlScript("test.sql",false);
    }

    public static void main(String[] args) {
        System.out.println(CommonUtils.md5("123qwe{admin}"));

        int i = 0;
        while (i ++ <= 3){
            System.out.println(i);
        }
    }

}
