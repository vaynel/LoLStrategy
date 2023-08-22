package com.solo.LoLStrategy.lol.VO;

import lombok.Data;

@Data
public class LeagueItemDTO {
	
	private String summonerId;
	private String summonerName;
	private Integer leaguePoints;
	private String rank;
	private Integer wins;
	private Integer losses;
	private boolean veteran;
	private boolean inactive;
	private boolean freshBlood;
    private boolean hotStreak;
}
