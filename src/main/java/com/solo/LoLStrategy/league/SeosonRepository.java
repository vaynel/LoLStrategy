package com.solo.LoLStrategy.league;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solo.LoLStrategy.league.Entity.Seoson;

@Repository
public interface SeosonRepository extends JpaRepository<Seoson, Integer>{

	Seoson findBySeoson(String string);

}

