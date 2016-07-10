package com.pt.pires.services.integrator;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.pt.pires.domain.NotificationTask;
import com.pt.pires.domain.User;
import com.pt.pires.domain.UserRole;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.services.external.IEmailService;
import com.pt.pires.services.local.INotificationTaskService;
import com.pt.pires.services.local.IUserService;

@Service("notificationIntegratorService")
public class NotificationTaskIntegratorService implements INotificationTaskIntegratorService {

	public final static String SUBJECT_HEADER = "Aviso: Manutenção ";
	
	@Value("${application.email}")
	public static String emailUsername;
	
	@Value("${application.password}")
	public static String emailPassword;
	
	@Autowired
	@Qualifier("emailService")
	private IEmailService emailService;
	
	@Autowired
	@Qualifier("notificationService")
	private INotificationTaskService notificationService;
	
	@Autowired
	@Qualifier("userService")
	private IUserService userService;
	
	
	public NotificationTaskIntegratorService(IEmailService es,INotificationTaskService ns,
			IUserService us) {
		this.emailService = es;
		this.notificationService = ns;
		this.userService = us;
	}
	
	public NotificationTaskIntegratorService() { }
	
	@Override
	public void sendNotificationTask(Long notificationId,Date currentDate) throws VehicleManagerException {
		if(notificationId == null || currentDate == null) {
			throw new IllegalArgumentException();
		}
		boolean notify = notificationService.notifyDay(notificationId, currentDate);
		NotificationTask n = notificationService.getNotificationTask(notificationId);
		System.out.println(notificationId + " " + (n.isNotificationSent()) + " " + emailUsername);
		if(notify && !n.isNotificationSent()) {
			List<User> usersToSend = (List<User>) userService.getUserByRole(UserRole.ROLE_USER);
			if(usersToSend.isEmpty()) {
				System.out.println("VAZIOOOOOOOOOOOOOOOOOO");
			}
			for(User u : usersToSend) {
				System.out.println("OLAAAAAAAAAAAAAAAAAAAAAAAAAAA");
				boolean sent = emailService.sendEmail(emailUsername, emailPassword, emailUsername,
						u.getEmail(), SUBJECT_HEADER + n.getVehicle().getName(), n.getDescription()); 
					if(sent) {
						System.out.println("HEREEEEEEEEEEEEEEEEEEEEEEEEE");
						notificationService.setNotificationTaskSent(notificationId, true);
						System.out.println("[Enviando notificação/email para " + u.getUsername() + "]");
					}
			}
		}
	}

}
