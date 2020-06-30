package com.fastcampus.javaallinone.project3.mycontact.controller.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class PersonDto {
    private String name;
    private int age;
    private String bloodType;
    private String address;
    private LocalDate birthday;
    private String phoneNumber;
    private String hobby;

}
