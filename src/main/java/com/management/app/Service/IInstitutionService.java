package com.management.app.Service;

import java.sql.Date;
import java.util.List;

import com.management.app.Entity.Institution;


public interface IInstitutionService {


	public List<Institution> listInstitutions();

	public Institution findOne(Long id);
	public void save(Institution institution);
	public void delete(Long id);
	
	public List<Institution> listactives();
	public List<String> groupMttoByInstitution(Date fecha1, Date fecha2);
}
