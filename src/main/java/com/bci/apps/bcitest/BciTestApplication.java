package com.bci.apps.bcitest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories("com.bci.apps.bcitest.jpa")
@EntityScan("com.bci.apps.bcitest.model")
@EnableTransactionManagement
public class BciTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(BciTestApplication.class, args);
	}

}
