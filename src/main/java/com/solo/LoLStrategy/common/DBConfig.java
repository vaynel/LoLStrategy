package com.solo.LoLStrategy.common;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// application.propertis가 안되어서 이렇게 하려고했는데 다시 하니까 됩니다...
/*@Configuration*/
public class DBConfig {
	
	/* @Bean */
	public javax.sql.DataSource dataSource() {
		return DataSourceBuilder.create()
				.url("jdbc:mysql://localhost:3306/lol_strategy?characterEncoding=UTF-8&serverTimezone=UTC")
				.username("fake_riot")
				.password("123qwe!@#QWE")
				.driverClassName("com.mysql.cj.jdbc.Driver")
				.build();
	}
	

}
