package com.solo.LoLStrategy.league.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.solo.LoLStrategy.lol.VO.ParticipantDTO;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ChampionsUsed {


	
	@Id
	@GeneratedValue
	private Integer idx;

	private String summonerName;
	private String championName;
	
	private Integer kills;
	private Integer assists;
	private Integer deaths;

	private Integer goldSpent;

	private Integer playGames;
	private Integer wins;
	private Integer losses;
	
	
	@ManyToOne
	@JoinColumn(name = "SEOSON")
	private Seoson season;
	
	@ManyToOne
	@JoinColumn(name = "summoner")
	private Summoner summoner;
	
	public ChampionsUsed(ParticipantDTO participantDTO) {
		this.championName = participantDTO.getChampionName();
		this.goldSpent = participantDTO.getGoldSpent();
		this.kills = participantDTO.getKills();
		this.deaths = participantDTO.getDeaths();
		this.assists = participantDTO.getAssists();
		this.summonerName = participantDTO.getSummonerName();
	}

	
}
