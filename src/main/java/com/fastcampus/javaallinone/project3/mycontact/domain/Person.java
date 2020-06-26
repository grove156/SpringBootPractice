package com.fastcampus.javaallinone.project3.mycontact.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private int age;

    private String hobby;

    private String bloodType;

    private String address;

    @ToString.Exclude
    private LocalDate birthday;


}
