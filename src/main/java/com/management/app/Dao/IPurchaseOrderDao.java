package com.management.app.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.management.app.Entity.PurchaseOrder;

@Repository
public interface IPurchaseOrderDao extends CrudRepository<PurchaseOrder,Long>{

	
	@Query("SELECT COUNT(po) FROM PurchaseOrder po")
    public int countAll();
	
	@Query("SELECT po FROM PurchaseOrder po INNER JOIN Provider p ON po.provider.id_Provider = p.id_Provider "
			+ "WHERE p.id_Provider=?1")
	public List<PurchaseOrder> comprasporproveedor(Long id);
	
	@Query("SELECT po FROM PurchaseOrder po INNER JOIN Provider p ON po.provider.id_Provider = p.id_Provider "
			+"INNER JOIN Institution i ON po.institution.id_Institution = i.id_Institution "+ 
			"WHERE p.id_Provider=?1 AND i.id_Institution=?2" +" ORDER BY po.Cantidad")
	public List<PurchaseOrder> comprasporproveedorxobra(Long id, Long idInstitution);
	
	@Query("SELECT po FROM PurchaseOrder po "
			+"INNER JOIN Institution i ON po.institution.id_Institution = i.id_Institution "+ 
			"WHERE i.id_Institution=?1" +" ORDER BY po.Cantidad")
	public List<PurchaseOrder> comprasporobra(Long id);
	
	@Query("SELECT po FROM PurchaseOrder po INNER JOIN Institution i ON po.institution.id_Institution = i.id_Institution "
			+ "INNER JOIN QuoteOrder qo ON qo.id_Quote = po.quoteOrder.id_Quote "
			+ "INNER JOIN Provider p ON po.provider.id_Provider = p.id_Provider "
			+ "WHERE i.id_Institution=?1 GROUP BY qo.Articulo,p.id_Provider")
	public List<PurchaseOrder> relacioncotizacionporobra(Long id);
	//Si se realiza cotizacion por obra
	
	
}
