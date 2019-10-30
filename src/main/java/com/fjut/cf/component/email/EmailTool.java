package com.fjut.cf.component.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * 邮件发送工具类
 *
 * @author axiang [2019/10/30]
 */
@Component
public class EmailTool {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String username;

    public void sendSimpleMailTest(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo("305133882@qq.com");
        message.setSubject("标题：测试标题");
        message.setText("测试内容部份");
        javaMailSender.send(message);

    }
}
