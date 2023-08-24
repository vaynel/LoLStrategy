package com.solo.LoLStrategy.user;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.security.core.Authentication;

import com.solo.LoLStrategy.league.Entity.Summoner;
import com.solo.LoLStrategy.user.security.Authority;
import com.solo.LoLStrategy.user.security.EncryptionAlgorithm;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class User {

	@Id
	@GeneratedValue
	private Integer id;

	private String userName;
	private String gameId;
	private String password;

	@Enumerated(EnumType.STRING)
	private EncryptionAlgorithm algorithm;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<Authority> authorities;
	private String email;
	private Date joinDate;

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Summoner summoner;

}
