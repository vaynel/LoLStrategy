package com.solo.LoLStrategy.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.solo.LoLStrategy.league.Entity.League;
import com.solo.LoLStrategy.league.Entity.Summoner;
import com.solo.LoLStrategy.lol.LoLAPIService;
import com.solo.LoLStrategy.lol.VO.ParticipantDTO;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private LoLAPIService lolAPIService;

	@RequestMapping("/hello")
	public String hello(Authentication a) {
		return "hello!";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup() {
		log.info("회원가입");
		return "signup.html";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(User user) {
		log.info(user.getGameId()+"회원가입을 진행합니다");
		userService.register(user); 
		userService.updateUserData(user);
		return "login.html";
	}

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main(Authentication a, Model model) {
		Summoner MyRiotAccount = userService.getSummonerByGameId(a.getName()); // 소환사 정보 
		League MyLeague = userService.getLeagueBySummoner(MyRiotAccount); // 소환사의 리그 정보
		String[] MatchList = lolAPIService.returnMatchList(MyRiotAccount.getPuuid()); // 소환사가 최근에 한 게임 매치 List(20개)
		ParticipantDTO[] myParticipants=userService.getMatchListDetails(MatchList,MyRiotAccount);
		String recentlyChampions = userService.returnRecentlyChampions(myParticipants);
		
		model.addAttribute("MyRiotAccount", MyRiotAccount);
		model.addAttribute("MyLeague", MyLeague);
		model.addAttribute("MatchList", MatchList); 
		model.addAttribute("MyParticipants", myParticipants);
		model.addAttribute("recentlyChampions", recentlyChampions);
		return "main.html";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		log.info("login을 하십시오");
		return "login.html";
	}

}
