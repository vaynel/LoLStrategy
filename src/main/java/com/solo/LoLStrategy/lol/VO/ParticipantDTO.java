package com.solo.LoLStrategy.lol.VO;

import lombok.Data;

@Data
public class ParticipantDTO {

	private String summonerName;
	
	private String championName;
	
	private Integer kills;
	private Integer assists;
	private Integer deaths;
	
	private Integer champExperience;
	private Integer champLevel;
	private Integer goldSpent;
	private Integer item0;
	private Integer item1;
	private Integer item2;
	private Integer item3;
	private Integer item4;
	private Integer item5;
	private Integer item6;
	
	private Boolean win;
	
	
	
}
