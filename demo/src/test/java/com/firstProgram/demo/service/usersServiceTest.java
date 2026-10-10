package com.firstProgram.demo.service;

import com.firstProgram.demo.entity.Users;
import org.apache.catalina.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class usersServiceTest {
    @Autowired
    usersService usersService;

    @Disabled
    @Test
    public void TestAdd(){
        assertEquals(44,22+22);
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "2,1,3",
            "5,1,6",
            "32,2,111"
    })
    public void test(int a,int b,int res){
        assertEquals(res,a+b);
    }


    @Disabled
    @ParameterizedTest
    @ValueSource(strings = {
            "anuj",
            "sam",
            "john"
    })

    public void testUser(String name){
        assertNotNull(usersService.findByUsername(name),name+" not Found");
        assertTrue(5>4);
    }

}
