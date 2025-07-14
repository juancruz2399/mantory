package com.management.app.Controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.management.app.Entity.Device;
import com.management.app.Entity.Resume;
import com.management.app.Service.IDeviceService;
import com.management.app.Service.IResumeService;
import com.management.app.Service.PdfGenerator;
import com.management.app.Service.UploadFileService;

@Controller
@RequestMapping
public class ResumeController {
	
	@Autowired
	private IResumeService ResumeService;
	
	@Autowired 
	private IDeviceService DeviceService;
	
	@Autowired
	private UploadFileService uploadFileService;
	
	@GetMapping("/newresume/{id}")
	public String newresume(@PathVariable(value = "id") Long id,
            Model model,Map<String, Object> map,
            RedirectAttributes flash) {
		Device device = DeviceService.findOne(id);
		Resume resume = new Resume();
		resume.setDevice(device);
		map.put("resume", resume);
		model.addAttribute("device",device);
		model.addAttribute("boughtdate",LocalDate.now());
		model.addAttribute("installdate",LocalDate.now());
		model.addAttribute("startdate",LocalDate.now());
		model.addAttribute("vctodate",LocalDate.now());
		
		return "newresume";
	}
	 
	@PostMapping("/newresume/{id}")
	public String savenewresume(@PathVariable(value = "id") Long id,@Valid Resume resume, BindingResult binRes,            
            Model model,
            RedirectAttributes flash,
            @RequestParam(value="fecha_compra",defaultValue = "2022-01-01")String fecha_compra,
            @RequestParam(value="fecha_instalacion",defaultValue = "2022-01-01")String fecha_instalacion,
            @RequestParam(value="fecha_inicio_operacion",defaultValue = "2022-01-01")String fecha_inicio_operacion,
            @RequestParam(value="fecha_vencimiento",defaultValue = "2022-01-01")String fecha_vencimiento,
            @RequestParam(value="fecha_fabricacion",defaultValue = "2022-01-01")String fecha_fabricacion,
            @RequestParam(value="foto",defaultValue = "empty")MultipartFile foto) throws IOException  {
		
		
		LocalDate fechacompra = LocalDate.parse(fecha_compra);
    	Date fechac = Date.valueOf(fechacompra);
    	LocalDate fechainstalacion = LocalDate.parse(fecha_instalacion);
    	Date fechai = Date.valueOf(fechainstalacion);
    	LocalDate fechainicio = LocalDate.parse(fecha_inicio_operacion);
    	Date fechaop = Date.valueOf(fechainicio);
    	LocalDate fechavencimiento= LocalDate.parse(fecha_vencimiento);
    	Date fechav = Date.valueOf(fechavencimiento);
    	LocalDate fechafabricacion= LocalDate.parse(fecha_fabricacion);
    	Date fechaf = Date.valueOf(fechafabricacion);
    	
    	resume.setFecha_compra(fechac);
    	resume.setFecha_instalacion(fechai);
    	resume.setFecha_iniciooperacion(fechaop);
    	resume.setFecha_vencimientogarantia(fechav);
    	resume.setFecha_fabricacion(fechaf);
    	
    	Device device = DeviceService.findOne(id);
    	if(foto.getOriginalFilename()!="") {
			uploadFileService.saveImage(foto, id);
			String Fotohv = "/images/"+String.valueOf(id)+foto.getOriginalFilename();
			device.setFoto(Fotohv);
		}

		Resume resumedit = ResumeService.findHVbyDevice(id);
    	if(resumedit!= null) {
    		resumedit.setFecha_compra(fechac);
        	resumedit.setFecha_instalacion(fechai);
        	resumedit.setFecha_iniciooperacion(fechaop);
        	resumedit.setFecha_vencimientogarantia(fechav);
        	resumedit.setFecha_fabricacion(fechaf);
        	resumedit.setDevice(device);	
        	resumedit.setAccesorio1(resume.getAccesorio1());
        	resumedit.setAccesorio2(resume.getAccesorio2());
        	resumedit.setAccesorio3(resume.getAccesorio3());
        	resumedit.setAccesorio4(resume.getAccesorio4());
        	resumedit.setCalval(resume.isCalval());
        	resumedit.setCapacidad(resume.getCapacidad());
        	resumedit.setCiudadproveedor(resume.getCiudadproveedor());
        	resumedit.setClase_biomedica(resume.getClase_biomedica());
        	resumedit.setClase_equipo(resume.getClase_equipo());
        	resumedit.setCodinternacional(resume.getCodinternacional());
        	resumedit.setContrato(resume.getContrato());
        	resumedit.setCorreoproveedor(resume.getCorreoproveedor());
        	resumedit.setCosto_compra(resume.getCosto_compra());
        	resumedit.setEmailinstitucion(resume.getEmailinstitucion());
        	resumedit.setEquipotipofijo(resume.isEquipotipofijo());
        	resumedit.setFabricante(resume.getFabricante());
        	resumedit.setFrecuencia(resume.getFrecuencia());
        	resumedit.setFuente_energia(resume.getFuente_energia());
        	resumedit.setImaxoperacion(resume.getImaxoperacion());
        	resumedit.setIminoperacion(resume.getIminoperacion());
        	resumedit.setModo_compra(resume.getModo_compra());
        	resumedit.setOtrosdatostecnicos(resume.getOtrosdatostecnicos());
        	resumedit.setPaisfabricante(resume.getPaisfabricante());
        	resumedit.setPeso(resume.getPeso());
        	resumedit.setPresion(resume.getPresion());
        	resumedit.setProveedor(resume.getProveedor());
        	resumedit.setRepresentante(resume.getRepresentante());
        	resumedit.setRequierecalibracion(resume.isRequierecalibracion());
        	resumedit.setTelefonoproveedor(resume.getTelefonoproveedor());
        	resumedit.setTelefonorepresentante(resume.getTelefonorepresentante());
        	resumedit.setTemperatura(resume.getTemperatura());
        	resumedit.setUso(resume.getUso());
        	resumedit.setVelocidad(resume.getVelocidad());
        	resumedit.setVmaxoperacion(resume.getVmaxoperacion());
        	resumedit.setVminoperacion(resume.getVminoperacion());
        	resumedit.setWconsumida(resume.getWconsumida());
        	resumedit.setDescripcion(resume.getDescripcion());
        	resumedit.setRecomendacion(resume.getRecomendacion());
        	resumedit.setObservacion(resume.getObservacion());
        	
    		ResumeService.save(resumedit);
    		
            flash.addFlashAttribute("success","guardado");
            return "redirect:/viewreports/"+device.getId_Device();
    	}
    	else {
    		resume.setDevice(device);	
    		ResumeService.save(resume);
    		
            flash.addFlashAttribute("success","guardado");
            return "redirect:/viewreports/"+device.getId_Device();
    	}
    	

	}
	@GetMapping("/editresume/{id}")
	public String editresume(@PathVariable(value="id") Long id,
			Model model,Map<String, Object> map,
            RedirectAttributes flash) {
		Device device = DeviceService.findOne(id);
		Resume resume = ResumeService.findHVbyDevice(id);

		map.put("resume", resume);
		model.addAttribute("device",device);
		model.addAttribute("boughtdate",resume.getFecha_compra());
		model.addAttribute("installdate",resume.getFecha_instalacion());
		model.addAttribute("startdate",resume.getFecha_iniciooperacion());
		model.addAttribute("vctodate",resume.getFecha_vencimientogarantia());
		model.addAttribute("fabdate", resume.getFecha_fabricacion());
		return  "newresume";
	}
	
