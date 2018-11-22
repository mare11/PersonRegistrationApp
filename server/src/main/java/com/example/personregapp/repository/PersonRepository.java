package com.example.personregapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.personregapp.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

}
