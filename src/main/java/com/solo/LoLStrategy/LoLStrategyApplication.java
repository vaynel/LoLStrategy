package com.solo.LoLStrategy;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.solo.LoLStrategy.common.board.BoardRepository;
import com.solo.LoLStrategy.league.Entity.Seoson;
import com.solo.LoLStrategy.league.Entity.SeosonRepository;
import com.solo.LoLStrategy.strategy.DTO.StrategyBoard;
import com.solo.LoLStrategy.user.User;
import com.solo.LoLStrategy.user.UserRepository;
import com.solo.LoLStrategy.user.UserService;
import com.solo.LoLStrategy.user.security.EncryptionAlgorithm;

@SpringBootApplication
public class LoLStrategyApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoLStrategyApplication.class, args);
	}

	// johh 아이디 생성

	@Bean
	public CommandLineRunner demo(
			UserRepository userRepository, 
			BoardRepository boardResRepository,
			SeosonRepository seosonRepository,
			UserService userService) {
		return (args) -> {
			// 시즌 설정하기 
			seosonRepository.save(setSeoson()); 
			// 유저 한명 생성하기
			
			User user = createUser();
			userService.register(user);
			userService.updateUserData(user);

			// 게시판 2개 만들기
//			StrategyBoard board1 = createBoard(1);
//			boardResRepository.save(board1);
//			StrategyBoard board2 = createBoard(2);
//			boardResRepository.save(board2);

		};
	}

	// cammel 유저 만들기
	public User createUser() {
		User user = new User();
		user.setGameId("cammel");
		user.setUserName("cammel");
		user.setPassword("123");
		user.setAlgorithm(EncryptionAlgorithm.BCRYPT);
		return user;
	}

	// 게시판 하나 생성
	public StrategyBoard createBoard(Integer i) {
		StrategyBoard board = new StrategyBoard();
		board.setChampion("vayne");
		board.setContents("테스트" + i);
		board.setDate(new Date());
		board.setSubject("테스트");
		board.setWriter("cammel");
		return board;
	}

	public Seoson setSeoson() {
		Seoson seoson = new Seoson();
		seoson.setSeoson("13-2");
		return seoson;
	}

}
