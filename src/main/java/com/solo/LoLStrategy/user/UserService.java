package com.solo.LoLStrategy.user;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

	public ParticipantDTO getMatchListDetails(String[] matchList, SummonerDTO myRiotAccount) {

		// participantDTO로 바꾸기
		Map<String, Object> info = (Map<String, Object>) lolAPIService.findMatchesByMatchId(matchList[0]).get("info");
		List<Object> test = (List<Object>) info.get("participants");

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		List<ParticipantDTO> participants = mapper.convertValue(test, new TypeReference<List<ParticipantDTO>>(){});
		ParticipantDTO myParticipant = new ParticipantDTO();
		for (ParticipantDTO participant : participants) {
			if(participant.getSummonerName().equals(myRiotAccount.getName())) {
				myParticipant=participant;
				break;
			}
		}
		log.info(myParticipant.toString());
		return myParticipant;
	}

}
