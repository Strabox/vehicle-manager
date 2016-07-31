package com.pt.pires.service.local;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VehicleManagerApplication.class)
@Transactional
@Rollback
public class VehicleServiceTest extends VehicleManagerServiceTest {
	
	@Inject
	@Named("vehicleService")
	private IVehicleService vehicleService;
	
	private final static String VEHICLE_NAME_DOESNT_EXIST = "Carrinha Cinza";
	private final static String VEHICLE_NAME_DOESNT_EXIST_2 = "Automóvel";
	private final static String VEHICLE_NAME_EXIST_1 = "Popó amarelo";
	private final static String VEHICLE_NAME_EXIST_2 = "Carro Azul";
	
	private final static String VALID_LICENSE = "55-11-KI";
	private final static String VALID_LICENSE_2 = "55-26-AI";
	private final static String INVALID_LICENSE = "II-11-GH";

	private final static String VALID_NEW_BRAND = "New brand wow";
	private final static Date VALID_NEW_DATE = DateUtil.getSimplifyDate(new Date());
	private final static String VALID_NEW_LICENSE = "88-GG-91";
	private final static int VALID_NEW_FABRICATION_YEAR = 1993;
	
	@Override
	@Before
	public void populate() throws VehicleManagerException {
		newUnlicensedVehicle(VEHICLE_NAME_EXIST_1, VALID_VEHICLE_BRAND, VALID_CURRENT_DATE, VALID_FABRICATION_YEAR);
		newLicensedVehicle(VEHICLE_NAME_EXIST_2, VALID_VEHICLE_BRAND, VALID_CURRENT_DATE, VALID_LICENSE, VALID_CURRENT_DATE);
	}
	
	/* ========= GetUnlicensedVehicles Service ================ */

	@Test
	public void getUnlicensedVehiclesSuccess() {
		List<VehicleUnlicensed> c = (List<VehicleUnlicensed>) vehicleService.getUnlicensedVehicles();
		Assert.assertTrue(c.size() == 1);
		VehicleUnlicensed v = c.get(0);
		Assert.assertTrue(v.getName().equals(VEHICLE_NAME_EXIST_1));
		Assert.assertTrue(v.getBrand().equals(VALID_VEHICLE_BRAND));
	}
	
	@Test
	public void getUnlicensedVehiclesNoVehicles() {
		deleteVehicle(VEHICLE_NAME_EXIST_1);
		List<VehicleUnlicensed> c = (List<VehicleUnlicensed>) vehicleService.getUnlicensedVehicles();
		Assert.assertTrue(c.size() == 0);
	}
	
	/* ========= GetLicensedVehicles Service ================ */

	@Test
	public void getLicensedVehicles() {
		List<VehicleLicensed> c = (List<VehicleLicensed>) vehicleService.getLicensedVehicles();
		Assert.assertTrue(c.size() == 1);
		VehicleLicensed v = c.get(0);
		Assert.assertTrue(v.getName().equals(VEHICLE_NAME_EXIST_2));
		Assert.assertTrue(v.getBrand().equals(VALID_VEHICLE_BRAND));
		Assert.assertTrue(v.getLicense().getLicense().equals(VALID_LICENSE));
	}

	@Test
	public void getLicensedVehiclesNoVehicles() {
		deleteVehicle(VEHICLE_NAME_EXIST_2);
		List<VehicleLicensed> c = (List<VehicleLicensed>) vehicleService.getLicensedVehicles();
		Assert.assertTrue(c.size() == 0);
	}
	
	/* ================== GetAllVehicles Service ============== */
	
	@Test
	public void gettAllVehiclesSuccess() {
		List<Vehicle> vehicles = (List<Vehicle>) vehicleService.getAllVehicles();
		Assert.assertTrue(vehicles.size() == 2);
	}
	
	/* ========= CreateLicensedVehicle Service ================ */
	
	@Test
	public void createLicensedVehicle() throws VehicleManagerException {
		vehicleService.createLicensedVehicle(VEHICLE_NAME_DOESNT_EXIST, VALID_VEHICLE_BRAND, VALID_CURRENT_DATE,
				VALID_LICENSE_2, VALID_CURRENT_DATE);
		VehicleLicensed v = obtainLicensedVehicle(VEHICLE_NAME_DOESNT_EXIST);
		Assert.assertNotNull(v);
		Assert.assertTrue(v.getName().equals(VEHICLE_NAME_DOESNT_EXIST));
		Assert.assertTrue(v.getBrand().equals(VALID_VEHICLE_BRAND));
		Assert.assertTrue(v.getLicense().getLicense().equals(VALID_LICENSE_2));
	}
	
