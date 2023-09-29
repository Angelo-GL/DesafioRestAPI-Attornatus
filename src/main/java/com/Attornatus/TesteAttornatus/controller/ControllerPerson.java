package com.Attornatus.TesteAttornatus.controller;

import com.Attornatus.TesteAttornatus.dto.MessageResponseDTO;
import com.Attornatus.TesteAttornatus.entities.Address;
import com.Attornatus.TesteAttornatus.entities.Person;
import com.Attornatus.TesteAttornatus.exceptions.NotFoundException;
import com.Attornatus.TesteAttornatus.service.AddressService;
import com.Attornatus.TesteAttornatus.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/person")
public class ControllerPerson {
    @Autowired
    private PersonService personService;

    @Autowired
    private AddressService addressService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO createPerson (@RequestBody @Validated Person person){
        return personService.createPerson(person);
    }

    @GetMapping()
    public List<Person> listPersons(){
        return personService.listPerson();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Person> findbyIdPerson (@PathVariable Long id) throws NotFoundException{
        Person person = personService.findByIdPerson(id);
        return ResponseEntity.ok().body(person);
    }

    @PutMapping(value = "/{id}")
    public MessageResponseDTO updatePerson (@PathVariable Long id, @RequestBody @Validated Person person){
        return personService.UpdatePerson(id, person);
    }

    @PostMapping(value = "/{id}/address")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO createAddress(@PathVariable Long id, @RequestBody @Validated Address address){
        Person person = personService.findByIdPerson(id);
        if(person != null){
            return addressService.createAddress(person, address);
        } else {
            return new MessageResponseDTO("Person not found id: "+ id);
        }
    }

    @GetMapping(value = "/{id}/address")
    public ResponseEntity<List<Address>> listAddresPerson (@PathVariable Long id){
        Person person = personService.findByIdPerson(id);
        List<Address> findAddres = addressService.listAddressPerson(person);
        return ResponseEntity.ok().body(findAddres);
    }


}
