package com.management.app.Controllers;

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

import com.management.app.Entity.Services;
import com.management.app.Service.IServiceService;

@Controller
@RequestMapping
public class ServiceController {


	
	@Autowired
	private IServiceService ServiceService;
	
	@GetMapping("/newservice")
    public String newservice(Map<String,Object>map, Model model){
        
		Services service = new Services();
		map.put("service", service);
        return "newservice";
    }
	@PostMapping("/newservice")
	public String saveservice(@Valid Services service, BindingResult result,
			  Model model,
			  RedirectAttributes flash,
			  SessionStatus status) {
		ServiceService.save(service);
		return "redirect:/home";
	}
	@GetMapping("/editservice/{id}")
	public String editservice(@PathVariable(value = "id")Long id,
				Map<String,Object>map,Model model,
				RedirectAttributes flash) {
		
		Services service = ServiceService.findOne(id);	
		map.put("service",service);
	
		return "editservice";
	}
	@PostMapping("/editservice/{id}")
	public String saveeditservice(@PathVariable(value = "id")Long id, 
			@Valid Services service,BindingResult result,
	  Model model,
	  RedirectAttributes flash,
	  SessionStatus status) {
		Services servicedit = ServiceService.findOne(id);
		servicedit.setNombre_servicio(service.getNombre_servicio());
		servicedit.setUbicacion_servicio(service.getUbicacion_servicio());
		ServiceService.save(servicedit);
		return "redirect:/home";
	}
}
