package com.management.app.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.app.Dao.IPurchaseOrderDao;
import com.management.app.Entity.PurchaseOrder;

@Service
public class IPurchaseOrderImp implements IPurchaseOrderService{

	
	@Autowired
	private IPurchaseOrderDao PurchaseOrderDao;
	
	@Override
	public int countAllcompras() {
		// TODO Auto-generated method stub
		return PurchaseOrderDao.countAll();
	}

	@Override
	public List<PurchaseOrder> compraProveedor(Long idproveedor) {
		// TODO Auto-generated method stub
		return PurchaseOrderDao.comprasporproveedor(idproveedor);
	}

	@Override
	public List<PurchaseOrder> compraProveedorObra(Long idproveedor, Long idobra) {
		// TODO Auto-generated method stub
		return PurchaseOrderDao.comprasporproveedorxobra(idproveedor, idobra);
	}

	@Override
	public List<PurchaseOrder> comprasObra(Long idObra) {
		// TODO Auto-generated method stub
		return PurchaseOrderDao.comprasporobra(idObra);
	}

	@Override
	public List<PurchaseOrder> relacioncotreq(Long idObra) {
		// TODO Auto-generated method stub
		return PurchaseOrderDao.relacioncotizacionporobra(idObra);
	}

	@Override
	public List<PurchaseOrder> listarCompras() {
		// TODO Auto-generated method stub
		return (List<PurchaseOrder>)PurchaseOrderDao.findAll();
	}

	@Override
	public PurchaseOrder findOne(Long id) {
		// TODO Auto-generated method stub
		return PurchaseOrderDao.findById(id).orElse(null);
	}

	@Override
	public void save(PurchaseOrder compra) {
		// TODO Auto-generated method stub
		PurchaseOrderDao.save(compra);
	}

	@Override
	public void delete(Long idCompra) {
		// TODO Auto-generated method stub
		PurchaseOrderDao.delete(findOne(idCompra));
	}

	@Override
	public List<String> compraPendientePago(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
