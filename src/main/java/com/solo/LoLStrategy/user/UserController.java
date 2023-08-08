package com.solo.LoLStrategy.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

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
		log.info("회원가입을 진행합니다");
		userService.register(user);
		return "signup.html";
	}

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main(Authentication a, Model model) {
		model.addAttribute("gameId", a.getName());
		model.addAttribute("user", userService.findAll());
		return "main.html";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		log.info("login을 하십시오");
		return "login.html";
	}

}
