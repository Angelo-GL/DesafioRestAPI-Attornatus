package com.Attornatus.TesteAttornatus.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.Attornatus.TesteAttornatus.entities.Address;
import com.Attornatus.TesteAttornatus.entities.Person;
import com.Attornatus.TesteAttornatus.exceptions.ObjectNotFoundExceptions;
import com.Attornatus.TesteAttornatus.repositories.AddressRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    @Test
    public void testCreateAddressNoExistingAddresses(){
        Person person = new Person(1l,"Gabriel", LocalDate.of(1989, 7, 10), new ArrayList<>());
        Address address = new Address(1l, "Logradouro Rua XYZ", "59815-000", 1,"Pau dos Ferros", false, person );

        when(addressRepository.findByPublicPlace(address.getPublicPlace())).thenReturn(Optional.empty());
        when(addressRepository.save(address)).thenReturn(address);

        Address result = addressService.createAddress(person, address);

        assertNotNull(result);
        assertEquals(person, result.getPerson());
        assertEquals(address, result);
    }

    @Test
    public void testCreateAddressWithMainAddress() {
        Person person = new Person(1l,"Gabriel", LocalDate.of(1989, 7, 10), new ArrayList<>());
        Address mainAddress = new Address(1l, "Logradouro Rua XYZ", "59815-000", 1,"Pau dos Ferros", true, person );
        Address newAddress = new Address(2l, "Logradouro Rua XYZ", "59815-000", 1,"Pau dos Ferros", false, person );


        List<Address> existingAddresses = new ArrayList<>();
        existingAddresses.add(mainAddress);

        when(addressRepository.findByPublicPlace(newAddress.getPublicPlace())).thenReturn(Optional.empty());
        when(addressRepository.save(newAddress)).thenReturn(newAddress);

        person.setAddresses(existingAddresses);

        Address result = addressService.createAddress(person, newAddress);

        assertNotNull(result);
        assertEquals(person, result.getPerson());
        assertEquals(newAddress, result);
    }

    @Test
    public void testCreateAddress_DuplicateName() {
        Person person = new Person(1l,"Gabriel", LocalDate.of(1989, 7, 10), new ArrayList<>());
        Address existingAddress = new Address(2l, "Logradouro Rua XYZ", "59815-000", 1,"Pau dos Ferros", false, person );
        Address newAddress = new Address(3l, "Logradouro Rua XYZ", "59815-000", 1,"Pau dos Ferros", false, person );
        

        when(addressRepository.findByPublicPlace(newAddress.getPublicPlace())).thenReturn(Optional.of(existingAddress));

        assertThrows(ObjectNotFoundExceptions.class, () -> {
            addressService.createAddress(person, newAddress);
        });
    }

    @Test
    public void testCreateAddress_MultipleAddresses() {
        Person person = new Person(1l,"Gabriel", LocalDate.of(1989, 7, 10), new ArrayList<>());
        Address mainAddress = new Address(2l, "Logradouro Rua XYZ", "59815-000", 1,"Pau dos Ferros", true, person );
        Address newAddress = new Address(3l, "Logradouro Rua XYZf", "59815-000", 1,"Pau dos Ferros", false, person );


        List<Address> existingAddresses = new ArrayList<>();
        existingAddresses.add(mainAddress);

        when(addressRepository.findByPublicPlace(newAddress.getPublicPlace())).thenReturn(Optional.empty());
        when(addressRepository.save(newAddress)).thenReturn(newAddress);

        person.setAddresses(existingAddresses);

        Address result = addressService.createAddress(person, newAddress);

        assertNotNull(result);
        assertEquals(person, result.getPerson());
        assertEquals(newAddress, result);
    }

    @Test
    public void testCreateAddress_MultipleAddresses_NoMainAddress() {
        Person person = new Person(1l,"Gabriel", LocalDate.of(1989, 7, 10), new ArrayList<>());
        Address address1 = new Address(2l, "Logradouro Rua XYZ", "59815-000", 1,"Pau dos Ferros", false, person );
        Address address2 = new Address(2l, "Logradouro Rua XYZdd", "59815-000", 1,"Pau dos Ferros", false, person );


        List<Address> existingAddresses = new ArrayList<>();
        existingAddresses.add(address1);
        existingAddresses.add(address2);

        when(addressRepository.findByPublicPlace(address2.getPublicPlace())).thenReturn(Optional.empty());
        when(addressRepository.save(address2)).thenReturn(address2);

        person.setAddresses(existingAddresses);

        Address result = addressService.createAddress(person, address2);

        assertNotNull(result);
        assertEquals(person, result.getPerson());
        assertEquals(address2, result);
    }

}
