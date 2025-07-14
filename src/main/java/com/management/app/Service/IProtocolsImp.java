package com.management.app.Service;

import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.management.app.Dao.IDeviceDao;
import com.management.app.Dao.IProtocolsDao;
import com.management.app.Dao.ITypesDao;
import com.management.app.Entity.Device;
import com.management.app.Entity.Protocols;
import com.management.app.Entity.Types;
@Service
public class IProtocolsImp implements IProtocolsService{

	@Autowired
	private IProtocolsDao ProtocolsDao;
	
	@Autowired
	private IDeviceDao DeviceDao;
	
	@Autowired
	private ITypesDao TypesDao;
	
	@Override
	public List<Protocols> lisProtocols() {
		// TODO Auto-generated method stub
		return (List<Protocols>)ProtocolsDao.findAll();
	}

	@Override
	public Protocols findOne(Long id) {
		// TODO Auto-generated method stub
		return ProtocolsDao.findById(id).orElse(null);
	}

	@Override
	public void save(Protocols protocols) {
		// TODO Auto-generated method stub
		ProtocolsDao.save(protocols);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		ProtocolsDao.delete(findOne(id));
	}

	@Override
	public List<Protocols> lastVersionProtocolsbyDevice(Long id, int version) {
		// TODO Auto-generated method stub
		int lastVersion = ProtocolsDao.ultimaVersion(id);
	
		return ProtocolsDao.listProtocolosbyDevice(id, lastVersion);
	}

	@Override
	public List<Protocols> findVersionProtocolsbyDevice(Long id, int version) {
		// TODO Auto-generated method stub
		return ProtocolsDao.listProtocolosbyDevice(id, version);
	}

	@Override
	public int findLastVersion(Long id) {
		// TODO Auto-generated method stub
		return ProtocolsDao.ultimaVersion(id);
	}
	
	public void importarDesdeExcel(MultipartFile file) throws Exception {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            Protocols protocolo = new Protocols();

            protocolo.setObservacion(getCellValue(row.getCell(1)));
            protocolo.setPaso(getCellValue(row.getCell(2)));
            protocolo.setVersion(Integer.valueOf(getCellValue(row.getCell(3))));

            Long deviceId = parseLong(getCellValue(row.getCell(4)));
            if (deviceId != null) {
                Device device = DeviceDao.findById(deviceId).orElse(null);
                protocolo.setDevice(device);
            }
            Long typesId = parseLong(getCellValue(row.getCell(5)));
            if (typesId != null) {
            	Types types = TypesDao.findById(typesId).orElse(null);
            	protocolo.setType(types);
            }
            
            
            ProtocolsDao.save(protocolo);
        }

        workbook.close();
    }

    private String getCellValue(Cell cell) {
    	switch (cell.getCellType()) {
	        case STRING:
	            return cell.getStringCellValue();
	        case NUMERIC:
	            return String.valueOf((long) cell.getNumericCellValue());
	        case BOOLEAN:
	            return String.valueOf(cell.getBooleanCellValue());
	        default:
	            return "";
	    }

    }

    private Long parseLong(String val) {
        try {
            return Long.parseLong(val);
        } catch (Exception e) {
            return null;
        }
    }

}
