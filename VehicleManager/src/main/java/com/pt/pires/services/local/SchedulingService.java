package com.pt.pires.services.local;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.pt.pires.domain.NotificationTask;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.services.integrator.INotificationTaskIntegratorService;

@Service("schedulingService")
public class SchedulingService implements ISchedulingService {
	
	@Autowired
	@Qualifier("notificationService")
	private INotificationTaskService notificationService;
	
	@Autowired
	@Qualifier("notificationIntegratorService")
	private INotificationTaskIntegratorService notiIntegratorService;
	
	
	@Override
	@Scheduled(fixedRate = 5000)
	//@Scheduled(cron = "${application.sendNotifications.time: 0 0 12 1/1 * ? *}")	//Default: every day at midnight
	public void sendNotificationsTaskScheduler() {
		System.out.println(("[Executing send notification task....]"));
		List<NotificationTask> notifications; 
		notifications = (List<NotificationTask>) notificationService.getAllNotificationsTasks();
		for(NotificationTask notif : notifications) {
			try {
				notiIntegratorService.sendNotificationTask(notif.getId());
			}catch(VehicleManagerException e){
				continue;	//Notification was deleted in the meantime!!
			}
		}
	}
	
}
