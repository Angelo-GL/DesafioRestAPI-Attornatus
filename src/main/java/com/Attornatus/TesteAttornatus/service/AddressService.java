package com.Attornatus.TesteAttornatus.service;

import com.Attornatus.TesteAttornatus.entities.Address;
import com.Attornatus.TesteAttornatus.entities.Person;
import com.Attornatus.TesteAttornatus.exceptions.ObjectNotFoundExceptions;
import com.Attornatus.TesteAttornatus.repositories.AddressRepository;
import com.Attornatus.TesteAttornatus.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AddressService {

    @Autowired
    private AddressRepository repository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonService personService;
    public Address createAddress(Person person, Address address){
        repository.findByPublicPlace(address.getPublicPlace()).ifPresent(address1 -> {
            throw new ObjectNotFoundExceptions("Address already has a registration!");
        });

        if(person.getAddresses().isEmpty()){
            address.setPerson(person);
            return repository.save(address);

        } else {
            if(validateAddressMain(person.getAddresses(), address)){
                address.setPerson(person);
                return repository.save(address);
            } else {
                throw new ObjectNotFoundExceptions("Already have a main address registration - Address id: "+person.getId());
            }
        }
    }

    public List<Address> listAddressPerson (Person person){
        List<Address> addressList = new ArrayList<>();
        addressList = person.getAddresses();
        return addressList;
    }
    public List<Address> updateAddressMain(Long idAddress, Person person ){

        Person findPerson = personService.findByIdPerson(person.getId());
        List<Address> addressList = new ArrayList<>(findPerson.getAddresses());
        boolean updated = false;

        for(Address a : addressList){
            if(a.getId() == idAddress){
                a.setMain(true);
                updated = true;
            } else {
                a.setMain(false);
            }
        }

        if (!updated) {
            throw new ObjectNotFoundExceptions("Address not found with id: " + idAddress);
        }

        return repository.saveAll(addressList);
    }

    public boolean validateAddressMain(List<Address> addresList, Address address){
        long cont = addresList.stream().filter(a -> a.getMain().equals(true)).count();

        if (cont == 0 ){
            return true;
        } else if(cont >= 1 && address.getMain().equals(false)) {
            return true;
        }else {
            return false;
        }
    }

}
