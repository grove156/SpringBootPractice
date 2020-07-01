package com.fastcampus.javaallinone.project3.mycontact.controller.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class PersonDto {
    private String name;
    private String address;
    private LocalDate birthday;
    private String phoneNumber;
    private String hobby;

}