	@PostMapping("/editresume/{id}")
	public String saveditresume(@PathVariable(value = "id") Long id,@Valid Resume resume, BindingResult binRes,            
            Model model,
            RedirectAttributes flash,
            @RequestParam(value="fecha_compra",defaultValue = "2022-01-01")String fecha_compra,
            @RequestParam(value="fecha_instalacion",defaultValue = "2022-01-01")String fecha_instalacion,
            @RequestParam(value="fecha_inicio_operacion",defaultValue = "2022-01-01")String fecha_inicio_operacion,
            @RequestParam(value="fecha_vencimiento",defaultValue = "2022-01-01")String fecha_vencimiento)  {
		
		Resume resumedit = ResumeService.findHVbyDevice(id);
		Device device = DeviceService.findOne(id);
		
		LocalDate fechacompra = LocalDate.parse(fecha_compra);
    	Date fechac = Date.valueOf(fechacompra);
    	LocalDate fechainstalacion = LocalDate.parse(fecha_instalacion);
    	Date fechai = Date.valueOf(fechainstalacion);
    	LocalDate fechainicio = LocalDate.parse(fecha_inicio_operacion);
    	Date fechaop = Date.valueOf(fechainicio);
    	LocalDate fechavencimiento= LocalDate.parse(fecha_vencimiento);
    	Date fechav = Date.valueOf(fechavencimiento);
    	
    	resumedit.setFecha_compra(fechac);
    	resumedit.setFecha_instalacion(fechai);
    	resumedit.setFecha_iniciooperacion(fechaop);
    	resumedit.setFecha_vencimientogarantia(fechav);
    	resumedit.setDevice(device);	
    	resumedit.setAccesorio1(resume.getAccesorio1());
    	resumedit.setAccesorio2(resume.getAccesorio2());
    	resumedit.setAccesorio3(resume.getAccesorio3());
    	resumedit.setAccesorio4(resume.getAccesorio4());
    	resumedit.setCalval(resume.isCalval());
    	resumedit.setCapacidad(resume.getCapacidad());
    	resumedit.setCiudadproveedor(resume.getCiudadproveedor());
    	resumedit.setClase_biomedica(resume.getClase_biomedica());
    	resumedit.setClase_equipo(resume.getClase_equipo());
    	resumedit.setCodinternacional(resume.getCodinternacional());
    	resumedit.setContrato(resume.getContrato());
    	resumedit.setCorreoproveedor(resume.getCorreoproveedor());
    	resumedit.setCosto_compra(resume.getCosto_compra());
    	resumedit.setEmailinstitucion(resume.getEmailinstitucion());
    	resumedit.setEquipotipofijo(resume.isEquipotipofijo());
    	resumedit.setFabricante(resume.getFabricante());
    	resumedit.setFrecuencia(resume.getFrecuencia());
    	resumedit.setFuente_energia(resume.getFuente_energia());
    	resumedit.setImaxoperacion(resume.getImaxoperacion());
    	resumedit.setIminoperacion(resume.getIminoperacion());
    	resumedit.setModo_compra(resume.getModo_compra());
    	resumedit.setOtrosdatostecnicos(resume.getOtrosdatostecnicos());
    	resumedit.setPaisfabricante(resume.getPaisfabricante());
    	resumedit.setPeso(resume.getPeso());
    	resumedit.setPresion(resume.getPresion());
    	resumedit.setProveedor(resume.getProveedor());
    	resumedit.setRepresentante(resume.getRepresentante());
    	resumedit.setRequierecalibracion(resume.isRequierecalibracion());
    	resumedit.setTelefonoproveedor(resume.getTelefonoproveedor());
    	resumedit.setTelefonorepresentante(resume.getTelefonorepresentante());
    	resumedit.setTemperatura(resume.getTemperatura());
    	resumedit.setUso(resume.getUso());
    	resumedit.setVelocidad(resume.getVelocidad());
    	resumedit.setVmaxoperacion(resume.getVmaxoperacion());
    	resumedit.setVminoperacion(resume.getVminoperacion());
    	resumedit.setWconsumida(resume.getWconsumida());
    	
		ResumeService.save(resumedit);
		
        flash.addFlashAttribute("success","guardado");
        return "redirect:/viewreports/"+device.getId_Device();

	}
	
	@GetMapping("/downloadhv/{id}")
    public void downloadFile(HttpServletResponse response,@PathVariable Long id) throws IOException {
        PdfGenerator generator = new PdfGenerator();
        Resume resume = ResumeService.findHVbyDevice(id);
        
        System.out.println(resume.getFabricante());
        System.out.println(resume.getDevice().getMarca());
        byte[] pdfReport = generator.getHVPDF(resume.getDevice(),resume).toByteArray();

        String mimeType =  "application/pdf";
        String namefile =String.valueOf(resume.getDevice().getId_Device());
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"",namefile+".pdf"));

        response.setContentLength(pdfReport.length);

        ByteArrayInputStream inStream = new ByteArrayInputStream(pdfReport);

        FileCopyUtils.copy(inStream, response.getOutputStream());
    }



}
