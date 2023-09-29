package com.Attornatus.TesteAttornatus.repositories;

import com.Attornatus.TesteAttornatus.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository  extends JpaRepository<Person, Long> {
    /*@Query(value = "SELECT p FROM Person p WHERE p.name like %1%")
    List<Person> findByName (String name);
     */
}
