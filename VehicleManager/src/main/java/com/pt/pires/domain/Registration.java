package com.pt.pires.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

@Entity
public class Registration {

	@GeneratedValue
	@Id
	private long id;
	
	@Column
	private long time;
	
	@Column
	private String description;
	
	@Column
	@Type(type="date")
	private Date date;
	
	public Registration(long time,String Description,Date date){
		setTime(time);
		setDescription(Description);
		setDate(date);
	}
	
	public Registration() { } //Needed for JPA/JSON
	
	/* === Getters and Setters === */
	
	public long getId(){
		return this.id;
	}
	
	public long getTime(){
		return this.time;
	}
	
	public void setTime(long time){
		this.time = time;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public Date getDate(){
		return this.date;
	}
	
	public void setDate(Date date){
		this.date = date;
	}
	
}
