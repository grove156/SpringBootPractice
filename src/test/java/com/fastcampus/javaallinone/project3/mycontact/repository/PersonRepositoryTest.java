package com.fastcampus.javaallinone.project3.mycontact.repository;

import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    PersonRepository personRepository;

    @Test
    public void crud(){
        Person person = new Person();
        person.setName("Martin");
        person.setAge(20);
        person.setBloodType("B");

        String martin = person.getName();
        int martinAge = person.getAge();
        String martinBloodType = person.getBloodType();

        personRepository.save(person);

        System.out.println(personRepository.findAll());
        List<Person> people = personRepository.findAll();
        assertThat(people.size()).isEqualTo(1);
        assertThat(people.get(0).getName()).isEqualTo(martin);
        assertThat(people.get(0).getAge()).isEqualTo(martinAge);
        assertThat(people.get(0).getBloodType()).isEqualTo(martinBloodType);
    }

    @Test
    public void allArgsConstructor(){
        Person person = new Person(1L,"Martin",20,"Soccer","A","Seoul",LocalDate.of(2020,1,1));

    }

    @Test
    public void hashCodeAndEquals(){
        Person person1 = new Person("martin", 10);
        Person person2 = new Person("martin", 10);

        System.out.println(person1.equals(person2));
        System.out.println(person1.hashCode());
        System.out.println(person2.hashCode());

        Map<Person, Integer> map = new HashMap<>();
        map.put(person1, person1.getAge());

        System.out.println(map);
        System.out.println(map.get(person2));
    }
}