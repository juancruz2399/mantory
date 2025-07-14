package com.management.app.Service;

import java.util.List;

import com.management.app.Entity.QuoteOrder;


public interface IQuoteOrderService {

	public int countAllCotizacion();
	public List<QuoteOrder> cotizacionRequerimiento(Long id);
	public List<QuoteOrder> cotizacionObra(Long id);
	public List<QuoteOrder> cotizacionEnviar(Long id);
	public List<QuoteOrder> cotizacionPpAprobar(Long id);
	public List<QuoteOrder> cotizacionNoCumplen(Long id);
	public List<QuoteOrder> listarCotizaciones();
	public List<QuoteOrder> cotizacionFechaProveedor(Long id);
	public QuoteOrder findOne(Long id);
	public void save(QuoteOrder quoteOrder);
	public void delete(Long id);
	public List<String> cuadrocomparativo(Long idReq);
	
	public QuoteOrder cotbyNum(Long idObra, String numcot);
}
