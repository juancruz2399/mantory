package com.management.app.Dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.management.app.Entity.Provider;

@Repository
public interface IProviderDao extends CrudRepository<Provider,Long>{
	
	
	@Query("SELECT COUNT(p) FROM Provider p")
    public int countAll();
}
