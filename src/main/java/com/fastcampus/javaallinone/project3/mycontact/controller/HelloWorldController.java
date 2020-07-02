package com.fastcampus.javaallinone.project3.mycontact.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @GetMapping(value = "/api/helloWorld")
    public String helloWorld(){
        return "Hello World";
    }

    @GetMapping(value = "/api/helloException")
    public String helloException(){
        throw new RuntimeException("Hello Runtime exception");
    }
}
