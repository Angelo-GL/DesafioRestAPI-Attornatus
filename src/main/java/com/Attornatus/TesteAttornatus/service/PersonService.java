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

    public MessageResponseDTO createPerson(Person person){
        Person savePerson = repository.save(person);
        return createMessageResponse(savePerson.getId());
    }


    public MessageResponseDTO UpdatePerson(Long id, Person person){
        Long t = id;
        Optional<Person> personQuery = repository.findById(id);

        if (personQuery.isPresent()){
            Person _person = personQuery.get();
            _person.setName(person.getName());
            _person.setBirtDate(person.getBirtDate());
            _person.setAddresses(person.getAddresses());
            repository.save(_person);
            return createMessageResponseUpdate(_person.getId(), 1);
        }else {
            return createMessageResponseUpdate(id, 0);
        }
    }

    public List<Person> listPerson (){
        return repository.findAll();
    }

    public Person findByIdPerson (Long id){
        Optional<Person> resultPerson = repository.findById(id);
        return resultPerson.orElseThrow(() -> new ObjectNotFoundExceptions("Person not fund, Type: "+Person.class.getName()));
    }
    /*
    public List<Person> findByPersonName (String name){
        List<Person> findPerson = repository.findByName(name);
        return findPerson;
    }
    */
    private MessageResponseDTO createMessageResponse(Long id){
        return new MessageResponseDTO("Created Person with Id "+ id);
    }

    private MessageResponseDTO createMessageResponseUpdate (Long id, Integer cod){
        if(cod == 1){
            return  new MessageResponseDTO("Update Person width Id" + id);
        }else {
            return new MessageResponseDTO("Error updating person Id = " + id);
        }
    }

}
