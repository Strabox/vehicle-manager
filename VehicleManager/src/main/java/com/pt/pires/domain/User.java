package com.pt.pires.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {

	@Id
	@Column
	private String username;
	
	@Column
	@JsonIgnore
	private String password;
	
	@Enumerated(EnumType.STRING)
	private UserRole role;
	
	public User(String username,String password){
		setUsername(username);
		setPassword(password);
	}
	
	public User() { }	//Needed for JPA/JSON
	
	
	/* === Getters and Setters === */
	
	public String getUsername(){
		return this.username;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public UserRole getRole(){
		return role;
	}
	
	public void setRole(UserRole role){
		this.role = role;
	}
	
}
