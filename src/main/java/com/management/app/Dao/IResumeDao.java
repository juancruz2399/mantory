package com.management.app.Dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.management.app.Entity.Resume;



@Repository
public interface IResumeDao extends CrudRepository<Resume,Long>{

	@Query("SELECT COUNT(r) FROM Resume r")
    public int countAll();
	
	@Query("SELECT r FROM Resume r "
			+ "INNER JOIN Device d ON r.device.id_Device=d.id_Device "
			+ "WHERE d.id_Device=?1")
	public Resume findHVbyDevice(Long id);
}