	@Test(expected = InvalidLicenseException.class)
	public void createLicensedVehicleInvalidLicense() throws VehicleManagerException {
		vehicleService.createLicensedVehicle(VEHICLE_NAME_DOESNT_EXIST, VALID_VEHICLE_BRAND, VALID_CURRENT_DATE,
				INVALID_LICENSE, VALID_CURRENT_DATE);
	}
	
	@Test(expected = VehicleAlreadyExistException.class)
	public void createLicensedVehicleAlreadyExist() throws VehicleManagerException {
		vehicleService.createLicensedVehicle(VEHICLE_NAME_EXIST_2, VALID_VEHICLE_BRAND, VALID_CURRENT_DATE,
				VALID_LICENSE_2, VALID_CURRENT_DATE);
	}
	
	@Test(expected = InvalidVehicleNameException.class)
	public void createLicensedVehicleInvalidName() throws VehicleManagerException {
		vehicleService.createLicensedVehicle(EMPTY_STRING, VALID_VEHICLE_BRAND, VALID_CURRENT_DATE,
				VALID_LICENSE_2, VALID_CURRENT_DATE);
	}
	
	@Test(expected = InvalidVehicleBrandException.class)
	public void createLicensedVehicleDontExist() throws VehicleManagerException {
		vehicleService.createLicensedVehicle(VEHICLE_NAME_DOESNT_EXIST, EMPTY_STRING, VALID_CURRENT_DATE,
				VALID_LICENSE_2, VALID_CURRENT_DATE);
	}
	
