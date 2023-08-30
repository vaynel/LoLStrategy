package com.solo.LoLStrategy.league;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solo.LoLStrategy.league.Entity.Summoner;
import com.solo.LoLStrategy.user.User;

@Repository
public interface SummonerRepository extends JpaRepository<Summoner, String>{
	
	Summoner findSummonerByUser(User user);

	Summoner findSummonerByName(String name);
	
}
