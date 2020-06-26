package com.fastcampus.javaallinone.project3.mycontact.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @GetMapping(value = "/api/helloworld")
    public String helloworld() {
        return "helloWorld";
    }

    @PostMapping(value="/api/helloworld")
    public String postWorld(){
        return "{}";
    }
}