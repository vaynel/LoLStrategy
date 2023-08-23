package com.solo.LoLStrategy.lol;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.solo.LoLStrategy.championMaster.ChampionMasteryDto;
import com.solo.LoLStrategy.league.Entity.Summoner;
import com.solo.LoLStrategy.lol.VO.LeagueEntryDTO;
import com.solo.LoLStrategy.lol.VO.LeagueItemDTO;
import com.solo.LoLStrategy.lol.VO.SummonerDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoLAPIService {

	private static String key = "RGAPI-5410aa4f-13b0-41f9-b631-fe9f3fc98dea";

	public void get() {
		// webClient 기본 설정
		WebClient webClient = WebClient.builder()
				.baseUrl("https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/").build();

		// api 요청
		Map<String, Object> response = webClient.get()
				.uri(uriBuilder -> uriBuilder.path("l9mBq2tCjP3Tx_Rubz6R40W8sZ0PFaye9o-H1OR5cS-Qkg").build())
				.header("Origin", "https://developer.riotgames.com").header("X-Riot-Token", key)
				.header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
				.header("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8").retrieve()
				.bodyToMono(Map.class).block();

		// 결과 확인
		log.info(response.toString());
	}

	
	// 소환사 DTO 얻기
	public Summoner getSummonerV4ById(String gameId) {
		WebClient webClient = WebClient.builder()
				.baseUrl("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/").build();

		// api 요청
		Summoner response = webClient.get().uri(uriBuilder -> uriBuilder.path(gameId).build())
				.header("X-Riot-Token", key).header("Origin", "https://developer.riotgames.com")
				.header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
				.header("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8").retrieve()
				.bodyToMono(Summoner.class).block();

		log.info(response.toString());
		return response;
	}

	public LeagueEntryDTO[] getLeagueV4BySummonerId(String SummonerId) {
		WebClient webClient = WebClient.builder()
				.baseUrl("https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/").build();

		// api 요청
		LeagueEntryDTO[] response = webClient.get().uri(uriBuilder -> uriBuilder.path(SummonerId).build())
				.header("Origin", "https://developer.riotgames.com").header("X-Riot-Token", key)
				.header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
				.header("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8").retrieve()
				.bodyToMono(LeagueEntryDTO[].class).block();
		return response;
	}

	// 매치의 ID를 받습니다. 
	public String[] returnMatchList(String puuid) {
		WebClient webClient = WebClient.builder()
				.baseUrl("https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/").build();

		// api 요청
		String[] response = webClient.get()
				.uri(uriBuilder -> uriBuilder.path(puuid + "/ids").queryParam("start", 0).queryParam("count", 20)
						.build())
				.header("Origin", "https://developer.riotgames.com").header("X-Riot-Token", key)
				.header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
				.header("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8").retrieve()
				.bodyToMono(String[].class).block();
		return response;
	}
	
	
	// 매치에 세부정보를 받습니다. 
	public Map<String, Object> findMatchesByMatchId(String matchId) {
		WebClient webClient = WebClient.builder().baseUrl("https://asia.api.riotgames.com/lol/match/v5/matches/")
				.build();

		// api 요청
		Map<String, Object> response = webClient.get().uri(uriBuilder -> uriBuilder.path(matchId).build())
				.header("Origin", "https://developer.riotgames.com").header("X-Riot-Token", key)
				.header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
				.header("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8").retrieve()
				.bodyToMono(Map.class).block();
		return response;
	}

	
	public Map<String, Object> getChallengers() {
		WebClient webClient = WebClient.builder()
				.baseUrl("https://kr.api.riotgames.com/lol/league/v4/challengerleagues/by-queue/RANKED_SOLO_5x5")
				.build();

		// api 요청
		Map<String, Object> response = webClient.get().uri(uriBuilder -> uriBuilder.build())
				.header("Origin", "https://developer.riotgames.com").header("X-Riot-Token", key)
				.header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
				.header("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8").retrieve()
				.bodyToMono(Map.class).block();

		return response;
	}

	public List<ChampionMasteryDto> getChampionMasteryTop3(String encryptedSummonerId) {
		WebClient webClient = WebClient.builder()
				.baseUrl("https://kr.api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-summoner/")
				.build();

		// api 요청
		List<ChampionMasteryDto> response = webClient.get().uri(uriBuilder -> 
				uriBuilder
				.path(encryptedSummonerId+"/top")
				.build()
				)
				.header("Origin", "https://developer.riotgames.com")
				.header("X-Riot-Token", key)
				.header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
				.header("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8")
				.retrieve()
				.bodyToMono(List.class)
				.block();
//		log.info(response.toString());
		
		return response;
	}

}
