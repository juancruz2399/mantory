package com.management.app.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.app.Dao.INotificationDao;
import com.management.app.Entity.Notification;

@Service
public class INotificationImp implements INotificationService{

	@Autowired
	INotificationDao NotificationDao;
	
	@Override
	public List<Notification> lisNotifications() {
		// TODO Auto-generated method stub
		return (List<Notification>)NotificationDao.findAll();
	}

	@Override
	public Notification findOne(Long id) {
		// TODO Auto-generated method stub
		return NotificationDao.findById(id).orElse(null);
	}

	@Override
	public void save(Notification notification) {
		// TODO Auto-generated method stub
		NotificationDao.save(notification);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		NotificationDao.delete(findOne(id));
	}

}
