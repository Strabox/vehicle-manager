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
	
	public static Date getSimplifyDate(int day,int month,int year){
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.DATE, day);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.YEAR, year);
		return cal.getTime();
	}
	
	public static Calendar getCalendar(Date date){
		Calendar cal = Calendar.getInstance(Locale.ENGLISH);
		cal.setTime(date);
		return cal;
	}
	
}
