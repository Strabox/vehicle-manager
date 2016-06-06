package com.pt.pires.service;

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
import com.pt.pires.domain.LicensedVehicle;
import com.pt.pires.domain.Note;
import com.pt.pires.domain.Registration;
import com.pt.pires.domain.UnlicensedVehicle;
import com.pt.pires.domain.exceptions.InvalidLicenseException;
import com.pt.pires.domain.exceptions.InvalidNoteException;
import com.pt.pires.domain.exceptions.InvalidRegistrationException;
import com.pt.pires.domain.exceptions.InvalidVehicleBrandException;
import com.pt.pires.domain.exceptions.InvalidVehicleNameException;
import com.pt.pires.domain.exceptions.VehicleAlreadyExistException;
import com.pt.pires.domain.exceptions.VehicleDoesntExistException;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.services.local.IVehicleService;
import com.pt.pires.util.DateUtil;

@RunWith( SpringJUnit4ClassRunner.class )
@SpringApplicationConfiguration(classes = VehicleManagerApplication.class)
@Transactional
@Rollback
public class VehicleServiceTest extends VehicleManagerServiceTest {

	private final static String EMPTY_STRING = "";
	
	private final static String VEHICLE_NAME_DOESNT_EXIST = "Automóvel";
	private final static String VEHICLE_NAME = "Carrinha Cinza";
	private final static String VEHICLE_NAME_EXIST_1 = "Popó amarelo";
	private final static String VEHICLE_NAME_EXIST_2 = "Carro Azul";
	
	private final static String VEHICLE_BRAND = "Mitsubishi";
	private final static String VEHICLE_BRAND_1 = "Skoda";
	
	private final static Date VEHICLE_DATE = DateUtil.getSimplifyDate(new Date());
	
	private final static String VALID_LICENSE = "55-11-KI"; 
	private final static String INVALID_LICENSE = "II-11-GH";
	
	private final static String VALID_DESCRIPTION = "Mudar o óleo do Motor!!";
	
	private final static long VALID_TIME = 1000;
	
	@Autowired
	@Qualifier("vehicleService")
	private IVehicleService vehicleService;
	
	@Override
	public void populate() throws VehicleManagerException {
		newUnlicensedVehicle(VEHICLE_NAME_EXIST_1,VEHICLE_BRAND_1,VEHICLE_DATE);
		newLicensedVehicle(VEHICLE_NAME_EXIST_2, VEHICLE_BRAND_1, VEHICLE_DATE, VALID_LICENSE, VEHICLE_DATE);
	}
	
	/* ========= GetUnlicensedVehicles Service ================ */

	@Test
	public void getUnlicensedVehicles(){
		List<UnlicensedVehicle> c = (List<UnlicensedVehicle>) vehicleService.getUnlicensedVehicles();
		Assert.assertTrue(c.size() == 1);
		UnlicensedVehicle v = c.get(0);
		Assert.assertTrue(v.getName().equals(VEHICLE_NAME_EXIST_1));
		Assert.assertTrue(v.getBrand().equals(VEHICLE_BRAND_1));
	}
	
	@Test
	public void getUnlicensedVehiclesNoVehicles(){
		deleteVehicle(VEHICLE_NAME_EXIST_1);
		List<UnlicensedVehicle> c = (List<UnlicensedVehicle>) vehicleService.getUnlicensedVehicles();
		Assert.assertTrue(c.size() == 0);
	}
	
	/* ========= GetLicensedVehicles Service ================ */

	@Test
	public void getLicensedVehicles(){
		List<LicensedVehicle> c = (List<LicensedVehicle>) vehicleService.getLicensedVehicles();
		Assert.assertTrue(c.size() == 1);
		LicensedVehicle v = c.get(0);
		Assert.assertTrue(v.getName().equals(VEHICLE_NAME_EXIST_2));
		Assert.assertTrue(v.getBrand().equals(VEHICLE_BRAND_1));
		Assert.assertTrue(v.getLicense().getLicense().equals(VALID_LICENSE));
	}

