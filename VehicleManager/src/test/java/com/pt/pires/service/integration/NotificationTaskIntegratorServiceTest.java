package com.pt.pires.service.integration;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.pt.pires.VehicleManagerApplication;
import com.pt.pires.domain.NotificationTask;
import com.pt.pires.domain.NotificationTaskHalfYear;
import com.pt.pires.domain.NotificationTaskOneTime;
import com.pt.pires.domain.NotificationTaskYear;
import com.pt.pires.domain.UserRole;
import com.pt.pires.domain.exceptions.NotificationDoesntExistException;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.service.VehicleManagerServiceTest;
import com.pt.pires.services.external.IEmailService;
import com.pt.pires.services.integrator.INotificationTaskIntegratorService;
import com.pt.pires.services.integrator.NotificationTaskIntegratorService;
import com.pt.pires.services.local.INotificationTaskService;
import com.pt.pires.services.local.IUserService;
import com.pt.pires.util.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VehicleManagerApplication.class)
@Transactional
@Rollback
public class NotificationTaskIntegratorServiceTest extends VehicleManagerServiceTest{

	@Mock
	private IEmailService emailService;
	
	@Autowired
	@Qualifier("notificationService")
	private INotificationTaskService notificationTaskService;
	
	@Autowired
	@Qualifier("userService")
	private IUserService userService;
	
	@Autowired
	@Qualifier("notificationIntegratorService")
	private INotificationTaskIntegratorService notificationTaskIntegratorService;
	
	private final static String USERNAME = "Bobbob";
	private final static String PASSWORD = "Bobbob";
	private final static String EMAIL = "bob@bob.com";
	
	private final static int FABRICATION_YEAR = 1994;
	
	private final static Date DATE_2 = DateUtil.getSimplifyDate(1, 1, 2016);
	
	private Long EXISTING_ID_1;
	private Long EXISTING_ID_2;
	private Long EXISTING_ID_3;
	private Long INEXISTENT_ID = new Long("999999");
	
	@Override
	public void populate() throws VehicleManagerException {
		MockitoAnnotations.initMocks(this);		//IMPORTANT: Setup Mockito annotations because !!!
		notificationTaskIntegratorService = new NotificationTaskIntegratorService(emailService,
				notificationTaskService,userService);
		newUnlicensedVehicle(VALID_VEHICLE_NAME, VALID_VEHICLE_BRAND, new Date(),FABRICATION_YEAR);
		newUser(USERNAME, PASSWORD, EMAIL, UserRole.ROLE_USER);
		NotificationTask n1 = new NotificationTaskOneTime(new Date(),VALID_DESCRIPTION);
		n1.setNotificationSent(false);
		EXISTING_ID_1 = newNotification(VALID_VEHICLE_NAME, n1);
		NotificationTask n2 = new NotificationTaskHalfYear(DATE_2, VALID_DESCRIPTION);
		n2.setNotificationSent(false);
		EXISTING_ID_2 = newNotification(VALID_VEHICLE_NAME, n2);
		NotificationTask n3 = new NotificationTaskYear(new Date(),VALID_DESCRIPTION);
		n3.setNotificationSent(true);
		EXISTING_ID_3 = newNotification(VALID_VEHICLE_NAME, n3);
	}
	
	
	@Test
	public void sendNotificationTask_1() throws VehicleManagerException {
		System.out.println(EXISTING_ID_1);
		Mockito.when(emailService.sendEmail(Mockito.eq(NotificationTaskIntegratorService.emailUsername),
				Mockito.eq(NotificationTaskIntegratorService.emailPassword), Mockito.eq(NotificationTaskIntegratorService.emailUsername),
				Mockito.eq(EMAIL),
				Mockito.eq(NotificationTaskIntegratorService.SUBJECT_HEADER + VALID_VEHICLE_NAME),
				Mockito.eq(VALID_DESCRIPTION))).thenReturn(true);
		notificationTaskIntegratorService.sendNotificationTask(EXISTING_ID_1,new Date());
		Mockito.verify(emailService,Mockito.times(1)).sendEmail(Mockito.eq(NotificationTaskIntegratorService.emailUsername),
				Mockito.eq(NotificationTaskIntegratorService.emailPassword), Mockito.eq(NotificationTaskIntegratorService.emailUsername),
				Mockito.eq(EMAIL),
				Mockito.eq(NotificationTaskIntegratorService.SUBJECT_HEADER + VALID_VEHICLE_NAME),
				Mockito.eq(VALID_DESCRIPTION));
		NotificationTask n = obtainNotification(VALID_VEHICLE_NAME, EXISTING_ID_1);
		Assert.assertTrue(n.isNotificationSent());
	}

	@Test
	public void sendNotificationTask_2() throws VehicleManagerException{
		Date currentDate = DateUtil.getSimplifyDate(1, 1, 2015);
		notificationTaskIntegratorService.sendNotificationTask(EXISTING_ID_2,currentDate);
		Mockito.verify(emailService,Mockito.times(0)).sendEmail(Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.eq(NotificationTaskIntegratorService.SUBJECT_HEADER + VALID_VEHICLE_NAME),
				Mockito.eq(VALID_DESCRIPTION));
		NotificationTask n = obtainNotification(VALID_VEHICLE_NAME, EXISTING_ID_2);
		Assert.assertFalse(n.isNotificationSent());
	}
	
	@Test
	public void sendNotificationTask_3() throws VehicleManagerException{
		notificationTaskIntegratorService.sendNotificationTask(EXISTING_ID_3,new Date());
		Mockito.verify(emailService,Mockito.times(0)).sendEmail(Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.eq(NotificationTaskIntegratorService.SUBJECT_HEADER + VALID_VEHICLE_NAME),
				Mockito.eq(VALID_DESCRIPTION));
		NotificationTask n = obtainNotification(VALID_VEHICLE_NAME, EXISTING_ID_3);
		Assert.assertTrue(n.isNotificationSent());
	}
	
	@Test(expected = NotificationDoesntExistException.class)
	public void sendNotificationTaskDoesntExist() throws VehicleManagerException{
		notificationTaskIntegratorService.sendNotificationTask(INEXISTENT_ID,new Date());
		Mockito.verify(emailService,Mockito.times(0)).sendEmail(Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.eq(NotificationTaskIntegratorService.SUBJECT_HEADER + VALID_VEHICLE_NAME),
				Mockito.eq(VALID_DESCRIPTION));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void sendNotificationTaskNullId() throws VehicleManagerException{
		notificationTaskIntegratorService.sendNotificationTask(null,new Date());
		Mockito.verify(emailService,Mockito.times(0)).sendEmail(Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.eq(NotificationTaskIntegratorService.SUBJECT_HEADER + VALID_VEHICLE_NAME),
				Mockito.eq(VALID_DESCRIPTION));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void sendNotificationTaskNullCurrentDate() throws VehicleManagerException{
		notificationTaskIntegratorService.sendNotificationTask(EXISTING_ID_1,null);
		Mockito.verify(emailService,Mockito.times(0)).sendEmail(Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.eq(NotificationTaskIntegratorService.SUBJECT_HEADER + VALID_VEHICLE_NAME),
				Mockito.eq(VALID_DESCRIPTION));
	}
	
}
