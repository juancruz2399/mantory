package com.management.app.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.management.app.Dao.IServiceDao;
import com.management.app.Entity.Device;
import com.management.app.Entity.Services;

@Service
public class IServiceImp implements IServiceService{

	@Autowired
	IServiceDao ServiceDao;
	
	@Override
	public List<Services> lisServices() {
		// TODO Auto-generated method stub
		return (List<Services>)ServiceDao.findAll();
	}

	@Override
	public Services findOne(Long id) {
		// TODO Auto-generated method stub
		return ServiceDao.findById(id).orElse(null);
	}

	@Override
	public void save(Services service) {
		// TODO Auto-generated method stub
		ServiceDao.save(service);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		ServiceDao.delete(findOne(id));
	}

	@Override
	public int countService() {
		// TODO Auto-generated method stub
		return ServiceDao.countAll();
	}

	@Override
	public Services findServicebyName(String name) {
		// TODO Auto-generated method stub
		return ServiceDao.findServiciobyname(name);
	}

	@Override
	public List<Device> findDevicesbyService(Long id) {
		// TODO Auto-generated method stub
		return ServiceDao.findEquiposbyServicio(id);
	}

}
