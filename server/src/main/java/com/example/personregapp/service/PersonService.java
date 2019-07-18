package com.example.personregapp.service;

import com.example.personregapp.exception.PersonNotFoundException;
import com.example.personregapp.model.Person;
import com.example.personregapp.repository.PersonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Flux<Person> findAllPersons() {
        return personRepository.findAll();
    }

    public Flux<Person> searchAllPersons(String searchQuery) {
        return personRepository.findByUsernameLikeIgnoreCaseOrFirstNameLikeIgnoreCaseOrLastNameLikeIgnoreCase(searchQuery, searchQuery, searchQuery);
    }

    public Mono<Person> findOnePerson(String username) {
        return getPersonByUsername(username);
    }

    public Mono<Person> addPerson(Person person) {
        return personRepository.save(person);
    }

    public Mono<Person> updatePerson(String username, Person newPerson) {
        return getPersonByUsername(username)
                .flatMap(oldPerson -> {
                    oldPerson.setFirstName(newPerson.getFirstName());
                    oldPerson.setLastName(newPerson.getLastName());
                    oldPerson.setAge(newPerson.getAge());
                    return personRepository.save(oldPerson);
                });
    }

    public Mono<Void> deletePerson(String username) {
        return getPersonByUsername(username)
                .flatMap(personRepository::delete);
    }

    private Mono<Person> getPersonByUsername(String username) {
        return personRepository.findOneByUsername(username)
                .switchIfEmpty(Mono.error(new PersonNotFoundException()));
    }
}
