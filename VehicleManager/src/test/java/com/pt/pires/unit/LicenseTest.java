package com.pt.pires.unit;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pt.pires.VehicleManagerApplication;
import com.pt.pires.domain.License;
import com.pt.pires.domain.exceptions.InvalidLicenseException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VehicleManagerApplication.class)
public class LicenseTest {

	private final static String VALID_LICENSE = "44-11-HH";
	
	@Test
	public void licenseYears_1() throws InvalidLicenseException{
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.setTime(new Date());
		int currentYear = currentCalendar.get(Calendar.YEAR);
		int currentMonth = currentCalendar.get(Calendar.MONTH);
		Calendar licenseDateCalendar = Calendar.getInstance();
		licenseDateCalendar.setTime(new Date());
		licenseDateCalendar.set(Calendar.YEAR,currentYear - 10);
		licenseDateCalendar.set(Calendar.MONTH,(currentMonth - 1) % 11);
		License l = new License(VALID_LICENSE, licenseDateCalendar.getTime());
		Assert.assertTrue(l.calculateLicenseYears() == 10);
	}
	
	@Test
	public void licenseYears_2() throws InvalidLicenseException{
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.setTime(new Date());
		int currentYear = currentCalendar.get(Calendar.YEAR);
		int currentMonth = currentCalendar.get(Calendar.MONTH);
		Calendar licenseDateCalendar = Calendar.getInstance();
		licenseDateCalendar.setTime(new Date());
		licenseDateCalendar.set(Calendar.YEAR,currentYear - 10);
		licenseDateCalendar.set(Calendar.MONTH,(currentMonth + 1) % 11);
		License l = new License(VALID_LICENSE, licenseDateCalendar.getTime());
		Assert.assertTrue(l.calculateLicenseYears() == 9);
	}
	
	
}
