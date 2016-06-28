package com.pt.pires.unit;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pt.pires.VehicleManagerApplication;
import com.pt.pires.domain.VehicleUnlicensed;
import com.pt.pires.domain.exceptions.VehicleManagerException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VehicleManagerApplication.class)
public class VehicleTest {

	private static final String VEHICLE_NAME = "Automovel Verfde";
	private static final String VEHICLE_BRAND = "Citroen";
	
	@Test
	public void vehicleYears() throws VehicleManagerException {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(new Date());
		int currentYear = c1.get(Calendar.YEAR);
		int currentMonth = c1.get(Calendar.MONTH);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(new Date());
		c2.set(Calendar.YEAR,currentYear - 10);
		c2.set(Calendar.MONTH,(currentMonth - 1) % 11);
		VehicleUnlicensed v = new VehicleUnlicensed(VEHICLE_NAME,VEHICLE_BRAND,c2.getTime());
		Assert.assertTrue(v.calculateAcquisitionYears() == 10);
	}
	
	@Test
	public void vehicleYears2() throws VehicleManagerException {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(new Date());
		int currentYear = c1.get(Calendar.YEAR);
		int currentMonth = c1.get(Calendar.MONTH);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(new Date());
		c2.set(Calendar.YEAR,currentYear - 10);
		c2.set(Calendar.MONTH,(currentMonth + 1) % 11);
		VehicleUnlicensed v = new VehicleUnlicensed(VEHICLE_NAME,VEHICLE_BRAND,c2.getTime());
		Assert.assertTrue(v.calculateAcquisitionYears() == 9);
	}
	
	@Test
	public void vehicleYears3() throws VehicleManagerException {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(new Date());
		int currentYear = c1.get(Calendar.YEAR);
		int currentMonth = c1.get(Calendar.MONTH);
		int currentDay = c1.get(Calendar.DATE);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(new Date());
		c2.set(Calendar.YEAR,currentYear - 10);
		c2.set(Calendar.MONTH,currentMonth);
		c2.set(Calendar.DATE, (currentDay + 1) % 31);
		VehicleUnlicensed v = new VehicleUnlicensed(VEHICLE_NAME,VEHICLE_BRAND,c2.getTime());
		Assert.assertTrue(v.calculateAcquisitionYears() == 9);
	}
	
	@Test
	public void vehicleYears4() throws VehicleManagerException {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(new Date());
		int currentYear = c1.get(Calendar.YEAR);
		int currentMonth = c1.get(Calendar.MONTH);
		int currentDay = c1.get(Calendar.DATE);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(new Date());
		c2.set(Calendar.YEAR,currentYear - 10);
		c2.set(Calendar.MONTH,currentMonth);
		c2.set(Calendar.DATE, (currentDay - 1) % 31);
		VehicleUnlicensed v = new VehicleUnlicensed(VEHICLE_NAME,VEHICLE_BRAND,c2.getTime());
		Assert.assertTrue(v.calculateAcquisitionYears() == 10);
	}
	
}
