package com.management.app.Dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.management.app.Entity.Institution;



@Repository
public interface IInstitutionDao extends CrudRepository<Institution,Long> {

	@Query("SELECT COUNT(i) FROM Institution i")
    public int countAll();
	
	@Query("SELECT i FROM Institution i WHERE i.estado = '1'")
	public List<Institution> activesInstitution();
	
	@Query("SELECT i.Nombre,r.Tipo_mantenimiento,COUNT(r) FROM Institution i INNER JOIN Device d ON i.id_Institution = d.institution.id_Institution "
			+ "INNER JOIN Report r ON r.device.id_Device = d.id_Device WHERE r.Fecha>=?1 AND r.Fecha<=?2 "
			+ "GROUP BY i.id_Institution,r.Tipo_mantenimiento")
	public List<String> groupmttobyinstitution(Date fecha1, Date fecha2);
}
