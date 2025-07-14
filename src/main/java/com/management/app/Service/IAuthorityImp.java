package com.management.app.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.app.Dao.IAuthorityDao;
import com.management.app.Entity.Authority;
@Service
public class IAuthorityImp implements IAuthorityService{

	@Autowired
	IAuthorityDao AuthorityDao;
	
	@Override
	public List<Authority> lisAuthorities() {
		// TODO Auto-generated method stub
		return (List<Authority>)AuthorityDao.findAll();
	}

	@Override
	public Authority findOne(Long id) {
		// TODO Auto-generated method stub
		return AuthorityDao.findById(id).orElse(null);
	}

	@Override
	public void save(Authority authority) {
		// TODO Auto-generated method stub
		AuthorityDao.save(authority);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		AuthorityDao.delete(findOne(id));
	}

}
