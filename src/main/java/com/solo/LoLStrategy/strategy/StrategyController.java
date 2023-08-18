package com.solo.LoLStrategy.strategy;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class StrategyController {
	
	
	@RequestMapping(value = "/strategy/{champion}")
	public String champion(@PathVariable("champion") String champion, Model model) {
		log.info(champion+" 챔피언 공략");
		model.addAttribute("champion", champion);
		
		
		return "strategy.html";
	}
	
	@RequestMapping(value = "/strategy/add",method = RequestMethod.GET)
	public String addStrategy() {
		log.info("공략 작성");
		
		return "addForm.html";
	}
	
	@RequestMapping(value = "/strategy/add",method = RequestMethod.POST)
	public String addPostStrategy() {
		log.info("공략 작성");
		
		return "strategy.html";
	}

}
