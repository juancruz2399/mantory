package com.management.app.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.management.app.Entity.Device;
import com.management.app.Entity.Types;



@Repository
public interface ITypesDao extends CrudRepository<Types,Long>{

	@Query("SELECT COUNT(t) FROM Types t")
    public int countAll();
	
	@Query("SELECT d FROM Device d "+
    		"INNER JOIN Types t ON d.type.id_Type=t.id_Type "
    		+"WHERE t.id_Type=?1")
    public List<Device> findEquiposbyTipoEquipo(Long id);
}
