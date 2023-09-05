package com.solo.LoLStrategy.league.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class League {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String tier;
	private String tierRank;
	private Integer leaguePoints;
	private Integer wins;
	private Integer losses;

	@ManyToOne
	@JoinColumn(name = "SEOSON_ID")
	private Seoson season;
	
	@ManyToOne
	@JoinColumn(name = "SUMMONER_ID")
	private Summoner summoner;

}
