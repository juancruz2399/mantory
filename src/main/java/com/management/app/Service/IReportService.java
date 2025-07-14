package com.management.app.Service;

import java.sql.Date;
import java.util.List;

import com.management.app.Entity.Report;


public interface IReportService {

	public List<Report> listReport();

	public Report findOne(Long id);
	public void save(Report report);
	public void delete(Long id);
	public int countReportsbyDevices(Long id);
	public List<Report> findReportsbyDevice(Long id);
	public Long lastIdReport();
	public List<String> findreportsbydate(Date fecha1, Date fecha2);
	public Object[] findReportDeviceDate(Date fecha, Long id);
	
	public List<Object[]> countMttsPorMes();
	public List<Object[]> sumaHorasMttoPorMes();
	public List<Object[]> countEquiposFueraDeServicioMes();
	public List<Object[]> countMttsPreventivosPorMes();
	public List<Object[]> countMttsCorrectivosPorMes();
	
} 
