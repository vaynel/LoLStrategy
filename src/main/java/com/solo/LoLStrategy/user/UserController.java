package com.solo.LoLStrategy.user;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@GetMapping("/hello")
	public String hello(Authentication a) {
		return "hello!"+ a.getName()+"!";
	}

}
