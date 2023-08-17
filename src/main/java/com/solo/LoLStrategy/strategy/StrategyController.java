package com.solo.LoLStrategy.strategy;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/strategy")
public class StrategyController {
	
	
	@RequestMapping("/champion")
	public String champion() {
		log.info("어떤 챔피언 이냐?");
		
		return "champion";
	}

}
