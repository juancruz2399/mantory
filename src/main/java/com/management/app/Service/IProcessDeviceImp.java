package com.management.app.Service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.app.Dao.IProcessDao;
import com.management.app.Entity.ProcessDevice;

@Service
public class IProcessDeviceImp implements IProcessDeviceService{

	@Autowired
	IProcessDao ProcessDao;
	
	@Override
	public List<ProcessDevice> lisProcessDevices() {
		// TODO Auto-generated method stub
		return (List<ProcessDevice>)ProcessDao.findAll();
	}

	@Override
	public ProcessDevice findOne(Long id) {
		// TODO Auto-generated method stub
		return ProcessDao.findById(id).orElse(null);
	}

	@Override
	public void save(ProcessDevice processDevice) {
		// TODO Auto-generated method stub
		ProcessDao.save(processDevice);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		ProcessDao.delete(findOne(id));
	}

	@Override
	public List<ProcessDevice> findbyId(Long id) {
		// TODO Auto-generated method stub
		return ProcessDao.findcalbyDevice(id);
	}

	@Override
	public Object[] findProcessbyIdDate(Date fecha, Long id) {
		// TODO Auto-generated method stub
		return ProcessDao.findReportbyProcessDate(fecha, id);
	}

	@Override
	public List<Object[]> countProcessPorMes() {
		// TODO Auto-generated method stub
		return ProcessDao.countProcesosPorMes();
	}

	@Override
	public List<Object[]> countProcessAprobadosPorMes() {
		// TODO Auto-generated method stub
		return ProcessDao.countProcesosAprobadosPorMes();
	}

	@Override
	public List<Object[]> countProcessRealizadosPorTipo() {
		// TODO Auto-generated method stub
		return ProcessDao.countProcesosRealizadosPorTipo();
	}

	

	@Override
	public List<Object[]> countProcessAprobadosPorTipo() {
		// TODO Auto-generated method stub
		return ProcessDao.countProcesosAprobadosPorTipo();
	}

	@Override
	public List<Object[]> countProcessEjecutadosPorTipo() {
		// TODO Auto-generated method stub
		return ProcessDao.countProcesosEjecutadosPorTipo();
	}

	@Override
	public List<Object[]> processProgramadosPorTipo() {
		// TODO Auto-generated method stub
		return null;
	}

}
