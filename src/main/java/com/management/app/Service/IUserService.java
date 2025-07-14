package com.management.app.Service;

import java.util.List;

import com.management.app.Entity.User;


public interface IUserService {

	public List<User> listUsers();

	public User findOne(Long id);
	public void save(User user);
	public void delete(Long id);
	
	public User findbyname(String name);
	public List<User> findtecsmt(int cargo);
}
