package com.solo.LoLStrategy.common;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DBConfig {
	
	@Bean
	public javax.sql.DataSource dataSource() {
		return DataSourceBuilder.create()
				.url("jdbc:mysql://localhost:3306/lol_strategy?characterEncoding=UTF-8&serverTimezone=UTC")
				.username("fake_riot")
				.password("123qwe!@#QWE")
				.driverClassName("com.mysql.cj.jdbc.Driver")
				.build();
	}
	

}
