package com.management.app.Service;

import java.util.List;

import com.management.app.Entity.Notification;


public interface INotificationService {

	public List<Notification> lisNotifications();

	public Notification findOne(Long id);
	public void save(Notification notification);
	public void delete(Long id);
}
