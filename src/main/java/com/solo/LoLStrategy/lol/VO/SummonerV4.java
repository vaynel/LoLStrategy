package com.solo.LoLStrategy.lol.VO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SummonerV4 {
	
	private String accountId;
	private Integer profileIconId;
	private long revisionData;
	private String name;
	private String id;
	private String puuid;
	private long summonerLevel;
}
