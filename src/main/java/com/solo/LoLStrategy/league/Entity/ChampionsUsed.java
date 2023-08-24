package com.solo.LoLStrategy.league.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ChampionsUsed {

//	@ManyToOne
//	@JoinColumn(name = "summoner")
//	private Summoner summoner;
	
	@Id
	private Long index;
	
	private String matchId;
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
