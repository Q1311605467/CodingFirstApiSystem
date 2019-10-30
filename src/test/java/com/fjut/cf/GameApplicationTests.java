package com.fjut.cf;

import com.fjut.cf.component.email.EmailTool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameApplicationTests {
    @Autowired
    EmailTool emailTool;


    @Test
    public void contextLoads() {
        emailTool.sendSimpleMailTest();
    }

}
