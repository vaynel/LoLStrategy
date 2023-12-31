package com.solo.LoLStrategy.league;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solo.LoLStrategy.league.Entity.ChampionsUsed;
import com.solo.LoLStrategy.league.Entity.Summoner;

@Repository
public interface ChampionsUsedRepository extends JpaRepository<ChampionsUsed, Integer>{

	ChampionsUsed findBySummonerAndChampionName(Summoner summoner, String championName);

}
