package com.pt.pires.services.integrator;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pt.pires.domain.NotificationTask;
import com.pt.pires.domain.User;
import com.pt.pires.domain.UserRole;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.services.external.IEmailService;
import com.pt.pires.services.local.INotificationTaskService;
import com.pt.pires.services.local.IUserService;
import com.pt.pires.services.local.IVehicleService;

@Service("notificationIntegratorService")
public class NotificationTaskIntegratorService implements INotificationTaskIntegratorService {

	@Value("${application.email}")
	private String emailUsername;
	
	@Value("${application.password}")
	private String emailPassword;
	
	@Autowired
	@Qualifier("emailService")
	private IEmailService emailService;
	
	@Autowired
	@Qualifier("notificationService")
	private INotificationTaskService notificationService;
	
	@Autowired
	@Qualifier("vehicleService")
	private IVehicleService vehicleService;
	
	@Autowired
	@Qualifier("userService")
	private IUserService userService;
	
	
	public NotificationTaskIntegratorService(IEmailService es,INotificationTaskService ns,
			IVehicleService vs, IUserService us) {
		this.emailService = es;
		this.notificationService = ns;
		this.vehicleService = vs;
		this.userService = us;
	}
	
	public NotificationTaskIntegratorService() { }
	
	@Override
	public void sendNotificationTask(Long notificationId) throws VehicleManagerException {
		boolean notify = notificationService.notifyDay(notificationId, new Date());
		if(notify) {
			NotificationTask n = notificationService.getNotificationTask(notificationId);
			List<User> usersToSend = (List<User>) userService.getUserByRole(UserRole.ROLE_USER);
			usersToSend.forEach(
				(v)-> {   
						/* emailService.sendEmail(emailUsername, emailPassword, emailUsername,
							v.getEmail(), "Aviso: Manutenção " + n.getVehicle().getName(), n.getDescription()); */
						System.out.println("[Enviando email para " + v.getUsername() + "]"); 
					});
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void notificationTaskCompleted(Long notificationId,Long time,Date date) throws VehicleManagerException {
		NotificationTask notification = notificationService.getNotificationTask(notificationId);
		vehicleService.addRegistrationToVehicle(notification.getVehicle().getName(),
				time, notification.getDescription(), date);
		notificationService.setNextNotificationTask(notificationId,new Date());
	}

}
