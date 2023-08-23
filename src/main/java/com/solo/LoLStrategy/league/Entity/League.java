package com.solo.LoLStrategy.league.Entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String tier;
	private String rank;
	private String season;
	private Integer leaguePoints;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	private User user;
}
