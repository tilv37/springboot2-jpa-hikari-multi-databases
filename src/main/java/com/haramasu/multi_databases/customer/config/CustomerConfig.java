package com.haramasu.multi_databases.customer.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @author: Ding, Shuo
 * @description:
 * @create: 2019-01-22 16:29
 **/
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "customerEntityManagerFactory",
        transactionManagerRef = "customerTransactionManager",
        basePackages = {
                "com.haramasu.multi_databases.customer.repo"
        }
)
public class CustomerConfig {

    @Bean
    @Primary
    @ConfigurationProperties("db1.datasource")
    public DataSourceProperties firstDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "customerDataSource")
    @ConfigurationProperties(prefix = "db1.datasource.configuration")
    public HikariDataSource customerDataSource() {
        return firstDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "customerEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
    entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("customerDataSource") DataSource dataSource
    ) {
        return builder
                .dataSource(dataSource)
                .packages("com.haramasu.multi_databases.customer.data")
                .persistenceUnit("db1")
                .build();
    }

    @Primary
    @Bean(name = "customerTransactionManager")
    public PlatformTransactionManager customerTransactionManager(
            @Qualifier("customerEntityManagerFactory") EntityManagerFactory customerEntityManagerFactory
    ) {
        return new JpaTransactionManager(customerEntityManagerFactory);
    }
}