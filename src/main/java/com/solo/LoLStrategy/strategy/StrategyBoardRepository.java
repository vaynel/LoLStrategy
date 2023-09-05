package com.solo.LoLStrategy.strategy;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solo.LoLStrategy.strategy.DTO.StrategyBoardDTO;

@Repository
public interface StrategyBoardRepository extends JpaRepository<StrategyBoardDTO, Integer> {
	
	List<StrategyBoardDTO> findAllStrategyBoardByChampion(String champion);

	StrategyBoard findStrategyBoardById(Integer id);
}
