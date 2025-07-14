package com.management.app.Dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.management.app.Entity.Notification;


@Repository
public interface INotificationDao extends CrudRepository<Notification,Long>{

	@Query("SELECT COUNT(n) FROM Notification n")
    public int countAll();
}
