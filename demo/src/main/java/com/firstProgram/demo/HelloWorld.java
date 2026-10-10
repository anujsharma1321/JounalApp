package com.firstProgram.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
    @Autowired
    private Animal aml;
    @GetMapping("/animal")
    public String Animals(){
        return aml.speak();
    }
    @GetMapping("/")
    public String sayHello(){
        return "Welcome to the page";
    }
}
