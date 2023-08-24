package com.solo.LoLStrategy.league.Entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeosonRepository extends JpaRepository<Seoson, Long>{

	Seoson findBySeoson(String string);

}
