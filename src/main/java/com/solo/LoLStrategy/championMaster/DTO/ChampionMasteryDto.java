package com.solo.LoLStrategy.championMaster.DTO;

import lombok.Data;

@Data
public class ChampionMasteryDto {

	private String puuid;
	private long championId;
	private Integer championLevel;
	private Integer championPoints;
	private long lastPlayTime;
	private long championPointsSinceLastLevel;
	private long championPointsUntilNextLevel;
	private boolean chestGranted;
	private Integer tokensEarned;
	private String summonerId;

}
