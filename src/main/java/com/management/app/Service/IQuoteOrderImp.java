package com.management.app.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.app.Dao.IQuoteOrderDao;
import com.management.app.Entity.QuoteOrder;

@Service
public class IQuoteOrderImp implements IQuoteOrderService{

	@Autowired
	private IQuoteOrderDao QuoteOrderDao;
	
	@Override
	public int countAllCotizacion() {
		// TODO Auto-generated method stub
		return QuoteOrderDao.countAll();
	}

	@Override
	public List<QuoteOrder> cotizacionRequerimiento(Long id) {
		// TODO Auto-generated method stub
		return (List<QuoteOrder>)QuoteOrderDao.findAll();
	}

	@Override
	public List<QuoteOrder> cotizacionObra(Long id) {
		// TODO Auto-generated method stub
		return QuoteOrderDao.cotizacionesxobra(id);
	}

	@Override
	public List<QuoteOrder> cotizacionEnviar(Long id) {
		// TODO Auto-generated method stub
		return QuoteOrderDao.cotizacionxenviar(id);
	}

	@Override
	public List<QuoteOrder> cotizacionPpAprobar(Long id) {
		// TODO Auto-generated method stub
		return QuoteOrderDao.cotizacionpendienteaprobar(id);
	}

	@Override
	public List<QuoteOrder> cotizacionNoCumplen(Long id) {
		// TODO Auto-generated method stub
		return QuoteOrderDao.cotizacionesnocumplen(id);
	}

	@Override
	public List<QuoteOrder> listarCotizaciones() {
		// TODO Auto-generated method stub
		return (List<QuoteOrder>)QuoteOrderDao.findAll();
	}

	@Override
	public List<QuoteOrder> cotizacionFechaProveedor(Long id) {
		// TODO Auto-generated method stub
		return QuoteOrderDao.cotizacionpendienteaprobar(id);
	}

	@Override
	public QuoteOrder findOne(Long id) {
		// TODO Auto-generated method stub
		return QuoteOrderDao.findById(id).orElse(null);
	}

	@Override
	public void save(QuoteOrder quoteOrder) {
		// TODO Auto-generated method stub
		QuoteOrderDao.save(quoteOrder);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		QuoteOrderDao.delete(findOne(id));
	}

	@Override
	public List<String> cuadrocomparativo(Long idReq) {
		// TODO Auto-generated method stub
		return QuoteOrderDao.cuadrocomparativo(idReq);
	}

	@Override
	public QuoteOrder cotbyNum(Long idObra, String numcot) {
		// TODO Auto-generated method stub
		return QuoteOrderDao.cotizacionbyNumero(idObra, numcot);
	}

}
