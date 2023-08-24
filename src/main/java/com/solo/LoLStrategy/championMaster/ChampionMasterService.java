package com.solo.LoLStrategy.championMaster;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solo.LoLStrategy.lol.LoLAPIService;
import com.solo.LoLStrategy.lol.VO.LeagueItemDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ChampionMasterService {
	
	@Autowired
	private LoLAPIService loLAPIService;
	public Map<String,Object> dd(){
	// 챌린저 티어 정보 얻기
		ObjectMapper mapper = new ObjectMapper();
		List<LeagueItemDTO> challengers = mapper.convertValue(loLAPIService.getChallengers().get("entries"), new TypeReference<List<LeagueItemDTO>>(){});
		
		Map<String,Object> championMasters = new HashMap<String, Object>();
		
		for (LeagueItemDTO challenger : challengers) {
			championMasters.put(challenger.getSummonerName(), loLAPIService.getChampionMasteryTop3(challenger.getSummonerId()));
		}
		
		return championMasters;
	}
}
