package com.productservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@org.springframework.context.annotation.Configuration
public class ApplicationConfiguration {


    @Autowired
    private DatabaseConfiguration configuration;

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder builder = DataSourceBuilder.create();
        builder.url(configuration.getUrl());
        builder.username(configuration.getUsername());
        builder.password(configuration.getPassword());
        builder.driverClassName(configuration.getDriverClassName());
        return builder.build();
    }
}
