package com.management.app.Controllers;

import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.management.app.Service.IDeviceService;
import com.management.app.Service.IInstitutionService;
import com.management.app.Service.IMaintenanceService;
import com.management.app.Service.IZoneService;

@Controller
@RequestMapping
public class LoginController {

	@Autowired
    private IDeviceService DeviceService;
	
	@Autowired
    private IInstitutionService InstitutionService;
	
	@Autowired
	private IMaintenanceService MaintenanceService;
	
	@Autowired
	private IZoneService ZoneService;
	
	@GetMapping("/signin")
	public String login(@RequestParam(value="error",required=false)String error,
						@RequestParam(value="logout",required=false)String logout,
						Model model, Principal principal, RedirectAttributes flash) {
		if(principal!=null) {
			flash.addFlashAttribute("mensaje", "Log in")
            .addFlashAttribute("clase", "success");
			return "redirect:/home";
		}
		if(error!=null) {
	
			flash
			.addFlashAttribute("mensaje", "Agregado correctamente")
            .addFlashAttribute("clase", "success");
		
		}
		if(logout!=null) {
	
			flash
			.addFlashAttribute("mensaje", "Sesión cerrada con exito")
            .addFlashAttribute("clase", "success");
		}
		return "signin";
	}
	@GetMapping({"","/"})
	public String index(Model model) {
		model.addAttribute("bienvenido","hola");
		return "signin";
	}
	@GetMapping("/home")
	public String home(Model model) {
		LocalDate now = LocalDate.now();
		Date hoy = Date.valueOf(now);
		model.addAttribute("bienvenido","hola");
		model.addAttribute("institutions",InstitutionService.listactives());
		model.addAttribute("totaldevice", DeviceService.countDevices());
		model.addAttribute("groupbyins", InstitutionService.groupMttoByInstitution(Date.valueOf(now.minusMonths(1)), hoy));
		model.addAttribute("pendientes", MaintenanceService.countPMes(LocalDate.now().getMonthValue()));
		model.addAttribute("zones", ZoneService.GroupbyMainZone(Date.valueOf(now.minusMonths(1)), hoy));
		return "home";
	}
}
