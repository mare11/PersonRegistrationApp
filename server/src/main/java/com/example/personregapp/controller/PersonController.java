package com.example.personregapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.personregapp.dto.PersonDTO;
import com.example.personregapp.model.Person;
import com.example.personregapp.service.PersonService;

@RestController
public class PersonController {
	
	@Autowired
	PersonService personService;

	@RequestMapping(value = "/getPersons", method = RequestMethod.GET)
	public ResponseEntity<List<PersonDTO>> getPersons(){
		
		List<Person> persons = personService.findAll();
		
		//convert persons to DTOs
		List<PersonDTO> personsDTO = new ArrayList<>();
		for (Person p : persons) {
			personsDTO.add(new PersonDTO(p));
		}
		
		return new ResponseEntity<>(personsDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/addPerson", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PersonDTO> addPerson(@RequestBody PersonDTO personDTO){
		
		Person person = new Person();
		person.setId(personDTO.getId());
		person.setFirstName(personDTO.getFirstName());
		person.setLastname(personDTO.getLastName());
		person.setAge(personDTO.getAge());
		
		person = personService.save(person);
		return new ResponseEntity<>(new PersonDTO(person), HttpStatus.CREATED);
		
	}
	
	@RequestMapping(value = "/updatePerson", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PersonDTO> updatePerson(@RequestBody PersonDTO personDTO){
		
		Optional<Person> findResult = personService.findOne(personDTO.getId()); 
		
		// if no person is found
		if (!findResult.isPresent()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Person person = new Person();
		person.setId(personDTO.getId());
		person.setFirstName(personDTO.getFirstName());
		person.setLastname(personDTO.getLastName());
		person.setAge(personDTO.getAge());
		
		person = personService.save(person);
		return new ResponseEntity<>(new PersonDTO(person), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/deletePerson/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletePerson(@PathVariable Long id){
		
		Optional<Person> findResult = personService.findOne(id);
		
		if(findResult.isPresent()) {
			personService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
			
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);			
		}			
		
	}

}
