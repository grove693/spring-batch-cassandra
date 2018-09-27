package com.example.springBatch.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.example.springBatch.model.Person;
import com.example.springBatch.model.PersonDTO;
import com.example.springBatch.model.PersonPrimaryKey;

public class PersonItemProcessor implements ItemProcessor<PersonDTO,Person>{
	
    private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);

    @Override
    public Person process(final PersonDTO person) throws Exception {
        final String firstName = person.getFirstName().toUpperCase();
        final String lastName = person.getLastName().toUpperCase();

        final Person transformedPerson = new Person(new PersonPrimaryKey(firstName, lastName));

        log.info("Converting (" + person + ") into (" + transformedPerson + ")");

        return transformedPerson;
    }



}
