package com.firstProgram.demo.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@Disabled
@SpringBootTest
public class EmailServiceTest {
    @Autowired
    private EmailService emailService;
    @Disabled
    @Test
    public void sendEmailTest(){
        emailService.sendEmail("anujsharma13212@gmail.com","Testing email service",
                "Sending this mail just to test service.");
    }
}
