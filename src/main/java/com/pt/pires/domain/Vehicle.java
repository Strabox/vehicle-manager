package com.pt.pires.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

import com.pt.pires.domain.exceptions.InvalidVehicleBrandException;
import com.pt.pires.domain.exceptions.InvalidVehicleNameException;
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
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Collection<Registration> registries = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Collection<Note> notes = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Collection<NotificationTask> notifications = new ArrayList<>();
	
	
	public Vehicle(String name, String brand, Date acquisitionDate) 
			throws InvalidVehicleNameException, InvalidVehicleBrandException {
		setName(name);
		setBrand(brand);
		setAcquisitionDate(acquisitionDate);
	}
	
	public Vehicle() { }	//Needed for JPA/JSON	
	
	
	public final void addRegistration(Registration reg) {
		registries.add(reg);
	}
	
	public final void addNote(Note note) {
		notes.add(note);
	}
	
	public final void addNotification(NotificationTask notification) {
		notifications.add(notification);
	}
	
	public final void removeRegistration(long id) {
		for(Registration reg : registries) {
			if(reg.getId() == id) {
				registries.remove(reg);
				break;
			}
		}
	}
	
	public final void removeNote(long id) {
		for(Note note : notes){
			if(note.getId() == id) {
				notes.remove(note);
				break;
			}
		}
	}
	
	public final void removeNotification(long id) {
		for(NotificationTask noti : notifications) {
			if(noti.getId() == id) {
				notifications.remove(noti);
				break;
			}
		}
	}
	
	public final int calculateAcquisitionYears() {
		Calendar acquisitionCalendar = DateUtil.getCalendar(acquisitionDate);
	    Calendar currentCalendar = DateUtil.getCalendar(new Date());
	    int diff = currentCalendar.get(Calendar.YEAR) - acquisitionCalendar.get(Calendar.YEAR);
	    if (acquisitionCalendar.get(Calendar.MONTH) > currentCalendar.get(Calendar.MONTH) || 
	        (acquisitionCalendar.get(Calendar.MONTH) == currentCalendar.get(Calendar.MONTH) &&
	        	acquisitionCalendar.get(Calendar.DATE) > currentCalendar.get(Calendar.DATE))) {
	        diff--;
	    }
	    return diff;
	}
	
	/* === Getters and Setters === */
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) throws InvalidVehicleNameException {
		if(name.isEmpty()) {
			throw new InvalidVehicleNameException();
		}
		this.name = name;
	}
	
	public String getBrand() {
		return this.brand;
	}
	
	public void setBrand(String brand) throws InvalidVehicleBrandException {
		if(brand.isEmpty()){
			throw new InvalidVehicleBrandException();
		}
		this.brand = brand;
	}
	
	public Date getAcquisitionDate() {
		return this.acquisitionDate;
	}
	
	public void setAcquisitionDate(Date date) {
		this.acquisitionDate = date;
	}
	
	public Collection<Registration> getRegistries() {
		return this.registries;
	}
	
	public Collection<Note> getNotes() {
		return this.notes;
	}
	
	public Collection<NotificationTask> getNotifications() {
		return this.notifications;
	}
	
}
