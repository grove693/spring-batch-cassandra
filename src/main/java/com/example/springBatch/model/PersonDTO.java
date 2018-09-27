package com.example.springBatch.model;

public class PersonDTO {
	
	private String firstname;
	private String lastname;
	
	
	public PersonDTO() {
		
	}
	public PersonDTO(String firstname, String lastname) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
	}
	public String getFirstName() {
		return firstname;
	}
	public void setFirstName(String firstname) {
		this.firstname = firstname;
	}
	public String getLastName() {
		return lastname;
	}
	public void setLastName(String lastname) {
		this.lastname = lastname;
	}
	
	

}
