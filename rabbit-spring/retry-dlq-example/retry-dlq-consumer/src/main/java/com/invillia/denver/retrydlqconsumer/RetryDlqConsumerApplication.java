package com.invillia.denver.retrydlqconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RetryDlqConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetryDlqConsumerApplication.class, args);
	}

}
