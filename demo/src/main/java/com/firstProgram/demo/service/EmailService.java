package com.firstProgram.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to,String subject,String body){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        try {
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(body);
            javaMailSender.send(simpleMailMessage);
        }catch(Exception e){
        log.error("Some problem in sending mail");
        throw new RuntimeException("Mail service not working",e);
        }

    }
}
