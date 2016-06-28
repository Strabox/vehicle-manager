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
import com.pt.pires.domain.NotificationTask;
import com.pt.pires.domain.NotificationTaskHalfYear;
import com.pt.pires.domain.NotificationTaskOneTime;
import com.pt.pires.domain.NotificationTaskYear;
import com.pt.pires.domain.exceptions.NotificationDoesntExistException;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.service.VehicleManagerServiceTest;
import com.pt.pires.services.local.INotificationTaskService;
import com.pt.pires.util.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VehicleManagerApplication.class)
@Transactional
@Rollback
public class NotificationServiceTest extends VehicleManagerServiceTest {

	private static final String VEHICLE_NAME = "Grey car";
	private static final String VEHICLE_BRAND = "Citroen";
	
	private static final Date DATE_1 = DateUtil.getSimplifyDate(1,0,2016);
	private static final Date DATE_2 = DateUtil.getSimplifyDate(31,11,2016);
	private static final Date DATE_3 = DateUtil.getSimplifyDate(23,9,2016);
	private static final Date DATE_4 = DateUtil.getSimplifyDate(31,2,2016);
	private static final Date DATE_5 = DateUtil.getSimplifyDate(9, 8, 2016);
	
	private static final String DESCRIPTION = "Mudar o óleo do motor";
	private static final String DESCRIPTION_1 = "Mudar o pneu :)";
	private static final String DESCRIPTION_2 = "Mudar o tejadilho lol";
	
	private static Long INEXISTENT_ID = new Long(999999999);
	private static Long EXISTING_ID_1;
	private static Long EXISTING_ID_2;
	private static Long EXISTING_ID_3;
	private static Long EXISTING_ID_4;
	private static Long EXISTING_ID_5;
	
	@Autowired
	@Qualifier("notificationService")
	private INotificationTaskService notificationTaskService;
	
	
	@Override
	public void populate() throws VehicleManagerException {
		newUnlicensedVehicle(VEHICLE_NAME, VEHICLE_BRAND, DATE_1);
		EXISTING_ID_1 = newNotification(VEHICLE_NAME, new NotificationTaskYear(DATE_1, DESCRIPTION));
		EXISTING_ID_2 = newNotification(VEHICLE_NAME, new NotificationTaskHalfYear(DATE_2, DESCRIPTION_1));
		EXISTING_ID_3 = newNotification(VEHICLE_NAME, new NotificationTaskYear(DATE_3,DESCRIPTION_2));
		EXISTING_ID_4 = newNotification(VEHICLE_NAME, new NotificationTaskHalfYear(DATE_4, DESCRIPTION_1));
		EXISTING_ID_5 = newNotification(VEHICLE_NAME, new NotificationTaskOneTime(DATE_5, DESCRIPTION_1));
	}
	
	/* ========================= NotifyDay Service ======================= */
	
