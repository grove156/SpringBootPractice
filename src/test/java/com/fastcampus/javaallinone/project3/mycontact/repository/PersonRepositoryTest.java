package com.fastcampus.javaallinone.project3.mycontact.repository;

import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.domain.dto.Birthday;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class PersonRepositoryTest {

    @Autowired
    PersonRepository personRepository;

    @Test
    public void crud(){
        Person person = new Person();
        person.setName("john");
        person.setAge(20);
        person.setBloodType("B");

        personRepository.save(person);

        List<Person> result = personRepository.findByName("john");


        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("john");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.get(0).getBloodType()).isEqualTo("B");
    }

    @Test
    public void allArgsConstructor(){
       // Person person = new Person(1L,"Martin",20,"Soccer","A","Seoul",LocalDate.of(2020,1,1),"01046470435");

    }

    @Test
    public void findByBloodType(){
        List<Person> result = personRepository.findByBloodType("A");

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo("martin");
        assertThat(result.get(1).getName()).isEqualTo("benny");

    }

   // private void givenPerson(String name, int age, String bloodType){
   //     personRepository.save(new Person(name, age, bloodType));
   // }

    @Test
    public void findByBirthday(){
        List<Person> result = personRepository.findByMonthOfBirthday(8,15);
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo("martin");
        assertThat(result.get(1).getName()).isEqualTo("sophie");
    }

}