	@Test(expected = LicenseAlreadyExistException.class)
	public void createLicensedVehicleLicenseAlreadyExist() throws VehicleManagerException {
		vehicleService.createLicensedVehicle(VEHICLE_NAME_DOESNT_EXIST, VALID_VEHICLE_BRAND,
				VALID_CURRENT_DATE, VALID_LICENSE, VALID_CURRENT_DATE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createLicensedVehicleNullVehicleName() throws VehicleManagerException {
		vehicleService.createLicensedVehicle(null, VALID_VEHICLE_BRAND,
				VALID_CURRENT_DATE, VALID_LICENSE, VALID_CURRENT_DATE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createLicensedVehicleNullBrand() throws VehicleManagerException {
		vehicleService.createLicensedVehicle(VEHICLE_NAME_DOESNT_EXIST_2, null,
				VALID_CURRENT_DATE, VALID_LICENSE, VALID_CURRENT_DATE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createLicensedVehicleNullVehicleDate() throws VehicleManagerException {
		vehicleService.createLicensedVehicle(VEHICLE_NAME_DOESNT_EXIST_2, VALID_VEHICLE_BRAND,
				null, VALID_LICENSE, VALID_CURRENT_DATE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createLicensedVehicleNullLicense() throws VehicleManagerException {
		vehicleService.createLicensedVehicle(VEHICLE_NAME_DOESNT_EXIST_2, VALID_VEHICLE_BRAND,
				VALID_CURRENT_DATE, null, VALID_CURRENT_DATE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createLicensedVehicleNullLicenseDate() throws VehicleManagerException {
		vehicleService.createLicensedVehicle(VEHICLE_NAME_DOESNT_EXIST_2, VALID_VEHICLE_BRAND,
				VALID_CURRENT_DATE, VALID_LICENSE, null);
	}
	
	/* ========= CreateUnlicensedVehicle Service ================ */

	@Test
	public void createUnlicensedVehicle() throws VehicleManagerException {
		vehicleService.createUnlicensedVehicle(VEHICLE_NAME_DOESNT_EXIST,VALID_VEHICLE_BRAND,VALID_CURRENT_DATE, VALID_FABRICATION_YEAR);		
		VehicleUnlicensed v = obtainUnlicensedVehicle(VEHICLE_NAME_DOESNT_EXIST);
		Assert.assertNotNull(v);
		Assert.assertTrue(v.getName().equals(VEHICLE_NAME_DOESNT_EXIST));
		Assert.assertTrue(v.getBrand().equals(VALID_VEHICLE_BRAND));
	}

	@Test(expected = VehicleAlreadyExistException.class)
	public void createUnlicensedVehicleAlreadyExist() throws VehicleManagerException {
		vehicleService.createUnlicensedVehicle(VEHICLE_NAME_EXIST_1,VALID_VEHICLE_BRAND,VALID_CURRENT_DATE, VALID_FABRICATION_YEAR);		
	}

	@Test(expected = InvalidVehicleNameException.class)
	public void createUnlicensedVehicleEmptyName() throws VehicleManagerException {
		vehicleService.createUnlicensedVehicle(EMPTY_STRING,VALID_VEHICLE_BRAND,VALID_CURRENT_DATE, VALID_FABRICATION_YEAR);		
	}

	@Test(expected = InvalidVehicleBrandException.class)
	public void createUnlicensedVehicleEmptyBrand() throws VehicleManagerException {
		vehicleService.createUnlicensedVehicle(VEHICLE_NAME_DOESNT_EXIST,EMPTY_STRING,VALID_CURRENT_DATE, VALID_FABRICATION_YEAR);		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createUnlicensedVehicleNullName() throws VehicleManagerException {
		vehicleService.createUnlicensedVehicle(null,VALID_VEHICLE_BRAND,VALID_CURRENT_DATE, VALID_FABRICATION_YEAR);		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createUnlicensedVehicleNullBrand() throws VehicleManagerException {
		vehicleService.createUnlicensedVehicle(VEHICLE_NAME_DOESNT_EXIST,null,VALID_CURRENT_DATE, VALID_FABRICATION_YEAR);		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createUnlicensedVehicleNullDate() throws VehicleManagerException {
		vehicleService.createUnlicensedVehicle(VEHICLE_NAME_DOESNT_EXIST,VALID_VEHICLE_BRAND,null, VALID_FABRICATION_YEAR);		
	}
	
	/* ============ GetUnlicensedVehicle Service ================ */

	@Test
	public void getUnlicensedVehicle() throws VehicleManagerException {
		VehicleUnlicensed v = vehicleService.getUnlicensedVehicle(VEHICLE_NAME_EXIST_1);
		Assert.assertNotNull(v);
		Assert.assertTrue(v.getName().equals(VEHICLE_NAME_EXIST_1));
		Assert.assertTrue(v.getBrand().equals(VALID_VEHICLE_BRAND));
	}

	@Test(expected = VehicleDoesntExistException.class)
	public void getUnlicensedVehicleDontExist() throws VehicleManagerException {
		vehicleService.getUnlicensedVehicle(VEHICLE_NAME_DOESNT_EXIST_2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getUnlicensedVehicleNullName() throws VehicleManagerException {
		vehicleService.getUnlicensedVehicle(null);
	}
	
	/* ============= GetLicensedVehicle Service ================ */

	@Test
	public void getLicensedVehicle() throws VehicleManagerException {
		VehicleLicensed v = vehicleService.getLicensedVehicle(VEHICLE_NAME_EXIST_2);
		Assert.assertNotNull(v);
		Assert.assertTrue(v.getName().equals(VEHICLE_NAME_EXIST_2));
		Assert.assertTrue(v.getBrand().equals(VALID_VEHICLE_BRAND));
	}

	@Test(expected = VehicleDoesntExistException.class)
	public void getLicensedVehicleDontExist() throws VehicleManagerException {
		vehicleService.getLicensedVehicle(VEHICLE_NAME_DOESNT_EXIST_2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getLicensedVehicleNullName() throws VehicleManagerException {
		vehicleService.getLicensedVehicle(null);
	}
	
	/* ================== GetVehicle Service ================== */
	
	@Test
	public void getVehicleSuccess() throws VehicleManagerException {
		Vehicle v = vehicleService.getVehicle(VEHICLE_NAME_EXIST_1);
		Assert.assertNotNull(v);
		Assert.assertTrue(v.getName().equals(VEHICLE_NAME_EXIST_1));
		Assert.assertTrue(v.getBrand().equals(VALID_VEHICLE_BRAND));
	}
	
	@Test(expected = VehicleDoesntExistException.class)
	public void getVehicleNoVehicle() throws VehicleManagerException {
		deleteVehicle(VEHICLE_NAME_EXIST_1);
		vehicleService.getVehicle(VEHICLE_NAME_EXIST_1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getVehicleNullVehicleName() throws VehicleManagerException {
		vehicleService.getVehicle(null);
	}
	
	/* ================= RemoveVehicle Service ================== */

	@Test
	public void removeVehicle() throws VehicleManagerException {
		vehicleService.removeVehicle(VEHICLE_NAME_EXIST_1);
		Assert.assertNull(obtainVehicle(VEHICLE_NAME_EXIST_1));
	}

	@Test
	public void removeVehicleDoesntExist() throws VehicleManagerException{
		vehicleService.removeVehicle(VEHICLE_NAME_DOESNT_EXIST_2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void removeVehicleNullName() throws VehicleManagerException {
		vehicleService.removeVehicle(null);
	}
	
	/* ====================== VehicleExist Service ======================= */
	
	@Test
	public void vehicleExistTrue() throws VehicleManagerException {
		Assert.assertTrue(vehicleService.vehicleExist(VEHICLE_NAME_EXIST_1));
		Assert.assertTrue(vehicleService.vehicleExist(VEHICLE_NAME_EXIST_2));
	}
	
	@Test
	public void vehicleExistFalse() throws VehicleManagerException {
		Assert.assertTrue(!vehicleService.vehicleExist(VEHICLE_NAME_DOESNT_EXIST_2));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void vehicleExistNullName() throws VehicleManagerException {
		vehicleService.vehicleExist(null);
	}
	
	/* ============= CalculateVehicleAcquisitionYears Service =========== */
	
	@Test(expected = IllegalArgumentException.class)
	public void calculateVehicleAcquisiontYearsNullName() throws VehicleManagerException {
		vehicleService.calculateVehicleAcquisitionYears(null);
	}
	
	@Test(expected = VehicleDoesntExistException.class)
	public void calculateVehicleAcquisiontYearsVehicleDoesntExist() throws VehicleManagerException {
		vehicleService.calculateVehicleAcquisitionYears(VEHICLE_NAME_DOESNT_EXIST);
	}
	
	/* ============= CalculateVehicleLicensedYears Service ============== */
	
	@Test(expected = IllegalArgumentException.class)
	public void calculateVehicleLicensedYearsNullName() throws VehicleManagerException {
		vehicleService.calculateVehicleLicensedYears(null);
	}
	
	@Test(expected = VehicleDoesntExistException.class)
	public void calculateVehicleLicensedYearsVehicleDoesntExist() throws VehicleManagerException {
		vehicleService.calculateVehicleLicensedYears(VEHICLE_NAME_DOESNT_EXIST);
	}
	
	/* ============= CalculateVehicleUnlicensedYears Service ============ */
	
	@Test(expected = IllegalArgumentException.class)
	public void calculateVehicleUnlicensedYearsNullName() throws VehicleManagerException {
		vehicleService.calculateVehicleUnlicensedYears(null);
	}
	
	@Test(expected = VehicleDoesntExistException.class)
	public void calculateVehicleUnlicensedYearsVehicleDoesntExist() throws VehicleManagerException {
		vehicleService.calculateVehicleUnlicensedYears(VEHICLE_NAME_DOESNT_EXIST);
	}
	
	/* ================ ChangeLicensedVehicleData Service ================ */
	
	@Test
	public void changeLicensedVehicleDataSuccessNewLicense() throws VehicleManagerException {
		vehicleService.changeVehicleLicensedData(VEHICLE_NAME_EXIST_2, VALID_NEW_BRAND,
				VALID_NEW_DATE, VALID_NEW_LICENSE, VALID_NEW_DATE);
		VehicleLicensed v = obtainLicensedVehicle(VEHICLE_NAME_EXIST_2);
		Assert.assertTrue(v.getBrand().equals(VALID_NEW_BRAND));
		Assert.assertTrue(v.getLicense().getLicense().equals(VALID_NEW_LICENSE));
		Assert.assertFalse(licenseExist(VALID_LICENSE));
	}
	
	@Test
	public void changeLicensedVehicleDataSuccessSameLicense() throws VehicleManagerException {
		vehicleService.changeVehicleLicensedData(VEHICLE_NAME_EXIST_2, VALID_NEW_BRAND,
				VALID_NEW_DATE, VALID_LICENSE, VALID_NEW_DATE);
		VehicleLicensed v = obtainLicensedVehicle(VEHICLE_NAME_EXIST_2);
		Assert.assertTrue(v.getBrand().equals(VALID_NEW_BRAND));
		Assert.assertTrue(v.getLicense().getLicense().equals(VALID_LICENSE));
		Assert.assertTrue(licenseExist(VALID_LICENSE));	//Previous license was deleted
	}
	
	@Test(expected = VehicleDoesntExistException.class)
	public void changeLicensedVehicleDataVehicleDoesntExist() throws VehicleManagerException {
		vehicleService.changeVehicleLicensedData(VEHICLE_NAME_DOESNT_EXIST, VALID_NEW_BRAND,
				VALID_NEW_DATE, VALID_NEW_LICENSE, VALID_NEW_DATE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void changeLicensedVehicleDataNullName() throws VehicleManagerException {
		vehicleService.changeVehicleLicensedData(null, VALID_NEW_BRAND,
				VALID_NEW_DATE, VALID_NEW_LICENSE, VALID_NEW_DATE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void changeLicensedVehicleDataNullNewBrand() throws VehicleManagerException {
		vehicleService.changeVehicleLicensedData(VEHICLE_NAME_EXIST_2, null,
				VALID_NEW_DATE, VALID_NEW_LICENSE, VALID_NEW_DATE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void changeLicensedVehicleDataNullNewAcquisitionDate() throws VehicleManagerException {
		vehicleService.changeVehicleLicensedData(VEHICLE_NAME_EXIST_2, VALID_NEW_BRAND,
				null, VALID_NEW_LICENSE, VALID_NEW_DATE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void changeLicensedVehicleDataNullNewLicense() throws VehicleManagerException {
		vehicleService.changeVehicleLicensedData(VEHICLE_NAME_EXIST_2, VALID_NEW_BRAND,
				VALID_NEW_DATE, null, VALID_NEW_DATE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void changeLicensedVehicleDataNullNewLicenseDate() throws VehicleManagerException {
		vehicleService.changeVehicleLicensedData(VEHICLE_NAME_EXIST_2, VALID_NEW_BRAND,
				VALID_NEW_DATE, VALID_NEW_LICENSE, null);
	}
	
	/* ================ ChangeUnlicensedVehicleData Service ================ */
	
	@Test
	public void changeUnlicensedVehicleDataSuccess() throws VehicleManagerException {
		vehicleService.changeVehicleUnlicensedData(VEHICLE_NAME_EXIST_1, VALID_NEW_BRAND,
				VALID_NEW_DATE, VALID_NEW_FABRICATION_YEAR);
		VehicleUnlicensed v = obtainUnlicensedVehicle(VEHICLE_NAME_EXIST_1);
		Assert.assertTrue(v.getBrand().equals(VALID_NEW_BRAND));
		Assert.assertTrue(v.getFabricationYear() == VALID_NEW_FABRICATION_YEAR);
	}
	
	@Test(expected = VehicleDoesntExistException.class)
	public void changeUnlicensedVehicleDataVehicleDoesntExist() throws VehicleManagerException {
		vehicleService.changeVehicleUnlicensedData(VEHICLE_NAME_DOESNT_EXIST, VALID_NEW_BRAND,
				VALID_NEW_DATE, VALID_NEW_FABRICATION_YEAR);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void changeUnlicensedVehicleDataNullName() throws VehicleManagerException {
		vehicleService.changeVehicleUnlicensedData(null, VALID_NEW_BRAND,
				VALID_NEW_DATE, VALID_NEW_FABRICATION_YEAR);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void changeUnlicensedVehicleDataNullNewBrand() throws VehicleManagerException {
		vehicleService.changeVehicleUnlicensedData(VEHICLE_NAME_EXIST_1, null,
				VALID_NEW_DATE, VALID_NEW_FABRICATION_YEAR);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void changeUnlicensedVehicleDataNullNewAcquisitionDate() throws VehicleManagerException {
		vehicleService.changeVehicleUnlicensedData(VEHICLE_NAME_EXIST_1, VALID_NEW_BRAND,
				null, VALID_NEW_FABRICATION_YEAR);
	}
	
}
