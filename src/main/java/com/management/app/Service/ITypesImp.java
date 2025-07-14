package com.management.app.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.app.Dao.ITypesDao;
import com.management.app.Entity.Device;
import com.management.app.Entity.Types;

@Service
public class ITypesImp implements ITypesService{

	@Autowired
	ITypesDao TypesDao;
	
	@Override
	public List<Types> listTypes() {
		// TODO Auto-generated method stub
		return (List<Types>)TypesDao.findAll();
	}

	@Override
	public Types findOne(Long id) {
		// TODO Auto-generated method stub
		return TypesDao.findById(id).orElse(null);
	}

	@Override
	public void save(Types types) {
		// TODO Auto-generated method stub
		TypesDao.save(types);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		TypesDao.delete(findOne(id));
	}

	@Override
	public int countTypes() {
		// TODO Auto-generated method stub
		return TypesDao.countAll();
	}

	@Override
	public List<Device> findDevicebyTypes(Long id) {
		// TODO Auto-generated method stub
		return TypesDao.findEquiposbyTipoEquipo(id);
	}

	
}
