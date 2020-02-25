package com.pt.pires.service.local;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.pt.pires.VehicleManagerApplication;
import com.pt.pires.domain.NotificationTask;
import com.pt.pires.domain.NotificationTaskHalfYear;
import com.pt.pires.domain.NotificationTaskOneTime;
import com.pt.pires.domain.NotificationTaskYear;
import com.pt.pires.domain.Registration;
import com.pt.pires.domain.exceptions.InvalidNotificationTaskException;
import com.pt.pires.domain.exceptions.NotificationDoesntExistException;
import com.pt.pires.domain.exceptions.VehicleDoesntExistException;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.service.VehicleManagerServiceTest;
import com.pt.pires.services.local.INotificationTaskService;
import com.pt.pires.util.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VehicleManagerApplication.class)
@Transactional
@Rollback
public class NotificationTaskServiceTest extends VehicleManagerServiceTest {
	
	private static final String VEHICLE_NAME_DOESNT_EXIST = "Inexistent car name";
	
	private static final Date DONE_DATE = DateUtil.getSimplifyDate(1,1,2016);
	
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
	
	@Inject
	@Named("notificationService")
	private INotificationTaskService notificationTaskService;
	
	
	@Override
	public void populate() throws VehicleManagerException {
		newUnlicensedVehicle(VALID_VEHICLE_NAME, VALID_VEHICLE_BRAND, DATE_1, VALID_FABRICATION_YEAR);
		NotificationTask n1 = new NotificationTaskYear(DATE_1, DESCRIPTION);
		NotificationTask n2 = new NotificationTaskHalfYear(DATE_2, DESCRIPTION_1);
		NotificationTask n3 = new NotificationTaskYear(DATE_3,DESCRIPTION_2);
		NotificationTask n4 = new NotificationTaskHalfYear(DATE_4, DESCRIPTION_1);
		NotificationTask n5 = new NotificationTaskOneTime(DATE_5, DESCRIPTION_1);
		n1.setNotificationSent(true);
		n2.setNotificationSent(true);
		n3.setNotificationSent(true);
		n4.setNotificationSent(true);
		n5.setNotificationSent(true);
		EXISTING_ID_1 = newNotification(VALID_VEHICLE_NAME, n1);
		EXISTING_ID_2 = newNotification(VALID_VEHICLE_NAME, n2);
		EXISTING_ID_3 = newNotification(VALID_VEHICLE_NAME, n3);
		EXISTING_ID_4 = newNotification(VALID_VEHICLE_NAME, n4);
		EXISTING_ID_5 = newNotification(VALID_VEHICLE_NAME, n5);
	}
	
	
	/* =================== AddNotificationToVehicleService ============== */
	
	@Test
	public void createNotification() throws VehicleManagerException{
		Long notiId = notificationTaskService.createYearNotification(VALID_VEHICLE_NAME, VALID_DESCRIPTION, VALID_CURRENT_DATE);
		List<NotificationTask> notis = obtainNotifications(VALID_VEHICLE_NAME);
		Assert.assertNotNull(notis);
		Assert.assertTrue(obtainNotification(VALID_VEHICLE_NAME, notiId).getDescription().equals(VALID_DESCRIPTION));
	}
	
	@Test(expected = InvalidNotificationTaskException.class)
	public void createNotificationInvalidNotification() throws VehicleManagerException{
		notificationTaskService.createYearNotification(VALID_VEHICLE_NAME, EMPTY_STRING, VALID_CURRENT_DATE);
	}
	
