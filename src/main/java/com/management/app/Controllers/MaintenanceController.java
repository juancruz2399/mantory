package com.management.app.Controllers;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.management.app.Entity.Device;
import com.management.app.Entity.Maintenance;
import com.management.app.Entity.Protocols;
import com.management.app.Entity.Report;
import com.management.app.Entity.User;
import com.management.app.Service.IDeviceService;
import com.management.app.Service.IInstitutionService;
import com.management.app.Service.IMaintenanceService;
import com.management.app.Service.IProtocolsService;
import com.management.app.Service.IReportService;
import com.management.app.Service.IUserService;

@Controller
@RequestMapping
public class MaintenanceController {

	@Autowired
    private IMaintenanceService MaintenanceService;
	
	@Autowired
	private IUserService UserService;
	
	@Autowired
	private IDeviceService DeviceService;
	
	@Autowired
    private IInstitutionService InstitutionService;
	
	@Autowired
	private IReportService ReportService;
	
	@Autowired
	private IProtocolsService ProtocolsService;
	
	
	@GetMapping("/maintenance")
    public String maintenance(Model model){
		
		
		LocalDate now = LocalDate.now();
		now = now.minusMonths(2);
		int mes = now.getMonthValue();
		String nombreMes = "";
		switch(mes) {
			case 1:
				nombreMes = "ENERO";
				break;
			case 2:
				nombreMes = "FEBRERO";
				break;
			case 3:
				nombreMes = "MARZO";
				break;
			case 4:
				nombreMes = "ABRIL";
				break;
			case 5:
				nombreMes = "MAYO";
				break;
			case 6:
				nombreMes = "JUNIO";
				break;
			case 7:
				nombreMes = "JULIO";
				break;
			case 8:
				nombreMes = "AGOSTO";
				break;
			case 9:
				nombreMes = "SEPTIEMBRE";
				break;
			case 10:
				nombreMes = "OCTUBRE";
				break;
			case 11:
				nombreMes = "NOVIEMBRE";
				break;
			case 12:
				nombreMes = "DICIEMBRE";
				break;
			
		}
		System.out.println(nombreMes);
		List<Device> devices = DeviceService.findbymonth(mes,nombreMes);
		
		
		List<Object[]> resumen = DeviceService.resumenMes(Date.valueOf(now),nombreMes);
		
		
		model.addAttribute("asign","prueba");
		model.addAttribute("devices",devices);
		model.addAttribute("resumen",resumen);
		model.addAttribute("institutions",InstitutionService.listactives());
        return "maintenance";
    }
	
	@GetMapping("/cargarMesMantenimiento")
	@ResponseBody
	public List<Object[]> getPagoSemana(
			@RequestParam(value="fecha_ingresada",defaultValue = "2024-12-01")String fecha_ingresada
			){
		LocalDate fecha = LocalDate.parse(fecha_ingresada);
		int mes = fecha.getMonthValue();
		String nombreMes = "";
		switch(mes) {
			case 1:
				nombreMes = "ENERO";
				break;
			case 2:
				nombreMes = "FEBRERO";
				break;
			case 3:
				nombreMes = "MARZO";
				break;
			case 4:
				nombreMes = "ABRIL";
				break;
			case 5:
				nombreMes = "MAYO";
				break;
			case 6:
				nombreMes = "JUNIO";
				break;
			case 7:
				nombreMes = "JULIO";
				break;
			case 8:
				nombreMes = "AGOSTO";
				break;
			case 9:
				nombreMes = "SEPTIEMBRE";
				break;
			case 10:
				nombreMes = "OCTUBRE";
				break;
			case 11:
				nombreMes = "NOVIEMBRE";
				break;
			case 12:
				nombreMes = "DICIEMBRE";
				break;
			
		}
		List<Object[]> resumen = DeviceService.resumenMes(Date.valueOf(fecha),nombreMes);
		
		List<Object[]> anexoCalVal = DeviceService.resumenCalVal(Date.valueOf(fecha), nombreMes);
		resumen.addAll(anexoCalVal);
		return resumen;
	}
	
	@GetMapping("/reporte/form/{id}")
	public String getFormularioMantenimiento(@PathVariable Long id, Model model, RedirectAttributes flash) {
	    Device device = DeviceService.findOne(id);
	    if (device == null) {
	        flash.addFlashAttribute("error", "El equipo no existe en la base de datos");
	        return "redirect:/viewreports/" + id;
	    }

	    Report report = new Report();
	    report.setDevice(device);
	    List<Protocols> protocolos = ProtocolsService.lastVersionProtocolsbyDevice(id, 0);

	    model.addAttribute("report", report);
	    model.addAttribute("device", device);
	    model.addAttribute("protocolos", protocolos);

	    model.addAttribute("fecha", Date.valueOf(LocalDate.now()));
	    model.addAttribute("horallamado", Time.valueOf(LocalTime.parse("00:00")));
	    model.addAttribute("horainicio", Time.valueOf(LocalTime.now()));
	    model.addAttribute("horaterminacion", Time.valueOf(LocalTime.now()));
	    model.addAttribute("numeroreporte", ReportService.lastIdReport());

	    return "fragments/form-mantenimiento :: formMantenimiento";
	}

	
	@GetMapping("/buscarReporteDinamico/{id}")
	@ResponseBody
	public Object[] getReporteDinamico(
			@PathVariable(value = "id") Long id,
			@RequestParam(value="fecha_reporte",defaultValue = "2024-11-11")String fecha_reporte
			){
		LocalDate fechareporte= LocalDate.parse(fecha_reporte);
	    Object[] reporte = ReportService.findReportDeviceDate(Date.valueOf(fechareporte), id);
    	return reporte;
	}
}
