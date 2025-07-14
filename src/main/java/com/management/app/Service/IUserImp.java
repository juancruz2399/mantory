package com.management.app.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.app.Dao.IUserDao;
import com.management.app.Entity.User;



@Service
public class IUserImp implements IUserService{

	@Autowired
	IUserDao UserDao;
	
	@Override
	public List<User> listUsers() {
		// TODO Auto-generated method stub
		return (List<User>)UserDao.findAll();
	}

	@Override
	public User findOne(Long id) {
		// TODO Auto-generated method stub
		return UserDao.findById(id).orElse(null);
	}

	@Override
	public void save(User user) {
		// TODO Auto-generated method stub
		UserDao.save(user);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		UserDao.delete(findOne(id));
	}

	@Override
	public User findbyname(String name) {
		// TODO Auto-generated method stub
		return UserDao.findByName(name);
	}

	@Override
	public List<User> findtecsmt(int cargo) {
		// TODO Auto-generated method stub
		return UserDao.findtecs(cargo);
	}

}
