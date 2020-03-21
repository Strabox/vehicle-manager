package com.pt.pires.controllers.rest;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pt.pires.controllers.ControllerExceptionHandler;
import com.pt.pires.domain.NotificationTaskYear;
import com.pt.pires.domain.Registration;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.services.local.INotificationTaskService;

/**
 * Rest endpoints for ntoficationTask services
 * @author André
 *
 */
@RestController
public class NotificationTaskControllerRest extends ControllerExceptionHandler {
	
	private static final String NOTIFICATION_TASK_YEAR = "Year";
	private static final String NOTIFICATION_TASK_HALF_YEAR = "HalfYear";
	private static final String NOTIFICATION_TASK_ONE_TIME = "OneTime";
	
	@Inject
	@Named("notificationService")
	private INotificationTaskService notificationTaskService;
	
	
	public NotificationTaskControllerRest(INotificationTaskService ns) {
		notificationTaskService = ns;
	}
	
	public NotificationTaskControllerRest() { }
	
	/**
	 * Add a notification to vehicle 
	 * @param vehicleName Target vehicle to add the notification
	 * @param notification Notification to add (From JSON) from HTTP Post
	 * @param type HTTP parameter expected { Year, HalfYear, OneTime } otherwise error is returned
	 * @return Id of the added notification 
	 * @throws VehicleManagerException 
	 */
	@RequestMapping(value = "/vehicle/{vehicleName}/notification",method = RequestMethod.POST,params = {"type"})
	public ResponseEntity<Long> addNotification(@PathVariable String vehicleName,
			@RequestBody NotificationTaskYear notification,@RequestParam(value = "type",required = true)String type)
					throws VehicleManagerException {
		System.out.println("[Add notification] Vehicle Name: " + vehicleName);
		Long notiId;
		if(type.equals(NOTIFICATION_TASK_YEAR)) {
			notiId = notificationTaskService.createYearNotification(vehicleName, notification.getDescription(),
					notification.getNotiDate());
		} else if(type.equals(NOTIFICATION_TASK_HALF_YEAR)) {
			notiId = notificationTaskService.createHalfYearNotification(vehicleName, notification.getDescription(),
					notification.getNotiDate());
		} else if(type.equals(NOTIFICATION_TASK_ONE_TIME)) {
			notiId = notificationTaskService.createOneTimeNotification(vehicleName, notification.getDescription(),
					notification.getNotiDate());
		}
		else {
			return new ResponseEntity<Long>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Long>(notiId,HttpStatus.OK);
	}
	
	/**
	 * Remove notification from the vehicle <b>if already exist</b>
	 * @param vehicleName Name of the vehicle
	 * @param notiId Notification id
	 * @return
	 * @throws VehicleManagerException
	 */
	@RequestMapping(value = "/vehicle/{vehicleName}/alert/{notiId}",method = RequestMethod.DELETE)
	public ResponseEntity<String> removeNotificationFromVehicle(@PathVariable String vehicleName,
			@PathVariable long notiId) throws VehicleManagerException {
		System.out.println("[Remove Alert] Vehicle: " + vehicleName);
		notificationTaskService.removeNotification(vehicleName, notiId);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	/**
	 * Submitting a completion registration for active notification task
	 * @param notificationId Notification ID
	 * @param reg Registrations added to the corresponding vehicle
	 * @return
	 * @throws VehicleManagerException
	 */
	@RequestMapping(value = "/notification/completed/{notificationId}",method = RequestMethod.POST)
	public ResponseEntity<String> notificationTaskCompleted(@PathVariable long notificationId
			,@RequestBody Registration reg) throws VehicleManagerException {
		System.out.println("[Notification Task Completed] Task Id: " + notificationId);
		notificationTaskService.notificationTaskDone(notificationId,new Date(), reg.getTime(),reg.getDescription(), reg.getDate());
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
}
