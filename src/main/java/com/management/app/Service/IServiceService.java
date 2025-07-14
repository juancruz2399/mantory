package com.management.app.Service;

import java.util.List;

import com.management.app.Entity.Device;
import com.management.app.Entity.Services;

public interface IServiceService {

	public List<Services> lisServices();

	public Services findOne(Long id);
	public void save(Services service);
	public void delete(Long id);
	
	public int countService();
	public Services findServicebyName(String name);
	public List<Device> findDevicesbyService(Long id);
}
