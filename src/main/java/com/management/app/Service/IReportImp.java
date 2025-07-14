package com.management.app.Service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.app.Dao.IReportDao;
import com.management.app.Entity.Report;
@Service
public class IReportImp implements IReportService{

	@Autowired
	IReportDao ReportDao; 
	
	@Override
	public List<Report> listReport() {
		// TODO Auto-generated method stub
		return (List<Report>)ReportDao.findAll();
	}

	@Override
	public Report findOne(Long id) {
		// TODO Auto-generated method stub
		return ReportDao.findById(id).orElse(null);
	}

	@Override
	public void save(Report report) {
		// TODO Auto-generated method stub
		ReportDao.save(report);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		ReportDao.delete(findOne(id));
	}

	@Override
	public int countReportsbyDevices(Long id) {
		// TODO Auto-generated method stub
		return ReportDao.countReportsbyDevice(id);
	}

	@Override
	public List<Report> findReportsbyDevice(Long id) {
		// TODO Auto-generated method stub
		return ReportDao.findReportbyDevice(id);
	}

	@Override
	public Long lastIdReport() {
		// TODO Auto-generated method stub
		return ReportDao.findLastIdReport();
	}

	@Override
	public List<String> findreportsbydate(Date fecha1, Date fecha2) {
		// TODO Auto-generated method stub
		return ReportDao.findreports(fecha1, fecha2);
	}

	@Override
	public Object[] findReportDeviceDate(Date fecha, Long id) {
		// TODO Auto-generated method stub
		return ReportDao.findReportbyDeviceDate(fecha, id);
	}

	@Override
	public List<Object[]> countMttsPorMes() {
		// TODO Auto-generated method stub
		return ReportDao.countMantenimientosPorMes();
	}

	@Override
	public List<Object[]> sumaHorasMttoPorMes() {
		// TODO Auto-generated method stub
		return ReportDao.sumaHorasMantenimientoPorMes();
	}

	@Override
	public List<Object[]> countEquiposFueraDeServicioMes() {
		// TODO Auto-generated method stub
		return ReportDao.countEquiposFueraDeServicioPorMes();
	}

	@Override
	public List<Object[]> countMttsPreventivosPorMes() {
		// TODO Auto-generated method stub
		return ReportDao.countMantenimientosPreventivosPorMes();
	}

	@Override
	public List<Object[]> countMttsCorrectivosPorMes() {
		// TODO Auto-generated method stub
		return ReportDao.countMantenimientosCorrectivosPorMes();
	}

}
