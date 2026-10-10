package com.firstProgram.demo.service;

import ch.qos.logback.core.testUtil.MockInitialContext;
import com.firstProgram.demo.entity.Users;
import com.firstProgram.demo.repository.usersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;

import static org.mockito.Mockito.*;


public class CustomUserDetailsServiceImpTest {

    @InjectMocks
    private CustomUserDetailsServiceImp customUserDetails;

    @Mock
    usersRepository usersRepository;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Disabled
    @Test
    void loadByUserNameTest(){
        when(usersRepository.findByUsername(ArgumentMatchers.any())).thenReturn(Users.builder().username("ram").pass("ram@123").roles(new ArrayList<>()).build());
        UserDetails usr=customUserDetails.loadUserByUsername("ram");
        Assertions.assertNotNull(usr);
    }
}
