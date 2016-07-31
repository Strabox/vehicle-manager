package com.pt.pires.domain;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

import com.pt.pires.domain.exceptions.InvalidNotificationTaskException;

/**
 * NotificationTask entities scheduled tasks
 * @author André
 *
 */
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
	
	@Column
	private boolean notificationSent;
	
	@ManyToOne
	private Vehicle vehicle;
	
	
	public NotificationTask(Date initDate, String description) throws InvalidNotificationTaskException {
		setNotiDate(initDate);
		setDescription(description);
		setNotificationSent(false);
	}
	
	public NotificationTask() { }		//Needed for JPA/JSON
	
	/**
	 * Verify if it is notification day
	 * @param currentDate Current date
	 * @return <b>true</b> if current date is after notiDate, <b>false</b> otherwise
	 */
	public boolean notifyDay(Date currentDate) {
		Calendar initCalendar = Calendar.getInstance();
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.setTime(currentDate);
		initCalendar.setTime(getNotiDate());
		return initCalendar.before(currentCalendar);
	}
	
	/**
	 * Set the next date to send the notification
	 * @param currentDate
	 */
	public final void notificationTaskDone(Date currentDate) {
		if(notifyDay(currentDate) && isNotificationSent()) {
			setNextNotification();
			setNotificationSent(false);
		}
	}
	
	/**
	 * Forget the notification this time
	 * ex: Don't need do the task this time.
	 */
	public final void forgetThisNotification() {
		setNextNotification();
		setNotificationSent(false);
	}
	
	/**
	 * Leave the responsibility of setting the new date to the
	 * sub-classes
	 */
	protected abstract void setNextNotification();
	
	/* === Getters and Setters === */
	
	public long getId() {
		return this.id;
	}
	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) throws InvalidNotificationTaskException {
		if(description.isEmpty()) {
			throw new InvalidNotificationTaskException();
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

	public boolean isNotificationSent() {
		return notificationSent;
	}

	public void setNotificationSent(boolean notificationSent) {
		this.notificationSent = notificationSent;
	}
	
}
