package com.management.app.Controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.management.app.Entity.Maintenance;
import com.management.app.Entity.Report;
import com.management.app.Entity.Resume;
import com.management.app.Entity.Protocols;
import com.management.app.Entity.ProcessDevice;
import com.management.app.Entity.User;
import com.management.app.Security.CustomUserDetails;
import com.management.app.Service.IDeviceService;
import com.management.app.Service.IMaintenanceService;
import com.management.app.Service.IProcessDeviceService;
import com.management.app.Service.IProtocolsService;
import com.management.app.Service.IReportService;
import com.management.app.Service.IResumeService;
import com.management.app.Service.IUserService;
import com.management.app.Service.PdfGenerator;
import com.management.app.Service.PdfSignerService;
import com.management.app.Service.UploadFileService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping
public class ReportController {

	@Autowired
    private IReportService ReportService;
	
	@Autowired
	private IDeviceService DeviceService;
	
	@Autowired
	private IProcessDeviceService ProcessDeviceService;
	
	@Autowired
	private IMaintenanceService MaintenanceService;
	
	@Autowired
	private IResumeService ResumeService;
	
	@Autowired
	private IUserService UserService;
	
	@Autowired
	private UploadFileService uploadFileService;
	
	@Autowired
	private IProtocolsService ProtocolsService;
	
	@Autowired
	private PdfSignerService signerService;
	
	@Autowired
	private PdfGenerator pdfGenerator;

	public static final String PDF_STORAGE_DIR = "C:/reportes/";


    @GetMapping("/firmar")
    public ResponseEntity<String> firmar() {
        try {
            signerService.signPdf(
                "C:/ruta/original.pdf",
                "C:/ruta/firmado.pdf",
                "C:/Users/juand/Documents/certificado_final.p12",
                "jdcgnr"
            );
            return ResponseEntity.ok("PDF firmado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al firmar el PDF");
        }
    }
    
	@GetMapping("/newreport/{id}")
    public String newreport(@PathVariable(value="id") Long id,
    		  Map<String,Object>map,Model model,
			  RedirectAttributes flash) {
		Device device = null;
		Report report = new Report();
		List<Protocols> protocolos = new ArrayList<Protocols>();
		map.put("report",report);
		if(id>0){
    		device = DeviceService.findOne(id);
        	report.setDevice(device);
        	protocolos = ProtocolsService.lastVersionProtocolsbyDevice(id, 0);
    		if(device==null) {
    			flash.addFlashAttribute("error","El equipo no existe en la base de datos");
    			return "redirect:/viewreports/{id}";
    		}
    		
    	}
    	
    	else {
    		flash.addFlashAttribute("error","El ID no puede ser cero");
    		return "redirect:/viewreports/{id}";
    	}
		Date defaultdate = Date.valueOf(LocalDate.now());
    	Time defaulthour = Time.valueOf(LocalTime.now());
    	Time defaultcall = Time.valueOf(LocalTime.parse("00:00"));
    	model.addAttribute("fecha",defaultdate);
    	model.addAttribute("horallamado",defaultcall);
    	model.addAttribute("horainicio",defaulthour);
    	model.addAttribute("horaterminacion",defaulthour);
		model.addAttribute("device", device);
		model.addAttribute("protocolos", protocolos);
		model.addAttribute("numeroreporte",ReportService.lastIdReport());
        return "newreport";
    }
	
