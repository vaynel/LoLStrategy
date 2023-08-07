package com.solo.LoLStrategy.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UserController {

	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public RedirectView returnToLogin() {
		return new RedirectView("/login");
	}
	
	@GetMapping("/hello")
	public String hello(Authentication a) {
		return "hello!";
	}
	
	@GetMapping("/main")
	public String main(Authentication a,Model model) {
		
		model.addAttribute("username", a.getName());
		model.addAttribute("user", userService.findAll());
		
		return "main.html";
	}
	
	@GetMapping("/login")
	public String login() {
		log.info("login을 하십시오");
		return "login.html";
	}
	
	@PostMapping("/login")
	public RedirectView postLogin() {
		log.info("login을 시도중입니다.");
		return new RedirectView("/main");
	}


}
