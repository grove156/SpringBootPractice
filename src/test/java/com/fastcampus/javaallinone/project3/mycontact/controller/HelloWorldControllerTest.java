package com.fastcampus.javaallinone.project3.mycontact.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HelloWorldControllerTest {
    @Autowired
    private HelloWorldController helloWorldController;
    private MockMvc mockMvc;
    @Test
    public void helloworld(){
        System.out.println(helloWorldController.helloworld());
        assertThat(helloWorldController.helloworld()).isEqualTo("helloWorld");

    }

    @Test
    public void mockMvcTest() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(helloWorldController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/helloworld")
        ).andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("helloWorld"));
    }

}