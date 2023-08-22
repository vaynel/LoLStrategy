package com.solo.LoLStrategy.league;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solo.LoLStrategy.league.Entity.League;

@Repository
public interface LeagueRepository extends JpaRepository<League, Integer>{
	
}
