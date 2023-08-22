package com.solo.LoLStrategy;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.solo.LoLStrategy.common.board.BoardRepository;
import com.solo.LoLStrategy.strategy.DTO.StrategyBoardDTO;
import com.solo.LoLStrategy.user.CustomUserDetails;
import com.solo.LoLStrategy.user.User;
import com.solo.LoLStrategy.user.UserRepository;
import com.solo.LoLStrategy.user.security.EncryptionAlgorithm;

@SpringBootApplication
public class LoLStrategyApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoLStrategyApplication.class, args);
	}

	// cammel 아이디 생성

	@Bean
	public CommandLineRunner demo(UserRepository userRepository,BoardRepository boardResRepository) {
		return (args) -> {
			User user = new User();
			user.setId(1);
			user.setGameId("cammel");
			user.setUserName("cammel");
			user.setPassword(new BCryptPasswordEncoder().encode("1234"));
			user.setAlgorithm(EncryptionAlgorithm.BCRYPT);
			userRepository.save(user);
			
			for (int i = 0; i < 2; i++) {
				StrategyBoardDTO board = new StrategyBoardDTO();
				board.setChampion("vayne");
				board.setContents("테스트"+i);
				board.setDate(new Date());
				board.setSubject("테스트");
				board.setWriter("cammel");
				boardResRepository.save(board);				
			}		
			
		};
	}

}
