package com.solo.LoLStrategy.championMaster;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.solo.LoLStrategy.common.champion.Champions;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/championMastery")
@Slf4j
public class ChampionMasterController {
	
	@Autowired
	private ChampionMasterService championMasterService;
	
	@RequestMapping(value = "/{champion}")
	public String championMastery(@PathVariable("champion") String champion, Model model) {
		log.info(champion+" 챔피언 장인");
		List<Champions> champions = Stream.of(Champions.values()).collect(Collectors.toList());
		model.addAttribute("champion", champion);
		model.addAttribute("champions", champions);
		model.addAttribute("where","championMastery");
		return "championMastery.html";
	}
}
