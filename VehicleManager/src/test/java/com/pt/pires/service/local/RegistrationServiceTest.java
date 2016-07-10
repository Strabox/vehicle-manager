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
import com.pt.pires.domain.Registration;
import com.pt.pires.domain.exceptions.InvalidRegistrationException;
import com.pt.pires.domain.exceptions.VehicleDoesntExistException;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.service.VehicleManagerServiceTest;
import com.pt.pires.services.local.IRegistrationService;
import com.pt.pires.util.DateUtil;

@RunWith( SpringJUnit4ClassRunner.class )
@SpringApplicationConfiguration(classes = VehicleManagerApplication.class)
@Transactional
@Rollback
public class RegistrationServiceTest extends VehicleManagerServiceTest {

	@Autowired
	@Qualifier("registrationService")
	private IRegistrationService registrationService;
	
	private final static String VEHICLE_NAME_DOESNT_EXIST_2 = "Automóvel";
	private final static String VEHICLE_NAME_EXIST_1 = "Popó amarelo";
	private final static String VEHICLE_NAME_EXIST_2 = "Carro Azul";
	
	private final static String VEHICLE_BRAND_1 = "Skoda";
	
	private final static Date VEHICLE_DATE = DateUtil.getSimplifyDate(new Date());
	
	private final static int FABRICATION_YEAR = 1994;
	
	private final static String VALID_LICENSE = "55-11-KI";
	
	private final static String VALID_DESCRIPTION = "Mudar o óleo do Motor!!";
	private final static String VALID_DESCRIPTION_2 = "Mudar a vareta do óleo :)?";
	
	private final static Long VALID_TIME = (long) 1000;
	
	private static Long EXISTING_REG_ID_1;
	private static Long EXISTING_REG_ID_2;
	
	@Override
	public void populate() throws VehicleManagerException {
		newUnlicensedVehicle(VEHICLE_NAME_EXIST_1,VEHICLE_BRAND_1,VEHICLE_DATE, FABRICATION_YEAR);
		EXISTING_REG_ID_1 = newRegistration(VEHICLE_NAME_EXIST_1, VALID_TIME, VALID_DESCRIPTION, VEHICLE_DATE);
		EXISTING_REG_ID_2 = newRegistration(VEHICLE_NAME_EXIST_1, VALID_TIME, VALID_DESCRIPTION_2, VEHICLE_DATE);
		newLicensedVehicle(VEHICLE_NAME_EXIST_2, VEHICLE_BRAND_1, VEHICLE_DATE, VALID_LICENSE, VEHICLE_DATE);
	}
	
/* ============== CreateRegistrationService =========== */
	
	@Test
	public void createRegistration() throws VehicleManagerException{
		Long regId = registrationService.createRegistration(VEHICLE_NAME_EXIST_1, VALID_TIME
				, VALID_DESCRIPTION, VEHICLE_DATE);
		List<Registration> regs = obtainRegistrations(VEHICLE_NAME_EXIST_1);
		Assert.assertNotNull(regs);
		Assert.assertTrue(obtainRegistration(VEHICLE_NAME_EXIST_1, regId).getTime() == VALID_TIME);
		Assert.assertTrue(obtainRegistration(VEHICLE_NAME_EXIST_1, regId).getDescription().equals(VALID_DESCRIPTION));
	}
	
	@Test(expected = VehicleDoesntExistException.class)
	public void createRegistrationDontExist() throws VehicleManagerException{
		registrationService.createRegistration(VEHICLE_NAME_DOESNT_EXIST_2, VALID_TIME
				, VALID_DESCRIPTION, VEHICLE_DATE);
	}
	
	@Test(expected = InvalidRegistrationException.class)
	public void createRegistrationInvalidRegistration() throws VehicleManagerException{
		registrationService.createRegistration(VEHICLE_NAME_EXIST_1, VALID_TIME
				, EMPTY_STRING, VEHICLE_DATE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createRegistrationNullName() throws VehicleManagerException{
		registrationService.createRegistration(null, VALID_TIME
				, VALID_DESCRIPTION, VEHICLE_DATE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createRegistrationNullDescription() throws VehicleManagerException{
		registrationService.createRegistration(VEHICLE_NAME_EXIST_1, VALID_TIME
				, null, VEHICLE_DATE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createRegistrationNullDate() throws VehicleManagerException{
		registrationService.createRegistration(VEHICLE_NAME_EXIST_1, VALID_TIME
				, VALID_DESCRIPTION, null);
	}
	
	/* =========== RemoveRegistrationService ============== */
	
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
		registrationService.removeRegistration(VEHICLE_NAME_DOESNT_EXIST_2, 1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void removeRegistrationNullName() throws VehicleManagerException{
		registrationService.removeRegistration(null, 1);
	}
	
	/* ============== GetVehicleRegistrationsService ========= */
	
	

}
