package com.example.personregapp.controller;

import com.example.personregapp.model.Person;
import com.example.personregapp.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private PersonService personService;

    private Person firstPerson;

    @Before
    public void setUp() {
        firstPerson = new Person("1", "mika", "Milan", "Milanovic", 37);
    }

    @Test
    public void getAllPersons() {
        Person secondPerson = new Person("2", "pera", "Petar", "Petrovic", 52);

        when(personService.findAllPersons()).thenReturn(Flux.just(firstPerson, secondPerson));

        webTestClient.get()
                .uri("/persons")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Person.class)
                .hasSize(2)
                .contains(firstPerson, secondPerson);

        verify(personService).findAllPersons();
    }

    @Test
    public void searchAllPersons() {
        String searchQuery = "mik";
        when(personService.searchAllPersons(searchQuery)).thenReturn(Flux.just(firstPerson));

        webTestClient.get()
                .uri("/persons/search/" + searchQuery)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Person.class)
                .hasSize(1)
                .contains(firstPerson);

        verify(personService).searchAllPersons(searchQuery);
    }

    @Test
    public void getOnePerson() {
        when(personService.findOnePerson(firstPerson.getUsername())).thenReturn(Mono.just(firstPerson));

        webTestClient.get()
                .uri("/persons/" + firstPerson.getUsername())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Person.class)
                .isEqualTo(firstPerson);

        verify(personService).findOnePerson(firstPerson.getUsername());
    }

    @Test
    public void addPerson() {
        when(personService.addPerson(firstPerson)).thenReturn(Mono.just(firstPerson));

        webTestClient.post()
                .uri("/persons")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(firstPerson))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Person.class)
                .isEqualTo(firstPerson);

        verify(personService).addPerson(firstPerson);
    }

    @Test
    public void updatePerson() {
        when(personService.updatePerson(firstPerson.getUsername(), firstPerson)).thenReturn(Mono.just(firstPerson));

        webTestClient.put()
                .uri("/persons/" + firstPerson.getUsername())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(firstPerson))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Person.class)
                .isEqualTo(firstPerson);

        verify(personService).updatePerson(firstPerson.getUsername(), firstPerson);
    }

    @Test
    public void deletePerson() {
        when(personService.deletePerson(firstPerson.getUsername())).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/persons/" + firstPerson.getUsername())
                .exchange()
                .expectStatus().isOk();

        verify(personService).deletePerson(firstPerson.getUsername());
    }
}