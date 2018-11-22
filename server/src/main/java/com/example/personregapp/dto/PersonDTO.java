package com.example.personregapp.dto;

import com.example.personregapp.model.Person;

public class PersonDTO {
	
	private Long id;
	private String firstName;
	private String lastName;
	private Integer age;
	
	public PersonDTO() {
		
	}
	
	public PersonDTO(Person person) {
		this(person.getId(), person.getFirstName(), person.getLastname(), person.getAge());
	}
	
	public PersonDTO(Long id, String firstName, String lastName, Integer age) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}

}
