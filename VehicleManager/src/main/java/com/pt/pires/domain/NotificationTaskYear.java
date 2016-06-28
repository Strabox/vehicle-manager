package com.pt.pires.domain;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;

import com.pt.pires.domain.exceptions.InvalidNotificationException;

@Entity
public class NotificationTaskYear extends NotificationTask {

	public NotificationTaskYear(Date initDate, String description) throws InvalidNotificationException {
		super(initDate, description);
	}

	public NotificationTaskYear() {	super(); }	//Needed for JPA/JSON
	
	@Override
	protected void setNextNotification() {
		Calendar notiCalendar = Calendar.getInstance();
		notiCalendar.setTime(getNotiDate());
		notiCalendar.add(Calendar.YEAR, 1);
		setNotiDate(notiCalendar.getTime());
	}

}
