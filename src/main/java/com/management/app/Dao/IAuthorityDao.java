package com.management.app.Dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.management.app.Entity.Authority;


@Repository
public interface IAuthorityDao extends CrudRepository<Authority, Long>{
	
	@Query("SELECT COUNT(a) FROM Authority a")
    public int countAll();

}
