package com.pt.pires.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

import com.pt.pires.util.DateUtil;

/**
 * Abstract class Vehicle 
 * @author André
 *
 */
@Entity
public abstract class Vehicle {

	@Id
	private String name;
	
	@Column
	private String brand;
	
	@Column
	@Type(type="date")
	private Date acquisitionDate; 
	
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
	private Collection<Registration> registries = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
	private Collection<Note> notes = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
	private Collection<Notification> notifications = new ArrayList<>();
	
	
	public Vehicle(String name,String brand,Date acquisitionDate){
		setName(name);
		setBrand(brand);
		setAcquisitionDate(acquisitionDate);
	}
	
	public Vehicle() { }	//Needed for JPA/JSON	
	
	public void addRegistration(Registration reg){
		registries.add(reg);
	}
	
	public void addNote(Note note){
		notes.add(note);
	}
	
	public void addNotification(Notification notification){
		notifications.add(notification);
	}
	
	public void removeRegistration(long id){
		for(Registration reg : registries){
			if(reg.getId() == id){
				registries.remove(reg);
				break;
			}
		}
	}
	
	public void removeNote(long id){
		for(Note note : notes){
			if(note.getId() == id){
				notes.remove(note);
				break;
			}
		}
	}
	
	public int calculateAcquisitionYears(){
		Calendar a = DateUtil.getCalendar(acquisitionDate);
	    Calendar b = DateUtil.getCalendar(new Date());
	    int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
	    if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) || 
	        (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
	        diff--;
	    }
	    return diff;
	}
	
	/* === Getters and Setters === */
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getBrand(){
		return this.brand;
	}
	
	public void setBrand(String brand){
		this.brand = brand;
	}
	
	public Date getAcquisitionDate(){
		return this.acquisitionDate;
	}
	
	public void setAcquisitionDate(Date date){
		this.acquisitionDate = date;
	}
	
	public Collection<Registration> getRegistries(){
		return this.registries;
	}
	
	public Collection<Note> getNotes(){
		return this.notes;
	}
	
	public Collection<Notification> getNotifications(){
		return this.notifications;
	}
	
}
