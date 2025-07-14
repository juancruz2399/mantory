package com.management.app.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.app.Dao.IRequirementDao;
import com.management.app.Entity.Requirement;

@Service
public class IRequirementImp implements IRequirementService{

	@Autowired
	private IRequirementDao RequerimentDao;
	
	@Override
	public int countAllReqs() {
		// TODO Auto-generated method stub
		return RequerimentDao.countAll();
	}

	@Override
	public List<Requirement> listReqs() {
		// TODO Auto-generated method stub
		return (List<Requirement>)RequerimentDao.findAll();
	}

	@Override
	public Requirement findOne(Long id) {
		// TODO Auto-generated method stub
		return RequerimentDao.findById(id).orElse(null);
	}

	@Override
	public void save(Requirement requerimiento) {
		// TODO Auto-generated method stub
		RequerimentDao.save(requerimiento);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		RequerimentDao.delete(findOne(id));
	}

	@Override
	public List<Requirement> listRequerimientoNoCotizadoObra(Long idObra) {
		// TODO Auto-generated method stub
		return RequerimentDao.listRequerimientoNoCotizado(idObra);
	}

	@Override
	public List<Requirement> listRequerimientoCotizadoObra(Long idObra) {
		// TODO Auto-generated method stub
		return RequerimentDao.listRequerimientosObra(idObra);
	}

	@Override
	public List<Requirement> listRequerimientoCancelado(Long idObra) {
		// TODO Auto-generated method stub
		return RequerimentDao.listRequerimientonoAprobado(idObra);
	}

}
