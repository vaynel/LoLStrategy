package com.solo.LoLStrategy.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/hello")
	public String hello(Authentication a) {
		return "hello!"+ a.getName()+"!";
	}
	
	@GetMapping("/main")
	public String main(Authentication a,Model model) {
		
		model.addAttribute("username", a.getName());
		model.addAttribute("user", userService.findAll());
		
		return "main.html";
	}

}
