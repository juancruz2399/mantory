package com.management.app.Controllers;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.management.app.Entity.Institution;
import com.management.app.Entity.PurchaseOrder;
import com.management.app.Entity.Requirement;
import com.management.app.Entity.QuoteOrder;
import com.management.app.Entity.User;
import com.management.app.Service.IInstitutionService;
import com.management.app.Service.IProviderService;
import com.management.app.Service.IPurchaseOrderService;
import com.management.app.Service.IQuoteOrderService;
import com.management.app.Service.IRequirementService;
import com.management.app.Service.IUserService;



@Controller
@RequestMapping
public class RequirementController {

	@Autowired
	private IUserService UserService;
	
	@Autowired
	private IInstitutionService InstitutionService;
	
	@Autowired
	private IRequirementService RequirementService;
	
	@Autowired
	private IQuoteOrderService QuoteOrderService;
	
	@Autowired
	private IProviderService ProviderService;
	
	@Autowired
	private IPurchaseOrderService PurchaseOrderService;
	
	@PostMapping("/nuevorequirement/{id}")
	public String nuevarequerimiento(@PathVariable(value = "id")Long id, @Valid Requirement requerimiento,
			BindingResult result,         			
			  Model model,
			  RedirectAttributes flash,
			  SessionStatus status) throws IOException {
		User usuario = UserService.findOne(1L);
		Institution institution = InstitutionService.findOne(id);
		requerimiento.setAprobado(true);
		requerimiento.setInstitution(institution);
		requerimiento.setUser(usuario);//pendiente validacion con security
		
		RequirementService.save(requerimiento);
		return "redirect:/listarequerimientos/"+requerimiento.getInstitution().getId_Institution();
	}
	@PostMapping("/desestimar/{id}")
	public String desestimar (@PathVariable(value = "id")Long id,
			       			
			  Model model,
			  RedirectAttributes flash,
			  SessionStatus status) throws IOException {
		Requirement requerimiento = RequirementService.findOne(id);
		requerimiento.setAprobado(false);
		RequirementService.save(requerimiento);
		
		return"redirect:/listarequerimientos/"+requerimiento.getInstitution().getId_Institution();
	}
	
	
	@GetMapping("/requirements")
	public String listadorequerimientos(
            Model model,Map<String, Object> map,
            RedirectAttributes flash) {
		
		List<Requirement> requerimientos = RequirementService.listRequerimientoCotizadoObra(8L);
		List<Requirement> requerimientosPendientes = RequirementService.listRequerimientoNoCotizadoObra(8L);
		List<Requirement> requerimientosCancelados = RequirementService.listRequerimientoCancelado(8L);
		
		ArrayList<List<QuoteOrder>> cotizacionesrequerimientos = new ArrayList<List<QuoteOrder>>();
		for(int i= 0;i<requerimientos.size();i++) {
			cotizacionesrequerimientos.add(QuoteOrderService.cotizacionRequerimiento(requerimientos.get(i).getId_Requirement()));
		}
		Institution institution = InstitutionService.findOne(8L);
		Requirement requerimiento = new Requirement();
		QuoteOrder cotizacion = new QuoteOrder();
		model.addAttribute("requerimientos",requerimientos);
		model.addAttribute("requerimientosPendientes",requerimientosPendientes);
		model.addAttribute("requerimientosCancelados", requerimientosCancelados);
		model.addAttribute("cotizacionesrequerimientos", cotizacionesrequerimientos);
		model.addAttribute("Institution",institution );
		model.addAttribute("requerimiento", requerimiento);
		model.addAttribute("cotizacion", cotizacion);
		model.addAttribute("proveedores", ProviderService.lisProvider());
		model.addAttribute("idObra", institution.getId_Institution());
		
		return "requirement";
	}
	@PostMapping("/nuevacompra/{id}")
	public String nuevacompra(@PathVariable(value = "id")Long id, @Valid PurchaseOrder purchaseOrder,
			BindingResult result,         			
			  Model model,
			  RedirectAttributes flash,
			  SessionStatus status) throws IOException {
		
    	LocalDate fechacompra = LocalDate.now();
    	Date fechaoc = Date.valueOf(fechacompra);
    	
    	QuoteOrder cotizacion = QuoteOrderService.findOne(id);
    	
    	purchaseOrder.setFecha_orden_compra(fechaoc);
    	purchaseOrder.setInstitution(cotizacion.getInstitution());
    	purchaseOrder.setProvider(cotizacion.getProvider());
    	purchaseOrder.setQuoteOrder(cotizacion);
    	purchaseOrder.setNumero_orden(0); //pendiente funcion last
    	purchaseOrder.setValor_flete(0);
    	purchaseOrder.setValor_total(purchaseOrder.getCantidad()*cotizacion.getValor_unitario()*cotizacion.getIva());
    	
		
		PurchaseOrderService.save(purchaseOrder);
		return "redirect:/requirements";
	}
	@PostMapping("/nuevaoc/{id}")
	public String nuevaOC(@PathVariable(value="id")Long id,
			@RequestParam(value="cotizacionesaprobadas",defaultValue = "0:0")String cotizacionesaprobadas,
			Model model,
			  RedirectAttributes flash,
			  SessionStatus status) {
		LocalDate now = LocalDate.now();
		Date hoy = Date.valueOf(now);
		if(!cotizacionesaprobadas.equals("0:0")) {
			ArrayList<String> idcots = new ArrayList<String>(Arrays.asList(cotizacionesaprobadas.split(",")));
			for(int i = 0;i<idcots.size();i++) {
				QuoteOrder cotizacion = QuoteOrderService.findOne(Long.valueOf(idcots.get(i)));
				PurchaseOrder compra = new PurchaseOrder();
				compra.setCantidad(cotizacion.getCantidad());//pendienteconfig
				compra.setAnticipo(cotizacion.getAnticipo());
				compra.setCantidad_recibida(0);
				compra.setQuoteOrder(cotizacion);
				compra.setFecha_orden_compra(hoy);
				compra.setNumero_factura(0);
				compra.setNumero_orden(0);
				compra.setInstitution(cotizacion.getInstitution());
				compra.setProvider(cotizacion.getProvider());
				compra.setValor_flete(0);
				compra.setValor_total(cotizacion.getValor());
				PurchaseOrderService.save(compra);
			}
		}
		
		System.out.println(cotizacionesaprobadas);
		return "redirect:/requirements";
		
	}

}
