package com.solo.LoLStrategy.league.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Seoson {

	@Id
	@GeneratedValue
	private Integer id;
	private String seoson;
	
	@OneToMany(mappedBy = "summoner")
	private List<League> LeagueList = new ArrayList<League>();
}
