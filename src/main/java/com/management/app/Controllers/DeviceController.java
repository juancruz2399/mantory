package com.management.app.Controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.management.app.Entity.Device;
import com.management.app.Entity.Institution;
import com.management.app.Entity.User;
import com.management.app.Entity.Resume;
import com.management.app.Service.IDeviceService;
import com.management.app.Service.IInstitutionService;
import com.management.app.Service.IResumeService;
import com.management.app.Service.IServiceService;
import com.management.app.Service.ITypesService;
import com.management.app.Service.PdfGenerator;
import com.management.app.Service.UploadFileService;




@Controller
@RequestMapping
public class DeviceController {

	@Autowired
    private IDeviceService DeviceService;
	
	@Autowired
	private IInstitutionService InstitutionService;
	
	@Autowired
	private UploadFileService uploadFileService;
	
	@Autowired
	private IServiceService ServiceService;
	
	@Autowired
	private ITypesService TypesService;
	
	@Autowired
	private IResumeService ResumeService;
	
	@GetMapping("/devices")
    public String listdevice(Model model){
    
        model.addAttribute("device", DeviceService.listDevices());
        model.addAttribute("institutions",InstitutionService.listactives());
        return "listinstitution";
    }
	@GetMapping("/allinventory")
	public String allinventory(Map<String,Object>map, Model model) {
		model.addAttribute("device", DeviceService.listDevices());
		model.addAttribute("institutions",InstitutionService.listactives());
		model.addAttribute("todos", "TODOS");
		return "inventory";
	}
	
	@GetMapping("/devicesbyin/{id}")
	public String newresume(@PathVariable(value = "id") Long id,
            Model model,Map<String, Object> map,
            RedirectAttributes flash) {
		Institution institution = InstitutionService.findOne(id);
		List<Device> devices = DeviceService.findbyInstitution(id);
		List<Resume> resumes = new ArrayList<Resume>();
		for(int i = 0;i<devices.size();i++) {
			resumes.add(ResumeService.findHVbyDevice(devices.get(i).getId_Device()));
		}
		model.addAttribute("institutions",InstitutionService.listactives());
		model.addAttribute("institution",institution);
		model.addAttribute("devices", devices);
		model.addAttribute("resumes", resumes);
		model.addAttribute("id", id);
		return "inventory";
	}
	
