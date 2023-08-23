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
	public SummonerRepository summonerRepository;
	
	@Autowired
	public LeagueRepository leagueRepository;

	@Autowired
	public LoLAPIService lolAPIService;

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public void register(User user) {
		user.setAlgorithm(EncryptionAlgorithm.BCRYPT);
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		user.setJoinDate(new Date());
		userRepository.save(user);
		Summoner summoner = lolAPIService.getSummonerV4ById(user.getGameId());
		summoner.setUser(user);
		summonerRepository.save(summoner);
		log.info(summoner.getUser().getGameId()+"등록완료");
	}
	
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
	
	
	public void updateUserData(User user ) {
		log.info(user.getGameId()+"소환사의 정보를 업데이트합니다");
		Summoner summoner = summonerRepository.findSummonerByUser(user);
		
		LeagueEntryDTO[] LeagueEntries = lolAPIService.getLeagueV4BySummonerId(summoner.getId());
		LeagueEntryDTO leagueEntry = new LeagueEntryDTO();
		for (LeagueEntryDTO leagueE : LeagueEntries) {
			if(leagueE.getQueueType().equals(QueueType.RANKED_SOLO_5x5)) leagueEntry=leagueE;
		}
		
		log.info(leagueEntry.toString());
		League league= League.builder()
				.summoner(summoner)
				.leaguePoints(leagueEntry.getLeaguePoints())
				.tierRank(leagueEntry.getRank())
				.tier(leagueEntry.getTier())
				.season("13-2")
				.build();
		
		leagueRepository.save(league);
		
	}

}
