package com.solo.LoLStrategy.league;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solo.LoLStrategy.league.Entity.Summoner;
import com.solo.LoLStrategy.league.Entity.MatchList;

public interface MatchListRepoistory extends JpaRepository<MatchList, Integer>{

	MatchList findBySummoner(Summoner summoner);

}
