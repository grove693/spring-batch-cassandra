package com.example.springBatch.jobs;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;

import com.example.springBatch.model.Person;
import com.example.springBatch.model.PersonDTO;

@Configuration
@EnableBatchProcessing
@Import(CassandraConfiguration.class)
@ComponentScan(basePackages = "com.example.springBatch")
public class BatchConfiguration {
	
		@Autowired
		public JobBuilderFactory jobBuilderFactory;

	    @Autowired
	    public StepBuilderFactory stepBuilderFactory;
	    
	    @Autowired
	    private ResourceLoader resourceLoader;

	    // tag::readerwriterprocessor[]
	    @Bean
	    public FlatFileItemReader<PersonDTO> reader() {
	        return new FlatFileItemReaderBuilder<PersonDTO>()
	            .name("personItemReader")
	            .resource(resourceLoader.getResource("classpath:sample_persons.csv"))
	            .delimited()
	            .delimiter(",")
	            .names(new String[]{"firstName", "lastName"})
	            .fieldSetMapper(new BeanWrapperFieldSetMapper<PersonDTO>() {{
	                setTargetType(PersonDTO.class);
	            }})
	            .build();
	    }

	    @Bean
	    public PersonItemProcessor processor() {
	        return new PersonItemProcessor();
	    }

	    @Bean
	    public ItemWriter<Person> writer(DataSource dataSource) {
	        return new CassandraBatchItemWriter<Person>(Person.class);
	    }
	    // end::readerwriterprocessor[]

	    // tag::jobstep[]
	    @Bean
	    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
	        return jobBuilderFactory.get("importUserJob")
	            .incrementer(new RunIdIncrementer())
	            .listener(listener)
	            .flow(step1)
	            .end()
	            .build();
	    }

	    @Bean
	    public Step step1(ItemWriter<Person> writer) {
	        return stepBuilderFactory.get("step1")
	            .<PersonDTO, Person> chunk(10)
	            .reader(reader())
	            .processor(processor())
	            .writer(writer)
	            .build();
	    }
	    // end::jobstep[]

}
