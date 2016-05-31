package com.pt.pires.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Note {
	
	@GeneratedValue
	@Id
	private long id;
	
	@Column
	private String description;
	
	public Note(String description){
		setDescription(description);
	}
	
	public Note() { }	//Needed for JPA/JSON
	
	/* === Getters and Setters === */
	
	public long getId(){
		return this.id;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
}
