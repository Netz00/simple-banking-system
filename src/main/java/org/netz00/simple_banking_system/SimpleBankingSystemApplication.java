package org.netz00.simple_banking_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SimpleBankingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleBankingSystemApplication.class, args);
	}

}
