package com.solo.LoLStrategy.user;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solo.LoLStrategy.league.ChampionsUsedRepository;
import com.solo.LoLStrategy.league.LeagueRepository;
import com.solo.LoLStrategy.league.MatchListRepoistory;
import com.solo.LoLStrategy.league.SeosonRepository;
import com.solo.LoLStrategy.league.SummonerRepository;
import com.solo.LoLStrategy.league.Entity.ChampionsUsed;
import com.solo.LoLStrategy.league.Entity.League;
import com.solo.LoLStrategy.league.Entity.MatchList;
import com.solo.LoLStrategy.league.Entity.Seoson;
import com.solo.LoLStrategy.league.Entity.Summoner;
import com.solo.LoLStrategy.lol.LoLAPIService;
import com.solo.LoLStrategy.lol.VO.LeagueEntryDTO;
import com.solo.LoLStrategy.lol.VO.ParticipantDTO;
import com.solo.LoLStrategy.lol.VO.QueueType;
import com.solo.LoLStrategy.user.security.EncryptionAlgorithm;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	@Autowired
	public MatchListRepoistory matchListRepoistory;
	
	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	public SeosonRepository seosonRepository;
	
	@Autowired
	public SummonerRepository summonerRepository;
	
	@Autowired
	public LeagueRepository leagueRepository;
	
	@Autowired
	public ChampionsUsedRepository championsUsedRepository;

	@Autowired
	public LoLAPIService lolAPIService;

	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	private static Seoson thisSeoson = new Seoson(1,"13-2");

	public void register(User user) {
		log.info(user.getGameId()+"회원가입중입니다.");
		// 유저 정보 저장하기 
		user.setUserName(user.getGameId());
		user.setAlgorithm(EncryptionAlgorithm.BCRYPT);
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		user.setJoinDate(new Date());
		userRepository.save(user);
		// 소환사 저장하기 
		Summoner summoner = lolAPIService.getSummonerV4ById(user.getGameId());
		summoner.setUser(user);
		summonerRepository.save(summoner);
		// 소환사 매치 정보 저장
		MatchList match = new MatchList();
		match.setSummoner(summoner);
		matchListRepoistory.save(match);
		log.info(summoner.getUser().getGameId()+"등록완료");
	}
	
	
	//최근에 사용한 챔피언과 몇번 사용했는지 반환
	public String returnRecentlyChampions(ParticipantDTO[] myParticipants) {
		
		String result ="";
		
		
		String[] champions = new String[20];
		for (int i = 0; i < champions.length; i++) {
			champions[i]=myParticipants[i].getChampionName();
		}
		Set<String> championsSet = new HashSet<String>(Arrays.asList(champions));
		Integer[] championCount = new Integer[championsSet.size()];
		for (int i = 0; i < championCount.length; i++) {
			championCount[i]=0;
		}
		
		String[] championsSets = championsSet.toArray(new String[championsSet.size()]);
		
		for (String champion : champions) {
			for (int i = 0; i < championsSets.length; i++) {
				if(championsSets[i].equals(champion)) {
					championCount[i]++;

				}	
			}
		}

		for (int i = 0; i < championsSets.length; i++) {
			result+=(championsSets[i]+" - " + championCount[i]+ " / ");
			
		}
		return result;
	}
	
	
	// matchId를 통해서 나의 전적을 받아옴
	public ParticipantDTO[] getMatchListDetails(String[] matchList, Summoner myRiotAccount) {

		ParticipantDTO[] myParticipants = new ParticipantDTO[20];
		ParticipantDTO myParticipant = new ParticipantDTO();

		// participantDTO로 바꾸기
		for (int i = 0; i < matchList.length; i++) {
			Map<String, Object> info = (Map<String, Object>) lolAPIService.findMatchesByMatchId(matchList[i])
					.get("info");
			List<Object> test = (List<Object>) info.get("participants");
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<ParticipantDTO> participants = mapper.convertValue(test, new TypeReference<List<ParticipantDTO>>() {
			});
			for (ParticipantDTO participant : participants) {
				if (participant.getSummonerName().equals(myRiotAccount.getName())) {
					myParticipant = participant;
					break;
				}
			}
			myParticipants[i] = myParticipant;
		}

		return myParticipants;
	}
	
	// 소환사의 정보를 업데이트 -> league 정보를 업데이트 
	public void updateUserData(User user ) {
		log.info(user.getGameId()+"소환사의 정보를 업데이트합니다");
		Summoner summoner = summonerRepository.findSummonerByUser(user);
		updateLeagueDataBySummoner(summoner); // 리그 정보를 업데이트 
		log.info(user.getGameId()+"소환사의 정보를 업데이트완료");
	}
	
	// 소환사의 정보를 업데이트
	public void updateSummonerData(Summoner summoner) {
		log.info(summoner.getName()+"소환사의 정보를 업데이트합니다");
		updateLeagueDataBySummoner(summoner); // 리그 정보를 업데이트 
		updateChampionsUsedBySummoner(summoner);  // 리스에서 사용한 챔피언 정보 업데이트 
	}
	
	
	

	

	// 소환사를 user로 찾음
	public Summoner getSummonerById(User user) {
		return summonerRepository.findSummonerByUser(user);
	}
	
	// 소환사를 name으로 찾음
	public Summoner getSummonerByGameId(String name) {
		return summonerRepository.findSummonerByName(name);
	}
	
	
	// 리그 정보를 소환사와 시즌을 검색해서 찾음
	public League getLeagueBySummoner(Summoner summoner) {
		List<League> LeagueList = leagueRepository.findAllLeagueBySummoner(summoner);
		League thisLeague = new League();
		for (League league : LeagueList) {
			if(league.getSeason().getSeoson().equals("13-2")) {
				thisLeague=league;
				return thisLeague;
			}
		}
		return thisLeague;
		
	}
	
	// 리그 정보를 업데이트 합니다.
	private void updateLeagueDataBySummoner(Summoner summoner) {
		
		LeagueEntryDTO[] LeagueEntries = lolAPIService.getLeagueV4BySummonerId(summoner.getId());
		LeagueEntryDTO leagueEntry = new LeagueEntryDTO();
		for (LeagueEntryDTO leagueE : LeagueEntries) {
			if(leagueE.getQueueType().equals(QueueType.RANKED_SOLO_5x5)) leagueEntry=leagueE;
		}
		
		
		League league= League.builder()
				.summoner(summoner)
				.leaguePoints(leagueEntry.getLeaguePoints())
				.tierRank(leagueEntry.getRank())
				.tier(leagueEntry.getTier())
				.wins(leagueEntry.getWins())
				.losses(leagueEntry.getLosses())
				.season(seosonRepository.findBySeoson("13-2"))
				.build();
		
		leagueRepository.save(league);
	}
	
	// 챔피언 데이터를 업데이트 합니다 .
	public void updateChampionsUsedBySummoner(Summoner summoner) {
		String[] Matchs = lolAPIService.returnMatchList(summoner.getPuuid()); // 소환사가 최근에 한 게임 매치 List(20개)
		ParticipantDTO[] myParticipants=getMatchListDetails(Matchs,summoner);
		
		for (int i = 19; i >= 0; i--) {
			MatchList matchList = new MatchList();	
			if(checkMatchId(Matchs[i],summoner)) {
				matchList = matchListRepoistory.findBySummoner(summoner);
				matchList.setMatchId(Matchs[i]);
				matchListRepoistory.save(matchList);
				ChampionsUsed championsUsed = new ChampionsUsed(myParticipants[i]);

					
					Optional<ChampionsUsed> CurrentchampionsUsed = Optional.ofNullable(championsUsedRepository.findBySummonerAndChampionName(summoner,myParticipants[i].getChampionName()));
					//CurrentchampionsUsed가 null일때 새로운 값을 넣는다.
					if(!CurrentchampionsUsed.isPresent()) {
						championsUsed.setSummoner(summoner);
						championsUsed.setPlayGames(1);
						championsUsed.setSeason(thisSeoson);
						if(myParticipants[i].getWin()) {
							championsUsed.setWins(1);
							championsUsed.setLosses(0);
						}
						else {
							championsUsed.setLosses(1);
							championsUsed.setWins(0);
						}
						
						championsUsedRepository.save(championsUsed);
						continue;
					}
					else {
						// null이 아닌경우 정보를 업데이트를 한다. 
						CurrentchampionsUsed.get().setAssists(CurrentchampionsUsed.get().getAssists()+championsUsed.getAssists());
						CurrentchampionsUsed.get().setDeaths(CurrentchampionsUsed.get().getDeaths()+championsUsed.getDeaths());
						CurrentchampionsUsed.get().setKills(CurrentchampionsUsed.get().getKills()+championsUsed.getKills());
						CurrentchampionsUsed.get().setPlayGames(CurrentchampionsUsed.get().getPlayGames()+1);
						CurrentchampionsUsed.get().setGoldSpent(CurrentchampionsUsed.get().getGoldSpent()+championsUsed.getGoldSpent());
						CurrentchampionsUsed.get().setSummoner(summoner);
						if(myParticipants[i].getWin()) {
							CurrentchampionsUsed.get().setWins(CurrentchampionsUsed.get().getWins()+1);
						}
						else {
							CurrentchampionsUsed.get().setLosses(CurrentchampionsUsed.get().getLosses()+1);
						}
						championsUsedRepository.save(CurrentchampionsUsed.get());
					}				
			}
		}
		
		
		  
	}
	
	
	// 배치 번호 확인하기 
	public boolean checkMatchId(String matchs, Summoner summoner) {
		try {
			MatchList matchList = matchListRepoistory.findBySummoner(summoner);
			return Long.valueOf(matchs.substring(3)) > Long.valueOf(matchList.getMatchId().substring(3));
		}
		finally {
			return true;
		}
		
		
	}

	// 최근데이터가 같은지 알려줌 
	public boolean checkRecentlyMatch(Summoner myRiotAccount) {
		String recentlyMatch = lolAPIService.returnRecentlyMatchId(myRiotAccount.getPuuid());
		MatchList dataMatchId = matchListRepoistory.findBySummoner(myRiotAccount);
		return recentlyMatch.equals(dataMatchId.getMatchId());
	}


	// 소환사 검색하기 
	public Summoner searchSummoner(String summoner) {
		Optional<Summoner> DBsummoner = Optional.ofNullable(summonerRepository.findSummonerByName(summoner));
		if(DBsummoner.isEmpty()) {
			log.info(summoner+"가 DB에 없기에 새로 등록합니다.");
			Summoner newSummoner = lolAPIService.getSummonerV4ById(summoner);
			summonerRepository.save(newSummoner);
			MatchList match = new MatchList();
			match.setSummoner(newSummoner);
			matchListRepoistory.save(match);
			log.info(newSummoner.getName()+"등록완료");
			return newSummoner;
		}
		log.info(DBsummoner+"를 반환합니다.");
		return DBsummoner.get();
		
	}

}
