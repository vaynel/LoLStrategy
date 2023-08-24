package com.solo.LoLStrategy.user;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solo.LoLStrategy.league.LeagueRepository;
import com.solo.LoLStrategy.league.SummonerRepository;
import com.solo.LoLStrategy.league.Entity.League;
import com.solo.LoLStrategy.league.Entity.Seoson;
import com.solo.LoLStrategy.league.Entity.SeosonRepository;
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
	public UserRepository userRepository;
	
	@Autowired
	public SeosonRepository seosonRepository;
	
	@Autowired
	public SummonerRepository summonerRepository;
	
	@Autowired
	public LeagueRepository leagueRepository;

	@Autowired
	public LoLAPIService lolAPIService;

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public void register(User user) {
		log.info(user.getGameId()+"회원가입중입니다.");
		user.setUserName(user.getGameId());
		user.setAlgorithm(EncryptionAlgorithm.BCRYPT);
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		user.setJoinDate(new Date());
		userRepository.save(user);
		Summoner summoner = lolAPIService.getSummonerV4ById(user.getGameId());
		summoner.setUser(user);
		summonerRepository.save(summoner);
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
		log.info(user.getGameId()+"소환사의 정보를 업데이트완료");
		
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

}
