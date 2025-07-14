package com.management.app.Controllers;

import java.sql.Date;
import java.time.LocalDate;
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
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.management.app.Entity.Device;
import com.management.app.Entity.Institution;
import com.management.app.Service.IDeviceService;
import com.management.app.Service.IInstitutionService;
@Controller
@RequestMapping
public class InstitutionController {

	@Autowired
    private IInstitutionService InstitutionService;
	
	@Autowired
    private IDeviceService DeviceService;
	
	@GetMapping("/listinstitution")
    public String listinstitution(Model model){
		LocalDate now = LocalDate.now();
		Date hoy = Date.valueOf(now);
		model.addAttribute("institutions",InstitutionService.listactives());
		model.addAttribute("groupbyins", InstitutionService.groupMttoByInstitution(Date.valueOf(now.minusMonths(1)), hoy));
		
        return "listinstitution";
    }
	@GetMapping("/newinstitution")
    public String newinstitution(Map<String,Object>map,Model model,
			  RedirectAttributes flash) {
		
		Institution institution = new Institution();
		
		map.put("institution",institution);
		model.addAttribute("institutions",InstitutionService.listactives());
        return "newinstitution";
    }
	@PostMapping("/newinstitution")
	public String saveinstitution(@Valid Institution institution, BindingResult result,
			  Model model,
			  RedirectAttributes flash,
			  SessionStatus status) {
		
		InstitutionService.save(institution);
		return "redirect:/listinstitution";
	}
	@GetMapping("/editinstitution/{id}")
	public String editinstitution(@PathVariable(value = "id")Long id,
				Map<String,Object>map,Model model,
				RedirectAttributes flash) {
		
		Institution institution = InstitutionService.findOne(id);	
		map.put("institution",institution);
		model.addAttribute("institutions",InstitutionService.listactives());
		return "editinstitution";
	}
	
	@PostMapping("/editinstitution/{id}")
	public String saveeditinstitution(@PathVariable(value = "id")Long id, 
			@Valid Institution institution, 
				BindingResult result,
			  Model model,
			  RedirectAttributes flash,
			  SessionStatus status) {
		Institution institutionedit = InstitutionService.findOne(id);
		institutionedit.setCiudad(institution.getCiudad());
		institutionedit.setDepartamento(institution.getDepartamento());
		institutionedit.setDireccion(institution.getDireccion());
		institutionedit.setEstado(institution.isEstado());
		institutionedit.setNit(institution.getNit());
		institutionedit.setNivel(institution.getNivel());
		institutionedit.setNombre(institution.getNombre());
		InstitutionService.save(institutionedit);
		return "redirect:/listinstitution";
	}
	@GetMapping("/deleteinstitution/{id}")
	public String deleteinstitution(@PathVariable(value = "id") Long id,
            RedirectAttributes flash) {
		Institution institution = InstitutionService.findOne(id);
		List<Device> devices = DeviceService.findbyInstitution(id);
		if(devices.isEmpty()) {
			InstitutionService.delete(id);
			flash.addFlashAttribute("delete","Eliminado correctamente");
		}
		else {
			flash.addFlashAttribute("delete","Reporte agregado correctamente"); 
		}
		return "redirect:/listinstitution";
	}
}