	@GetMapping("/newpreventive/{id}/{idmtto}")
    public String newpreventive(@PathVariable(value="id") Long id,
    		  @PathVariable(value="idmtto") Long idmtto,
    		  Map<String,Object>map,Model model,
			  RedirectAttributes flash) {
		Device device = null;
		Report report = new Report();
		Maintenance maintenance = MaintenanceService.findOne(idmtto);
		map.put("report",report);
		if(id>0){
    		device = DeviceService.findOne(id);
        	report.setDevice(device);
    		if(device==null) {
    			flash.addFlashAttribute("error","El equipo no existe en la base de datos");
    			return "redirect:/viewreports/{id}";
    		}
    		
    	}
    	
    	else {
    		flash.addFlashAttribute("error","El ID no puede ser cero");
    		return "redirect:/viewreports/{id}";
    	}
		Date defaultdate = Date.valueOf(LocalDate.now());
    	Time defaulthour = Time.valueOf(LocalTime.now());
    	Time defaultcall = Time.valueOf(LocalTime.parse("00:00"));
    	model.addAttribute("fecha",defaultdate);
    	model.addAttribute("horallamado",defaultcall);
    	model.addAttribute("horainicio",defaulthour);
    	model.addAttribute("horaterminacion",defaulthour);
		model.addAttribute("device", device);
		model.addAttribute("idmtto",idmtto);
		model.addAttribute("numeroreporte",ReportService.lastIdReport());
        return "newpreventive";
    }
	private String extraerBase64(String dataUri) {
	    if (dataUri != null && dataUri.startsWith("data:image")) {
	        String[] partes = dataUri.split(",");
	        if (partes.length == 2) {
	            return partes[1]; // solo los datos base64
	        }
	    }
	    return null;
	}
	@PostMapping("/newpreventive/{id}/{idmtto}")
	public String savepreventive(@PathVariable Long id,@PathVariable(value="idmtto") Long idmtto,
			@RequestParam(value="fecha")String fecha,
			@RequestParam("carta") MultipartFile cart,
			@RequestParam("descripcion") MultipartFile description,
    		@RequestParam(value="hora_llamado",defaultValue = "00:00")String hora_llamado,
    		@RequestParam(value="hora_inicio",defaultValue = "00:00")String hora_inicio,
    		@RequestParam(value = "hora_finalizacion",defaultValue = "00:00")String hora_finalizacion,
    		@Valid Report report,
    								  BindingResult result,
    								  Model model,
    								  RedirectAttributes flash,
    								  SessionStatus status) throws IOException {
		Device device = DeviceService.findOne(id);
        
    	LocalDate fechareporte = LocalDate.parse(fecha);
    	Date fechaas = Date.valueOf(fechareporte);
    	report.setFecha(fechaas);
    	
    	String hora1 = hora_llamado; 
    	
    	Time hl = Time.valueOf(LocalTime.parse(hora1));
    	report.setHora_llamado(hl);
    	
    	String hora2 = hora_inicio;
    	Time hi = Time.valueOf(LocalTime.parse(hora2));
    	report.setHora_inicio(hi);
    	
    	String hora3 = hora_finalizacion;
    	Time hf = Time.valueOf(LocalTime.parse(hora3));
    	report.setHora_terminacion(hf);
    	
    	
    	LocalTime hinicio = hi.toLocalTime();
    	LocalTime hfinal = hf.toLocalTime();
    	LocalTime hginalminus = hfinal.minusHours(hinicio.getHour());
    	LocalTime thora = hginalminus.minusMinutes(hinicio.getMinute());
    	Time totalhoras = Time.valueOf(thora);
    	
    	report.setTotal_horas(totalhoras);    	
    	report.setDevice(device);
    	report.setServicio(device.getService().getNombre_servicio());
    	report.setUbicacion(device.getUbicacion());
    	report.setObservaciones("");
    	report.setRepuesto_cambiado("");
    	report.setAutor_recibido("");
    	report.setTrabajo_realizado("");
    	String upload_folder = "./src/main/resources/files/";
    	report.setRutapdf(upload_folder + String.valueOf(id)+ description.getOriginalFilename());
    	report.setRutapruebas(upload_folder + String.valueOf(id)+ cart.getOriginalFilename());
    	uploadFileService.saveFile(cart, id);
    	uploadFileService.saveFile(description,id);
    	Authentication auth = SecurityContextHolder
			    .getContext()
			    .getAuthentication();
		CustomUserDetails userDetail = (CustomUserDetails) auth.getPrincipal();
		User user = UserService.findbyname(userDetail.getUsername());
		report.setResponsable(user.getNombre()+' '+user.getApellido());
		ReportService.save(report);
		LocalDate now = LocalDate.now();
		
		
    	Maintenance maintenance = MaintenanceService.findOne(idmtto);
    	maintenance.setFecha_realizacion(Date.valueOf(now));
    	maintenance.setDevice(report.getDevice());
    	maintenance.setRealizado(true);
    	maintenance.setReport(report);
    	maintenance.setTiempo_realizacion(totalhoras);
    	MaintenanceService.save(maintenance);
    	status.setComplete();
		return "redirect:/viewreports/"+id;
	}
	@PostMapping("/newreport/{id}")
	public String savereport(@PathVariable Long id,
	                         @RequestParam(value = "fecha") String fecha,
	                         @RequestParam(value = "hora_llamado", defaultValue = "00:00") String hora_llamado,
	                         @RequestParam(value = "hora_inicio", defaultValue = "00:00") String hora_inicio,
	                         @RequestParam(value = "hora_finalizacion", defaultValue = "00:00") String hora_finalizacion,
	                         @RequestParam(value = "pasosrutinas", defaultValue = "0;0") String pasosrutina,
	                         @RequestParam(value = "observacionesrutinas", defaultValue = ".,.,.,.") String obsrutinas,

	                         @RequestParam(value = "firmaEjecutorBase64", required = false) String firmaEjecutorBase64,
	                         @RequestParam(value = "firmaReceptorBase64", required = false) String firmaReceptorBase64,
	                         @Valid Report report,
	                         BindingResult result,
	                         Model model,
	                         RedirectAttributes flash,
	                         SessionStatus status) throws Exception {

	    Device device = DeviceService.findOne(id);

	    // Set campos normales
	    report.setFecha(Date.valueOf(LocalDate.parse(fecha)));
	    report.setHora_llamado(Time.valueOf(LocalTime.parse(hora_llamado)));
	    report.setHora_inicio(Time.valueOf(LocalTime.parse(hora_inicio)));
	    report.setHora_terminacion(Time.valueOf(LocalTime.parse(hora_finalizacion)));

	    if (report.getTipo_mantenimiento() == 3) {
	        int version = ProtocolsService.findLastVersion(id);
	        String rutapruebas = version + ";" + pasosrutina;
	        report.setRutapruebas(rutapruebas);
	    }

	    report.setObservacionPruebas(obsrutinas);

	    // Calcular total horas
	    LocalTime hi = report.getHora_inicio().toLocalTime();
	    LocalTime hf = report.getHora_terminacion().toLocalTime();
	    Duration duracion = Duration.between(hi, hf);
	    report.setTotal_horas(Time.valueOf(LocalTime.MIDNIGHT.plus(duracion)));

	    report.setDevice(device);
	    report.setServicio(device.getService().getNombre_servicio());
	    report.setUbicacion(device.getUbicacion());

	 // Guardar reporte en BD
	    ReportService.save(report);
	    // Obtener protocolos de la última versión (tipo 0)
	    List<Protocols> protocolos = ProtocolsService.lastVersionProtocolsbyDevice(device.getId_Device(), 0);

	    // ✅ Procesar firmas base64 → byte[]
	    String firma1 = extraerBase64(firmaEjecutorBase64);
	    String firma2 = extraerBase64(firmaReceptorBase64);
	    System.out.println("Firma ejecutor base64: " + firmaEjecutorBase64);
	    byte[] pdfBytes = pdfGenerator.getReportPDFFinal(report, device.getInstitution(), protocolos, firma1, firma2)
	            .toByteArray();

	    // Guardar archivo localmente
	    String baseDir = "C:/reportes/";
	    Files.createDirectories(Paths.get(baseDir));

	    String filename = "REPORTE_" + report.getId_Report() + "_" + LocalDate.now() + ".pdf";
	    Path path = Paths.get(baseDir + filename);
	    Files.write(path, pdfBytes);

	    // Guardar ruta del PDF en el reporte
	    report.setRutapdf(filename);
	    ReportService.save(report);

	    flash.addFlashAttribute("success", "Reporte generado correctamente.");
	    status.setComplete();
	    return "redirect:/viewreports/" + id;
	}
	@GetMapping("/editreport/{id}")
	public String editarreporte(@PathVariable(value="id") Long id,
			Model model,Map<String, Object> map,
            RedirectAttributes flash) {
		Report report  = ReportService.findOne(id);
		Device device= report.getDevice();
		map.put("reporte", report);
		model.addAttribute("device", device);
		model.addAttribute("fecha",report.getFecha());
    	model.addAttribute("horallamado",report.getHora_llamado());
    	model.addAttribute("horainicio",report.getHora_inicio());
    	model.addAttribute("horaterminacion",report.getHora_terminacion());
    	model.addAttribute("numeroreporte",report.getId_Report());
		return "editreport";
	}
	@PostMapping("/editreport/{id}")
	public String guardarnuevoreportedit(@PathVariable Long id,@RequestParam(value="fecha")String fecha,
    		@RequestParam(value="hora_llamado",defaultValue = "00:00")String hora_llamado,
    		@RequestParam(value="hora_inicio",defaultValue = "00:00")String hora_inicio,
    		@RequestParam(value = "hora_finalizacion",defaultValue = "00:00")String hora_finalizacion,
    		@Valid Report report,
    								  BindingResult result,
    								  Model model,
    								  RedirectAttributes flash,
    								  SessionStatus status) {
		Report reportedit = ReportService.findOne(id);
		LocalDate fechareporte = LocalDate.parse(fecha);
    	Date fechaas = Date.valueOf(fechareporte);
    	Device device = reportedit.getDevice();
    	reportedit.setFecha(fechaas);
    	String hora1 = hora_llamado;     	
    	Time hl = Time.valueOf(LocalTime.parse(hora1));
    	reportedit.setHora_llamado(hl);
    	
    	String hora2 = hora_inicio;
    	Time hi = Time.valueOf(LocalTime.parse(hora2));
    	reportedit.setHora_inicio(hi);
    	
    	String hora3 = hora_finalizacion;
    	Time hf = Time.valueOf(LocalTime.parse(hora3));
    	reportedit.setHora_terminacion(hf);
    	LocalTime hinicio = hi.toLocalTime();
    	LocalTime hfinal = hf.toLocalTime();
    	LocalTime hginalminus = hfinal.minusHours(hinicio.getHour());
    	LocalTime thora = hginalminus.minusMinutes(hinicio.getMinute());
    	Time totalhoras = Time.valueOf(thora);
    	reportedit.setTotal_horas(totalhoras);
    	reportedit.setAutor_recibido(report.getAutor_recibido());
    	reportedit.setDevice(report.getDevice());
    	reportedit.setObservaciones(report.getObservaciones());
    	reportedit.setRepuesto_cambiado(report.getRepuesto_cambiado());
    	reportedit.setResponsable(report.getResponsable());
    	reportedit.setServicio(device.getService().getNombre_servicio());
    	reportedit.setTiempo_fuera_servicio(report.getTiempo_fuera_servicio());
    	reportedit.setTipo_falla(report.getTipo_falla());
    	reportedit.setTipo_mantenimiento(report.getTipo_mantenimiento());
    	reportedit.setTrabajo_realizado(report.getTrabajo_realizado());
    	reportedit.setUbicacion(report.getDevice().getUbicacion());
		ReportService.save(reportedit);
		return "redirect:/viewreports/"+reportedit.getDevice().getId_Device();
	}
	
