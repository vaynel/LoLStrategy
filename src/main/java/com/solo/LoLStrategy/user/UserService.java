package com.solo.LoLStrategy.user;

import java.util.ArrayList;
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
import com.solo.LoLStrategy.lol.LoLAPIService;
import com.solo.LoLStrategy.lol.VO.ParticipantDTO;
import com.solo.LoLStrategy.lol.VO.SummonerDTO;
import com.solo.LoLStrategy.user.security.EncryptionAlgorithm;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	@Autowired
	public UserRepository userRepository;

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
	
	

	public ParticipantDTO[] getMatchListDetails(String[] matchList, SummonerDTO myRiotAccount) {

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

}
