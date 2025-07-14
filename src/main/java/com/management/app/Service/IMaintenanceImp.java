package com.management.app.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.app.Dao.IMaintenanceDao;
import com.management.app.Entity.Maintenance;

@Service
public class IMaintenanceImp implements IMaintenanceService{

	@Autowired
	IMaintenanceDao MaintenanceDao;
	
	@Override
	public List<Maintenance> listMaintenances() {
		// TODO Auto-generated method stub
		return (List<Maintenance>)MaintenanceDao.findAll();
	}

	@Override
	public Maintenance findOne(Long id) {
		// TODO Auto-generated method stub
		return MaintenanceDao.findById(id).orElse(null);
	}

	@Override
	public void save(Maintenance maintenance) {
		// TODO Auto-generated method stub
		MaintenanceDao.save(maintenance);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		MaintenanceDao.delete(findOne(id));
	}

	@Override
	public List<Maintenance> findbytecdate(Long id, int mes, int ano) {
		// TODO Auto-generated method stub
		return MaintenanceDao.findbytecnicomonthandyear(id, mes, ano);
	}

	@Override
	public List<Maintenance> mainformonth(int mes, int ano) {
		// TODO Auto-generated method stub
		return MaintenanceDao.findMesAnoinitial(mes, ano);
	}

	@Override
	public Maintenance findbyreport(Long id) {
		// TODO Auto-generated method stub
		return MaintenanceDao.findmttobyReport(id);
	}

	@Override
	public List<Maintenance> findbydevice(Long id) {
		// TODO Auto-generated method stub
		return MaintenanceDao.findmttobyequipo(id);
	}

	@Override
	public int countPMes(int mes) {
		// TODO Auto-generated method stub
		return MaintenanceDao.countPendientesmes(mes);
	}

}
