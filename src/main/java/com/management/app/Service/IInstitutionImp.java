package com.management.app.Service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.app.Dao.IInstitutionDao;
import com.management.app.Entity.Institution;

@Service
public class IInstitutionImp implements IInstitutionService{

	@Autowired
	IInstitutionDao InstitutionDao;
	
	@Override
	public List<Institution> listInstitutions() {
		// TODO Auto-generated method stub
		return (List<Institution>)InstitutionDao.findAll();
	}

	@Override
	public Institution findOne(Long id) {
		// TODO Auto-generated method stub
		return InstitutionDao.findById(id).orElse(null);
	}

	@Override
	public void save(Institution institution) {
		// TODO Auto-generated method stub
		InstitutionDao.save(institution);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		InstitutionDao.delete(findOne(id));
	}

	@Override
	public List<Institution> listactives() {
		// TODO Auto-generated method stub
		return InstitutionDao.activesInstitution();
	}

	@Override
	public List<String> groupMttoByInstitution(Date fecha1, Date fecha2) {
		// TODO Auto-generated method stub
		return InstitutionDao.groupmttobyinstitution(fecha1, fecha2);
	}

}
