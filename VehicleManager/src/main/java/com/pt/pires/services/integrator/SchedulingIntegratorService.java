package com.pt.pires.services.integrator;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.pt.pires.domain.NotificationTask;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.services.local.INotificationTaskService;

@Service
@Named("schedulingService")
public class SchedulingIntegratorService implements ISchedulingIntegratorService {
	
	@Value("${application.email: error}")
	public String emailUsername;
	
	@Value("${application.password: error}")
	public String emailPassword;
	
	@Inject
	@Named("notificationService")
	private INotificationTaskService notificationService;
	
	@Inject
	@Named("notificationIntegratorService")
	private INotificationTaskIntegratorService notiIntegratorService;
	
	/**
	 * Schedule is every day at midnight by DEFAULT
	 */
	@Override
	@Scheduled(cron = "${application.sendNotifications.time: 0 1 12 1/1 * ?}")
	public void sendNotificationsTaskScheduler() {
		System.out.println(("[Executing notifications scheduler task....]"));
		List<NotificationTask> notifications; 
		notifications = (List<NotificationTask>) notificationService.getAllNotificationsTasks();
		for(NotificationTask notif : notifications) {
			try {
				notiIntegratorService.sendNotificationTask(notif.getId(),new Date(),emailUsername,emailPassword);
			}catch(VehicleManagerException e){
				continue;	//Notification was deleted in the meantime!!
			}
		}
	}
	
}