	@GetMapping("/viewreports/{id}")
	public String ShowReportsbyDevice(@PathVariable(value="id") Long id,
			 Model model,
			 RedirectAttributes flash) {
		Device device = DeviceService.findOne(id);
		Resume resume = ResumeService.findHVbyDevice(id);
		List<ProcessDevice> calibracion = ProcessDeviceService.findbyId(id); 
		ProcessDevice lastcalibracion = new ProcessDevice();
		if(calibracion.size()>0) {
			lastcalibracion = calibracion.get(calibracion.size()-1);
		}
		List<Report> reports = ReportService.findReportsbyDevice(id);
		int countr = ReportService.countReportsbyDevices(id);
		List<Maintenance> mttos = new ArrayList<Maintenance>();
		for(int i=0;i<reports.size();i++) {
			Maintenance mtto = MaintenanceService.findbyreport(reports.get(i).getId_Report());
			
			mttos.add(mtto);
			
		}
		ArrayList<String> materials = new ArrayList<String>(Arrays.asList(device.getType().getMaterial_consumible().split(",")));
		ArrayList<String> tools = new ArrayList<String>(Arrays.asList(device.getType().getHerramienta().split(",")));

		model.addAttribute("calibracion", lastcalibracion);
		model.addAttribute("reports", reports);
		model.addAttribute("device", device);
		model.addAttribute("countr", countr);
		model.addAttribute("tools", tools);
		model.addAttribute("materials", materials);
		model.addAttribute("mttos", mttos);
		model.addAttribute("resume", resume);
		return "reports";
	}
	
