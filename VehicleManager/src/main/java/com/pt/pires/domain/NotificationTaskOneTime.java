package com.pt.pires.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.pt.pires.domain.exceptions.InvalidNotificationTaskException;

/**
 * Notification that alert user only one time (initDate)
 * @author André
 *
 */
@Entity
public class NotificationTaskOneTime extends NotificationTask {

	@Column
	private boolean expired;
	
	
	public NotificationTaskOneTime(Date initDate,String description) throws InvalidNotificationTaskException {
		super(initDate,description);
		setExpired(false);
	}
	
	public NotificationTaskOneTime() { setExpired(false); }		//Needed for JPA/JSON
	
	@Override
	public boolean notifyDay(Date currentDate) {
		if(expired) {
			return false;
		}
		else {
			return super.notifyDay(currentDate);
		}
	}
	
	@Override
	protected void setNextNotification() {
		this.expired = true;
	}
	
	/* === Getters and Setters === */

	public boolean getExpired() {
		return this.expired;
	}
	
	public void setExpired(boolean expired) {
		this.expired = expired;
	}
	
}
