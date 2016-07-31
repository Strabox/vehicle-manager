package com.pt.pires.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Login saved with the "Remember Me" feature at login time
 * @author André
 *
 */
@Entity
@Table(name = "persistent_logins")	//Name required by Spring Security
public class RememberedLogin {

	@Column
	private String username;
	
	@Id
	private String series;
	
	@Column
	private String token;
	
	@Column(name = "last_used")		//Name required by Spring Security
	private Timestamp timestamp;
	
	
	public RememberedLogin(String username,String series,String token,Timestamp timestamp) {
		setUsername(username);
		setSeries(series);
		setToken(token);
		setTimestamp(timestamp);
	}
	
	public RememberedLogin() { }	//Needed for JPA/JSON


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
}
