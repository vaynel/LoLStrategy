package com.solo.LoLStrategy.LoLAPITest;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solo.LoLStrategy.championMaster.ChampionMasterService;
import com.solo.LoLStrategy.lol.LoLAPIService;
import com.solo.LoLStrategy.lol.VO.LeagueItemDTO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class LoLAPITest {
	
	@Autowired
    private LoLAPIService LoLAPIService;
	
	@Autowired
    private ChampionMasterService championMasterService;
	
	@Test
	public void getJsonData() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			System.out.println(mapper.readTree(new URL("https://ddragon.leagueoflegends.com/cdn/13.15.1/data/ko_KR/champion.json")));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	@Test
	public void receiveChampioneData() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode  request = (JsonNode) mapper.readTree(new URL("https://ddragon.leagueoflegends.com/cdn/13.15.1/data/ko_KR/champion.json"));
			JsonNode champions = request.get("data");
			String champion = champions.get("Aatrox").toPrettyString();
			//이미지 url  https://ddragon.leagueoflegends.com/cdn/13.15.1/img/champion/Aatrox.png
			log.info(request.toPrettyString());
			log.info(champion);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void CHALLENGERTierUsers() {
		WebClient webClient =
				WebClient
				.builder()
				.baseUrl("https://kr.api.riotgames.com/lol/league/v4/challengerleagues/by-queue/RANKED_SOLO_5x5")
				.build();
		
		Map<String,Object> response =
                webClient
		        .get()
		        .uri(uriBuilder ->
		                uriBuilder
		                        .build())
		        .header("X-Riot-Token", "RGAPI-bc3ed4b0-3744-473a-8bfe-65f9d1b435e8")
		        .header("Origin", "https://developer.riotgames.com")
		        .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
		        .header("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8")
		        .retrieve()
		        .bodyToMono(Map.class)
		        .block();
		ObjectMapper mapper = new ObjectMapper();
		List<LeagueItemDTO> leagueItems = mapper.convertValue(response.get("entries"),new TypeReference<List<LeagueItemDTO>>() {}); 
		log.info(response.get("entries").toString());
		log.info(leagueItems.get(1).toString());
				
				
	}
	
	@Test
	public void ChampionMasteryTop3() {
		championMasterService.getCHALLENGERTierUsers();
	}

}
