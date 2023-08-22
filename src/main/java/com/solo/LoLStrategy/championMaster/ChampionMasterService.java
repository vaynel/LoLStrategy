package com.solo.LoLStrategy.championMaster;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solo.LoLStrategy.lol.LoLAPIService;
import com.solo.LoLStrategy.lol.VO.LeagueItemDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ChampionMasterService {
	
	@Autowired
	private LoLAPIService loLAPIService;

	
	public void getCHALLENGERTierUsers() {
		List<LeagueItemDTO> challengers = loLAPIService.CHALLENGERTierUsers();
		Map<String,Object> ChampionMasteryTop3 = new HashMap<String, Object>();
		
		for (int i = 0; i < 3; i++) {
			log.info(challengers.get(i).getSummonerId()+"->"+loLAPIService.returnChampionMasteryTop3(challengers.get(i).getSummonerId()));
			ChampionMasteryTop3.put(challengers.get(i).getSummonerId(), loLAPIService.returnChampionMasteryTop3(challengers.get(i).getSummonerId()));
			
		}
		
//		for (LeagueItemDTO challenger : challengers) {
//			ChampionMasteryTop3.put(challenger.getSummonerId(),loLAPIService.returnChampionMasteryTop3(challenger.getSummonerId()));
//		}
//		
//		for (LeagueItemDTO challenger : challengers) {
//			log.info((String) ChampionMasteryTop3.get(challenger.getSummonerId()));
//		}
		
	}
}
