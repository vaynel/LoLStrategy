package com.solo.LoLStrategy.strategy;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.solo.LoLStrategy.common.board.BoardService;
import com.solo.LoLStrategy.strategy.DTO.StrategyBoard;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class StrategyController {
	
	@Autowired
	private BoardService boardService;
	
	
	@RequestMapping(value = "/strategy/{champion}")
	public String champion(@PathVariable("champion") String champion, Model model) {
		List<StrategyBoard> BoardList = boardService.findBoardByChampion(champion);
		log.info(champion+" 챔피언 공략");
		log.info(BoardList.toString());
		model.addAttribute("champion", champion);
		model.addAttribute("BoardList", BoardList);
		
		
		return "strategy.html";
	}
	
	@RequestMapping(value = "/strategy/{champion}/detail/{id}")
	public String boardDetail(@PathVariable("champion")String champion,@PathVariable("id")String id,Model model) {
		StrategyBoard board = boardService.findBoardById(id);
		
		
		return "strategyBoard.html";
	}
	
	@RequestMapping(value = "/strategy/add",method = RequestMethod.GET)
	public String addStrategy() {
		log.info("공략 작성");
		
		return "addForm.html";
	}
	
	@RequestMapping(value = "/strategy/add",method = RequestMethod.POST)
	@ResponseBody
	public String addPostStrategy(StrategyBoard board,Authentication a)  {
		log.info("게시글 등록하기");
		board.setWriter(a.getName());
		board.setChampion("vayne");
		board.setDate(new Date());
		log.info(board.toString());
		boardService.saveBoard(board);
		
		return "strategy.html"; 
	}
	
	
	

}
