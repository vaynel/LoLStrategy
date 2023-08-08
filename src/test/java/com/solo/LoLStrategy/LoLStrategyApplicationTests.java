package com.solo.LoLStrategy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.solo.LoLStrategy.lol.LoLAPIService;

@SpringBootTest
class LoLStrategyApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Autowired
    private LoLAPIService webClientService;

    @Test
    void get() {
        webClientService.get();
    }

}
