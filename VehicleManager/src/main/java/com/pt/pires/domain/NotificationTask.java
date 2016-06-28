package com.pt.pires.domain;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

import com.pt.pires.domain.exceptions.InvalidNotificationException;

@Entity
public abstract class NotificationTask {

	@GeneratedValue
	@Id
	private long id;
	
	@Column
	@Type(type="date")
	private Date notiDate;
	
	@Column
	private String description;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Vehicle vehicle;
	
	
	public NotificationTask(Date initDate,String description) throws InvalidNotificationException {
		setNotiDate(initDate);
		setDescription(description);
	}
	
	public NotificationTask() { }		//Needed for JPA/JSON
	
	/**
	 * Verify if it is notification day
	 * @param currentDate Current date
	 * @return true if current date is after notiDate false otherwise
	 */
	public boolean notifyDay(Date currentDate) {
		Calendar initCalendar = Calendar.getInstance();
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.setTime(currentDate);
		initCalendar.setTime(getNotiDate());
		return initCalendar.before(currentCalendar);
	}
	
	public final void setNextNotificationDate(Date currentDate) {
		if(notifyDay(currentDate)) {
			setNextNotification();
		}
	}
	
	protected abstract void setNextNotification();
	
	/* === Getters and Setters === */
	
	public long getId() {
		return this.id;
	}
	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) throws InvalidNotificationException {
		if(description.isEmpty()) {
			throw new InvalidNotificationException();
		}
		this.description = description;
	}

	public Date getNotiDate() {
		return notiDate;
	}

	public void setNotiDate(Date notiDate) {
		this.notiDate = notiDate;
	}
	
	public Vehicle getVehicle() {
		return this.vehicle;
	}
	
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
}
