package com.management.app.Service;

import java.util.List;

import com.management.app.Entity.Device;
import com.management.app.Entity.Types;

public interface ITypesService {

	public List<Types> listTypes();

	public Types findOne(Long id);
	public void save(Types types);
	public void delete(Long id);
	
	public int countTypes();
	public List<Device> findDevicebyTypes(Long id);
}
