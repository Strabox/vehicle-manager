package com.pt.pires.services.local;

import java.util.Collection;
import java.util.Date;

import com.pt.pires.domain.NotificationTask;
import com.pt.pires.domain.exceptions.VehicleManagerException;

/**
 * Services related to notification's tasks
 * @author André
 *
 */
public interface INotificationTaskService {

	Long createYearNotification(String vehicleName, String description, Date initDate) throws VehicleManagerException;
	
	Long createHalfYearNotification(String vehicleName, String description, Date initDate) throws VehicleManagerException;
	
	Long createOneTimeNotification(String vehicleName, String description,Date initDate) throws VehicleManagerException;
	
	void removeNotification(String vehicleName, long alertId) throws VehicleManagerException;
		
	NotificationTask getNotificationTaskById(Long notificationId) throws VehicleManagerException; 
		 
	Collection<NotificationTask> getActiveNotificationsTasks(Date currentDate);
	 
	Collection<NotificationTask> getAllNotificationsTasks();
	 
	boolean notifyDay(Long notificationId, Date currentDate) throws VehicleManagerException;
	 
	void setNotificationTaskSent(Long notificationId, boolean sent) throws VehicleManagerException;
	 
	void notificationTaskDone(Long notifiationId, Date currentDate, Long time, String description, Date date) throws VehicleManagerException;
	 
}
