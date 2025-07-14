package com.management.app.Service;

import java.util.List;

import com.management.app.Entity.Maintenance;

public interface IMaintenanceService {

	public List<Maintenance> listMaintenances();

	public Maintenance findOne(Long id);
	public void save(Maintenance maintenance);
	public void delete(Long id);
	public List<Maintenance> findbytecdate(Long id,int mes, int ano);
	public List<Maintenance> mainformonth(int mes,int ano);
	public Maintenance findbyreport(Long id);
	public List<Maintenance> findbydevice(Long id);
	public int countPMes(int mes);
}
