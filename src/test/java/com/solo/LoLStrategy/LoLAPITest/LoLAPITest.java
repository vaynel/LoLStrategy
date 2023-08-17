package com.solo.LoLStrategy.LoLAPITest;

import java.io.IOException;
import java.net.URL;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solo.LoLStrategy.lol.LoLAPIService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class LoLAPITest {
	
	@Autowired
    private LoLAPIService LoLAPIService;
	
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

}
