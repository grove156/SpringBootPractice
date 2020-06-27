package com.fastcampus.javaallinone.project3.mycontact.repository;

import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.domain.dto.Birthday;
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
       // Person person = new Person(1L,"Martin",20,"Soccer","A","Seoul",LocalDate.of(2020,1,1),"01046470435");

    }

    @Test
    public void hashCodeAndEquals(){
        Person person1 = new Person("martin", 10, "A");
        Person person2 = new Person("martin", 10,"A");

        System.out.println(person1.equals(person2));
        System.out.println(person1.hashCode());
        System.out.println(person2.hashCode());

        Map<Person, Integer> map = new HashMap<>();
        map.put(person1, person1.getAge());

        System.out.println(map);
        System.out.println(map.get(person2));
    }

    @Test
    public void findByBloodType(){
        givenPerson("martin",10,"A");
        givenPerson("Daniel",14,"B");
        givenPerson("Sophie",13,"O");
        givenPerson("Tons",12,"A");

        List<Person> result = personRepository.findByBloodType("A");

        result.forEach(System.out::println);

    }

   // private void givenPerson(String name, int age, String bloodType){
   //     personRepository.save(new Person(name, age, bloodType));
   // }

    @Test
    public void findByBirthday(){
        givenPerson("martin",10,"A",LocalDate.of(1991,8,11));
        givenPerson("Daniel",14,"B",LocalDate.of(1990,11,11));
        givenPerson("Sophie",13,"O",LocalDate.of(1999,7,12));
        givenPerson("Tons",12,"A",LocalDate.of(1993,8,21));

        List<Person> result = personRepository.findByMonthOfBirthday(8,11);

        result.forEach(System.out::println);
    }
    private void givenPerson(String name, int age, String bloodType){
        Person person = new Person(name, age, bloodType);
    }
    private void givenPerson(String name, int age, String bloodType, LocalDate birthday){
        Person person = new Person(name, age, bloodType);
        person.setBirthday(new Birthday(birthday));
        personRepository.save(person);
    }
}