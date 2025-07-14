package com.management.app.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.management.app.Entity.Resume;
import com.management.app.Dao.IDeviceDao;
import com.management.app.Dao.IInstitutionDao;
import com.management.app.Dao.IServiceDao;
import com.management.app.Dao.ITypesDao;
import com.management.app.Entity.Device;
import java.io.InputStream;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Service
public class IDeviceImp implements IDeviceService{

	@Autowired
	private IDeviceDao DeviceDao;
	
	@Autowired
	private IInstitutionDao InstitutionDao;
	
	@Autowired
	private IServiceDao ServiceDao;
	
	@Autowired
	private ITypesDao TypesDao;
	
	@Override
	public List<Device> listDevices() {
		// TODO Auto-generated method stub
		return (List<Device>)DeviceDao.findAll();
	}

	@Override
	public Device findOne(Long id) {
		// TODO Auto-generated method stub
		return DeviceDao.findById(id).orElse(null);
	}

	@Override
	public void save(Device device) {
		// TODO Auto-generated method stub
		DeviceDao.save(device);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		DeviceDao.delete(findOne(id));
	}

	@Override
	public List<Device> findbyInstitution(Long id) {
		// TODO Auto-generated method stub
		return DeviceDao.listbyinstitution(id);
	}

	@Override
	public Long findLastDeviceId() {
		// TODO Auto-generated method stub
		return DeviceDao.findLastIdDevice();
	}

	@Override
	public List<Device> findbymonth(int mes, String month) {
		// TODO Auto-generated method stub
		return DeviceDao.findbyMonnth(mes,month);
	}

	@Override
	public int countDevices() {
		// TODO Auto-generated method stub
		return DeviceDao.countAll();
	}

	@Override
	public List<Object[]> resumenMes(Date fecha,String mes) {
		// TODO Auto-generated method stub
		return DeviceDao.resumeMaintenance(fecha,mes);
	}

	@Override
	public List<Object[]> resumenCalVal(Date fecha, String mes) {
		// TODO Auto-generated method stub
		return DeviceDao.resumeProcessProgram(fecha, mes);
	}

	@Override
	public List<Device> findPrcoess() {
		// TODO Auto-generated method stub
		return DeviceDao.findConProcesos();
	}
	private String getCellValue(Cell cell) {
	    if (cell == null) {
	        return "";
	    }

	    switch (cell.getCellType()) {
	        case STRING:
	            return cell.getStringCellValue().trim();
	        case BOOLEAN:
	            return String.valueOf(cell.getBooleanCellValue());
	        case NUMERIC:
	            if (DateUtil.isCellDateFormatted(cell)) {
	                return cell.getDateCellValue().toString();
	            } else {
	                return String.valueOf((long) cell.getNumericCellValue()); // si son enteros
	            }
	        case FORMULA:
	            return cell.getCellFormula();
	        case BLANK:
	            return "";
	        default:
	            return "";
	    }
	}
	@Override
	public void importarDesdeExcel(MultipartFile file) throws Exception {
        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Saltar encabezado

                Device device = new Device();
                
                device.setId_Device(Long.valueOf(getCellValue(row.getCell(0))));
                device.setName_device(getCellValue(row.getCell(11)));
                device.setActivo(Boolean.parseBoolean(getCellValue(row.getCell(1))));
                device.setAno_ingreso(Integer.parseInt(getCellValue(row.getCell(2))));
                device.setDias_mantenimiento(getCellValue(row.getCell(3)));
                device.setEstado(Integer.parseInt(getCellValue(row.getCell(4))));
                device.setFuncion(Boolean.parseBoolean(getCellValue(row.getCell(5))));
                System.out.println(getCellValue(row.getCell(6)));
                if(getCellValue(row.getCell(6))== null || getCellValue(row.getCell(6)).equals("0")) {
                	device.setGarantia(null);
                    
                }
                else {
                	device.setGarantia(Date.valueOf(LocalDate.parse(getCellValue(row.getCell(6)))));
                    
                }
                
                device.setInventario(getCellValue(row.getCell(7)));
                device.setMarca(getCellValue(row.getCell(8)));
                device.setMeses_mantenimiento(getCellValue(row.getCell(9)));
                device.setModelo(getCellValue(row.getCell(10)));
                device.setPeriodicidad(Integer.parseInt(getCellValue(row.getCell(12))));
                device.setRegistro_invima(getCellValue(row.getCell(13)));
                device.setRiesgo(Integer.parseInt(getCellValue(row.getCell(14))));
                device.setSerie(getCellValue(row.getCell(15)));
                device.setUbicacion(getCellValue(row.getCell(16)));
                device.setUbicacion_especifica(getCellValue(row.getCell(17)));
                device.setFoto(getCellValue(row.getCell(21)));
                device.setGuide(getCellValue(row.getCell(22)));
                device.setManual(getCellValue(row.getCell(23)));
                device.setMeses_procesos(getCellValue(row.getCell(24)));
                device.setTipo_proceso(getCellValue(row.getCell(25)));
                device.setOptional_1(getCellValue(row.getCell(26)));
                device.setOptional_2(getCellValue(row.getCell(27)));

                // Relaciones (FKs)
                Long idInst = Long.parseLong(getCellValue(row.getCell(18)));
                Long idServ = Long.parseLong(getCellValue(row.getCell(19)));
                Long idType = Long.parseLong(getCellValue(row.getCell(20)));

                device.setInstitution(InstitutionDao.findById(idInst).orElse(null));
                device.setService(ServiceDao.findById(idServ).orElse(null));
                device.setType(TypesDao.findById(idType).orElse(null));

                DeviceDao.save(device);
            }
        }
        
    }
	
	@Override
	public void actualizarFotosDesdeExcel(MultipartFile file) throws Exception {
	    InputStream inputStream = file.getInputStream();
	    Workbook workbook = new XSSFWorkbook(inputStream);
	    Sheet sheet = workbook.getSheetAt(0);

	    for (int i = 1; i <= sheet.getLastRowNum(); i++) {
	        Row row = sheet.getRow(i);
	        if (row == null) continue;

	        String marca = getCellValue(row.getCell(0)).trim();
	        String modelo = getCellValue(row.getCell(1)).trim();
	        String foto = getCellValue(row.getCell(2)).trim();

	        if (!marca.isEmpty() && !modelo.isEmpty() && !foto.isEmpty()) {
	            List<Device> equipos = DeviceDao.findByMarcaAndModelo(marca, modelo);
	            for (Device equipo : equipos) {
	                equipo.setFoto(foto);
	                DeviceDao.save(equipo);
	            }
	        }
	    }

	    workbook.close();
	}

	
}
