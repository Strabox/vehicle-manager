package com.pt.pires.services.local;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pt.pires.domain.NotificationTask;
import com.pt.pires.domain.exceptions.NotificationDoesntExistException;
import com.pt.pires.persistence.NotificationRepository;

@Service("notificationService")
public class NotificationTaskService implements INotificationTaskService {

	@Autowired
	private NotificationRepository notificationRepository;
	
	
	@Override
	@Transactional(readOnly = true)
	public NotificationTask getNotificationTask(Long notificationId) throws NotificationDoesntExistException {
		if(notificationId == null) {
			throw new IllegalArgumentException();
		}
		return obtainNotification(notificationId);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<NotificationTask> getActiveNotificationsTasks(Date currentDate) {
		ArrayList<NotificationTask> res = new ArrayList<>();
		ArrayList<NotificationTask> all = (ArrayList<NotificationTask>) notificationRepository.findAll();
		for(NotificationTask n : all){
			if(n.notifyDay(new Date())){
				res.add(n);
			}
		}
		return res;
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<NotificationTask> getAllNotificationsTasks() {
		return (Collection<NotificationTask>) notificationRepository.findAll();
	}
	
	@Override
	@Transactional(readOnly = false)
	public void setNextNotificationTask(Long notificationId,Date currentDate) throws NotificationDoesntExistException {
		if(notificationId == null) {
			throw new IllegalArgumentException();
		}
		NotificationTask noti = obtainNotification(notificationId);
		noti.setNextNotificationDate(currentDate);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean notifyDay(Long notificationId,Date currentDate) throws NotificationDoesntExistException {
		if(notificationId == null || currentDate == null) {
			throw new IllegalArgumentException();
		}
		NotificationTask noti = obtainNotification(notificationId);
		return noti.notifyDay(currentDate);
	}
	
	/* ================== Auxiliary methods ====================== */
	
	private NotificationTask obtainNotification(Long notificationId) throws NotificationDoesntExistException {
		NotificationTask noti = notificationRepository.findOne(notificationId);
		if(noti == null) {
			throw new NotificationDoesntExistException();
		}
		return noti;
	}
	
}
