package com.solo.LoLStrategy.user.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.solo.LoLStrategy.user.CustomUserDetails;
import com.solo.LoLStrategy.user.UserController;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider{
	
	@Autowired
	private JpaUserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private SCryptPasswordEncoder sCryptPasswordEncoder;
	

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		log.info(username);
		
		CustomUserDetails user = userDetailsService.loadUserByUsername(username);
		
		switch (user.getUser().getAlgorithm()) {
		case BCRYPT:
			return checkPassword(user,password,bCryptPasswordEncoder);
		case SCRYPT:
			return checkPassword(user,password,sCryptPasswordEncoder);
		}
		throw new BadCredentialsException("Bad credentials");
		
	}

	private Authentication checkPassword(CustomUserDetails user, String rawPassword,PasswordEncoder passwordEncoder) {
		System.out.println(user.getPassword());
		System.out.println(rawPassword);
		System.out.println(passwordEncoder.matches(rawPassword, user.getPassword()));
		if(passwordEncoder.matches(rawPassword, user.getPassword())) {
			log.info(user.getUsername()+"님 비밀번호 인코딩 결과 성공");
			return new UsernamePasswordAuthenticationToken(
					user.getUsername(),
					user.getPassword(),
					user.getAuthorities()
					);
		}else {
			log.info("로그인 실패");
			throw new BadCredentialsException("Bad credentials");
		}
	}


	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
