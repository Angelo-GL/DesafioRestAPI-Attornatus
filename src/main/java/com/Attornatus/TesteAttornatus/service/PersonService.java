package com.Attornatus.TesteAttornatus.service;

import com.Attornatus.TesteAttornatus.dto.MessageResponseDTO;
import com.Attornatus.TesteAttornatus.entities.Person;
import com.Attornatus.TesteAttornatus.exceptions.ObjectNotFoundExceptions;
import com.Attornatus.TesteAttornatus.repositories.PersonRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    private PersonRepository repository;

    public Person createPerson(Person person){
        repository.findByName(person.getName()).ifPresent( person1 -> {
            throw new ObjectNotFoundExceptions("Person already has a registration!");
        });
        return repository.save(person);
    }


    public Person UpdatePerson(Long id, Person person){
        Optional<Person> personQuery = repository.findById(id);

        if (personQuery.isPresent()){
            Person _person = personQuery.get();
            _person.setName(person.getName());
            _person.setBirtDate(person.getBirtDate());
            _person.setAddresses(person.getAddresses());

            return repository.save(_person);
        }else {
            throw new ObjectNotFoundExceptions("Error updating person");
        }
    }

    public List<Person> listPerson (){
        return repository.findAll();
    }

    public Person findByIdPerson (Long id){
        Optional<Person> resultPerson = repository.findById(id);
        return resultPerson.orElseThrow(() -> new ObjectNotFoundExceptions("Person not fund id: "+id));
    }
}
