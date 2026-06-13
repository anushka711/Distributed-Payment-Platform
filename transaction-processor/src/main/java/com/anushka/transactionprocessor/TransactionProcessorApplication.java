package com.anushka.transactionprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class TransactionProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionProcessorApplication.class, args);
	}

}
