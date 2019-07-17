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

    //    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @GetMapping
    public Flux<Person> getAllPersons() {
        return personService.findAllPersons();
    }

    @GetMapping(value = "/{username}")
    public Mono<Person> getOnePerson(@PathVariable String username) {
        return personService.findOnePerson(username);
    }

    @PostMapping
    public Mono<Person> addPerson(@RequestBody Person person) {
        return personService.addPerson(person);
    }

    @PutMapping
    public Mono<Person> updatePerson(@RequestBody Person person) {
        return personService.updatePerson(person);
    }

    @DeleteMapping
    public Mono<Void> deletePerson(@RequestBody Person person) {
        return personService.removePerson(person);
    }
}
