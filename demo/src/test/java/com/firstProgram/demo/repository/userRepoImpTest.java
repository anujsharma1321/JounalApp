package com.firstProgram.demo.repository;



import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
public class userRepoImpTest {
    @Autowired
    private userRepoImpl userRepo;
    @Disabled
    @Test
    public void testUser(){
       userRepo.getUserForSA();
    }

}
