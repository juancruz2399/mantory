package com.management.app.Controllers;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.management.app.Entity.Device;
import com.management.app.Entity.Resume;
import com.management.app.Service.IProtocolsService;

@Controller
@RequestMapping
public class ProtocolsController {
	@Autowired
    private IProtocolsService protocolsService;
	
	
	@GetMapping("/plan")
	public String planning(Model model,Map<String, Object> map,
            RedirectAttributes flash) {
		
		
		return "plan";
	}

    @PostMapping("/importarProtocols")
    public String importarProtocols(@RequestParam("file") MultipartFile file, RedirectAttributes redirect) {
        try {
            protocolsService.importarDesdeExcel(file);
            redirect.addFlashAttribute("msg", "Archivo cargado correctamente");
        } catch (Exception e) {
            redirect.addFlashAttribute("msg", "Error al cargar el archivo: " + e.getMessage());
        }
        return "redirect:/protocols/import"; // o el path que corresponda
    }
}
