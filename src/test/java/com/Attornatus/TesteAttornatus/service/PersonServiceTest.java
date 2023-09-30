package com.Attornatus.TesteAttornatus.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import com.Attornatus.TesteAttornatus.exceptions.ObjectNotFoundExceptions;
import com.Attornatus.TesteAttornatus.repositories.PersonRepository;
import com.Attornatus.TesteAttornatus.entities.Person;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PersonServiceTest {
    @Mock
    PersonRepository repository;
    @InjectMocks
    PersonService service;


    @Test
    public void testSalvarPessoaSucesso() {
        String name = "Gabriel";
        LocalDate birtDate = LocalDate.of(1989, 7, 10);
        Long id = 1l;
        List listeAddres = new ArrayList<>();
        Person person = new Person(id,name, birtDate, listeAddres);

        when(repository.findByName("Gabriel")).thenReturn(Optional.empty());
        when(repository.save(person)).thenReturn(person);

        Person savedPerson = service.createPerson(person);

        verify(repository, times(1)).save(person);

        assertEquals(person, savedPerson);

    }

    @Test
    public void testSalvarPessoaDuplicada() {
        String name = "Gabriel";
        LocalDate birtDate = LocalDate.of(1989, 7, 10);
        Long id = 1l;
        List listeAddres = new ArrayList<>();
        Person person = new Person(id,name, birtDate, listeAddres);

        when(repository.findByName("Gabriel")).thenReturn(Optional.of(person));

        assertThrows(ObjectNotFoundExceptions.class, () -> {
            service.createPerson(person);
        });

        verify(repository, never()).save(any(Person.class));

    }

    @Test
    public void testUpdatePersonSuccess() {
        String name = "Gabriel";
        LocalDate birtDate = LocalDate.of(1989, 7, 10);
        Long id = 1l;
        List listeAddres = new ArrayList<>();
        Person personExisting = new Person(id,name, birtDate, listeAddres);

        String name2 = "Gabriel Lopes";
        LocalDate birtDate2 = LocalDate.of(1989, 7, 10);
        Long id2 = 1l;
        List listeAddres2 = new ArrayList<>();
        Person personUpdated = new Person(id2, name2, birtDate2, listeAddres2);

        when(repository.findById(id)).thenReturn(Optional.of(personExisting));
        when(repository.save(any(Person.class))).thenReturn(personUpdated);

        Person result = service.UpdatePerson(id, personUpdated);

        assertNotNull(result);
        assertEquals(personUpdated.getName(), result.getName());
        assertEquals(personUpdated.getBirtDate(), result.getBirtDate());
        assertEquals(personUpdated.getAddresses(), result.getAddresses());
    }

    @Test
    public void testUpdatePersonNotFound() {
        Long id = 1L;
        Person person = new Person();
        person.setName("Gabriel");


        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundExceptions.class, () -> {
            service.UpdatePerson(id, person);
        });

        verify(repository, never()).save(any(Person.class));
    }

    @Test
    public void testListPerson() {

        List<Person> list = new ArrayList<>();

        String name = "Gabriel";
        LocalDate birtDate = LocalDate.of(1989, 7, 10);
        Long id = 1l;
        List listeAddres = new ArrayList<>();
        Person person1 = new Person(id,name, birtDate, listeAddres);

        String name2 = "Gabriel Lopes";
        LocalDate birtDate2 = LocalDate.of(1989, 7, 10);
        Long id2 = 2l;
        List listeAddres2 = new ArrayList<>();
        Person person2 = new Person(id2, name2, birtDate2, listeAddres2);

        list.add(person1);
        list.add(person2);

        when(repository.findAll()).thenReturn(list);


        List<Person> result = service.listPerson();


        assertEquals(list, result);
    }

    @Test
    public void testFindByIdPersonSuccess() {
        String name = "Gabriel";
        LocalDate birtDate = LocalDate.of(1989, 7, 10);
        Long id = 1l;
        List listeAddres = new ArrayList<>();
        Person person1 = new Person(id,name, birtDate, listeAddres);


        when(repository.findById(id)).thenReturn(Optional.of(person1));


        Person result = service.findByIdPerson(id);

        assertEquals(person1, result);
    }

    @Test
    public void testFindByIdPerson_NotFound() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundExceptions.class, () -> {
            service.findByIdPerson(id);
        });
    }


}

