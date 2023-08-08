package com.solo.LoLStrategy.lol;


import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.solo.LoLStrategy.lol.VO.SummonerV4;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoLAPIService {

	public void get() {
        String code = "myCode";

        // webClient 기본 설정
        WebClient webClient =
                WebClient
                        .builder()
                        .baseUrl("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name")
                        .build();

        // api 요청
        SummonerV4 response =
                (SummonerV4) webClient
		        .get()
		        .uri(uriBuilder ->
		                uriBuilder
		                        .path("/cammel")
		                        .build())
		        .header("Origin", "https://developer.riotgames.com")
		        .header("X-Riot-Token", "RGAPI-c6eaf9c1-a7bf-41c7-8454-93e6a99f0c11")
		        .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
		        .header("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8")
		        .retrieve()
		        .bodyToMono(SummonerV4.class)
		        .block();

        // 결과 확인
        log.info(response.toString());
    }

}
