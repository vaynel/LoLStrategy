package com.solo.LoLStrategy.user.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.solo.LoLStrategy.user.CustomUserDetails;
import com.solo.LoLStrategy.user.User;
import com.solo.LoLStrategy.user.UserRepository;

@Configuration
public class JpaUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("loadUserByUsername : "+username);
		
		User user= userRepository
				.findUserByUserName(username)
				.orElseThrow();
		if(user==null) System.out.println("user가없음 ");
		else System.out.println(user);
		
		if(user == null) throw new UsernameNotFoundException("Not Found User");
        return new CustomUserDetails(user);
	}

}
