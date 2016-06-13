package com.pt.pires.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public abstract class DateUtil {
	
	public static Date getSimplifyDate(Date d){
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.clear(Calendar.HOUR);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		return cal.getTime();
	}
	
	public static Calendar getCalendar(Date date){
		Calendar cal = Calendar.getInstance(Locale.ENGLISH);
		cal.setTime(date);
		return cal;
	}
	
}
