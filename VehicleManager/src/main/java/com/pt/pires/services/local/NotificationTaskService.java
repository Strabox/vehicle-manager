package com.pt.pires.services.local;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pt.pires.domain.NotificationTask;
import com.pt.pires.domain.NotificationTaskHalfYear;
import com.pt.pires.domain.NotificationTaskOneTime;
import com.pt.pires.domain.NotificationTaskYear;
import com.pt.pires.domain.Vehicle;
import com.pt.pires.domain.exceptions.NotificationDoesntExistException;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.persistence.INotificationRepository;

@Service
@Named("notificationService")
public class NotificationTaskService implements INotificationTaskService {

	@Inject
	@Named("registrationService")
	private IRegistrationService registrationService;
	
	@Inject
	@Named("vehicleService")
	private IVehicleService vehicleService;
	
	@Inject
	private INotificationRepository notificationRepository;
	
	
	@Override
	@Transactional(readOnly = false)
	public Long createYearNotification(String vehicleName, String notiDescription, Date initDate)
			throws VehicleManagerException {
		if(vehicleName == null || notiDescription == null || initDate == null) {
			throw new IllegalArgumentException();
		}
		Vehicle v = vehicleService.getVehicle(vehicleName);
		NotificationTaskYear newNotif = new NotificationTaskYear(initDate,notiDescription);
		newNotif.setVehicle(v);
		NotificationTaskYear noti = notificationRepository.save(newNotif);
		v.addNotification(noti);
		return noti.getId();
	}

	@Override
	@Transactional(readOnly = false)
	public Long createHalfYearNotification(String vehicleName, String notiDescription, Date initDate)
			throws VehicleManagerException {
		if(vehicleName == null || notiDescription == null || initDate == null) {
			throw new IllegalArgumentException();
		}
		Vehicle v = vehicleService.getVehicle(vehicleName);
		NotificationTaskHalfYear newNotif = new NotificationTaskHalfYear(initDate,notiDescription);
		newNotif.setVehicle(v);
		NotificationTaskHalfYear noti = notificationRepository.save(newNotif);
		v.addNotification(noti);
		return noti.getId();
	}

	@Override
	@Transactional(readOnly = false)
	public Long createOneTimeNotification(String vehicleName, String notiDescription, Date initDate)
			throws VehicleManagerException {
		if(vehicleName == null || notiDescription == null || initDate == null) {
			throw new IllegalArgumentException();
		}
		Vehicle v = vehicleService.getVehicle(vehicleName);
		NotificationTaskOneTime newNotif = new NotificationTaskOneTime(initDate,notiDescription);
		newNotif.setVehicle(v);
		newNotif = notificationRepository.save(newNotif);
		v.addNotification(newNotif);
		return newNotif.getId();
	}

	@Override
	@Transactional(readOnly = false)
	public void removeNotification(String vehicleName, long alertId) throws VehicleManagerException {
		if(vehicleName == null) {
			throw new IllegalArgumentException();
		}
		Vehicle v = vehicleService.getVehicle(vehicleName);
		v.removeNotification(alertId);
	}
	
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
	public boolean notifyDay(Long notificationId, Date currentDate) throws NotificationDoesntExistException {
		if(notificationId == null || currentDate == null) {
			throw new IllegalArgumentException();
		}
		NotificationTask noti = obtainNotification(notificationId);
		return noti.notifyDay(currentDate);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void setNotificationTaskSent(Long notificationId, boolean sent) throws VehicleManagerException {
		if(notificationId == null) {
			throw new IllegalArgumentException();
		}
		NotificationTask n = obtainNotification(notificationId);
		n.setNotificationSent(sent);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void notificationTaskDone(Long notificationId, Date currentDate, Long time,
			String description, Date date)
			throws VehicleManagerException {
		if(notificationId == null || currentDate == null || time == null || 
				description == null || date == null) {
			throw new IllegalArgumentException();
		}
		NotificationTask notification = obtainNotification(notificationId);
		registrationService.createRegistration(notification.getVehicle().getName(),
				time, description, date);
		notification.notificationTaskDone(currentDate);
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
