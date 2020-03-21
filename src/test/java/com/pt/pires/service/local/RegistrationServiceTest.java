package com.pt.pires.service.local;

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
import com.pt.pires.domain.Registration;
import com.pt.pires.domain.exceptions.InvalidRegistrationException;
import com.pt.pires.domain.exceptions.InvalidRegistrationTimeException;
import com.pt.pires.domain.exceptions.VehicleDoesntExistException;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.service.VehicleManagerServiceTest;
import com.pt.pires.services.local.IRegistrationService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VehicleManagerApplication.class)
@Transactional
@Rollback
public class RegistrationServiceTest extends VehicleManagerServiceTest {

	@Inject
	@Named("registrationService")
	private IRegistrationService registrationService;
	
	private final static String VEHICLE_NAME_DOESNT_EXIST = "Automóvel do Boss";
	private final static String VEHICLE_NAME_EXIST_1 = "Popó amarelo";
	private final static String VEHICLE_NAME_EXIST_2 = "Carro Azul";
	
	private static Long EXISTING_REG_ID_1;
	private static Long EXISTING_REG_ID_2;
	
	
	@Override
	@Before
	public void populate() throws VehicleManagerException {
		newUnlicensedVehicle(VEHICLE_NAME_EXIST_1, VALID_VEHICLE_BRAND, VALID_CURRENT_DATE, VALID_FABRICATION_YEAR);
		EXISTING_REG_ID_1 = newRegistration(VEHICLE_NAME_EXIST_1, VALID_TIME, VALID_DESCRIPTION, VALID_CURRENT_DATE);
		EXISTING_REG_ID_2 = newRegistration(VEHICLE_NAME_EXIST_1, VALID_TIME, VALID_DESCRIPTION, VALID_CURRENT_DATE);
		newLicensedVehicle(VEHICLE_NAME_EXIST_2, VALID_VEHICLE_BRAND, VALID_CURRENT_DATE, VALID_LICENSE, VALID_CURRENT_DATE);
	}
	
	/* ================= CreateRegistration Service ================ */
	
	@Test
	public void createRegistration() throws VehicleManagerException{
		Long regId = registrationService.createRegistration(VEHICLE_NAME_EXIST_1, VALID_TIME
				, VALID_DESCRIPTION, VALID_CURRENT_DATE);
		List<Registration> regs = obtainRegistrations(VEHICLE_NAME_EXIST_1);
		Assert.assertNotNull(regs);
		Assert.assertTrue(obtainRegistration(VEHICLE_NAME_EXIST_1, regId).getTime() == VALID_TIME);
		Assert.assertTrue(obtainRegistration(VEHICLE_NAME_EXIST_1, regId).getDescription().equals(VALID_DESCRIPTION));
	}
	
	@Test(expected = VehicleDoesntExistException.class)
	public void createRegistrationDontExist() throws VehicleManagerException{
		registrationService.createRegistration(VEHICLE_NAME_DOESNT_EXIST, VALID_TIME
				, VALID_DESCRIPTION, VALID_CURRENT_DATE);
	}
	
	@Test(expected = InvalidRegistrationTimeException.class)
	public void createRegistrationInvalidRegistrationTime() throws VehicleManagerException {
		registrationService.createRegistration(VEHICLE_NAME_EXIST_1, -1, VALID_DESCRIPTION,
				VALID_CURRENT_DATE);
	}
	
	@Test(expected = InvalidRegistrationException.class)
	public void createRegistrationInvalidRegistration() throws VehicleManagerException{
		registrationService.createRegistration(VEHICLE_NAME_EXIST_1, VALID_TIME
				, EMPTY_STRING, VALID_CURRENT_DATE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createRegistrationNullName() throws VehicleManagerException{
		registrationService.createRegistration(null, VALID_TIME
				, VALID_DESCRIPTION, VALID_CURRENT_DATE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createRegistrationNullDescription() throws VehicleManagerException{
		registrationService.createRegistration(VEHICLE_NAME_EXIST_1, VALID_TIME
				, null, VALID_CURRENT_DATE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createRegistrationNullDate() throws VehicleManagerException{
		registrationService.createRegistration(VEHICLE_NAME_EXIST_1, VALID_TIME
				, VALID_DESCRIPTION, null);
	}
	
	/* ================ RemoveRegistration Service ================= */
	
	@Test
	public void removeRegistrationExist() throws VehicleManagerException{
		registrationService.removeRegistration(VEHICLE_NAME_EXIST_1, EXISTING_REG_ID_1);
		Assert.assertNotNull(obtainRegistrations(VEHICLE_NAME_EXIST_1));
		Assert.assertNull(obtainRegistration(VEHICLE_NAME_EXIST_1, EXISTING_REG_ID_1));
	}
	
	public void removeAllRegistrationExist() throws VehicleManagerException{
		registrationService.removeRegistration(VEHICLE_NAME_EXIST_1, EXISTING_REG_ID_1);
		registrationService.removeRegistration(VEHICLE_NAME_EXIST_1, EXISTING_REG_ID_2);
		Assert.assertNotNull(obtainRegistrations(VEHICLE_NAME_EXIST_1));
		Assert.assertTrue(obtainRegistrations(VEHICLE_NAME_EXIST_1).isEmpty());
		Assert.assertNull(obtainRegistration(VEHICLE_NAME_EXIST_1, EXISTING_REG_ID_1));
		Assert.assertNull(obtainRegistration(VEHICLE_NAME_EXIST_2, EXISTING_REG_ID_2));
	}
	
	@Test(expected = VehicleDoesntExistException.class)
	public void removeRegistrationDoesntExist() throws VehicleManagerException{
		registrationService.removeRegistration(VEHICLE_NAME_DOESNT_EXIST, 1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void removeRegistrationNullName() throws VehicleManagerException{
		registrationService.removeRegistration(null, 1);
	}
	
	/* ============== GetVehicleRegistrations Service ========= */
	
	@Test
	public void getVehicleRegistrationsSuccess() throws VehicleManagerException {
		List<Registration> regs = (List<Registration>) registrationService.getVehicleRegistrations(VEHICLE_NAME_EXIST_1);
		Assert.assertTrue(regs.size() == 2);
	}
	
	@Test
	public void getVehicleRegistrationsSuccess_2() throws VehicleManagerException {
		List<Registration> regs = (List<Registration>) registrationService.getVehicleRegistrations(VEHICLE_NAME_EXIST_2);
		Assert.assertTrue(regs.isEmpty());
	}
	
	@Test(expected = VehicleDoesntExistException.class)
	public void getVehicleRegistrationsVehicleDoesntExist() throws VehicleManagerException {
		registrationService.getVehicleRegistrations(VEHICLE_NAME_DOESNT_EXIST);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getVehicleRegistrationsNullVehicleName() throws VehicleManagerException {
		registrationService.getVehicleRegistrations(null);
	}

}
