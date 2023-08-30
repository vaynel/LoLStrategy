package com.solo.LoLStrategy.strategy.DTO;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.solo.LoLStrategy.common.board.Board;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "strategyBoard")
@EqualsAndHashCode(callSuper = true)
public class StrategyBoardDTO extends Board {

	private Integer id;
	private String champion;

}
