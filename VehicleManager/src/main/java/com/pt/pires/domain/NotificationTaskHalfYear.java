package com.pt.pires.domain;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;

import com.pt.pires.domain.exceptions.InvalidNotificationException;

@Entity
public class NotificationTaskHalfYear extends NotificationTask {

	public NotificationTaskHalfYear(Date initDate, String description) throws InvalidNotificationException {
		super(initDate, description);
	}
	
	public NotificationTaskHalfYear() {	super(); }	//Needed for JPA/JSON

	@Override
	protected void setNextNotification() {
		Calendar c = Calendar.getInstance();
		c.setTime(getNotiDate());
		c.add(Calendar.MONTH, 6);
		setNotiDate(c.getTime());
	}
	
}