	@Test(expected = VehicleDoesntExistException.class)
	public void createNotificationInexistentVehicle() throws VehicleManagerException{
		notificationTaskService.createYearNotification(VEHICLE_NAME_DOESNT_EXIST, VALID_DESCRIPTION, VALID_CURRENT_DATE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createNotificationNullName() throws VehicleManagerException{
		notificationTaskService.createYearNotification(null, VALID_DESCRIPTION, VALID_CURRENT_DATE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createNotificationNullDescription() throws VehicleManagerException{
		notificationTaskService.createYearNotification(VALID_VEHICLE_NAME, null, VALID_CURRENT_DATE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createNotificationNullDate() throws VehicleManagerException{
		notificationTaskService.createYearNotification(VALID_VEHICLE_NAME, VALID_DESCRIPTION, null);
	}
	
	/* ================ RemoveNotificationFromVehicleService ============ */
	
	@Test
	public void removeNotificationSuccess()throws VehicleManagerException{
		notificationTaskService.removeNotification(VALID_VEHICLE_NAME, EXISTING_ID_1);
		notificationTaskService.removeNotification(VALID_VEHICLE_NAME, EXISTING_ID_2);
		notificationTaskService.removeNotification(VALID_VEHICLE_NAME, EXISTING_ID_3);
		notificationTaskService.removeNotification(VALID_VEHICLE_NAME, EXISTING_ID_4);
		notificationTaskService.removeNotification(VALID_VEHICLE_NAME, EXISTING_ID_5);
		Assert.assertNotNull(obtainVehicle(VALID_VEHICLE_NAME));
		Assert.assertTrue(obtainNotifications(VALID_VEHICLE_NAME).isEmpty());
		Assert.assertNull(obtainNotification(VALID_VEHICLE_NAME, EXISTING_ID_1));
		Assert.assertNull(obtainNotification(VALID_VEHICLE_NAME, EXISTING_ID_2));
		Assert.assertNull(obtainNotification(VALID_VEHICLE_NAME, EXISTING_ID_3));
		Assert.assertNull(obtainNotification(VALID_VEHICLE_NAME, EXISTING_ID_4));
		Assert.assertNull(obtainNotification(VALID_VEHICLE_NAME, EXISTING_ID_5));
	}
	
	@Test(expected = VehicleDoesntExistException.class)
	public void removeNotificationInexistentVehicle() throws VehicleManagerException{
		notificationTaskService.removeNotification(VEHICLE_NAME_DOESNT_EXIST, 2);	
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void removeNotificationeNullName() throws VehicleManagerException{
		notificationTaskService.removeNotification(null, 2);	
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
	
	@Test(expected = IllegalArgumentException.class)
	public void notificationTaskCompletedNullId() throws VehicleManagerException{
		notificationTaskService.notificationTaskDone(null, DATE_1, VALID_TIME, DESCRIPTION, DATE_1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void notificationTaskCompletedNullCurrentDate() throws VehicleManagerException{
		notificationTaskService.notificationTaskDone(new Long(99), null, VALID_TIME, DESCRIPTION, DATE_1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void notificationTaskCompletedNullTime() throws VehicleManagerException{
		notificationTaskService.notificationTaskDone(new Long(99), DATE_1, null, DESCRIPTION, DATE_1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void notificationTaskCompletedNullDescription() throws VehicleManagerException{
		notificationTaskService.notificationTaskDone(new Long(99), DATE_1, VALID_TIME, null, DATE_1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void notificationTaskCompletedNullDate() throws VehicleManagerException{
		notificationTaskService.notificationTaskDone(new Long(99), DATE_1, VALID_TIME, DESCRIPTION, null);
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
		notificationTaskService.notificationTaskDone(EXISTING_ID_1, currentDate, VALID_TIME, DESCRIPTION, DONE_DATE);
		NotificationTaskYear n = (NotificationTaskYear) obtainNotification(VALID_VEHICLE_NAME, EXISTING_ID_1);
		List<Registration> regs = obtainRegistrations(VALID_VEHICLE_NAME);
		Assert.assertTrue(regs.get(0).getDescription().equals(DESCRIPTION));
		Assert.assertTrue(regs.get(0).getTime() == VALID_TIME);
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
		notificationTaskService.notificationTaskDone(EXISTING_ID_3,currentDate,VALID_TIME,DESCRIPTION,DONE_DATE);
		NotificationTaskYear n = (NotificationTaskYear) obtainNotification(VALID_VEHICLE_NAME, EXISTING_ID_3);
		List<Registration> regs = obtainRegistrations(VALID_VEHICLE_NAME);
		Assert.assertTrue(regs.get(0).getDescription().equals(DESCRIPTION));
		Assert.assertTrue(regs.get(0).getTime() == VALID_TIME);
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
		notificationTaskService.notificationTaskDone(EXISTING_ID_2,currentDate,VALID_TIME,DESCRIPTION,DONE_DATE);
		NotificationTaskHalfYear n = (NotificationTaskHalfYear) obtainNotification(VALID_VEHICLE_NAME, EXISTING_ID_2);
		List<Registration> regs = obtainRegistrations(VALID_VEHICLE_NAME);
		Assert.assertTrue(regs.get(0).getDescription().equals(DESCRIPTION));
		Assert.assertTrue(regs.get(0).getTime() == VALID_TIME);
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
		obtainNotification(VALID_VEHICLE_NAME, EXISTING_ID_4);
		Assert.assertTrue(res);
	}
	
	/* DATE_4 = (31,2,2016) */
	@Test
	public void nextNotificationHalfYearNoti_2() throws VehicleManagerException {
		Date expected = DateUtil.getSimplifyDate(30,8,2016);
		Date currentDate = DateUtil.getSimplifyDate(24,9,2019);
		notificationTaskService.notificationTaskDone(EXISTING_ID_4,currentDate,VALID_TIME,DESCRIPTION,DONE_DATE);
		NotificationTaskHalfYear n = (NotificationTaskHalfYear) obtainNotification(VALID_VEHICLE_NAME, EXISTING_ID_4);
		List<Registration> regs = obtainRegistrations(VALID_VEHICLE_NAME);
		Assert.assertTrue(regs.get(0).getDescription().equals(DESCRIPTION));
		Assert.assertTrue(regs.get(0).getTime() == VALID_TIME);
		Assert.assertTrue(n.getNotiDate().equals(expected));
	}
	
	/* === OneTime === */
	
	/* Date_5 = (9, 8, 2016) */
	@Test
	public void notifyDayNotificationOneTimeNotiNoNotification() throws VehicleManagerException {
		Date current = DateUtil.getSimplifyDate(29,1,2016);
		Assert.assertFalse(notificationTaskService.notifyDay(EXISTING_ID_5, current));
	}
	
	/* Date_5 = (9, 8, 2016) */
	@Test
	public void notifyDayNotificationOneTimeNotiNotificationSuccess() throws VehicleManagerException {
		Date current = DateUtil.getSimplifyDate(29,10,2016);
		Assert.assertTrue(notificationTaskService.notifyDay(EXISTING_ID_5, current));
	}
	
	/* Date_5 = (9, 8, 2016) */
	@Test
	public void notificationTaskDoneOneTimeNoti() throws VehicleManagerException {
		Date currentDate = DateUtil.getSimplifyDate(24,9,2019);
		notificationTaskService.notificationTaskDone(EXISTING_ID_5,currentDate,VALID_TIME,DESCRIPTION,DONE_DATE);
		NotificationTaskOneTime n = (NotificationTaskOneTime) obtainNotification(VALID_VEHICLE_NAME, EXISTING_ID_5);
		List<Registration> regs = obtainRegistrations(VALID_VEHICLE_NAME);
		Assert.assertNull(n); 			// OneTimeNotification removed from vehicle !!! 
		Assert.assertTrue(regs.get(0).getDescription().equals(DESCRIPTION));
		Assert.assertTrue(regs.get(0).getTime() == VALID_TIME);
	}
	
	/* ============== GetAllNotifications Service ============= */
	
	@Test
	public void getAllNotifications() throws VehicleManagerException {
		List<NotificationTask> noti = (List<NotificationTask>) notificationTaskService.getAllNotificationsTasks();
		Assert.assertTrue(noti.size() == 5);
	}
	
	@Test
	public void getAllNotificationsEmptySet() throws VehicleManagerException {
		deleteVehicle(VALID_VEHICLE_NAME);
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
		deleteVehicle(VALID_VEHICLE_NAME);
		List<NotificationTask> activeNoti = (List<NotificationTask>) notificationTaskService.getActiveNotificationsTasks(currentDate);
		Assert.assertTrue(activeNoti.isEmpty());
	}
	
}
