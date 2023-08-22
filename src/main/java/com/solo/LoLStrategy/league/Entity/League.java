package com.solo.LoLStrategy.league.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.solo.LoLStrategy.user.User;

import lombok.Data;

@Entity
@Data
public class League {
	
	@Id
	private Integer LeagueId;
	private String League;
	private String Tier;
	private String Season;
	
	@JoinColumn(name = "user")
	@ManyToOne
	private User user;
}
