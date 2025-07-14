package com.management.app.Service;

import java.sql.Date;
import java.util.List;

import com.management.app.Entity.ProcessDevice;


public interface IProcessDeviceService {

	public List<ProcessDevice> lisProcessDevices();

	public ProcessDevice findOne(Long id);
	public void save(ProcessDevice processDevice);
	public void delete(Long id);
	public List<ProcessDevice> findbyId(Long id);
	public Object[] findProcessbyIdDate(Date fecha, Long id);
	public List<Object[]> countProcessPorMes();
	public List<Object[]> countProcessAprobadosPorMes();
	public List<Object[]> countProcessRealizadosPorTipo();
	public List<Object[]> processProgramadosPorTipo();
	public List<Object[]> countProcessAprobadosPorTipo();
	public List<Object[]> countProcessEjecutadosPorTipo();
}
