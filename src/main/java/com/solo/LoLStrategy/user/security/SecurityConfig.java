package com.solo.LoLStrategy.user.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

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
		http.addFilterBefore(new RequestValidationFilter(), BasicAuthenticationFilter.class)
		.authorizeRequests()
		.antMatchers("/login").permitAll()
		.antMatchers("/signup").permitAll()
		.antMatchers("/register").permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/login")
		.loginProcessingUrl("/login")
		.usernameParameter("id")
        .passwordParameter("password")
		.defaultSuccessUrl("/main")
		.and()
		.logout()
		.logoutUrl("/logout")
		.logoutSuccessUrl("/login")
		.logoutSuccessHandler((request, response, authentication) -> {
             response.sendRedirect("/login");
         }) // 로그아웃 성공 핸들러
         .deleteCookies("remember-me");;
		
		/*
		 * http .authorizeRequests()
		 * .mvcMatchers("/","/css/**","/scripts/**","/plugin/**","/fonts/**")
		 * .permitAll();
		 */
		
//		http.httpBasic();
//		http.authorizeRequests() 
//			.anyRequest().authenticated();
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
