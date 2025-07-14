package com.management.app.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.app.Dao.IProviderDao;
import com.management.app.Entity.Provider;

@Service
public class IProviderImp implements IProviderService{

	@Autowired
	private IProviderDao ProviderDao;
	
	@Override
	public List<Provider> lisProvider() {
		// TODO Auto-generated method stub
		return (List<Provider>)ProviderDao.findAll();
	}

	@Override
	public Provider findOne(Long id) {
		// TODO Auto-generated method stub
		return ProviderDao.findById(id).orElse(null);
	}

	@Override
	public void save(Provider provider) {
		// TODO Auto-generated method stub
		ProviderDao.save(provider);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		ProviderDao.delete(findOne(id));
	}

}
