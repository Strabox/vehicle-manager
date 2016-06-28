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

	 NotificationTask getNotificationTask(Long notificationId) throws VehicleManagerException; 
	 
	 Collection<NotificationTask> getActiveNotificationsTasks(Date currentDate);
	 
	 Collection<NotificationTask> getAllNotificationsTasks();
	 
	 boolean notifyDay(Long notificationId,Date currentDate) throws VehicleManagerException;
	 
	 void setNextNotificationTask(Long notificationId,Date currentDate) throws VehicleManagerException;
	 
}
