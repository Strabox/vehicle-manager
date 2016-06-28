package com.pt.pires.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import org.apache.commons.validator.routines.EmailValidator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pt.pires.domain.exceptions.InvalidEmailException;
import com.pt.pires.domain.exceptions.InvalidPasswordException;
import com.pt.pires.domain.exceptions.InvalidUsernameException;

/**
 * User class represents a user in system
 * @author André
 *
 */
@Entity
public class User {

	private final static int MINIMUM_USERNAME_LENGTH = 4;
	
	public final static int MINIMUM_PASSWORD_LENGTH = 4;
	
	@Id
	@Column
	private String username;
	
	@Column
	@JsonIgnore
	private String encodedPassword;

	@Column
	private String email;
	
	@Enumerated(EnumType.STRING)
	private UserRole role;

	
	public User(String username,String encodedPassword,String email,UserRole role) 
			throws InvalidUsernameException, InvalidEmailException, InvalidPasswordException {
		setUsername(username);
		setPassword(encodedPassword);
		setEmail(email);
		setRole(role);
	}
	
	public User() { }	//Needed for JPA/JSON
	
	
	/* === Getters and Setters === */
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) throws InvalidUsernameException {
		if(username.length() < MINIMUM_USERNAME_LENGTH) {
			throw new InvalidUsernameException();
		}
		this.username = username;
	}
	
	public String getPassword() {
		return this.encodedPassword;
	}
	
	public void setPassword(String encodedPasswod) {
		this.encodedPassword = encodedPasswod;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) throws InvalidEmailException {
		if(!EmailValidator.getInstance().isValid(email)) {
			throw new InvalidEmailException();
		}
		this.email = email;
	}
	
	public UserRole getRole() {
		return role;
	}
	
	public void setRole(UserRole role) {
		this.role = role;
	}
	
}
