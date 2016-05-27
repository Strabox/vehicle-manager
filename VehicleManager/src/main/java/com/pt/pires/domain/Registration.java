package com.pt.pires.domain;

import java.util.Date;

public class Registration {

	private String description;
	
	private Date date;
	
	public Registration(String Description,Date date){
		setDescription(Description);
	}
	
	/* === Getters and Setters === */
	
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
