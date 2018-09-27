package com.example.springBatch.jobs;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;

public class CassandraBatchItemWriter<Person> implements ItemWriter<Person>, InitializingBean {
	
	  protected static final Log logger = LogFactory.getLog(CassandraBatchItemWriter.class);
	    private final Class<Person> aClass;
	    @Autowired
	    private CassandraTemplate cassandraTemplate;

	    @Override
	    public void afterPropertiesSet() throws Exception { }

	    public CassandraBatchItemWriter(final Class<Person> aClass) {
	        this.aClass = aClass;
	    }

	    @Override
	    public void write(final List<? extends Person> items) throws Exception {
	        logger.debug("Write operations is performing, the size is {}" + items.size());
	        if (!items.isEmpty()) {
	            logger.info("Inserting in a batch performing...");
	            for (Person entity : items) {
	            	cassandraTemplate.insert(entity);
	            }
	            
	        }

	        
	        
	    }

}
