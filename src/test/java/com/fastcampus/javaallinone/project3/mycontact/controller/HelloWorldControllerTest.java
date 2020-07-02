package com.fastcampus.javaallinone.project3.mycontact.controller;

import com.fastcampus.javaallinone.project3.mycontact.exception.dto.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class HelloWorldControllerTest {
    @Autowired
    private HelloWorldController helloWorldController;

    private MockMvc mockMvc;

//    @BeforeEach
//    public void beforeEach(){
//       mockMvc = MockMvcBuilders.standaloneSetup(helloWorldController)
//                .alwaysDo(print())
//                .build();
//  }

    @Test
    public void helloWorld() throws Exception{
        mockMvc = MockMvcBuilders.standaloneSetup(helloWorldController).build();
        mockMvc.perform(
          MockMvcRequestBuilders.get("/api/helloWorld"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("helloWorld"));
    }

    @Test
    public void HelloException() throws Exception{
        mockMvc = MockMvcBuilders.standaloneSetup(helloWorldController).build();
        mockMvc.perform(
          MockMvcRequestBuilders.get("/api/helloException"))
                .andExpect(status().isInternalServerError());
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex){
        return new ResponseEntity<>(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR,"Unknown error occured"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}