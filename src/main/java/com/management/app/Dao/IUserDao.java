package com.management.app.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.management.app.Entity.User;



@Repository
public interface IUserDao extends CrudRepository<User, Long> {

	@Query("SELECT COUNT(u) from User u")
    public int countAll();
	
	@Query("SELECT u FROM User u WHERE u.Nombre=?1")
    public User findByName(String name);
	
	@Query("SELECT u FROM User u WHERE u.Cargo=?1")
	public List<User> findtecs(int cargo);
}
