package com.solo.LoLStrategy.user;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.solo.LoLStrategy.user.security.EncryptionAlgorithm;

@Service
public class UserService {

	@Autowired
	public UserRepository userRepository;
	
	public List<User> findAll() {
		return userRepository.findAll();
	}

	public void register(User user) {
		user.setAlgorithm(EncryptionAlgorithm.BCRYPT);
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		user.setJoinDate(new Date());
		userRepository.save(user);
	}

}
