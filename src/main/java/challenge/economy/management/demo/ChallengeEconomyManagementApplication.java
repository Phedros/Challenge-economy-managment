package challenge.economy.management.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ChallengeEconomyManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeEconomyManagementApplication.class, args);
	}

}
