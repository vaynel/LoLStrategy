package com.solo.LoLStrategy.user;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.ibatis.ognl.ComparisonExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solo.LoLStrategy.lol.LoLAPIService;
import com.solo.LoLStrategy.lol.VO.LeagueEntryDTO;
import com.solo.LoLStrategy.lol.VO.ParticipantDTO;
import com.solo.LoLStrategy.lol.VO.SummonerDTO;

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
	public void register(User user) {
		log.info("회원가입을 진행합니다");
		userService.register(user); 
	}

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main(Authentication a, Model model) {
		
		
		SummonerDTO MyRiotAccount = lolAPIService.getSummonerV4ById(a.getName()); // 소환사 정보 
		LeagueEntryDTO[] MyLeague = lolAPIService.getLeagueV4BySummonerId(MyRiotAccount.getId()); // 소환사의 리그 정보
		String[] MatchList = lolAPIService.returnMatchList(MyRiotAccount.getPuuid()); // 소환사가 최근에 한 게임 매치 List(20개)
		
		ParticipantDTO myParticipant=userService.getMatchListDetails(MatchList,MyRiotAccount);
		
		
		
		model.addAttribute("MyRiotAccount", MyRiotAccount);
		model.addAttribute("MyLeague", MyLeague);
		model.addAttribute("MatchList", MatchList); 
		model.addAttribute("MyParticipant", myParticipant);
		return "main.html";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		log.info("login을 하십시오");
		return "login.html";
	}

}
