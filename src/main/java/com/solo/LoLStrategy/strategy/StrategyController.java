package com.solo.LoLStrategy.strategy;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.solo.LoLStrategy.common.board.BoardService;
import com.solo.LoLStrategy.common.champions.Champions;
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
		List<Champions> champions = Stream.of(Champions.values()).collect(Collectors.toList());
		log.info(champion+" 챔피언 공략");
		model.addAttribute("champion", champion);
		model.addAttribute("BoardList", BoardList);
		model.addAttribute("champions", champions);
		return "strategy.html";
	}
	
	@RequestMapping(value = "/strategy/{champion}/detail")
	public String boardDetail(@PathVariable("champion")String champion,@RequestParam("id")Integer id,Model model) {
		StrategyBoard board = boardService.findBoardById(id);
		model.addAttribute("board", board);
		
		
		return "strategyBoard.html";
	}
	
	@RequestMapping(value = "/strategy/add",method = RequestMethod.GET)
	public String addStrategy(@RequestParam("champion") String champion,Model model) {
		log.info(champion+" 공략 작성");
		model.addAttribute("champion", champion);
		return "addForm.html";
	}
	
	@RequestMapping(value = "/strategy/add",method = RequestMethod.POST)
	@ResponseBody
	public RedirectView addPostStrategy(StrategyBoard board,Authentication a,@RequestParam("champion") String champion)  {
		log.info("게시글 등록하기");
		board.setWriter(a.getName());
		board.setChampion(champion);
		board.setDate(new Date());
		boardService.saveBoard(board);
		return new RedirectView("/strategy/"+champion);
	}
	

	
	
	

}
