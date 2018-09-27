package com.example.springBatch.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.CassandraTemplate;

import com.datastax.driver.core.policies.DowngradingConsistencyRetryPolicy;
import com.datastax.driver.core.policies.ExponentialReconnectionPolicy;
import com.datastax.driver.core.policies.ReconnectionPolicy;
import com.datastax.driver.core.policies.RetryPolicy;
import com.example.springBatch.model.Person;
import com.example.springBatch.properties.PropertyKeys;

@Configuration
@PropertySource(value = {"classpath:application.properties"})
//@EnableCassandraRepositories
public class CassandraConfiguration  extends AbstractCassandraConfiguration {

	private static final Logger LOG = LoggerFactory.getLogger(CassandraConfiguration.class);

	
	@Autowired
	private Environment propertyEnvironment;
	

    @Override
    protected String getKeyspaceName() {
        return propertyEnvironment.getProperty(PropertyKeys.CASSANDRA_KEYSPACE);
    }

    @Override
    protected String getContactPoints() {
        return propertyEnvironment.getProperty(PropertyKeys.CASSANDRA_CONTACT_POINTS);
    }

    @Override
    protected int getPort() {
        return Integer.parseInt(propertyEnvironment.getProperty(PropertyKeys.CASSANDRA_PORT));
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.valueOf(propertyEnvironment.getProperty(PropertyKeys.CASSANDRA_SCHEMA_ACTION).toUpperCase());
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[] {Person.class.getPackage().getName()};
    }

    @Bean(name = "cassandraTemplate")
    public CassandraTemplate profileCassandraTemplate() throws Exception {
        final CassandraTemplate cassandraTemplate = new CassandraTemplate(session().getObject(), cassandraConverter());
        logCassandraTemplateCreation(cassandraTemplate);

        return cassandraTemplate;
    }

    private static void logCassandraTemplateCreation(final CassandraTemplate cassandraTemplate) {
       
    }

    @Override
    protected RetryPolicy getRetryPolicy() {
        return DowngradingConsistencyRetryPolicy.INSTANCE;
    }

    @Override
    protected ReconnectionPolicy getReconnectionPolicy() {
        return new ExponentialReconnectionPolicy(200L, 5000L);
    }
}
