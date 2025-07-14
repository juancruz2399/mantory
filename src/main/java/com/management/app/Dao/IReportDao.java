package com.management.app.Dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.management.app.Entity.Report;

@Repository
public interface IReportDao extends CrudRepository<Report,Long>{

	@Query("SELECT COUNT(r) FROM Report r")
    public int countAll();
	
	// Indicadores mensuales desde la tabla report
	@Query(value = "SELECT FUNCTION('MONTH', r.fecha), FUNCTION('YEAR', r.fecha), COUNT(r) FROM report r GROUP BY FUNCTION('YEAR', r.fecha), FUNCTION('MONTH', r.fecha)",nativeQuery = true)
	List<Object[]> countMantenimientosPorMes();

	@Query(value = "SELECT MONTH(r.fecha), YEAR(r.fecha), SUM(r.total_horas) " +
            "FROM report r " +
            "WHERE r.total_horas IS NOT NULL " +
            "GROUP BY YEAR(r.fecha), MONTH(r.fecha)", nativeQuery = true)
	List<Object[]> sumaHorasMantenimientoPorMes();


	@Query(value = "SELECT FUNCTION('MONTH', r.fecha), FUNCTION('YEAR', r.fecha), COUNT(r) FROM report r WHERE r.tiempo_fuera_servicio IS NOT NULL GROUP BY FUNCTION('YEAR', r.fecha), FUNCTION('MONTH', r.fecha)",nativeQuery = true)
	List<Object[]> countEquiposFueraDeServicioPorMes();

	// KPIs por tipo de mantenimiento
	@Query(value = "SELECT MONTH(r.fecha), YEAR(r.fecha), COUNT(*) " +
            "FROM report r " +
            "WHERE r.tipo_mantenimiento = 3 " +
            "GROUP BY YEAR(r.fecha), MONTH(r.fecha)", nativeQuery = true)
	List<Object[]> countMantenimientosPreventivosPorMes();


	@Query(value = "SELECT MONTH(r.fecha), YEAR(r.fecha), COUNT(*) " +
            "FROM report r " +
            "WHERE r.tipo_mantenimiento = 2 " +
            "GROUP BY YEAR(r.fecha), MONTH(r.fecha)", nativeQuery = true)
	List<Object[]> countMantenimientosCorrectivosPorMes();

	@Query("SELECT COUNT(r) FROM Report r "+
	    		"INNER JOIN Device d ON r.device.id_Device=d.id_Device "+
	    		"WHERE d.id_Device=?1")
	public int countReportsbyDevice(Long id);
	    
    @Query("SELECT r FROM Report r " + 
    		"INNER JOIN Device d ON r.device.id_Device=d.id_Device " + 
    		"WHERE d.id_Device=?1 ORDER BY r.Fecha DESC")    
    public List<Report> findReportbyDevice(Long id);
    
    @Query("SELECT MAX(r.id_Report) FROM Report r")
    public Long findLastIdReport();
    
    @Query("SELECT r.Fecha,r.Hora_inicio,r.Hora_terminacion,r.Hora_llamado,r.Responsable,r.Total_horas,"
    		+ "r.id_Report,d.Name_device,d.Marca,d.Modelo,d.Serie,d.Inventario,r.Servicio,r.Tipo_mantenimiento,"
    		+ "r.Trabajo_realizado,r.Autor_recibido,r.Rutapdf FROM Report r INNER JOIN Device d ON r.device.id_Device=d.id_Device "+
    		"WHERE r.Fecha>=?1 AND r.Fecha<?2")
    public List<String> findreports(Date fecha1, Date fecha2);
    
    @Query(value = "SELECT r.id_report,d.name_device,d.marca,d.modelo,d.serie,"
    		+ "s.nombre_servicio,r.fecha,r.motivo,r.trabajo_realizado,r.rutapruebas,r.repuesto,r.rutapdf "
    		+ " FROM report r "
    		+ "INNER JOIN device d ON r.id_device_fk = d.id_device "
    		+ "INNER JOIN services s ON s.id_service = d.id_service_fk "
    		+ "WHERE r.fecha=?1 AND d.id_device=?2 AND r.tipo_mantenimiento = 3 "
    		+ "ORDER BY r.id_report DESC LIMIT 1",nativeQuery = true)
    public Object[] findReportbyDeviceDate(Date fecha, Long id);
    
  
}
