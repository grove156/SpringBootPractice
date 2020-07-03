package com.fastcampus.javaallinone.project3.mycontact.controller.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class PersonDto {
    @NotBlank(message = "Name is required field")
    private String name;
    private String address;
    private LocalDate birthday;
    private String phoneNumber;
    private String hobby;

}
