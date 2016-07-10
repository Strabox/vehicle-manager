package com.pt.pires.service.local;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.pt.pires.VehicleManagerApplication;
import com.pt.pires.domain.VehicleLicensed;
import com.pt.pires.domain.VehicleUnlicensed;
import com.pt.pires.domain.Vehicle;
import com.pt.pires.domain.exceptions.InvalidLicenseException;
import com.pt.pires.domain.exceptions.InvalidVehicleBrandException;
import com.pt.pires.domain.exceptions.InvalidVehicleNameException;
import com.pt.pires.domain.exceptions.LicenseAlreadyExistException;
import com.pt.pires.domain.exceptions.VehicleAlreadyExistException;
import com.pt.pires.domain.exceptions.VehicleDoesntExistException;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.service.VehicleManagerServiceTest;
import com.pt.pires.services.local.IVehicleService;
import com.pt.pires.util.DateUtil;

@RunWith( SpringJUnit4ClassRunner.class )
@SpringApplicationConfiguration(classes = VehicleManagerApplication.class)
@Transactional
@Rollback
public class VehicleServiceTest extends VehicleManagerServiceTest {
	
	private final static String VEHICLE_NAME_DOESNT_EXIST = "Carrinha Cinza";
	private final static String VEHICLE_NAME_DOESNT_EXIST_2 = "Automóvel";
	private final static String VEHICLE_NAME_EXIST_1 = "Popó amarelo";
	private final static String VEHICLE_NAME_EXIST_2 = "Carro Azul";
	
	private final static String VEHICLE_BRAND = "Mitsubishi";
	private final static String VEHICLE_BRAND_1 = "Skoda";
	
	private final static Date VEHICLE_DATE = DateUtil.getSimplifyDate(new Date());
	
	private final static int FABRICATION_YEAR = 1994;
	
	private final static String VALID_LICENSE = "55-11-KI";
	private final static String VALID_LICENSE_2 = "55-26-AI";
	private final static String INVALID_LICENSE = "II-11-GH";
	
	
	@Autowired
	@Qualifier("vehicleService")
	private IVehicleService vehicleService;
	
	@Override
	public void populate() throws VehicleManagerException {
		newUnlicensedVehicle(VEHICLE_NAME_EXIST_1,VEHICLE_BRAND_1,VEHICLE_DATE, FABRICATION_YEAR);
		newLicensedVehicle(VEHICLE_NAME_EXIST_2, VEHICLE_BRAND_1, VEHICLE_DATE, VALID_LICENSE, VEHICLE_DATE);
	}
	
	/* ========= GetUnlicensedVehicles Service ================ */

	@Test
	public void getUnlicensedVehicles(){
		List<VehicleUnlicensed> c = (List<VehicleUnlicensed>) vehicleService.getUnlicensedVehicles();
		Assert.assertTrue(c.size() == 1);
		VehicleUnlicensed v = c.get(0);
		Assert.assertTrue(v.getName().equals(VEHICLE_NAME_EXIST_1));
		Assert.assertTrue(v.getBrand().equals(VEHICLE_BRAND_1));
	}
	
	@Test
	public void getUnlicensedVehiclesNoVehicles(){
		deleteVehicle(VEHICLE_NAME_EXIST_1);
		List<VehicleUnlicensed> c = (List<VehicleUnlicensed>) vehicleService.getUnlicensedVehicles();
		Assert.assertTrue(c.size() == 0);
	}
	
	/* ========= GetLicensedVehiclesService ================ */

	@Test
	public void getLicensedVehicles(){
		List<VehicleLicensed> c = (List<VehicleLicensed>) vehicleService.getLicensedVehicles();
		Assert.assertTrue(c.size() == 1);
		VehicleLicensed v = c.get(0);
		Assert.assertTrue(v.getName().equals(VEHICLE_NAME_EXIST_2));
		Assert.assertTrue(v.getBrand().equals(VEHICLE_BRAND_1));
		Assert.assertTrue(v.getLicense().getLicense().equals(VALID_LICENSE));
	}

	@Test
	public void getLicensedVehiclesNoVehicles(){
		deleteVehicle(VEHICLE_NAME_EXIST_2);
		List<VehicleLicensed> c = (List<VehicleLicensed>) vehicleService.getLicensedVehicles();
		Assert.assertTrue(c.size() == 0);
	}
	
	/* ================== GetVehicleService ================== */
	
	@Test
	public void getVehicle() throws VehicleManagerException{
		Vehicle v = vehicleService.getVehicle(VEHICLE_NAME_EXIST_1);
		Assert.assertNotNull(v);
		Assert.assertTrue(v.getName().equals(VEHICLE_NAME_EXIST_1));
		Assert.assertTrue(v.getBrand().equals(VEHICLE_BRAND_1));
	}
	
