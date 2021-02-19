package com.datastax.workshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(exclude = { CassandraDataAutoConfiguration.class, CassandraAutoConfiguration.class })
@SpringBootApplication
public class TodobackendSpringdataApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodobackendSpringdataApplication.class, args);
    }

}
