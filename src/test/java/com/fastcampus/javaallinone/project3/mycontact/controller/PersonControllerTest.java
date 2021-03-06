package com.fastcampus.javaallinone.project3.mycontact.controller;

import com.fastcampus.javaallinone.project3.mycontact.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.domain.dto.Birthday;
import com.fastcampus.javaallinone.project3.mycontact.repository.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.NestedServletException;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.h2.value.ValueInt.get;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
class PersonControllerTest {
    @Autowired
    PersonController personController;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter messageConverter;

    private MockMvc mockMvc;

    @BeforeEach
    public void beforeEach(){
        mockMvc = MockMvcBuilders
                .standaloneSetup(personController)
                .setMessageConverters(messageConverter)
                .alwaysDo(print())
                .build();
    }

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.totalElements").value(6))
                .andExpect(jsonPath("$.content.[0].name").value("martin"))
                .andExpect(jsonPath("$.content.[1].name").value("david"))
                .andExpect(jsonPath("$.content.[2].name").value("dennis"))
                .andExpect(jsonPath("$.content.[3].name").value("sophie"))
                .andExpect(jsonPath("$.content.[4].name").value("benny"))
                .andExpect(jsonPath("$.content.[5].name").value("tony"));
    }

    @Test
    public void getPerson() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hobby").isEmpty())
                .andExpect(jsonPath("$.address").isEmpty())
                .andExpect(jsonPath("$.birthday").value("1991-08-15"))
                .andExpect(jsonPath("$.phoneNumber").isEmpty())
                .andExpect(jsonPath("$.deleted").value(false))
                .andExpect(jsonPath("$.age").isNumber()) //isExisted
                .andExpect(jsonPath("$.birthdayToday").isBoolean());

    }

    @Test
    public void postPerson() throws Exception {
        PersonDto dto =  PersonDto.of("martin","Seoul",LocalDate.now(),"010-1111-2222","programming");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(dto)))
                .andExpect(status().isCreated());

        Person result = personRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).get(0);

        assertAll(
                () -> assertThat(result.getName()).isEqualTo("martin"),
                () -> assertThat(result.getHobby()).isEqualTo("programming"),
                () -> assertThat(result.getAddress()).isEqualTo("Seoul"),
                () -> assertThat(result.getBirthday()).isEqualTo(Birthday.of(LocalDate.now())),
                () -> assertThat(result.getPhoneNumber()).isEqualTo("010-1111-2222")
        );
    }

    @Test
    public void postPersonIfNameIsNull() throws Exception{
        PersonDto dto = new PersonDto();

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(dto)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value(400))
        .andExpect(jsonPath("$.message").value("Name is required field"));
    }

    @Test
    public void postIfNameisEmpty() throws Exception{
        PersonDto dto = new PersonDto();
        dto.setName("");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(dto)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value(400))
        .andExpect(jsonPath("$.message").value("Name is required field"));
    }

    @Test
    public void postPersonIfNameIsBlank() throws Exception{
        PersonDto dto = new PersonDto();
        dto.setName(" ");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(dto)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value(400))
        .andExpect(jsonPath("$.message").value("Name is required field"));

    }

    @Test
    public void modifyPerson() throws Exception {
        PersonDto dto = PersonDto.of("martin","Seoul",LocalDate.now(),"010-1111-2222","Programing");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(dto)))
                .andExpect(status().isOk());

        Person result = personRepository.findById(1L).get();

        assertAll(
                ()-> assertThat(result.getName()).isEqualTo("martin"),
                ()-> assertThat(result.getHobby()).isEqualTo("Programing"),
                ()-> assertThat(result.getAddress()).isEqualTo("Seoul"),
                ()-> assertThat(result.getBirthday()).isEqualTo(Birthday.of(LocalDate.now())),

                ()-> assertThat(result.getPhoneNumber()).isEqualTo("010-1111-2222")
        );
    }

    @Test
    public void modifyPersonIfNameIsDifferent() throws Exception {
        PersonDto dto = PersonDto.of("James","Seoul",LocalDate.now(),"010-1111-2222","Programing");
            mockMvc.perform(
                    MockMvcRequestBuilders.put("/api/person/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJsonString(dto)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(400))
                    .andExpect(jsonPath("$.message").value("Cannot change the name"));
    }

    @Test
    public void modifyPersonIfPersonNotFound() throws Exception{
        PersonDto dto = PersonDto.of("martin","seoul", LocalDate.now(),"010-1111-2222","programming");

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/10")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(toJsonString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Person Entity dose not exist"));
    }

    @Test
    public void modifyName() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/person/1")
                .param("name","martinModified"))
                .andExpect(status().isOk());

        assertThat(personRepository.findById(1L).get().getName()).isEqualTo("martinModified");
    }

    @Test
 //   @Disabled
    public void deletePerson() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/person/1"))
                .andExpect(status().isOk());

        assertTrue(personRepository.findPeopleDeleted().stream().anyMatch(person -> person.getId().equals(1L)));
    }

    @Test
    public void checkJsonString() throws JsonProcessingException {
        PersonDto dto = new PersonDto();
        dto.setName("martin");
        dto.setBirthday(LocalDate.now());
        dto.setAddress("seoul");

        System.out.println(">>>" + toJsonString(dto));
    }

    private String toJsonString(PersonDto personDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(personDto);
    }

}