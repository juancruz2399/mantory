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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.management.app.Entity.Types;
import com.management.app.Service.ITypesService;



@Controller
@RequestMapping
public class TypesController {
	
	@Autowired
	private ITypesService TypesService;
	
	@GetMapping("/newtypes")
    public String newdevice(Map<String,Object>map, Model model){
        
		Types types = new Types();
		map.put("types", types);
        return "newtypes";
    }
	
	@PostMapping("/newtypes")
	public String saveservice(@Valid Types types, BindingResult result,
			  Model model,
			  RedirectAttributes flash,
			  @RequestParam(value = "timemain",defaultValue = "B00")String tiempomain,
			  SessionStatus status) {
		if(tiempomain.equals("B00")) {
			types.setTiempo_minutos(0);
		}
		else {
			types.setTiempo_minutos(Integer.valueOf(tiempomain));
		}
		TypesService.save(types);
		return "redirect:/home";
	}
	@GetMapping("/editypes/{id}")
	public String editypes(@PathVariable(value = "id")Long id,
			Map<String,Object>map,Model model,
			RedirectAttributes flash) {
		Types types = TypesService.findOne(id);
		map.put("types",types);
		return "editypes";
	}
	@PostMapping("/editypes/{id}")
	public String saveditypes(@PathVariable(value = "id")Long id, @Valid Types types,BindingResult result,
			  Model model,
			  RedirectAttributes flash,
			  SessionStatus status) {
		Types typesedit = TypesService.findOne(id);
		typesedit.setHerramienta(types.getHerramienta());
		typesedit.setMaterial_consumible(types.getMaterial_consumible());
		typesedit.setNombre_tipo(types.getNombre_tipo());
		typesedit.setTiempo_minutos(types.getTiempo_minutos());
		TypesService.save(typesedit);
		return "redirect:/home";
	}
}
