package com.pt.pires.service;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pt.pires.VehicleManagerApplication;
import com.pt.pires.domain.LicensedVehicle;
import com.pt.pires.domain.UnlicensedVehicle;
import com.pt.pires.domain.exceptions.InvalidVehicleBrandException;
import com.pt.pires.domain.exceptions.InvalidVehicleNameException;
import com.pt.pires.domain.exceptions.VehicleAlreadyExistException;
import com.pt.pires.domain.exceptions.VehicleDoesntExistException;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.services.IVehicleService;
import com.pt.pires.util.DateUtil;

@RunWith( SpringJUnit4ClassRunner.class )
@SpringApplicationConfiguration(classes = VehicleManagerApplication.class)
public class VehicleServiceTest extends VehicleManagerServiceTest {

	private final static String VEHICLE_NAME_DOESNT_EXIST = "Automóvel";
	private final static String VEHICLE_NAME = "Carrinha Cinza";
	private final static String VEHICLE_NAME_1 = "Popó amarelo";
	private final static String VEHICLE_NAME_2 = "Carro Azul";
	private final static String VEHICLE_NAME_3 = "Carro Amarelo";
	private final static String EMPTY_NAME = "";
	
	private final static String VEHICLE_BRAND = "Mitsubishi";
	private final static String VEHICLE_BRAND_1= "Skoda";
	
	private final static Date VEHICLE_DATE = DateUtil.getSimplifyDate(new Date());
	
	private final static String VALID_LICENSE = "55-11-KI"; 
	
	@Autowired
	@Qualifier("vehicleService")
	private IVehicleService vehicleService;
	
	@Override
	public void populate() throws VehicleManagerException {
		newUnlicensedVehicle(VEHICLE_NAME_1,VEHICLE_BRAND_1,VEHICLE_DATE);
		newLicensedVehicle(VEHICLE_NAME_2, VEHICLE_BRAND_1, VEHICLE_DATE, VALID_LICENSE, VEHICLE_DATE);
	}
	
	/* ========= CreateUnlicensedVehicle Service ================ */
	
	@Test
	public void createUnlicensedVehicle() throws VehicleManagerException {
		vehicleService.createUnlicensedVehicle(VEHICLE_NAME,VEHICLE_BRAND,VEHICLE_DATE);		
		UnlicensedVehicle v =  obtainUnlicensedVehicle(VEHICLE_NAME);
		Assert.assertNotNull(v);
		Assert.assertTrue(v.getName().equals(VEHICLE_NAME));
		Assert.assertTrue(v.getBrand().equals(VEHICLE_BRAND));
	}
	
	@Test(expected = VehicleAlreadyExistException.class)
	public void createUnlicensedVehicleAlreadyExist() throws VehicleManagerException {
		vehicleService.createUnlicensedVehicle(VEHICLE_NAME_1,VEHICLE_BRAND,VEHICLE_DATE);		
	}
	
	@Test(expected = InvalidVehicleNameException.class)
	public void createUnlicensedVehicleEmptyName() throws VehicleManagerException {
		vehicleService.createUnlicensedVehicle(EMPTY_NAME,VEHICLE_BRAND,VEHICLE_DATE);		
	}
	
	@Test(expected = InvalidVehicleBrandException.class)
	public void createUnlicensedVehicleEmptyBrand() throws VehicleManagerException {
		vehicleService.createUnlicensedVehicle(VEHICLE_NAME_3,EMPTY_NAME,VEHICLE_DATE);		
	}
	
	/* ========= GetUnlicensedVehicle Service ================ */
	
	@Test
	public void getUnlicensedVehicle() throws VehicleManagerException{
		UnlicensedVehicle v = vehicleService.getUnlicensedVehicle(VEHICLE_NAME_1);
		Assert.assertNotNull(v);
		Assert.assertTrue(v.getName().equals(VEHICLE_NAME_1));
		Assert.assertTrue(v.getBrand().equals(VEHICLE_BRAND_1));
	}
	
	@Test(expected = VehicleDoesntExistException.class)
	public void getUnlicensedVehicleDontExist() throws VehicleManagerException{
		vehicleService.getLicensedVehicle(VEHICLE_NAME_DOESNT_EXIST);
	}
	
	/* ========= CreateLicensedVehicle Service ================ */
	
	@Test
	public void getLicensedVehicle() throws VehicleManagerException{
		LicensedVehicle v = vehicleService.getLicensedVehicle(VEHICLE_NAME_2);
		Assert.assertNotNull(v);
		Assert.assertTrue(v.getName().equals(VEHICLE_NAME_2));
		Assert.assertTrue(v.getBrand().equals(VEHICLE_BRAND_1));
	}
	
	@Test(expected = VehicleDoesntExistException.class)
	public void getLicensedVehicleDontExist() throws VehicleManagerException{
		vehicleService.getLicensedVehicle(VEHICLE_NAME_DOESNT_EXIST);
	}
	
	/* ============== RemoveVehicle Service ================ */
	
	@Test
	public void removeVehicle(){
		vehicleService.removeVehicle(VEHICLE_NAME_1);
		Assert.assertNull(obtainVehicle(VEHICLE_NAME_1));
	}
	
	@Test
	public void removeVehicleDoesntExist(){
		vehicleService.removeVehicle(VEHICLE_NAME_DOESNT_EXIST);
	}
	
}
