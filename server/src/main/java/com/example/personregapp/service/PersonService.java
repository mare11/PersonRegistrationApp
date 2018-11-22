package com.example.personregapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.personregapp.model.Person;
import com.example.personregapp.repository.PersonRepository;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository personRepository;
	
	public Optional<Person> findOne(Long id) {
		return personRepository.findById(id);
	}
	
	public List<Person> findAll() {
		return personRepository.findAll();
	}
	
	public Person save(Person person) {
		return personRepository.save(person);
	}

	public void remove(Long id) {
		personRepository.deleteById(id);
	}

}
