package com.management.app.Dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.management.app.Entity.Zone;



@Repository
public interface IZoneDao extends CrudRepository<Zone, Long>{

	@Query("SELECT COUNT(z) FROM Zone z")
    public int countAll();
	
	@Query("SELECT z.Nombre_zone,SUM(TIME_TO_SEC(m.Tiempo_realizacion)),COUNT(m),m.Fecha_realizacion FROM Maintenance m "
			+ "INNER JOIN Device d ON d.id_Device = m.device.id_Device "
			+ "INNER JOIN Institution i ON i.id_Institution = d.institution.id_Institution "
			+ "INNER JOIN Zone z ON z.id_Zone = i.zone.id_Zone "
			+ "WHERE m.Fecha_realizacion>=?1 AND m.Fecha_realizacion<=?2 "
			+ "GROUP BY DAY(m.Fecha_realizacion),z.id_Zone")
	public List<String> groupmainbyzone(Date fecha1, Date fecha2);
}