	@Test
	public void getLicensedVehiclesNoVehicles(){
		deleteVehicle(VEHICLE_NAME_EXIST_2);
		List<LicensedVehicle> c = (List<LicensedVehicle>) vehicleService.getLicensedVehicles();
		Assert.assertTrue(c.size() == 0);
	}
	/* ========= CreateLicensedVehicle Service ================ */
	
	@Test
	public void createLicensedVehicle() throws VehicleManagerException{
		vehicleService.createLicensedVehicle(VEHICLE_NAME, VEHICLE_BRAND, VEHICLE_DATE,
				VALID_LICENSE, VEHICLE_DATE);
		LicensedVehicle v = obtainLicensedVehicle(VEHICLE_NAME);
		Assert.assertNotNull(v);
		Assert.assertTrue(v.getName().equals(VEHICLE_NAME));
		Assert.assertTrue(v.getBrand().equals(VEHICLE_BRAND));
		Assert.assertTrue(v.getLicense().getLicense().equals(VALID_LICENSE));
	}
	
	@Test(expected = InvalidLicenseException.class)
	public void createLicensedVehicleInvalidLicense() throws VehicleManagerException{
		vehicleService.createLicensedVehicle(VEHICLE_NAME, VEHICLE_BRAND, VEHICLE_DATE,
				INVALID_LICENSE, VEHICLE_DATE);
	}
	
	@Test(expected = VehicleAlreadyExistException.class)
	public void createLicensedVehicleAlreadyExist() throws VehicleManagerException{
		vehicleService.createLicensedVehicle(VEHICLE_NAME_EXIST_2, VEHICLE_BRAND, VEHICLE_DATE,
				VALID_LICENSE, VEHICLE_DATE);
	}
	
	@Test(expected = InvalidVehicleNameException.class)
	public void createLicensedVehicleInvalidName() throws VehicleManagerException{
		vehicleService.createLicensedVehicle(EMPTY_STRING, VEHICLE_BRAND, VEHICLE_DATE,
				VALID_LICENSE, VEHICLE_DATE);
	}
	
	@Test(expected = InvalidVehicleBrandException.class)
	public void createLicensedVehicleDontExist() throws VehicleManagerException{
		vehicleService.createLicensedVehicle(VEHICLE_NAME, EMPTY_STRING, VEHICLE_DATE,
				VALID_LICENSE, VEHICLE_DATE);
	}
	
	/* ========= CreateUnlicensedVehicle Service ================ */

	@Test
	public void createUnlicensedVehicle() throws VehicleManagerException {
		vehicleService.createUnlicensedVehicle(VEHICLE_NAME,VEHICLE_BRAND,VEHICLE_DATE);		
		UnlicensedVehicle v = obtainUnlicensedVehicle(VEHICLE_NAME);
		Assert.assertNotNull(v);
		Assert.assertTrue(v.getName().equals(VEHICLE_NAME));
		Assert.assertTrue(v.getBrand().equals(VEHICLE_BRAND));
	}

	@Test(expected = VehicleAlreadyExistException.class)
	public void createUnlicensedVehicleAlreadyExist() throws VehicleManagerException {
		vehicleService.createUnlicensedVehicle(VEHICLE_NAME_EXIST_1,VEHICLE_BRAND,VEHICLE_DATE);		
	}

	@Test(expected = InvalidVehicleNameException.class)
	public void createUnlicensedVehicleEmptyName() throws VehicleManagerException {
		vehicleService.createUnlicensedVehicle(EMPTY_STRING,VEHICLE_BRAND,VEHICLE_DATE);		
	}

	@Test(expected = InvalidVehicleBrandException.class)
	public void createUnlicensedVehicleEmptyBrand() throws VehicleManagerException {
		vehicleService.createUnlicensedVehicle(VEHICLE_NAME,EMPTY_STRING,VEHICLE_DATE);		
	}
	
	/* ========= GetUnlicensedVehicle Service ================ */

	@Test
	public void getUnlicensedVehicle() throws VehicleManagerException{
		UnlicensedVehicle v = vehicleService.getUnlicensedVehicle(VEHICLE_NAME_EXIST_1);
		Assert.assertNotNull(v);
		Assert.assertTrue(v.getName().equals(VEHICLE_NAME_EXIST_1));
		Assert.assertTrue(v.getBrand().equals(VEHICLE_BRAND_1));
	}

