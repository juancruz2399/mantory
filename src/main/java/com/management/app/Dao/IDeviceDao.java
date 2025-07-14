package com.management.app.Dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.management.app.Entity.Device;



@Repository
public interface IDeviceDao extends CrudRepository<Device, Long>{

	@Query("SELECT COUNT(d) FROM Device d")
    public int countAll();
	
	@Query("SELECT d FROM Device d INNER JOIN Institution i ON i.id_Institution = d.institution.id_Institution "
			+ "WHERE i.id_Institution=?1")
	public List<Device> listbyinstitution(Long id);
	
	@Query("SELECT MAX(d.id_Device) FROM Device d")
    public Long findLastIdDevice();
	
	@Query("SELECT d FROM Device d "
			+ "INNER JOIN Report r ON r.device.id_Device = d.id_Device "
			+ "WHERE MONTH(r.Fecha) =?1 AND d.Meses_mantenimiento LIKE '%:month%'")
	public List<Device> findbyMonnth(int mes,String month);//no ejecutados
	
	@Query(value ="SELECT d.id_device,d.name_device,d.marca,d.modelo,d.serie,"
			+ "d.meses_mantenimiento,d.periodicidad,i.nombre AS institucion,"
			+ "ur.ultima_fecha,'PREVENTIVO' AS tipo_programa,"
			+ "CASE "
			+ "	WHEN ur.ultima_fecha IS NULL THEN 'NO EJECUTADO' "
			+ " WHEN MONTH(ur.ultima_fecha) = MONTH(:hoy)"
			+ "		AND YEAR(ur.ultima_fecha) = YEAR(:hoy)"
			+ "		AND FIND_IN_SET(UPPER(:mes), REPLACE(d.meses_mantenimiento, ' ', '')) > 0 "
			+ "		THEN 'EJECUTADO'"
			+ "WHEN FIND_IN_SET(UPPER(:mes), REPLACE(d.meses_mantenimiento, ' ', '')) > 0 "
			+ "		THEN 'REPROGRAMADO' "
			+ "ELSE 'FUERA DEL CALENDARIO' "
			
			+ "END AS estado_mantenimiento "
			+ "FROM device d "
			+ "LEFT JOIN Institution i ON d.id_institution_FK = i.id_institution "
			+ "LEFT JOIN ("
			+ "SELECT r.id_device_fk, MAX(r.fecha) AS ultima_fecha "
			+ "FROM report r WHERE r.tipo_mantenimiento = 3 "
			+ "GROUP BY r.id_device_fk) ur ON d.id_device = ur.id_device_fk "
			+ "WHERE FIND_IN_SET(UPPER(:mes), REPLACE(d.meses_mantenimiento, ' ', '')) > 0 "
			+ "ORDER BY d.id_device",nativeQuery = true)
	public List<Object[]> resumeMaintenance(@Param("hoy") Date hoy,@Param("mes") String mes);
	
	
	@Query(value = "SELECT  d.id_device,"
			       + "d.name_device,"
			       + "d.marca,"
			       + "d.modelo,"
			       + "d.serie,"
			       + "d.tipo_proceso,"
			       + "d.meses_procesos,"
			       + "i.nombre AS institucion,"
			       + "up.ultima_fecha_proceso,"
			       + "'PROCESO' AS tipo_programa,"	
			       + " CASE"
			       +   "  WHEN up.ultima_fecha_proceso IS NULL"
			       +   "   THEN 'NO EJECUTADO' "

			       +   "  WHEN MONTH(up.ultima_fecha_proceso) = MONTH(:hoy)"
			       +   "   AND YEAR(up.ultima_fecha_proceso) = YEAR(:hoy)"
			       +   "   AND FIND_IN_SET(UPPER(:mes),"
			       +                    " REPLACE(d.meses_procesos,' ','')) > 0 "
			       +        " THEN 'EJECUTADO' "

			       +   "  WHEN FIND_IN_SET(UPPER(:mes),"
			       +                     " REPLACE(d.meses_procesos,' ','')) > 0 "
			       +        " THEN 'REPROGRAMADO' "

			       +   "  ELSE 'FUERA DEL CALENDARIO' "
			       + " END AS estado_proceso "

			+"FROM    device d "
			+ "LEFT JOIN institution i "
			+ "     ON d.id_institution_fk = i.id_institution "

			+" LEFT JOIN ("
			+      " SELECT  p.device_fk,"
			+                "MAX(p.date_process) AS ultima_fecha_proceso"
			+       " FROM    process p "
			+       " GROUP BY p.device_fk "
			+") up ON d.id_device = up.device_fk "

			+ " WHERE   FIND_IN_SET(UPPER(:mes), "
			+                  " REPLACE(d.meses_procesos, ' ', '')) > 0 "

			+ " ORDER BY d.id_device",nativeQuery = true)
			List<Object[]> resumeProcessProgram(@Param("hoy") Date hoy,@Param("mes") String mes);
	
	
	
			@Query("SELECT d FROM Device d "
					+ "WHERE d.Meses_procesos IS NOT NULL AND d.Tipo_proceso IS NOT NULL "
					+ "AND d.Meses_procesos <> '' AND d.Tipo_proceso <> ''")
			List<Device> findConProcesos();

	
			@Query("SELECT d FROM Device d WHERE d.Marca = :marca AND d.Modelo = :modelo")
			List<Device> findByMarcaAndModelo(@Param("marca") String marca, @Param("modelo") String modelo);

	
	
	
	
	
	
	
}
