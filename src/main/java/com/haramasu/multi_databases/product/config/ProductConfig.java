package com.haramasu.multi_databases.product.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
 * @create: 2019-01-22 16:31
 **/
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "productEntityManagerFactory",
        transactionManagerRef = "productTransactionManager",
        basePackages = {
                "com.haramasu.multi_databases.product.repo"
        }
)
public class ProductConfig {

    @Bean
    @ConfigurationProperties("db2.datasource")
    public DataSourceProperties productDataSourceProperties() {
        return new DataSourceProperties();
    }


    @Bean(name = "productDataSource")
    @ConfigurationProperties(prefix = "db2.datasource")
    public HikariDataSource dataSource() {
        return productDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean(name = "productEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
    barEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("productDataSource") DataSource dataSource
    ) {
        return
                builder
                        .dataSource(dataSource)
                        .packages("com.haramasu.multi_databases.product.data")
                        .persistenceUnit("db2")
                        .build();
    }

    @Bean(name = "productTransactionManager")
    public PlatformTransactionManager productTransactionManager(
            @Qualifier("productEntityManagerFactory") EntityManagerFactory productEntityManagerFactory
    ) {
        return new JpaTransactionManager(productEntityManagerFactory);
    }
}