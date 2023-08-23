package com.solo.LoLStrategy.league.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;


@Entity
@Data
public class League {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String tier;
	private String tierRank;
	private String season;
	private Integer leaguePoints;
	
	@ManyToOne
	@JoinColumn(name = "SUMMONER_ID")
	private Summoner summoner;
}
