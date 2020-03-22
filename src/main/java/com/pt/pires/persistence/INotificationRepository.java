package com.pt.pires.persistence;

import org.springframework.data.repository.CrudRepository;

import com.pt.pires.domain.NotificationTask;

public interface INotificationRepository extends CrudRepository<NotificationTask, Long> {
	
}
