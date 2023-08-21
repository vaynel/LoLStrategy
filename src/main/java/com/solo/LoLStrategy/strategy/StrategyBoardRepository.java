package com.solo.LoLStrategy.strategy;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solo.LoLStrategy.strategy.DTO.StrategyBoard;

@Repository
public interface StrategyBoardRepository extends JpaRepository<StrategyBoard, Integer> {
	
	List<StrategyBoard> findAllStrategyBoardByChampion(String champion);

	StrategyBoard findStrategyBoardById(String id);
}
