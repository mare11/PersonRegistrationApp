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
//        return personRepository.findAll().delayElements(Duration.ofSeconds(1));
        return personRepository.findAll();
    }

    public Mono<Person> findOnePerson(String username) {
        return personRepository.findOneByUsername(username)
                .doOnSuccess(person -> {
                    if (person == null) {
                        throw new PersonNotFoundException();
                    }
                });
    }

    public Mono<Person> addPerson(Person person) {
        return personRepository.save(person);
    }

    public Mono<Person> updatePerson(Person person) {
        getPerson(person);
        return personRepository.save(person);
    }

    public Mono<Void> removePerson(Person person) {
        getPerson(person);
        return personRepository.delete(person);
    }

    private void getPerson(Person person) {
        personRepository.findById(person.getId())
                .doOnError(throwable -> {
                    throw new PersonNotFoundException();
                });
    }
}
