package com.pt.pires.persistence;

import org.springframework.data.repository.CrudRepository;

import com.pt.pires.domain.Notification;

public interface NotificationRepository extends CrudRepository<Notification, Long>{

}
