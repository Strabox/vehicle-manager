package com.pt.pires.domain;

import java.util.Calendar;
import java.util.Date;

public class NotificationHalfYear extends Notification{

	public NotificationHalfYear(Date initDate, String description) {
		super(initDate, description);
	}
	
	public NotificationHalfYear() {	super(); }

	@Override
	public boolean notifyDay(Date currentDate) {
		Calendar initCalendar = Calendar.getInstance();
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.setTime(currentDate);
		initCalendar.setTime(getInitDate());
		initCalendar.add(Calendar.MONTH, 6);
		boolean res = initCalendar.before(currentCalendar);
		if(res){	//Update init date
			setInitDate(initCalendar.getTime());
		}
		return res;
	}

}
