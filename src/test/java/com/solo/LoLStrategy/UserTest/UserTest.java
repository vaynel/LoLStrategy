package com.solo.LoLStrategy.UserTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.solo.LoLStrategy.league.SeosonRepository;
import com.solo.LoLStrategy.league.Entity.Seoson;
import com.solo.LoLStrategy.league.Entity.Summoner;
import com.solo.LoLStrategy.user.User;
import com.solo.LoLStrategy.user.UserService;
import com.solo.LoLStrategy.user.security.EncryptionAlgorithm;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class UserTest {
	
	@Autowired
	private UserService userService;
	@Autowired
	private SeosonRepository seosonRepository;
	
	@Test
	public void updateTest() {
		seosonRepository.save(setSeoson());
		User user = new User();
		user.setId(2);
		user.setGameId("cammel");
		user.setUserName("cammel");
		user.setPassword("123");
//		userService.register(user);
		Summoner summoner = (Summoner)userService.getSummonerById(user);
		userService.updateChampionsUsedBySummoner(summoner);
		
	}
	
	
	
	public User createUser() {
		User user = new User();
		user.setId(1);
		user.setGameId("cammel");
		user.setUserName("cammel");
		user.setPassword(new BCryptPasswordEncoder().encode("1234"));
		user.setAlgorithm(EncryptionAlgorithm.BCRYPT);
		return user;
	}
	
	
	public Seoson setSeoson() {
		Seoson seoson = new Seoson();
		seoson.setSeoson("13-2");
		return seoson;
	}

}
