package com.solo.LoLStrategy.league.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.solo.LoLStrategy.user.User;

import lombok.Data;

@Data
@Entity
public class Summoner {
	
	@Id
	private String id;
	private String accountId; 
	private String puuid; 
	private String name; 
	private Integer profileIconId; 
	private long revisionDate; 
	private long summonerLevel; 
	
	@OneToMany(mappedBy = "summoner")
	private List<League> LeagueList = new ArrayList<League>();
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id",referencedColumnName = "id")
	private User user;

}
