package com.solo.LoLStrategy.championMaster.DTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.solo.LoLStrategy.common.champion.Champions;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "championMaster")
public class ChampionMaster {
	
	@Id
	@Column(name = "masterId")
	private Long index;
	
	@Enumerated(EnumType.STRING)
	private Champions champion;
	private String summonerId;
	private String tier;
	private Integer win;
	private Integer loss;
	private Integer gamePlay;
	private Integer championPoints;

}
