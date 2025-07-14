package com.management.app.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.management.app.Entity.Device;
import com.management.app.Entity.Services;



@Repository
public interface IServiceDao extends CrudRepository<Services,Long>{
	
	@Query("SELECT COUNT(s) FROM Services s")
    public int countAll();
	
	@Query("SELECT s FROM Services s "+
    		"WHERE s.Nombre_servicio = ?1")
    public Services findServiciobyname(String name);
	
    
    @Query("SELECT d FROM Device d "+
    		"INNER JOIN Services s ON d.service.id_Service=s.id_Service "
    		+"WHERE s.id_Service=?1")
    public List<Device> findEquiposbyServicio(Long id);

}
