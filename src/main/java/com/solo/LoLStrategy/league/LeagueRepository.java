package com.solo.LoLStrategy.league;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solo.LoLStrategy.league.Entity.League;
import com.solo.LoLStrategy.league.Entity.Seoson;
import com.solo.LoLStrategy.league.Entity.Summoner;

@Repository
public interface LeagueRepository extends JpaRepository<League, Integer>{
	
	List<League> findAllLeagueBySummoner(Summoner summoner);

}
