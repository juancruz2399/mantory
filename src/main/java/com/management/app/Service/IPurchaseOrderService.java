package com.management.app.Service;

import java.util.List;

import com.management.app.Entity.PurchaseOrder;


public interface IPurchaseOrderService {

	public int countAllcompras();
	public List<PurchaseOrder> compraProveedor(Long idproveedor);
	public List<PurchaseOrder> compraProveedorObra(Long idproveedor, Long idobra);
	public List<PurchaseOrder> comprasObra(Long idObra);
	public List<PurchaseOrder> relacioncotreq(Long idObra);
	public List<PurchaseOrder> listarCompras();
	public PurchaseOrder findOne(Long id);
	public void save(PurchaseOrder compra);
	public void delete(Long idCompra);
	
	public List<String> compraPendientePago(Long id);
}