	@Test(expected = NotificationDoesntExistException.class)
	public void notifyDayNotificationInexistentNoti() throws VehicleManagerException {
		Date current = DateUtil.getSimplifyDate(31,11,2015);
		notificationTaskService.notifyDay(INEXISTENT_ID, current);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void notifyDayNotificationNullId() throws VehicleManagerException {
		Date current = DateUtil.getSimplifyDate(31,11,2015);
		notificationTaskService.notifyDay(null, current);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void notifyDayNotificationNullCurrentDate() throws VehicleManagerException {
		notificationTaskService.notifyDay(EXISTING_ID_1, null);
	}
	
	/* === NotificationYear === */
	
	/* DATE_1 = (1,0,2016) */
	@Test
	public void notifyDayNotificationYearNoNoti_1() throws VehicleManagerException {
		Date current = DateUtil.getSimplifyDate(31,11,2015);
		Assert.assertFalse(notificationTaskService.notifyDay(EXISTING_ID_1, current));
	}
	
	/* DATE_1 = (1,0,2016) */
	@Test
	public void notifyDayNotificationYearNoti_1() throws VehicleManagerException {
		Date current = DateUtil.getSimplifyDate(2,0,2016);
		Assert.assertTrue(notificationTaskService.notifyDay(EXISTING_ID_1, current));
	}
	
	/* DATE_1 = (1,0,2016) */
	@Test
	public void nextNotificationYearNoti_1() throws VehicleManagerException {
		Date expected = DateUtil.getSimplifyDate(1,0,2017);
		Date currentDate = DateUtil.getSimplifyDate(24,9,2019);
		notificationTaskService.setNextNotificationTask(EXISTING_ID_1,currentDate);
		NotificationTaskYear n = (NotificationTaskYear) obtainNotification(VEHICLE_NAME, EXISTING_ID_1);
		Assert.assertTrue(n.getNotiDate().equals(expected));
	}
	
	/* DATE_3 = (23,9,2016) */
	@Test
	public void notifyDayNotificationYearNoNoti_2() throws VehicleManagerException {
		Date current = DateUtil.getSimplifyDate(22,9,2016);
		Assert.assertFalse(notificationTaskService.notifyDay(EXISTING_ID_3, current));
	}
	
	/* DATE_3 = (23,9,2016) */
	@Test
	public void notifyDayNotificationYearNoti_2() throws VehicleManagerException {
		Date current = DateUtil.getSimplifyDate(27,9,2016);
		Assert.assertTrue(notificationTaskService.notifyDay(EXISTING_ID_3, current));
	}
	
	/* DATE_3 = (23,9,2016) */
	@Test
	public void nextNotificationYearNoti_2() throws VehicleManagerException {
		Date expected = DateUtil.getSimplifyDate(23,9,2017);
		Date currentDate = DateUtil.getSimplifyDate(24,9,2017);
		notificationTaskService.setNextNotificationTask(EXISTING_ID_3,currentDate);
		NotificationTaskYear n = (NotificationTaskYear) obtainNotification(VEHICLE_NAME, EXISTING_ID_3);
		Assert.assertTrue(n.getNotiDate().equals(expected));
	}
	
	/* === NotificationHalfYear === */
	
	/* DATE_2 = (31,11,2016) */
	@Test
	public void notifyDayNotificationHalfYearNoNoti_1() throws VehicleManagerException {
		Date current = DateUtil.getSimplifyDate(29,11,2016);
		Assert.assertFalse(notificationTaskService.notifyDay(EXISTING_ID_2, current));
	}
	
	/* DATE_2 = (31,11,2016) */
	@Test
	public void notifyDayNotificationHalfYearNoti_1() throws VehicleManagerException {
		Date current = DateUtil.getSimplifyDate(1,0,2017);
		Assert.assertTrue(notificationTaskService.notifyDay(EXISTING_ID_2, current));
	}
	
	/* DATE_2 = (31,11,2016) */
	@Test
	public void nextNotificationHalfYearNoti_1() throws VehicleManagerException {
		Date expected = DateUtil.getSimplifyDate(30,5,2017);
		Date currentDate = DateUtil.getSimplifyDate(24,9,2019);
		notificationTaskService.setNextNotificationTask(EXISTING_ID_2,currentDate);
		NotificationTaskHalfYear n = (NotificationTaskHalfYear) obtainNotification(VEHICLE_NAME, EXISTING_ID_2);
		Assert.assertTrue(n.getNotiDate().equals(expected));
	}
	
	/* DATE_4 = (31,2,2016) */
	@Test
	public void notifyDayNotificationHalfYearNoNoti_2() throws VehicleManagerException {
		Date current = DateUtil.getSimplifyDate(29,1,2016);
		Assert.assertFalse(notificationTaskService.notifyDay(EXISTING_ID_4, current));
	}
	
	/* DATE_4 = (31,2,2016) */
	@Test
	public void notifyDayNotificationHalfYearNoti_2() throws VehicleManagerException {
		Date current = DateUtil.getSimplifyDate(9,3,2016);
		boolean res = notificationTaskService.notifyDay(EXISTING_ID_4, current);
		obtainNotification(VEHICLE_NAME, EXISTING_ID_4);
		Assert.assertTrue(res);
	}
	
	/* DATE_4 = (31,2,2016) */
	@Test
	public void nextNotificationHalfYearNoti_2() throws VehicleManagerException {
		Date expected = DateUtil.getSimplifyDate(30,8,2016);
		Date currentDate = DateUtil.getSimplifyDate(24,9,2019);
		notificationTaskService.setNextNotificationTask(EXISTING_ID_4,currentDate);
		NotificationTaskHalfYear n = (NotificationTaskHalfYear) obtainNotification(VEHICLE_NAME, EXISTING_ID_4);
		Assert.assertTrue(n.getNotiDate().equals(expected));
	}
	
	/* === OneTime === */
	
	/* Date_5 = (9, 8, 2016) */
	@Test
	public void notifyDayNotificationOneTimeNoNoti_1() throws VehicleManagerException {
		Date current = DateUtil.getSimplifyDate(29,1,2016);
		Assert.assertFalse(notificationTaskService.notifyDay(EXISTING_ID_5, current));
	}
	
	/* Date_5 = (9, 8, 2016) */
	@Test
	public void notifyDayNotificationOneTimeNoti_1() throws VehicleManagerException {
		Date current = DateUtil.getSimplifyDate(29,10,2016);
		Assert.assertTrue(notificationTaskService.notifyDay(EXISTING_ID_5, current));
	}
	
	/* ============== GetAllNotifications Service ============= */
	
	@Test
	public void getAllNotifications() throws VehicleManagerException {
		List<NotificationTask> noti = (List<NotificationTask>) notificationTaskService.getAllNotificationsTasks();
		Assert.assertTrue(noti.size() == 5);
	}
	
	@Test
	public void getAllNotificationsEmptySet() throws VehicleManagerException {
		deleteVehicle(VEHICLE_NAME);
		List<NotificationTask> noti = (List<NotificationTask>) notificationTaskService.getAllNotificationsTasks();
		Assert.assertTrue(noti.isEmpty());
	}
	
	/* ============== GetActiveNotifications Service ============= */
	
	@Test
	public void getActiveNotifications() throws VehicleManagerException {
		Date currentDate = DateUtil.getSimplifyDate(1, 5, 2016);
		List<NotificationTask> activeNoti = (List<NotificationTask>) notificationTaskService.getActiveNotificationsTasks(currentDate);
		Assert.assertTrue(activeNoti.size() == 2);
	}
	
	@Test
	public void getActiveNotificationsEmptySet() throws VehicleManagerException {
		Date currentDate = new Date();
		deleteVehicle(VEHICLE_NAME);
		List<NotificationTask> activeNoti = (List<NotificationTask>) notificationTaskService.getActiveNotificationsTasks(currentDate);
		Assert.assertTrue(activeNoti.isEmpty());
	}
	
}
