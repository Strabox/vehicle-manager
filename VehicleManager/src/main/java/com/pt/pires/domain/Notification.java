package com.pt.pires.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Type;


@Entity
public abstract class Notification {

	@GeneratedValue
	@Id
	private long id;
	
	@Column
	@Type(type="date")
	private Date notiDate;
	
	@Column
	private String description;
	
	
	public Notification(Date initDate,String description){
		setNotiDate(initDate);
		setDescription(description);
	}
	
	public Notification() { }
	
	public abstract boolean notifyDay(Date currentDate);
	
	
	/* === Getters and Setters === */
	
	public long getId(){
		return this.id;
	}
	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getNotiDate() {
		return notiDate;
	}

	public void setNotiDate(Date initDate) {
		this.notiDate = initDate;
	}
	
}
