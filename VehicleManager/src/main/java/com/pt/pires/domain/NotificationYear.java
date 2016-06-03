package com.pt.pires.domain;

import java.util.Calendar;
import java.util.Date;

public class NotificationYear extends Notification{

	public NotificationYear(Date initDate, String description) {
		super(initDate, description);
	}

	public NotificationYear() {	super(); }
	
	@Override
	public boolean notifyDay(Date currentDate) {
		Calendar initCalendar = Calendar.getInstance();
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.setTime(currentDate);
		initCalendar.setTime(getInitDate());
		initCalendar.add(Calendar.YEAR, 1);
		boolean res = initCalendar.before(currentCalendar);
		if(res){	//Update init date
			setInitDate(initCalendar.getTime());
		}
		return res;
	}

}
