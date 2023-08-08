package com.solo.LoLStrategy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.solo.LoLStrategy.user.CustomUserDetails;
import com.solo.LoLStrategy.user.User;
import com.solo.LoLStrategy.user.UserRepository;
import com.solo.LoLStrategy.user.security.EncryptionAlgorithm;

@SpringBootApplication
public class LoLStrategyApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoLStrategyApplication.class, args);
	}

	// johh 아이디 생성

	@Bean
	public CommandLineRunner demo(UserRepository userRepository) {
		return (args) -> {
			User user = new User();
			user.setId(1);
			user.setGameId("cammel");
			user.setUserName("johh");
			user.setPassword(new BCryptPasswordEncoder().encode("1234"));
			user.setAlgorithm(EncryptionAlgorithm.BCRYPT);
			userRepository.save(user);
		};
	}

}
