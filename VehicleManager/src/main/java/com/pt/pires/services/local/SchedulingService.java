package com.pt.pires.services.local;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pt.pires.domain.Notification;
import com.pt.pires.persistence.NotificationRepository;

@Service
public class SchedulingService implements ISchedulingService{
	
	private class ScheduleMidnight extends TimerTask {

		@Autowired
		private NotificationRepository notifRepository;
		
		@Autowired
		@Qualifier("emailService")
		private IEmailService emailService;
		
		@SuppressWarnings("unused")
		@Override
		public void run() {
			List<Notification> notifications; 
			notifications = (List<Notification>) notifRepository.findAll();
			for(Notification notif : notifications){
				//TODO
			}
		}
	
	}

	private static final int ONE_DAY_MILLISECONDS = 86600000;
	
	private AtomicBoolean running = new AtomicBoolean(false);
	
	@Override
	public void startMidinghScheduler(Date startTime) {
		if(!running.get()){
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(new ScheduleMidnight(), startTime, ONE_DAY_MILLISECONDS);
			running.set(true);
		}
	}
	
	
	
}
