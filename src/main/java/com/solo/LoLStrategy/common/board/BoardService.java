package com.solo.LoLStrategy.common.board;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solo.LoLStrategy.strategy.StrategyBoardRepository;
import com.solo.LoLStrategy.strategy.DTO.StrategyBoard;

@Service
public class BoardService {
	
	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	StrategyBoardRepository strategyBoardRepository;

	public void saveBoard(StrategyBoard board) {
		boardRepository.save(board);
			
	}

	public List<StrategyBoard> findBoardByChampion(String Champion) {
		
		return strategyBoardRepository.findAllStrategyBoardByChampion(Champion);
	}

	public StrategyBoard findBoardById(String id) {
		
		return strategyBoardRepository.findStrategyBoardById(id);
	}

}