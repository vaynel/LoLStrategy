package com.solo.LoLStrategy.user;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.solo.LoLStrategy.league.Entity.League;
import com.solo.LoLStrategy.user.security.Authority;
import com.solo.LoLStrategy.user.security.EncryptionAlgorithm;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String userName;
	private String gameId;
	private String password;
	
	@Enumerated(EnumType.STRING)
	private EncryptionAlgorithm algorithm;
	
	@OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
	private List<Authority> authorities;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
	private List<League> leagues;
	
	private String email;
	private Date joinDate;
	
	
}
