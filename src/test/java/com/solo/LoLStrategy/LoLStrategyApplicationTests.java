package com.solo.LoLStrategy;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import com.solo.LoLStrategy.lol.LoLAPIService;
import com.solo.LoLStrategy.lol.VO.LeagueEntryDTO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class LoLStrategyApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Autowired
    private LoLAPIService webClientService;

    @Test
    void get() {
    	WebClient webClient =
                WebClient
                        .builder()
                        .baseUrl("https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/")
                        .build();

        // api 요청
		String[] response =
				 webClient
		        .get()
		        .uri(uriBuilder ->
		                uriBuilder
		                        .path("DsRtPVkmbbLpK2xhv-SGT_hUnvSD1pm-dvCHH0zIS34wQ-o2I88kCD7v7irbmeN-nTb22WLCRS5hlw/ids?start=0&count=20")
		                        .build())
		        .header("Origin", "https://developer.riotgames.com")
		        .header("X-Riot-Token", "RGAPI-c6eaf9c1-a7bf-41c7-8454-93e6a99f0c11")
		        .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
		        .header("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8")
		        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36")
		        .retrieve()	
		        .bodyToMono(String[].class)
		        .block();
         // 결과 확인
         log.info(response.toString());
    }

}
