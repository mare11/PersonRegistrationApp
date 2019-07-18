package com.example.personregapp.controller;

import com.example.personregapp.model.Person;
import com.example.personregapp.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public Flux<Person> getAllPersons() {
        return personService.findAllPersons();
    }

    @GetMapping(value = "/search/{searchQuery}")
    public Flux<Person> searchAllPersons(@PathVariable String searchQuery) {
        return personService.searchAllPersons(searchQuery);
    }

    @GetMapping(value = "/{username}")
    public Mono<Person> getOnePerson(@PathVariable String username) {
        return personService.findOnePerson(username);
    }

    @PostMapping
    public Mono<Person> addPerson(@RequestBody Person person) {
        return personService.addPerson(person);
    }

    @PutMapping(value = "/{username}")
    public Mono<Person> updatePerson(@PathVariable String username, @RequestBody Person person) {
        return personService.updatePerson(username, person);
    }

    @DeleteMapping(value = "/{username}")
    public Mono<Void> deletePerson(@PathVariable String username) {
        return personService.deletePerson(username);
    }
}
