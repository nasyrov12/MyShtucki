package ru.nasyrov.spring.MyProject2.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nasyrov.spring.MyProject2.models.Person;

import java.util.List;


@Repository
public interface PeopleRepository extends JpaRepository<Person,Integer> {

    List<Person>findByAge(int age);


}