	@GetMapping("/newdevice")
    public String newdevice(Map<String,Object>map, Model model){
        Device device = new Device();
        map.put("device",device);
        model.addAttribute("services", ServiceService.lisServices());
        System.out.println(InstitutionService.listactives().get(0));
        model.addAttribute("types", TypesService.listTypes());
        model.addAttribute("institutions",InstitutionService.listactives());
        return "newdevice";
    }
	@PostMapping("/newdevice")
	public String savedevice(@Valid Device device, BindingResult result,
			  Model model,
			  @RequestParam(value="fecha_vencimiento",defaultValue = "2022-01-01")String fecha_garantia,
			  @RequestParam(value="service",defaultValue = "0")String idservice,
			  @RequestParam(value="types",defaultValue = "0")String idtype,
			  @RequestParam(value="institutions",defaultValue = "0")String idinst,
			  @RequestParam(value="foto",defaultValue = "empty")MultipartFile foto,
			  RedirectAttributes flash,
			  
			  SessionStatus status) throws IOException{
		Long id = DeviceService.findLastDeviceId();
		LocalDate fechavencimiento= LocalDate.parse(fecha_garantia);
    	Date fechav = Date.valueOf(fechavencimiento);
    	device.setGarantia(fechav);
    	device.setService(ServiceService.findOne(Long.valueOf(idservice)));
    	device.setType(TypesService.findOne(Long.valueOf(idtype)));
    	device.setInstitution(InstitutionService.findOne(Long.valueOf(idinst)));
    	
		if(foto.getOriginalFilename()!="") {
			uploadFileService.saveImage(foto, id);
			String Fotohv = "/images/"+String.valueOf(id)+foto.getOriginalFilename();
			device.setFoto(Fotohv);
		}
		DeviceService.save(device);
		System.out.println(device.getDias_mantenimiento());
		model.addAttribute("institutions",InstitutionService.listactives());
		return "redirect:/devices";
	}
	@GetMapping("/editdevice/{id}")
	public String editdevice(@PathVariable(value = "id")Long id,
			Map<String,Object>map,Model model,
			RedirectAttributes flash) {
		Device device = DeviceService.findOne(id);
		LocalDate now = LocalDate.now();
		Date hoy = Date.valueOf(now);
		map.put("types",device);
		model.addAttribute("institutions",InstitutionService.listactives());
		model.addAttribute("groupbyins", InstitutionService.groupMttoByInstitution(Date.valueOf(now.minusMonths(1)), hoy));
		return "editdevice";
	}
	@PostMapping("/editdevice/{id}")
	public String saveditdevice(@PathVariable(value = "id")Long id, @Valid Device device,
			BindingResult result,
            @RequestParam(value="fecha_vencimiento",defaultValue = "2022-01-01")String fecha_garantia,
			@RequestParam(value="foto",defaultValue = "empty")MultipartFile foto,
			  Model model,
			  RedirectAttributes flash,
			  SessionStatus status) throws IOException {
		
    	LocalDate fechavencimiento= LocalDate.parse(fecha_garantia);
    	Date fechav = Date.valueOf(fechavencimiento);
    	
		Device devicedit = DeviceService.findOne(id);
		devicedit.setActivo(device.isActivo());
		devicedit.setAno_ingreso(device.getAno_ingreso());
		devicedit.setDias_mantenimiento(device.getDias_mantenimiento());//obs
		devicedit.setEstado(device.getEstado());
		if(foto.getOriginalFilename()!="") {
			uploadFileService.saveImage(foto, id);
			String Fotohv = "/images/"+String.valueOf(id)+foto.getOriginalFilename();
			devicedit.setFoto(Fotohv);
		}
		devicedit.setFuncion(device.isFuncion());
		devicedit.setGarantia(fechav);
		devicedit.setInstitution(device.getInstitution());
		devicedit.setInventario(device.getInventario());
		devicedit.setMarca(device.getMarca());
		devicedit.setMeses_mantenimiento(device.getMeses_mantenimiento());//obs
		devicedit.setModelo(device.getModelo());
		devicedit.setName_device(device.getName_device());
		devicedit.setPeriodicidad(device.getPeriodicidad());
		devicedit.setRegistro_invima(device.getRegistro_invima());
		devicedit.setRiesgo(device.getRiesgo());
		devicedit.setSerie(device.getSerie());
		devicedit.setService(device.getService());//obs
		devicedit.setType(device.getType());//obs
		devicedit.setUbicacion(device.getUbicacion());
		devicedit.setUbicacion_especifica(device.getUbicacion_especifica());
		DeviceService.save(devicedit);
		return "redirect:/devices";
	}
	@GetMapping("/downloadplaninv/{id}")
    public void downloadFile(HttpServletResponse response,@PathVariable Long id) throws IOException {
        PdfGenerator generator = new PdfGenerator();
    
        List<Device> devices = DeviceService.findbyInstitution(id);
        Institution institution = InstitutionService.findOne(id);
        byte[] pdfReport = generator.getInventoryPDF(devices, institution).toByteArray();

        String mimeType =  "application/pdf";
        String namefile =String.valueOf(institution.getId_Institution());
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"",namefile+".pdf"));

        response.setContentLength(pdfReport.length);

        ByteArrayInputStream inStream = new ByteArrayInputStream( pdfReport);

        FileCopyUtils.copy(inStream, response.getOutputStream());
    }
	@PostMapping("/devices/import")
    public String importarDispositivos(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttrs) throws Exception {
        
        DeviceService.importarDesdeExcel(file);
        redirectAttrs.addFlashAttribute("mensaje", "¡Importación completada con éxito!");
     
        return "redirect:/devices";
    }
	
	@PostMapping("/device/updateFotosByExcel")
	public String actualizarFotosDesdeExcel(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttrs) {
	    try {
	        DeviceService.actualizarFotosDesdeExcel(file);
	        redirectAttrs.addFlashAttribute("success", "Fotos actualizadas correctamente.");
	    } catch (Exception e) {
	        redirectAttrs.addFlashAttribute("error", "Error al actualizar fotos: " + e.getMessage());
	    }
	    return "redirect:/devices"; // o donde quieras volver
	}


}
