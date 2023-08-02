package com.solo.LoLStrategy.user;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User {

	@Id
	private String id;
	private String password;
	private String authority;
	private String email;
	private String username;
	private Date joinDate;
	
}
