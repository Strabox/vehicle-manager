package com.pt.pires.service;

import java.util.Date;
import org.junit.Before;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pt.pires.VehicleManagerApplication;
import com.pt.pires.domain.License;
import com.pt.pires.domain.LicensedVehicle;
import com.pt.pires.domain.UnlicensedVehicle;
import com.pt.pires.domain.Vehicle;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.persistence.LicensedVehicleRepository;
import com.pt.pires.persistence.UnlicensedVehicleRepository;
import com.pt.pires.persistence.VehicleRepository;

/**
 * Note: The @Test methods are automatic Rollbacked by the 
 * @RunWith(SpringJUnit4ClassRunner.class) annotation in the test class.
 * 
 * @author André
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VehicleManagerApplication.class)
public abstract class VehicleManagerServiceTest {
	
	@Autowired
	private VehicleRepository vehicleRepository; 
	
	@Autowired
	private UnlicensedVehicleRepository unlicensedRepository;
	
	@Autowired
	private LicensedVehicleRepository licensedRepository;
	
	/**
	 * Executed before each test.
	 * @throws VehicleManagerException
	 */
	@Before
	public void beforeTest() throws VehicleManagerException {
		populate();
	};
	
	public abstract void populate() throws VehicleManagerException;
	
	/* ================= Auxiliary test methods =============== */
	
	protected void newUnlicensedVehicle(String name,String brand,Date acquisitionDate){
		unlicensedRepository.save(new UnlicensedVehicle(name, brand, acquisitionDate));
	}
	
	protected void newLicensedVehicle(String name,String brand,Date acquisitionDate,
			String license,Date licenseDate) throws VehicleManagerException {
		License licenseO = new License(license,licenseDate);
		licensedRepository.save(new LicensedVehicle(name, brand, acquisitionDate, licenseO));
	}
	
	protected Vehicle obtainVehicle(String name){
		return vehicleRepository.findOne(name);
	}
	
	protected UnlicensedVehicle obtainUnlicensedVehicle(String name){
		return unlicensedRepository.findOne(name);
	}
	
	protected LicensedVehicle obtainLicensedVehicle(String name){
		return licensedRepository.findOne(name);
	}
	
}
