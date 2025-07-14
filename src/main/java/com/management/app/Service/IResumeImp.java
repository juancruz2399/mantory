package com.management.app.Service;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
import com.management.app.Dao.IResumeDao;
import com.management.app.Entity.Device;
import com.management.app.Entity.Resume;
@Service
public class IResumeImp implements IResumeService{

	@Autowired
	private IResumeDao ResumeDao;
	
	@Autowired
	private IDeviceDao DeviceDao;
	
	@Override
	public List<Resume> listResumes() {
		// TODO Auto-generated method stub
		return (List<Resume>)ResumeDao.findAll();
	}

	@Override
	public Resume findOne(Long id) {
		// TODO Auto-generated method stub
		return ResumeDao.findById(id).orElse(null);
	}

	@Override
	public void save(Resume resume) {
		// TODO Auto-generated method stub
		ResumeDao.save(resume);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		ResumeDao.delete(findOne(id));
	}

	@Override
	public Resume findHVbyDevice(Long id) {
		// TODO Auto-generated method stub
		return ResumeDao.findHVbyDevice(id);
	}


	@Override
    public void importarHvDesdeExcel(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        int rowNumber = 0;
        for (Row row : sheet) {
            if (rowNumber == 0) { // saltar encabezado
                rowNumber++;
                continue;
            }

            Resume resume = new Resume();

            // 🔁 Lee y asigna los campos por índice de columna (ajustar si cambia el orden)
            resume.setId_Resumes(Long.valueOf(getString(row,0)));
            resume.setAccesorio1(getString(row, 1));
            resume.setAccesorio2(getString(row, 2));
            resume.setAccesorio3(getString(row, 3));
            resume.setAccesorio4(getString(row, 4));
            resume.setCapacidad(getString(row, 5));
            resume.setCiudadproveedor(getString(row, 6));
            resume.setClase_biomedica(getInteger(row, 7));
            resume.setClase_equipo(getInteger(row, 8));
            resume.setCodinternacional(getString(row, 9));
            resume.setContrato(getString(row, 10));
            resume.setCorreoproveedor(getString(row, 11));
            resume.setCosto_compra(getString(row, 12));
            resume.setEmailinstitucion(getString(row, 13));
            resume.setEquipotipofijo(getBoolean(row, 14));
            resume.setFabricante(getString(row, 15));
            resume.setFecha_compra(getDate(row, 16));
            resume.setFecha_iniciooperacion(getDate(row, 17));
            resume.setFecha_instalacion(getDate(row, 18));
            resume.setFecha_vencimientogarantia(getDate(row, 19));
            resume.setFrecuencia(getString(row, 20));
            resume.setFuente_energia(getInteger(row, 21));
            resume.setImaxoperacion(getString(row, 22));
            resume.setIminoperacion(getString(row, 23));
            resume.setModo_compra(Integer.valueOf(getString(row, 24)));
            resume.setOtrosdatostecnicos(getString(row, 25));
            resume.setPaisfabricante(getString(row, 26));
            resume.setPeso(getString(row, 27));
            resume.setPresion(getString(row, 28));
            resume.setProveedor(getString(row, 29));
            resume.setRepresentante(getString(row, 30));
            resume.setRequierecalibracion(getBoolean(row, 31));
            resume.setTelefonoproveedor(getString(row, 32));
            resume.setTelefonorepresentante(getString(row, 33));
            resume.setTemperatura(getString(row, 34));
            resume.setUso(getInteger(row, 35));
            resume.setVelocidad(getString(row, 36));
            resume.setVmaxoperacion(getString(row, 37));
            resume.setVminoperacion(getString(row, 38));
            resume.setWconsumida(getString(row, 39));
            resume.setCalval(getBoolean(row, 40));
            resume.setDescripcion(getString(row, 42));
            resume.setObservacion(getString(row, 43));
            resume.setRecomendacion(getString(row, 44));
            resume.setFecha_fabricacion(getDate(row, 45));

            // Relación con equipo
            Long idDevice = getLong(row, 41); // columna 41
            Optional<Device> deviceOpt = DeviceDao.findById(idDevice);
            deviceOpt.ifPresent(resume::setDevice);

            // Guardar en la base de datos
            ResumeDao.save(resume);
        }

        workbook.close();
        inputStream.close();
    }

    // Métodos auxiliares:
    private String getString(Row row, int col) {
        Cell cell = row.getCell(col);
        return (cell != null) ? cell.toString().trim() : "";
    }

    private Boolean getBoolean(Row row, int col) {
        String value = getString(row, col);
        return "1".equals(value) || "true".equalsIgnoreCase(value);
    }

    private Integer getInteger(Row row, int col) {
        String val = getString(row, col);
        return (val.isEmpty()) ? null : Integer.valueOf(val.split("\\.")[0]);
    }

    private Long getLong(Row row, int col) {
        String val = getString(row, col);
        return (val.isEmpty()) ? null : Long.valueOf(val.split("\\.")[0]);
    }

    private Date getDate(Row row, int col) {
        Cell cell = row.getCell(col);
        if (cell == null) return null;
        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            return (Date) cell.getDateCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            try {
                return java.sql.Date.valueOf(LocalDate.parse(cell.getStringCellValue()));
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}
