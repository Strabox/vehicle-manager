package com.pt.pires.domain;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;

@Entity
public class NotificationYear extends Notification{

	public NotificationYear(Date initDate, String description) {
		super(initDate, description);
	}

	public NotificationYear() {	super(); }
	
	@Override
	public boolean notifyDay(Date currentDate) {
		Calendar notiCalendar = Calendar.getInstance();
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.setTime(currentDate);
		notiCalendar.setTime(getNotiDate());
		boolean res = notiCalendar.before(currentCalendar);
		if(res){	//Update notification date
			notiCalendar.add(Calendar.MONTH, 12);
			setNotiDate(notiCalendar.getTime());
		}
		return res;
	}

}
