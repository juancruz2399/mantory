package com.management.app.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.management.app.Entity.Maintenance;

@Repository
public interface IMaintenanceDao extends CrudRepository<Maintenance,Long>{

	@Query("SELECT COUNT(m) FROM Maintenance m")
    public int countAll();
	
	@Query("SELECT m FROM Maintenance m INNER JOIN User u "
			+ "ON m.user.id_Usuario = u.id_Usuario WHERE u.id_Usuario=?1 AND m.Mes=?2 AND m.Ano=?3")
	public List<Maintenance> findbytecnicomonthandyear(Long id,int mes, int ano);
	
	@Query("SELECT m FROM Maintenance m "
			+ "WHERE m.Mes=?1 AND m.Ano=?2")
	public List<Maintenance> findMesAnoinitial(int mes,int ano);
	
	
	@Query("SELECT m FROM Maintenance m "+
			"INNER JOIN Report r ON m.report.id_Report = r.id_Report "+
			"WHERE r.id_Report=?1")
	public Maintenance findmttobyReport(Long id);
	
	@Query("SELECT m FROM Maintenance m "+
			"INNER JOIN Device d ON m.device.id_Device = d.id_Device "+
			"WHERE d.id_Device=?1")
	public List<Maintenance> findmttobyequipo(Long id);
	
	@Query("SELECT COUNT(m) FROM Maintenance m WHERE m.Mes=?1 AND m.Realizado = '0'")
	public int countPendientesmes(int mes);
}
