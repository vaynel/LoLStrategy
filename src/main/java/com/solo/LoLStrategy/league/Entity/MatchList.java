package com.solo.LoLStrategy.league.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MatchList {

	@Id
	@GeneratedValue
	private Integer idx;
	private String matchId;
	
	@OneToOne
	@JoinColumn(name = "summoner")
	private Summoner summoner;
}
