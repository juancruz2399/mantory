package com.management.app.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.management.app.Entity.QuoteOrder;

@Repository
public interface IQuoteOrderDao extends CrudRepository<QuoteOrder,Long>{
	
	@Query("SELECT COUNT(qo) FROM QuoteOrder qo")
    public int countAll();
	
	@Query("SELECT qo FROM QuoteOrder qo INNER JOIN Requirement r "
			+ "ON qo.requirement.id_Requirement = r.id_Requirement "
			+ "WHERE r.id_Requirement=?1 ORDER BY qo.Valor")
	public List<QuoteOrder> cotizacionxrequerimiento(Long id);
	
	@Query("SELECT qo FROM QuoteOrder qo INNER JOIN Institution i "
			+ "ON qo.institution.id_Institution = i.id_Institution WHERE i.id_Institution =?1")
	public List<QuoteOrder> cotizacionesxobra(Long id);
	
	@Query("SELECT qo FROM QuoteOrder qo INNER JOIN Institution i "
			+ "ON qo.institution.id_Institution = i.id_Institution INNER JOIN Requirement r "
			+ "ON r.id_Requirement = qo.requirement.id_Requirement "
			+ "WHERE qo.Cumple_especificacion IS TRUE AND qo.Autorizacion_envio IS TRUE AND i.id_Institution =?1")
	public List<QuoteOrder> cotizacionxenviar(Long id);
	
	@Query("SELECT qo FROM QuoteOrder qo INNER JOIN Institution i "
			+ "ON  qo.institution.id_Institution = i.id_Institution INNER JOIN Requirement r "
			+ "ON r.id_Requirement = qo.requirement.id_Requirement "
			+ "WHERE qo.Cumple_especificacion IS TRUE AND qo.Autorizacion_envio IS NOT TRUE AND i.id_Institution =?1")
	public List<QuoteOrder> cotizacionpendienteaprobar(Long id);
	
	@Query("SELECT qo FROM QuoteOrder qo INNER JOIN Institution i "
			+ "ON qo.institution.id_Institution = i.id_Institution INNER JOIN Requirement r "
			+ "ON r.id_Requirement = qo.requirement.id_Requirement "
			+ "WHERE qo.Cumple_especificacion IS NOT TRUE AND qo.Autorizacion_envio IS NOT TRUE AND i.id_Institution =?1")
	public List<QuoteOrder> cotizacionesnocumplen(Long id);
	
	@Query("SELECT qo FROM QuoteOrder qo LEFT JOIN PurchaseOrder po "
			+ "ON po.quoteOrder.id_Quote = qo.id_Quote "
			+ "WHERE po.institution.id_Institution =?1 AND po.id_Purchase IS NULL "
			+ "GROUP BY qo.Fecha_cotizacion,qo.provider ")
	public List<QuoteOrder> cotizacionespendientessincompra(Long id);
	
	@Query("SELECT r.id_Requirement,p.Nombre_proveedor,p.Celular_proveedor,qo.id_Quote,qo.Articulo,qo.Valor_unitario,qo.Cantidad,qo.Valor,"
			+ "qo.Iva,qo.Fecha_cotizacion,qo.Plazo_entrega,r.Nombre_requerimiento, r.Cantidad,r.Justificacion FROM QuoteOrder qo INNER JOIN Provider p "
			+ "ON qo.provider.id_Provider = p.id_Provider "
			+ "INNER JOIN Requirement r ON qo.requirement.id_Requirement = r.id_Requirement "
			+ "WHERE r.id_Requirement =?1 "
			+ "GROUP BY qo.requirement, p.id_Provider")
	public List<String> cuadrocomparativo(Long id);
	
	@Query(value = "SELECT qo FROM quoteorder qo INNER JOIN institution i "
			+ "ON qo.institution.id_institution = i.id_institution "
			+ "WHERE i.id_institution=?1 AND qo.numero_cotizacion=?2 "
			+ "ORDER BY i.id_institution "
			+ "LIMIT 1",nativeQuery = true)
	public QuoteOrder cotizacionbyNumero(Long idObra,String numcot);

}
