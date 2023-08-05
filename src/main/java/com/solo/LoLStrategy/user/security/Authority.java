package com.solo.LoLStrategy.user.security;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.solo.LoLStrategy.user.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Authority {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	@JoinColumn(name = "user")
	@ManyToOne
	private User user;
	

}
