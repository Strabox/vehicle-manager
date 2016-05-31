package com.pt.pires.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

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
	private List<Registration> registries = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Note> notes = new ArrayList<>();
	
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
	
	public int getAcquisitionYears(){
		Calendar a = getCalendar(acquisitionDate);
	    Calendar b = getCalendar(new Date());
	    int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
	    if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) || 
	        (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
	        diff--;
	    }
	    return diff;
	}

	private static Calendar getCalendar(Date date) {
	    Calendar cal = Calendar.getInstance(Locale.ENGLISH);
	    cal.setTime(date);
	    return cal;
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
	
	public List<Registration> getRegistries(){
		return registries;
	}
	
	public List<Note> getNotes(){
		return notes;
	}
	
}
