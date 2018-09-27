package com.example.springBatch.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("person")
public class Person {

	@PrimaryKey
	private PersonPrimaryKey person;

	public Person() {
	}

	public PersonPrimaryKey getPerson() {
		return person;
	}

	public void setPerson(PersonPrimaryKey person) {
		this.person = person;
	}

	public Person(PersonPrimaryKey person) {
		super();
		this.person = person;
	}

	
}
