package com.management.app.Service;

import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.management.app.Entity.Device;


public interface IDeviceService {

	public List<Device> listDevices();
	public int countDevices();
	public Device findOne(Long id);
	public void save(Device device);
	public void delete(Long id);
	
	public List<Device> findbyInstitution(Long id);
	public Long findLastDeviceId();
	public List<Device> findbymonth(int mes,String month);
	
	public List<Object[]> resumenMes(Date fecha,String mes);
	
	public List<Object[]> resumenCalVal(Date fecha, String mes);
	
	public List<Device> findPrcoess();
	
	public void importarDesdeExcel(MultipartFile file) throws Exception;
	
	public void actualizarFotosDesdeExcel(MultipartFile file) throws Exception;
	
}
