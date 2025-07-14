package com.management.app.Service;

import java.util.List;

import com.management.app.Entity.Requirement;


public interface IRequirementService {

	public int countAllReqs();
	public List<Requirement> listReqs();
	public Requirement findOne(Long id);
	public void save(Requirement requerimiento);
	public void delete(Long id);
	
	public List<Requirement> listRequerimientoNoCotizadoObra(Long idObra);
	public List<Requirement> listRequerimientoCotizadoObra(Long idObra);
	public List<Requirement> listRequerimientoCancelado(Long idObra);
}
