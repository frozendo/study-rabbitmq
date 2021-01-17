package com.invillia.denver.multivhostproducer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MultiVhostProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiVhostProducerApplication.class, args);
	}

}
