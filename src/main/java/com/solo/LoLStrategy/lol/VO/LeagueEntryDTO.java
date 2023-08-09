package com.solo.LoLStrategy.lol.VO;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Data;

@Data
public class LeagueEntryDTO {
	
	private String leagueId;
	private String summonerId;
	private String summonerName;
	
	@Enumerated(EnumType.STRING)
	private QueueType queueType;
	
	private String tier;
	private String rank;
	private Integer leaguePoints;
	private Integer wins;
	private Integer losses;
	private boolean hotStreak;
	private boolean veteran;
	private boolean freshBlood;
	private boolean inactive;
}
