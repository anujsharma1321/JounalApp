package com.firstProgram.demo;

import org.springframework.stereotype.Component;

@Component
public class Animal {
    public String speak(){
        return "Meow only for cat.";
    }
}
