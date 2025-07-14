package com.management.app.Dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.management.app.Entity.Device;
import com.management.app.Entity.ProcessDevice;


@Repository
public interface IProcessDao extends CrudRepository<ProcessDevice,Long>{
	
	@Query("SELECT COUNT(pd) FROM ProcessDevice pd")
    public int countAll();
	
	@Query("SELECT pd FROM ProcessDevice pd "+
			"INNER JOIN Device d ON pd.device.id_Device = d.id_Device "+
			"WHERE d.id_Device=?1")
	public List<ProcessDevice> findcalbyDevice(Long id);
	
	@Query(value = "SELECT p.id_process,d.name_device,d.marca,d.modelo,d.serie,"
    		+ "s.nombre_servicio,p.date_process,p.conditions,p.certificate,p.number,p.approved,p.url_ubicacion "
    		+ " FROM process p "
    		+ "INNER JOIN device d ON p.device_fk = d.id_device "
    		+ "INNER JOIN services s ON s.id_service = d.id_service_fk "
    		+ "WHERE p.date_process=?1 AND d.id_device=?2 "
    		+ "ORDER BY p.id_process DESC LIMIT 1",nativeQuery = true)
    public Object[] findReportbyProcessDate(Date fecha, Long id);
	
	// Consulta para obtener dispositivos con programación válida
	@Query(value = "SELECT d FROM device d WHERE d.meses_procesos IS NOT NULL AND d.tipo_proceso IS NOT NULL AND TRIM(d.meses_procesos) <> '' AND TRIM(d.tipo_proceso) <> ''",nativeQuery = true)
	List<Device> findConProcesos();
	
	
	
	// Indicadores mensuales desde la tabla process
	@Query(value = "SELECT MONTH(p.date_process), YEAR(p.date_process), COUNT(p) FROM process p GROUP BY YEAR(p.date_process), MONTH(p.date_process)", nativeQuery = true)
	List<Object[]> countProcesosPorMes();

	@Query(value ="SELECT MONTH(p.date_process), YEAR(p.date_process), COUNT(p) FROM process p WHERE p.approved IS NOT NULL AND p.approved <> '' GROUP BY YEAR(p.date_process), MONTH(p.date_process)", nativeQuery = true)
	List<Object[]> countProcesosAprobadosPorMes();


	// Nuevas: cantidad de procesos realizados por tipo (name_process)
	@Query(value = "SELECT p.name_process, MONTH(p.date_process), YEAR(p.date_process), COUNT(p) FROM process p WHERE p.approved IS NOT NULL AND p.approved <> '' GROUP BY p.name_process, YEAR(p.date_process), MONTH(p.date_process)", nativeQuery = true)
	List<Object[]> countProcesosRealizadosPorTipo();

	// Procesos programados según programación en tabla device
	@Query(value =
			"SELECT :mes AS mes, :anio AS anio, d.tipo_proceso, COUNT(*) " +
			"FROM device d " +
			"WHERE FIND_IN_SET(UPPER(:nombreMes), UPPER(d.meses_procesos)) > 0 " +
			"AND d.tipo_proceso IS NOT NULL AND d.tipo_proceso <> '' " +
			"GROUP BY d.tipo_proceso",
			nativeQuery = true)
			List<Object[]> countProcesosProgramadosPorTipo(@Param("mes") int mes, @Param("anio") int anio, @Param("nombreMes") String nombreMes);

	
	
	// Repositorio para ProcessDevice
	@Query(value = 
	"SELECT MONTH(p.date_process), YEAR(p.date_process), COUNT(*) "
	+"FROM process p "
	+"JOIN device d ON p.device_fk = d.id_device "
	+"WHERE p.approved IS NOT NULL AND p.approved <> '' "
	+"AND p.name_process IN ("
	+"  SELECT TRIM(tp) "
	+"  FROM device dev "
	+"  JOIN dev.tipo_proceso tp "
	+"  WHERE p.device_fk = d.id_device) "
	+" AND UPPER(FUNCTION('MONTHNAME', p.date_process)) IN ("
	+" SELECT TRIM(mp) "
	+"  FROM device dev "
	+"  JOIN dev.meses_procesos mp "
	+"  WHERE p.device_fk = d.id_device) "
	+"GROUP BY YEAR(p.date_process), MONTH(p.date_process)", nativeQuery = true)
	List<Object[]> countProcesosAprobadosPorTipo();

	@Query(value =
		    "SELECT MONTH(p.date_process), YEAR(p.date_process), d.tipo_proceso, COUNT(*) " +
		    "FROM process p " +
		    "JOIN device d ON p.device_fk = d.id_device " +
		    "WHERE p.approved IS NOT NULL AND p.approved <> '' " +
		    "AND p.name_process = d.tipo_proceso " +
		    "AND FIND_IN_SET(UPPER(MONTHNAME(p.date_process)), UPPER(d.meses_procesos)) > 0 " +
		    "GROUP BY YEAR(p.date_process), MONTH(p.date_process), d.tipo_proceso",
		    nativeQuery = true)
		List<Object[]> countProcesosEjecutadosPorTipo();



}
