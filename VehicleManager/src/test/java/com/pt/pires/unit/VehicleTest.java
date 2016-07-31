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
import com.pt.pires.util.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VehicleManagerApplication.class)
public class VehicleTest {

	private static final String VEHICLE_NAME = "Automovel Verde";
	private static final String VEHICLE_BRAND = "Citroen";	
	private static final int FABRICATION_YEAR = 1994;
	
	/* ================ Calculate vehicles acquisition years =================== */
	
	@Test
	public void calculateAcquisitionYears() throws VehicleManagerException {
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.setTime(new Date());
		int currentYear = currentCalendar.get(Calendar.YEAR);
		int currentMonth = currentCalendar.get(Calendar.MONTH);
		Calendar acquisitionCalendar = Calendar.getInstance();
		acquisitionCalendar.setTime(new Date());
		acquisitionCalendar.set(Calendar.YEAR,currentYear - 10);
		acquisitionCalendar.set(Calendar.MONTH,(currentMonth - 1) % 11);
		VehicleUnlicensed v = new VehicleUnlicensed(VEHICLE_NAME,VEHICLE_BRAND, acquisitionCalendar.getTime(), FABRICATION_YEAR);
		Assert.assertTrue(v.calculateAcquisitionYears() == 10);
	}
	
	@Test
	public void calculateAcquisitionYears_2() throws VehicleManagerException {
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.setTime(new Date());
		int currentYear = currentCalendar.get(Calendar.YEAR);
		int currentMonth = currentCalendar.get(Calendar.MONTH);
		Calendar acquisitionCalendar = Calendar.getInstance();
		acquisitionCalendar.setTime(new Date());
		acquisitionCalendar.set(Calendar.YEAR,currentYear - 10);
		acquisitionCalendar.set(Calendar.MONTH,(currentMonth + 1) % 11);
		VehicleUnlicensed v = new VehicleUnlicensed(VEHICLE_NAME,VEHICLE_BRAND, acquisitionCalendar.getTime(), FABRICATION_YEAR);
		Assert.assertTrue(v.calculateAcquisitionYears() == 9);
	}
	
	@Test
	public void calculateAcquisitionYears_3() throws VehicleManagerException {
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.setTime(new Date());
		int currentYear = currentCalendar.get(Calendar.YEAR);
		int currentMonth = currentCalendar.get(Calendar.MONTH);
		int currentDay = currentCalendar.get(Calendar.DATE);
		Calendar acquisitionCalendar = Calendar.getInstance();
		acquisitionCalendar.setTime(new Date());
		acquisitionCalendar.set(Calendar.YEAR,currentYear - 10);
		acquisitionCalendar.set(Calendar.MONTH,currentMonth);
		acquisitionCalendar.set(Calendar.DATE, (currentDay + 1) % 31);
		VehicleUnlicensed v = new VehicleUnlicensed(VEHICLE_NAME,VEHICLE_BRAND, acquisitionCalendar.getTime(), FABRICATION_YEAR);
		Assert.assertTrue(v.calculateAcquisitionYears() == 9);
	}
	
	@Test
	public void calculateAcquisitionYears_4() throws VehicleManagerException {
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.setTime(new Date());
		int currentYear = currentCalendar.get(Calendar.YEAR);
		int currentMonth = currentCalendar.get(Calendar.MONTH);
		int currentDay = currentCalendar.get(Calendar.DATE);
		Calendar acquisitionCalendar = Calendar.getInstance();
		acquisitionCalendar.setTime(new Date());
		acquisitionCalendar.set(Calendar.YEAR,currentYear - 10);
		acquisitionCalendar.set(Calendar.MONTH,currentMonth);
		acquisitionCalendar.set(Calendar.DATE, (currentDay - 1) % 31);
		VehicleUnlicensed v = new VehicleUnlicensed(VEHICLE_NAME,VEHICLE_BRAND, acquisitionCalendar.getTime(), FABRICATION_YEAR);
		Assert.assertTrue(v.calculateAcquisitionYears() == 10);
	}
	
	/* ============ Unlicensed vehicles fabrication age ================ */
	
	@Test
	public void calculateUnlicensedVehicleFabricationAge() throws VehicleManagerException {
		Calendar currentCalendar = DateUtil.getCalendar(new Date());
		int currentYear = currentCalendar.get(Calendar.YEAR);
		VehicleUnlicensed v = new VehicleUnlicensed(VEHICLE_NAME,VEHICLE_BRAND, new Date(), FABRICATION_YEAR);
		Assert.assertTrue(v.calculateFabricationAge() == (currentYear - FABRICATION_YEAR));
	}
	
}
