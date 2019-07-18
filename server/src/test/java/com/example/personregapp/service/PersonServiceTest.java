package com.example.personregapp.service;

import com.example.personregapp.model.Person;
import com.example.personregapp.repository.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(PersonService.class)
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @MockBean
    private PersonRepository personRepository;

    private Person firstPerson;

    @Before
    public void setUp() {
        firstPerson = new Person("1", "mika", "Milan", "Milanovic", 37);
    }

    @Test
    public void findAllPersons() {
        Person secondPerson = new Person("2", "pera", "Petar", "Petrovic", 52);
        List<Person> expectedPersonList = new ArrayList<>(Arrays.asList(firstPerson, secondPerson));

        when(personRepository.findAll()).thenReturn(Flux.fromIterable(expectedPersonList));

        Flux<Person> returnedPersonFlux = personService.findAllPersons();

        assertEquals(returnedPersonFlux.collectList().block(), expectedPersonList);

        verify(personRepository).findAll();
    }

    @Test
    public void searchAllPersons() {
        String searchQuery = "mik";
        List<Person> expectedPersonList = new ArrayList<>(Arrays.asList(firstPerson));

        when(personRepository.findByUsernameLikeIgnoreCaseOrFirstNameLikeIgnoreCaseOrLastNameLikeIgnoreCase(searchQuery, searchQuery, searchQuery)).thenReturn(Flux.fromIterable(expectedPersonList));

        Flux<Person> returnedPersonFlux = personService.searchAllPersons(searchQuery);

        assertEquals(returnedPersonFlux.collectList().block(), expectedPersonList);

        verify(personRepository).findByUsernameLikeIgnoreCaseOrFirstNameLikeIgnoreCaseOrLastNameLikeIgnoreCase(searchQuery, searchQuery, searchQuery);
    }

    @Test
    public void findOnePersonSuccess() {
        when(personRepository.findOneByUsername(firstPerson.getUsername())).thenReturn(Mono.just(firstPerson));

        Mono<Person> returnedPersonMono = personService.findOnePerson(firstPerson.getUsername());

        assertEquals(returnedPersonMono.block(), firstPerson);

        verify(personRepository).findOneByUsername(firstPerson.getUsername());
    }

//    @Test(expected = PersonNotFoundException.class)
//    public void findOnePersonFailure() {
//        when(personRepository.findOneByUsername(firstPerson.getUsername())).thenReturn(Mono.empty());
//
//        personService.findOnePerson(firstPerson.getUsername());
//
////        assertEquals(returnedPersonMono.block(), firstPerson);
//
//        verify(personRepository).findOneByUsername(firstPerson.getUsername());
//    }

    @Test
    public void addPerson() {
        when(personRepository.save(firstPerson)).thenReturn(Mono.just(firstPerson));

        Mono<Person> returnedPersonMono = personService.addPerson(firstPerson);

        assertEquals(returnedPersonMono.block(), firstPerson);

        verify(personRepository).save(firstPerson);
    }

    @Test
    public void updatePerson() {
        when(personRepository.findOneByUsername(firstPerson.getUsername())).thenReturn(Mono.just(firstPerson));
        when(personRepository.save(firstPerson)).thenReturn(Mono.just(firstPerson));

        Mono<Person> returnedPersonMono = personService.updatePerson(firstPerson.getUsername(), firstPerson);

        assertEquals(returnedPersonMono.block(), firstPerson);

        verify(personRepository).findOneByUsername(firstPerson.getUsername());
        verify(personRepository).save(firstPerson);
    }

    @Test
    public void deletePerson() {
        when(personRepository.findOneByUsername(firstPerson.getUsername())).thenReturn(Mono.just(firstPerson));
        when(personRepository.delete(firstPerson)).thenReturn(Mono.empty());

        personService.deletePerson(firstPerson.getUsername());

        verify(personRepository).findOneByUsername(firstPerson.getUsername());
//        verify(personRepository).delete(firstPerson);
    }
}