	@Test(expected = VehicleDoesntExistException.class)
	public void getVehicleNoVehicle() throws VehicleManagerException{
		deleteVehicle(VEHICLE_NAME_EXIST_1);
		vehicleService.getVehicle(VEHICLE_NAME_EXIST_1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getVehicleNullVehicleName() throws VehicleManagerException {
		vehicleService.getVehicle(null);
	}
	
	/* ========= CreateLicensedVehicleService ================ */
	
	@Test
	public void createLicensedVehicle() throws VehicleManagerException{
		vehicleService.createLicensedVehicle(VEHICLE_NAME_DOESNT_EXIST, VEHICLE_BRAND, VEHICLE_DATE,
				VALID_LICENSE_2, VEHICLE_DATE);
		VehicleLicensed v = obtainLicensedVehicle(VEHICLE_NAME_DOESNT_EXIST);
		Assert.assertNotNull(v);
		Assert.assertTrue(v.getName().equals(VEHICLE_NAME_DOESNT_EXIST));
		Assert.assertTrue(v.getBrand().equals(VEHICLE_BRAND));
		Assert.assertTrue(v.getLicense().getLicense().equals(VALID_LICENSE_2));
	}
	
	@Test(expected = InvalidLicenseException.class)
	public void createLicensedVehicleInvalidLicense() throws VehicleManagerException{
		vehicleService.createLicensedVehicle(VEHICLE_NAME_DOESNT_EXIST, VEHICLE_BRAND, VEHICLE_DATE,
				INVALID_LICENSE, VEHICLE_DATE);
	}
	
	@Test(expected = VehicleAlreadyExistException.class)
	public void createLicensedVehicleAlreadyExist() throws VehicleManagerException{
		vehicleService.createLicensedVehicle(VEHICLE_NAME_EXIST_2, VEHICLE_BRAND, VEHICLE_DATE,
				VALID_LICENSE_2, VEHICLE_DATE);
	}
	
	@Test(expected = InvalidVehicleNameException.class)
	public void createLicensedVehicleInvalidName() throws VehicleManagerException{
		vehicleService.createLicensedVehicle(EMPTY_STRING, VEHICLE_BRAND, VEHICLE_DATE,
				VALID_LICENSE_2, VEHICLE_DATE);
	}
	
	@Test(expected = InvalidVehicleBrandException.class)
	public void createLicensedVehicleDontExist() throws VehicleManagerException{
		vehicleService.createLicensedVehicle(VEHICLE_NAME_DOESNT_EXIST, EMPTY_STRING, VEHICLE_DATE,
				VALID_LICENSE_2, VEHICLE_DATE);
	}
	
	@Test(expected = LicenseAlreadyExistException.class)
	public void createLicensedVehicleLicenseAlreadyExist() throws VehicleManagerException{
		vehicleService.createLicensedVehicle(VEHICLE_NAME_DOESNT_EXIST, VEHICLE_BRAND,
				VEHICLE_DATE, VALID_LICENSE, VEHICLE_DATE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createLicensedVehicleNullVehicleName() throws VehicleManagerException{
		vehicleService.createLicensedVehicle(null, VEHICLE_BRAND,
				VEHICLE_DATE, VALID_LICENSE, VEHICLE_DATE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createLicensedVehicleNullBrand() throws VehicleManagerException{
		vehicleService.createLicensedVehicle(VEHICLE_NAME_DOESNT_EXIST_2, null,
				VEHICLE_DATE, VALID_LICENSE, VEHICLE_DATE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createLicensedVehicleNullVehicleDate() throws VehicleManagerException{
		vehicleService.createLicensedVehicle(VEHICLE_NAME_DOESNT_EXIST_2, VEHICLE_BRAND,
				null, VALID_LICENSE, VEHICLE_DATE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createLicensedVehicleNullLicense() throws VehicleManagerException{
		vehicleService.createLicensedVehicle(VEHICLE_NAME_DOESNT_EXIST_2, VEHICLE_BRAND,
				VEHICLE_DATE, null, VEHICLE_DATE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createLicensedVehicleNullLicenseDate() throws VehicleManagerException{
		vehicleService.createLicensedVehicle(VEHICLE_NAME_DOESNT_EXIST_2, VEHICLE_BRAND,
				VEHICLE_DATE, VALID_LICENSE, null);
	}
	
	/* ========= CreateUnlicensedVehicleService ================ */

	@Test
	public void createUnlicensedVehicle() throws VehicleManagerException {
		vehicleService.createUnlicensedVehicle(VEHICLE_NAME_DOESNT_EXIST,VEHICLE_BRAND,VEHICLE_DATE, FABRICATION_YEAR);		
		VehicleUnlicensed v = obtainUnlicensedVehicle(VEHICLE_NAME_DOESNT_EXIST);
		Assert.assertNotNull(v);
		Assert.assertTrue(v.getName().equals(VEHICLE_NAME_DOESNT_EXIST));
		Assert.assertTrue(v.getBrand().equals(VEHICLE_BRAND));
	}

	@Test(expected = VehicleAlreadyExistException.class)
	public void createUnlicensedVehicleAlreadyExist() throws VehicleManagerException {
		vehicleService.createUnlicensedVehicle(VEHICLE_NAME_EXIST_1,VEHICLE_BRAND,VEHICLE_DATE, FABRICATION_YEAR);		
	}

	@Test(expected = InvalidVehicleNameException.class)
	public void createUnlicensedVehicleEmptyName() throws VehicleManagerException {
		vehicleService.createUnlicensedVehicle(EMPTY_STRING,VEHICLE_BRAND,VEHICLE_DATE, FABRICATION_YEAR);		
	}

	@Test(expected = InvalidVehicleBrandException.class)
	public void createUnlicensedVehicleEmptyBrand() throws VehicleManagerException {
		vehicleService.createUnlicensedVehicle(VEHICLE_NAME_DOESNT_EXIST,EMPTY_STRING,VEHICLE_DATE, FABRICATION_YEAR);		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createUnlicensedVehicleNullName() throws VehicleManagerException {
		vehicleService.createUnlicensedVehicle(null,VEHICLE_BRAND,VEHICLE_DATE, FABRICATION_YEAR);		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createUnlicensedVehicleNullBrand() throws VehicleManagerException {
		vehicleService.createUnlicensedVehicle(VEHICLE_NAME_DOESNT_EXIST,null,VEHICLE_DATE, FABRICATION_YEAR);		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createUnlicensedVehicleNullDate() throws VehicleManagerException {
		vehicleService.createUnlicensedVehicle(VEHICLE_NAME_DOESNT_EXIST,VEHICLE_BRAND,null, FABRICATION_YEAR);		
	}
	
	/* ============ GetUnlicensedVehicleService ================ */

	@Test
	public void getUnlicensedVehicle() throws VehicleManagerException{
		VehicleUnlicensed v = vehicleService.getUnlicensedVehicle(VEHICLE_NAME_EXIST_1);
		Assert.assertNotNull(v);
		Assert.assertTrue(v.getName().equals(VEHICLE_NAME_EXIST_1));
		Assert.assertTrue(v.getBrand().equals(VEHICLE_BRAND_1));
	}

	@Test(expected = VehicleDoesntExistException.class)
	public void getUnlicensedVehicleDontExist() throws VehicleManagerException{
		vehicleService.getUnlicensedVehicle(VEHICLE_NAME_DOESNT_EXIST_2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getUnlicensedVehicleNullName() throws VehicleManagerException{
		vehicleService.getUnlicensedVehicle(null);
	}
	
	/* ============= GetLicensedVehicleService ================ */

	@Test
	public void getLicensedVehicle() throws VehicleManagerException{
		VehicleLicensed v = vehicleService.getLicensedVehicle(VEHICLE_NAME_EXIST_2);
		Assert.assertNotNull(v);
		Assert.assertTrue(v.getName().equals(VEHICLE_NAME_EXIST_2));
		Assert.assertTrue(v.getBrand().equals(VEHICLE_BRAND_1));
	}

	@Test(expected = VehicleDoesntExistException.class)
	public void getLicensedVehicleDontExist() throws VehicleManagerException{
		vehicleService.getLicensedVehicle(VEHICLE_NAME_DOESNT_EXIST_2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getLicensedVehicleNullName() throws VehicleManagerException{
		vehicleService.getLicensedVehicle(null);
	}
	
	/* ================= RemoveVehicleService ================== */

	@Test
	public void removeVehicle() throws VehicleManagerException{
		vehicleService.removeVehicle(VEHICLE_NAME_EXIST_1);
		Assert.assertNull(obtainVehicle(VEHICLE_NAME_EXIST_1));
	}

	@Test
	public void removeVehicleDoesntExist() throws VehicleManagerException{
		vehicleService.removeVehicle(VEHICLE_NAME_DOESNT_EXIST_2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void removeVehicleNullName() throws VehicleManagerException{
		vehicleService.removeVehicle(null);
	}
	
	/* ====================== VehicleExistService ======================= */
	
	@Test
	public void vehicleExistTrue() throws VehicleManagerException{
		Assert.assertTrue(vehicleService.vehicleExist(VEHICLE_NAME_EXIST_1));
		Assert.assertTrue(vehicleService.vehicleExist(VEHICLE_NAME_EXIST_2));
	}
	
	@Test
	public void vehicleExistFalse() throws VehicleManagerException{
		Assert.assertTrue(!vehicleService.vehicleExist(VEHICLE_NAME_DOESNT_EXIST_2));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void vehicleExistNullName() throws VehicleManagerException{
		vehicleService.vehicleExist(null);
	}
}
