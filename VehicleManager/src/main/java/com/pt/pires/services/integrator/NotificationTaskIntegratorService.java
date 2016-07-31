package com.pt.pires.services.integrator;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.pt.pires.domain.NotificationTask;
import com.pt.pires.domain.User;
import com.pt.pires.domain.UserRole;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.services.external.IEmailService;
import com.pt.pires.services.local.INotificationTaskService;
import com.pt.pires.services.local.IUserService;

@Service
@Named("notificationIntegratorService")
public class NotificationTaskIntegratorService implements INotificationTaskIntegratorService {

	public final static String SUBJECT_HEADER = "Aviso: Manutenção ";
	
	@Value("${application.email: error}")
	public String emailUsername;
	
	@Value("${application.password: error}")
	public String emailPassword;
	
	@Inject
	@Named("emailService")
	private IEmailService emailService;
	
	@Inject
	@Named("notificationService")
	private INotificationTaskService notificationService;
	
	@Inject
	@Named("userService")
	private IUserService userService;
	
	
	public NotificationTaskIntegratorService(IEmailService es,INotificationTaskService ns,
			IUserService us) {
		this.emailService = es;
		this.notificationService = ns;
		this.userService = us;
	}
	
	public NotificationTaskIntegratorService() { }
	
	@Override
	public void sendNotificationTask(Long notificationId,Date currentDate,String emailUsername,
			String emailPassword) throws VehicleManagerException {
		if(notificationId == null || currentDate == null || emailUsername == null || emailPassword == null) {
			throw new IllegalArgumentException();
		}
		boolean notify = notificationService.notifyDay(notificationId, currentDate);
		NotificationTask n = notificationService.getNotificationTask(notificationId);
		if(notify && !n.isNotificationSent()) {
			List<User> usersToSend = (List<User>) userService.getUsersByRole(UserRole.ROLE_USER);
			for(User u : usersToSend) {
				 boolean sent = emailService.sendEmail(emailUsername, emailPassword, emailUsername,
						u.getEmail(), SUBJECT_HEADER + n.getVehicle().getName(), n.getDescription());
					if(sent) {
						notificationService.setNotificationTaskSent(notificationId, true);
						System.out.println("[Enviando notificação/email para " + u.getUsername() + "]");
					}
			}
		}
	}

}