	@GetMapping(value = "/visualpdfreport/{id}")
    public ResponseEntity<InputStreamResource> visualizarpdfreporte(HttpServletRequest request,HttpServletResponse response,@PathVariable(value="id") Long id,
			  Map<String,Object>map,Model model,
			  RedirectAttributes flash) throws IOException{
    	Report report = ReportService.findOne(id);
    	File file = new File(PDF_STORAGE_DIR+report.getRutapdf());
    	HttpHeaders headers = new HttpHeaders();
    	System.out.println("ruta: " + report.getRutapdf());
	    
    	InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
    	
    	return ResponseEntity.ok()
    			.headers(headers)
    			.contentLength(file.length())
    			.contentType(MediaType.parseMediaType("application/pdf"))
    			.body(resource);
    			
    }
	@GetMapping(value = "/visualpdfcarta/{id}")
    public ResponseEntity<InputStreamResource> visualizarpdfcarta(HttpServletRequest request,HttpServletResponse response,@PathVariable(value="id") Long id,
			  Map<String,Object>map,Model model,
			  RedirectAttributes flash) throws IOException{
    	Report report = ReportService.findOne(id);
    	File file = new File(report.getRutapruebas());
    	HttpHeaders headers = new HttpHeaders();
    	
    	InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
    	
    	return ResponseEntity.ok()
    			.headers(headers)
    			.contentLength(file.length())
    			.contentType(MediaType.parseMediaType("application/pdf"))
    			.body(resource);
    			
    }
	@GetMapping("/{filename:.+}")
    public void verReporte(@PathVariable String filename, HttpServletResponse response) throws IOException {
        Path pdfPath = Paths.get(PDF_STORAGE_DIR, filename);
        if (!Files.exists(pdfPath)) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Archivo no encontrado: " + filename);
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");
        Files.copy(pdfPath, response.getOutputStream());
        response.getOutputStream().flush();
    }
	
}
