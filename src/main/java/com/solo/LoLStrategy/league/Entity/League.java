package com.solo.LoLStrategy.league.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.solo.LoLStrategy.user.User;

import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class League {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String tier;
	private String rank;
	private String season;
	private Integer leaguePoints;
	
	@JoinColumn(name = "user")
	@ManyToOne
	private User user;
}
