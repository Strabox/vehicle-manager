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
import com.pt.pires.domain.Note;
import com.pt.pires.domain.NotificationTask;
import com.pt.pires.domain.NotificationTaskYear;
import com.pt.pires.domain.Registration;
import com.pt.pires.domain.VehicleUnlicensed;
import com.pt.pires.domain.Vehicle;
import com.pt.pires.domain.exceptions.InvalidLicenseException;
import com.pt.pires.domain.exceptions.InvalidNoteException;
import com.pt.pires.domain.exceptions.InvalidNotificationException;
import com.pt.pires.domain.exceptions.InvalidRegistrationException;
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

	private final static String EMPTY_STRING = "";
	
	private final static String VEHICLE_NAME_DOESNT_EXIST = "Carrinha Cinza";
	private final static String VEHICLE_NAME_DOESNT_EXIST_2 = "Automóvel";
	private final static String VEHICLE_NAME_EXIST_1 = "Popó amarelo";
	private final static String VEHICLE_NAME_EXIST_2 = "Carro Azul";
	
	private final static String VEHICLE_BRAND = "Mitsubishi";
	private final static String VEHICLE_BRAND_1 = "Skoda";
	
	private final static Date VEHICLE_DATE = DateUtil.getSimplifyDate(new Date());
	
	private final static String VALID_LICENSE = "55-11-KI";
	private final static String VALID_LICENSE_2 = "55-26-AI";
	private final static String INVALID_LICENSE = "II-11-GH";
	
	private final static String VALID_DESCRIPTION = "Mudar o óleo do Motor!!";
	private final static String VALID_DESCRIPTION_2 = "Mudar a vareta do óleo :)?";
	
	private final static Long VALID_TIME = (long) 1000;
	
	private static Long EXISTING_REG_ID_1;
	private static Long EXISTING_REG_ID_2;
	private static Long EXISTING_NOTE_ID_1;
	private static Long EXISTING_NOTI_ID_1;
	
	@Autowired
	@Qualifier("vehicleService")
	private IVehicleService vehicleService;
	
	@Override
	public void populate() throws VehicleManagerException {
		newUnlicensedVehicle(VEHICLE_NAME_EXIST_1,VEHICLE_BRAND_1,VEHICLE_DATE);
		EXISTING_NOTE_ID_1 = newNote(VEHICLE_NAME_EXIST_1, VALID_DESCRIPTION);
		EXISTING_REG_ID_1 = newRegistration(VEHICLE_NAME_EXIST_1, VALID_TIME, VALID_DESCRIPTION, VEHICLE_DATE);
		EXISTING_REG_ID_2 = newRegistration(VEHICLE_NAME_EXIST_1, VALID_TIME, VALID_DESCRIPTION_2, VEHICLE_DATE);
		EXISTING_NOTI_ID_1 = newNotification(VEHICLE_NAME_EXIST_1, new NotificationTaskYear(VEHICLE_DATE, VALID_DESCRIPTION));
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
		vehicleService.createUnlicensedVehicle(VEHICLE_NAME_DOESNT_EXIST,VEHICLE_BRAND,VEHICLE_DATE);		
		VehicleUnlicensed v = obtainUnlicensedVehicle(VEHICLE_NAME_DOESNT_EXIST);
		Assert.assertNotNull(v);
		Assert.assertTrue(v.getName().equals(VEHICLE_NAME_DOESNT_EXIST));
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
		vehicleService.createUnlicensedVehicle(VEHICLE_NAME_DOESNT_EXIST,EMPTY_STRING,VEHICLE_DATE);		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createUnlicensedVehicleNullName() throws VehicleManagerException {
		vehicleService.createUnlicensedVehicle(null,VEHICLE_BRAND,VEHICLE_DATE);		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createUnlicensedVehicleNullBrand() throws VehicleManagerException {
		vehicleService.createUnlicensedVehicle(VEHICLE_NAME_DOESNT_EXIST,null,VEHICLE_DATE);		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createUnlicensedVehicleNullDate() throws VehicleManagerException {
		vehicleService.createUnlicensedVehicle(VEHICLE_NAME_DOESNT_EXIST,VEHICLE_BRAND,null);		
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
	
	/* ============== AddRegistrationToVehicleService =========== */
	
	@Test
	public void addRegistrationToVehicle() throws VehicleManagerException{
		Long regId = vehicleService.addRegistrationToVehicle(VEHICLE_NAME_EXIST_1, VALID_TIME
				, VALID_DESCRIPTION, VEHICLE_DATE);
		List<Registration> regs = obtainRegistrations(VEHICLE_NAME_EXIST_1);
		Assert.assertNotNull(regs);
		Assert.assertTrue(obtainRegistration(VEHICLE_NAME_EXIST_1, regId).getTime() == VALID_TIME);
		Assert.assertTrue(obtainRegistration(VEHICLE_NAME_EXIST_1, regId).getDescription().equals(VALID_DESCRIPTION));
	}
	
	@Test(expected = VehicleDoesntExistException.class)
	public void addRegistrationToVehicleDontExist() throws VehicleManagerException{
		vehicleService.addRegistrationToVehicle(VEHICLE_NAME_DOESNT_EXIST_2, VALID_TIME
				, VALID_DESCRIPTION, VEHICLE_DATE);
	}
	
	@Test(expected = InvalidRegistrationException.class)
	public void addRegistrationToVehicleInvalidRegistration() throws VehicleManagerException{
		vehicleService.addRegistrationToVehicle(VEHICLE_NAME_EXIST_1, VALID_TIME
				, EMPTY_STRING, VEHICLE_DATE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addRegistrationToVehicleNullName() throws VehicleManagerException{
		vehicleService.addRegistrationToVehicle(null, VALID_TIME
				, VALID_DESCRIPTION, VEHICLE_DATE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addRegistrationToVehicleNullDescription() throws VehicleManagerException{
		vehicleService.addRegistrationToVehicle(VEHICLE_NAME_EXIST_1, VALID_TIME
				, null, VEHICLE_DATE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addRegistrationToVehicleNullDate() throws VehicleManagerException{
		vehicleService.addRegistrationToVehicle(VEHICLE_NAME_EXIST_1, VALID_TIME
				, VALID_DESCRIPTION, null);
	}
	
	/* =========== RemoveRegistrationFromVehicleService ============== */
	
	@Test
	public void removeRegistrationFromVehicleExist() throws VehicleManagerException{
		vehicleService.removeRegistrationFromVehicle(VEHICLE_NAME_EXIST_1, EXISTING_REG_ID_1);
		Assert.assertNotNull(obtainRegistrations(VEHICLE_NAME_EXIST_1));
		Assert.assertNull(obtainRegistration(VEHICLE_NAME_EXIST_1, EXISTING_REG_ID_1));
	}
	
	public void removeAllRegistrationFromVehicleExist() throws VehicleManagerException{
		vehicleService.removeRegistrationFromVehicle(VEHICLE_NAME_EXIST_1, EXISTING_REG_ID_1);
		vehicleService.removeRegistrationFromVehicle(VEHICLE_NAME_EXIST_1, EXISTING_REG_ID_2);
		Assert.assertNotNull(obtainRegistrations(VEHICLE_NAME_EXIST_1));
		Assert.assertTrue(obtainRegistrations(VEHICLE_NAME_EXIST_1).isEmpty());
		Assert.assertNull(obtainRegistration(VEHICLE_NAME_EXIST_1, EXISTING_REG_ID_1));
		Assert.assertNull(obtainRegistration(VEHICLE_NAME_EXIST_2, EXISTING_REG_ID_2));
	}
	
	@Test(expected = VehicleDoesntExistException.class)
	public void removeRegistrationFromVehicleDoesntExist() throws VehicleManagerException{
		vehicleService.removeRegistrationFromVehicle(VEHICLE_NAME_DOESNT_EXIST_2, 1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void removeRegistrationFromVehicleNullName() throws VehicleManagerException{
		vehicleService.removeRegistrationFromVehicle(null, 1);
	}
	
	/* =================== AddNoteToVehicleService ============== */
	
	@Test
	public void addNoteToVehicle() throws VehicleManagerException{
		Long noteId = vehicleService.addNoteToVehicle(VEHICLE_NAME_EXIST_1, VALID_DESCRIPTION);
		List<Note> notes = obtainNotes(VEHICLE_NAME_EXIST_1);
		Assert.assertNotNull(notes);
		Assert.assertTrue(obtainNote(VEHICLE_NAME_EXIST_1, noteId).getDescription().equals(VALID_DESCRIPTION));
	}
	
	@Test(expected = VehicleDoesntExistException.class)
	public void addNoteToVehicleDontExist() throws VehicleManagerException{
		vehicleService.addNoteToVehicle(VEHICLE_NAME_DOESNT_EXIST_2, VALID_DESCRIPTION);
	}
	
	@Test(expected = InvalidNoteException.class)
	public void addNoteToVehicleInvalidNote() throws VehicleManagerException{
		vehicleService.addNoteToVehicle(VEHICLE_NAME_EXIST_1, EMPTY_STRING);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addNoteToVehicleNullName() throws VehicleManagerException{
		vehicleService.addNoteToVehicle(null, EMPTY_STRING);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addNoteToVehicleNullNote() throws VehicleManagerException{
		vehicleService.addNoteToVehicle(VEHICLE_NAME_EXIST_1, null);
	}
	
	/* =================== RemoveNoteFromVehicleService ================= */
	
	@Test
	public void removeNoteFromVehicleExist() throws VehicleManagerException{
		vehicleService.removeNoteFromVehicle(VEHICLE_NAME_EXIST_1, EXISTING_NOTE_ID_1);
		Assert.assertNotNull(obtainNotes(VEHICLE_NAME_EXIST_1));
		Assert.assertTrue(obtainNotes(VEHICLE_NAME_EXIST_1).isEmpty());
		Assert.assertNull(obtainNote(VEHICLE_NAME_EXIST_1, EXISTING_NOTE_ID_1));
	}
	
	@Test(expected = VehicleDoesntExistException.class)
	public void removeNoteFromVehicleDoesntExist() throws VehicleManagerException{
		vehicleService.removeNoteFromVehicle(VEHICLE_NAME_DOESNT_EXIST_2, 1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void removeNoteFromVehicleNullName() throws VehicleManagerException{
		vehicleService.removeNoteFromVehicle(null, 1);
	}
	
	/* =================== AddNotificationToVehicleService ============== */
	
	@Test
	public void addNotificationToVehicle() throws VehicleManagerException{
		Long notiId = vehicleService.addYearNotification(VEHICLE_NAME_EXIST_1, VALID_DESCRIPTION, VEHICLE_DATE);
		List<NotificationTask> notis = obtainNotifications(VEHICLE_NAME_EXIST_1);
		Assert.assertNotNull(notis);
		Assert.assertTrue(obtainNotification(VEHICLE_NAME_EXIST_1, notiId).getDescription().equals(VALID_DESCRIPTION));
	}
	
	@Test(expected = InvalidNotificationException.class)
	public void addNotificationToVehicleInvalidNotification() throws VehicleManagerException{
		vehicleService.addYearNotification(VEHICLE_NAME_EXIST_1, EMPTY_STRING, VEHICLE_DATE);
	}
	
	@Test(expected = VehicleDoesntExistException.class)
	public void addNotificationToVehicleInexistentVehicle() throws VehicleManagerException{
		vehicleService.addYearNotification(VEHICLE_NAME_DOESNT_EXIST_2, VALID_DESCRIPTION, VEHICLE_DATE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addNotificationToVehicleNullName() throws VehicleManagerException{
		vehicleService.addYearNotification(null, VALID_DESCRIPTION, VEHICLE_DATE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addNotificationToVehicleNullDescription() throws VehicleManagerException{
		vehicleService.addYearNotification(VEHICLE_NAME_EXIST_1, null, VEHICLE_DATE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addNotificationToVehicleNullDate() throws VehicleManagerException{
		vehicleService.addYearNotification(VEHICLE_NAME_EXIST_1, VALID_DESCRIPTION, null);
	}
	
	/* ================ RemoveNotificationFromVehicleService ============ */
	
	@Test
	public void removeNotificationFromVehicle()throws VehicleManagerException{
		vehicleService.removeNotificationFromVehicle(VEHICLE_NAME_EXIST_1, EXISTING_NOTI_ID_1);
		Assert.assertNotNull(obtainNotifications(VEHICLE_NAME_EXIST_1));
		Assert.assertTrue(obtainNotifications(VEHICLE_NAME_EXIST_1).isEmpty());
		Assert.assertNull(obtainNotification(VEHICLE_NAME_EXIST_1, EXISTING_NOTI_ID_1));
	}
	
	@Test(expected = VehicleDoesntExistException.class)
	public void removeNotificationFromVehicleInexistentVehicle() throws VehicleManagerException{
		vehicleService.removeNotificationFromVehicle(VEHICLE_NAME_DOESNT_EXIST_2, 2);	
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void removeNotificationFromVehicleNullName() throws VehicleManagerException{
		vehicleService.removeNotificationFromVehicle(null, 2);	
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
