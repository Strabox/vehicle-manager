package com.pt.pires.services.integrator;

import java.util.Date;

import com.pt.pires.domain.exceptions.VehicleManagerException;

public interface INotificationTaskIntegratorService {

	void sendNotificationTask(Long notificationId,Date currentDate) throws VehicleManagerException;
	
}
