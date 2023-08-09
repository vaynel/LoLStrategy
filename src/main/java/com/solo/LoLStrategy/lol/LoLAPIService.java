package com.solo.LoLStrategy.lol;


import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.solo.LoLStrategy.lol.VO.LeagueEntryDTO;
import com.solo.LoLStrategy.lol.VO.SummonerDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoLAPIService {
	
	private static String key = "RGAPI-4e9fc425-4ece-4671-a2e5-705eee760786";

	public void get() {
        // webClient 기본 설정
        WebClient webClient =
                WebClient
                        .builder()
                        .baseUrl("https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/")
                        .build();

        // api 요청
        Map<String, Object> response =
                 webClient
		        .get()
		        .uri(uriBuilder ->
		                uriBuilder
		                        .path("l9mBq2tCjP3Tx_Rubz6R40W8sZ0PFaye9o-H1OR5cS-Qkg")
		                        .build())
		        .header("Origin", "https://developer.riotgames.com")
		        .header("X-Riot-Token", key)
		        .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
		        .header("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8")
		        .retrieve()
		        .bodyToMono(Map.class)
		        .block();

        // 결과 확인
        log.info(response.toString());
    }

	public SummonerDTO getSummonerV4ById(String gameId) {
		WebClient webClient =
                WebClient
                        .builder()
                        .baseUrl("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/")
                        .build();

        // api 요청
		SummonerDTO response =
                 webClient
		        .get()
		        .uri(uriBuilder ->
		                uriBuilder
		                        .path(gameId)
		                        .queryParam("api_key", key)
		                        .build())
		        .header("Origin", "https://developer.riotgames.com")
		        .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
		        .header("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8")
		        .retrieve()
		        .bodyToMono(SummonerDTO.class)
		        .block();
		 
		 log.info(response.toString());
		 return response;
	}
	
	
	public LeagueEntryDTO[] getLeagueV4BySummonerId(String SummonerId) {
		WebClient webClient =
                WebClient
                        .builder()
                        .baseUrl("https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/")
                        .build();

        // api 요청
		LeagueEntryDTO[] response =
				 webClient
		        .get()
		        .uri(uriBuilder ->
		                uriBuilder
		                        .path(SummonerId)
		                        .build())
		        .header("Origin", "https://developer.riotgames.com")
		        .header("X-Riot-Token", key)
		        .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
		        .header("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8")
		        .retrieve()
		        .bodyToMono(LeagueEntryDTO[].class)
		        .block();
		return response;
	}
	
	
	public String[] returnMatchList(String puuid) {
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
		                        .path(puuid+"/ids")
		                        .queryParam("start", 0)
		                        .queryParam("count", 20)
		                        .build())
		        .header("Origin", "https://developer.riotgames.com")
		        .header("X-Riot-Token", key)
		        .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
		        .header("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8")
		        .retrieve()
		        .bodyToMono(String[].class)
		        .block();
		return response;
	}
	
	
	
	public Map<String, Object> findMatchesByMatchId (String matchId) {
		WebClient webClient =
                WebClient
                        .builder()
                        .baseUrl("https://asia.api.riotgames.com/lol/match/v5/matches/")
                        .build();

        // api 요청
		Map<String,Object> response =
				 webClient
		        .get()
		        .uri(uriBuilder ->
		                uriBuilder
		                        .path(matchId)
		                        .build())
		        .header("Origin", "https://developer.riotgames.com")
		        .header("X-Riot-Token", key)
		        .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
		        .header("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8")
		        .retrieve()
		        .bodyToMono(Map.class)
		        .block();
		return response;
	}
	

}
