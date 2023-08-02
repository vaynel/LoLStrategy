package com.solo.LoLStrategy.user.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter  {
	
	@Autowired
	private CustomAuthenticationProvider authenticationProvider;

	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authenticationProvider);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.httpBasic();
		http.authorizeRequests()
			.anyRequest().authenticated();
	}
	
	
	
	
	
// 인증 객체를 만들지 않고 시큐리티에서 하나를 생성해서 바로 로그인함
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth)throws Exception{
//		var userDetailsService = new InMemoryUserDetailsManager();
//		
//		var user = User.withUsername("john")
//				.password("12345")
//				.authorities("read")
//				.build();
//		userDetailsService.createUser(user);
//		
//		auth.userDetailsService(userDetailsService)
//			.passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
//		
//	}
}
