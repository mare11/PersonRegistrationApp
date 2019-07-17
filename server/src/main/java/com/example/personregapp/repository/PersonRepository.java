package com.example.personregapp.repository;

import com.example.personregapp.model.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PersonRepository extends ReactiveMongoRepository<Person, String> {

    //    @Query("{ 'username' : ?0 }")
    Mono<Person> findOneByUsername(String username);
}
