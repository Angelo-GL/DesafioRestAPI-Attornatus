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
    public ResponseEntity<Person> createPerson (@RequestBody @Validated Person person){
        personService.createPerson(person);
        return ResponseEntity.ok().body(person);
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
    public ResponseEntity<Person> updatePerson (@PathVariable Long id, @RequestBody @Validated Person person){
        Person personUpdate = personService.UpdatePerson(id, person);
        return ResponseEntity.ok().body(personUpdate);
    }

    @PostMapping(value = "/{id}/address")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Address> createAddress(@PathVariable Long id, @RequestBody @Validated Address address) {
        Person person = personService.findByIdPerson(id);

        Address _address = addressService.createAddress(person, address);
        return ResponseEntity.ok().body(_address);
    }

    @GetMapping(value = "/{id}/address")
    public ResponseEntity<List<Address>> listAddresPerson (@PathVariable Long id){
        Person person = personService.findByIdPerson(id);
        List<Address> findAddres = addressService.listAddressPerson(person);
        return ResponseEntity.ok().body(findAddres);
    }
}