	@Test(expected = VehicleDoesntExistException.class)
	public void getUnlicensedVehicleDontExist() throws VehicleManagerException{
		vehicleService.getLicensedVehicle(VEHICLE_NAME_DOESNT_EXIST);
	}
	
	/* ========= CreateLicensedVehicle Service ================ */

	@Test
	public void getLicensedVehicle() throws VehicleManagerException{
		LicensedVehicle v = vehicleService.getLicensedVehicle(VEHICLE_NAME_EXIST_2);
		Assert.assertNotNull(v);
		Assert.assertTrue(v.getName().equals(VEHICLE_NAME_EXIST_2));
		Assert.assertTrue(v.getBrand().equals(VEHICLE_BRAND_1));
	}

	@Test(expected = VehicleDoesntExistException.class)
	public void getLicensedVehicleDontExist() throws VehicleManagerException{
		vehicleService.getLicensedVehicle(VEHICLE_NAME_DOESNT_EXIST);
	}
	
	/* ================= RemoveVehicle Service ================== */

	@Test
	public void removeVehicle(){
		vehicleService.removeVehicle(VEHICLE_NAME_EXIST_1);
		Assert.assertNull(obtainVehicle(VEHICLE_NAME_EXIST_1));
	}

	@Test
	public void removeVehicleDoesntExist(){
		vehicleService.removeVehicle(VEHICLE_NAME_DOESNT_EXIST);
	}
	
	/* ============== AddRegistrationToVehicle Service =========== */
	
	@Test
	public void addRegistrationToVehicle() throws VehicleManagerException{
		vehicleService.addRegistrationToVehicle(VEHICLE_NAME_EXIST_1, VALID_TIME
				, VALID_DESCRIPTION, VEHICLE_DATE);
		List<Registration> regs = obtainRegistrations(VEHICLE_NAME_EXIST_1);
		Assert.assertNotNull(regs);
		Assert.assertTrue(regs.size() == 1);
		Assert.assertTrue(regs.get(0).getTime() == VALID_TIME);
		Assert.assertTrue(regs.get(0).getDescription().equals(VALID_DESCRIPTION));
	}
	
	@Test(expected = VehicleDoesntExistException.class)
	public void addRegistrationToVehicleDontExist() throws VehicleManagerException{
		vehicleService.addRegistrationToVehicle(VEHICLE_NAME_DOESNT_EXIST, VALID_TIME
				, VALID_DESCRIPTION, VEHICLE_DATE);
	}
	
	@Test(expected = InvalidRegistrationException.class)
	public void addRegistrationToVehicleInvalidRegistration() throws VehicleManagerException{
		vehicleService.addRegistrationToVehicle(VEHICLE_NAME_EXIST_1, VALID_TIME
				, EMPTY_STRING, VEHICLE_DATE);
	}
	
	/* =================== AddNoteToVehicle Service ============== */
	
	@Test
	public void addNoteToVehicle() throws VehicleManagerException{
		vehicleService.addNoteToVehicle(VEHICLE_NAME_EXIST_1, VALID_DESCRIPTION);
		List<Note> notes = obtainNotes(VEHICLE_NAME_EXIST_1);
		Assert.assertNotNull(notes);
		Assert.assertTrue(notes.size() == 1);
		Assert.assertTrue(notes.get(0).getDescription().equals(VALID_DESCRIPTION));
	}
	
	@Test(expected = VehicleDoesntExistException.class)
	public void addNoteToVehicleDontExist() throws VehicleManagerException{
		vehicleService.addNoteToVehicle(VEHICLE_NAME_DOESNT_EXIST, VALID_DESCRIPTION);
	}
	
	@Test(expected = InvalidNoteException.class)
	public void addNoteToVehicleInvalidNote() throws VehicleManagerException{
		vehicleService.addNoteToVehicle(VEHICLE_NAME_EXIST_1, EMPTY_STRING);
	}
	
}
