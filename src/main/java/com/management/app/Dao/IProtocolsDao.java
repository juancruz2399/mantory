package com.management.app.Dao;

import org.springframework.stereotype.Repository;

import com.management.app.Entity.Protocols;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface IProtocolsDao extends CrudRepository<Protocols,Long>{

	@Query("SELECT COUNT(p) FROM Protocols p")
    public int countAll();
	
	@Query("SELECT COALESCE(MAX(p.Version), 0) FROM Protocols p "
			+ "INNER JOIN Device d ON d.id_Device = p.device.id_Device "
			+ "WHERE d.id_Device=?1")
	public Integer ultimaVersion(Long id);
	
	@Query("SELECT p FROM Protocols p "
			+ "INNER JOIN Device d ON d.id_Device = p.device.id_Device "
			+ "WHERE d.id_Device=?1 AND p.Version=?2 ORDER BY p.id_Protocols")
	public List<Protocols> listProtocolosbyDevice(Long id, int version);
	
	
}
