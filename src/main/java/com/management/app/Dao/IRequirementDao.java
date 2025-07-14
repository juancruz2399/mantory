package com.management.app.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.management.app.Entity.Requirement;


@Repository
public interface IRequirementDao extends CrudRepository<Requirement, Long>{
	
	@Query("SELECT COUNT(r) FROM Requirement r")
    public int countAll();
	
	@Query("SELECT r FROM Requirement r LEFT JOIN QuoteOrder qo "
			+ "ON r.id_Requirement = qo.requirement.id_Requirement "
			+ "WHERE r.institution.id_Institution =?1 AND qo.requirement IS NULL AND r.Aprobado IS TRUE")
	public List<Requirement> listRequerimientoNoCotizado(Long idObra);

	@Query("SELECT r FROM Requirement r INNER JOIN QuoteOrder qo "
			+ "ON r.id_Requirement = qo.requirement.id_Requirement "
			+ "INNER JOIN Institution i ON qo.institution.id_Institution = i.id_Institution "
			+ "LEFT JOIN PurchaseOrder po ON po.quoteOrder.id_Quote = qo.id_Quote "			
			+ "WHERE i.id_Institution =?1 AND r.Aprobado IS TRUE AND po.id_Purchase IS NULL")
	public List<Requirement> listRequerimientosObra(Long idObra);
	
	@Query("SELECT r FROM Requirement r INNER JOIN Institution i "
			+ "ON r.institution.id_Institution = i.id_Institution "
			+ "WHERE i.id_Institution =?1 AND r.Aprobado IS NOT TRUE")
	public List<Requirement> listRequerimientonoAprobado(Long idObra);
	
}
