package com.management.app.Controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.management.app.Service.IDeviceService;
import com.management.app.Service.IProcessDeviceService;
import com.management.app.Dao.IProcessDao;
import com.management.app.Entity.Device;
import com.management.app.Entity.ProcessDevice;

@Controller
public class ProcessController {
	
	@Autowired
	private IDeviceService DeviceService;
	
	@Autowired
    private IProcessDao processDao;
	
	@Autowired
	private IProcessDeviceService ProcessService;

	private final String RUTA_UPLOADS = "src/main/resources/static/files/procesos/";

    /**
     * Carga el formulario del proceso más reciente relacionado al dispositivo.
     * @param id ID del dispositivo (id_Device)
     * @param model Modelo para Thymeleaf
     * @return Fragmento Thymeleaf del formulario
     */
    @GetMapping("/proceso/form/{id}")
    public String cargarFormularioProcesoPorDevice(@PathVariable Long id, Model model) {
        List<ProcessDevice> procesos = processDao.findcalbyDevice(id);

        if (procesos == null || procesos.isEmpty()) {
        	ProcessDevice proceso = new ProcessDevice();
        	Device device = DeviceService.findOne(id);
        	proceso.setDevice(device);
        	model.addAttribute("proceso", proceso);
        	model.addAttribute("device", device);
            return "fragments/formProceso :: formProceso";
        }

        // Intentamos encontrar el proceso más reciente (aunque sea de años anteriores)
        Optional<ProcessDevice> procesoMasReciente = procesos.stream()
            .filter(p -> p.getDate_process() != null)
            .max(Comparator.comparing(ProcessDevice::getDate_process));
        Device device = DeviceService.findOne(id);
        ProcessDevice proceso = procesoMasReciente.orElse(procesos.get(0));
        model.addAttribute("proceso", proceso);
        model.addAttribute("device", device);
        return "fragments/formProceso :: formProceso";
    }

    /**
     * Guarda el proceso actualizado.
     * Este método debes completarlo si implementas lógica POST.
     */
    @PostMapping("/proceso/form/{id}")
    public String guardarProcesoConPDF(
        @PathVariable Long id,
        @ModelAttribute("proceso") ProcessDevice proceso,
        @RequestParam("archivoPdf") MultipartFile archivo,  // <-- Usa "archivoPdf"
        Model model
    ) {
        try {
            if (!archivo.isEmpty()) {
                String nombreArchivo = UUID.randomUUID().toString() + ".pdf";
                Path rutaArchivo = Paths.get(RUTA_UPLOADS).resolve(nombreArchivo).toAbsolutePath();
                Files.createDirectories(rutaArchivo.getParent());
                archivo.transferTo(rutaArchivo);

                String rutaWeb = "/files/procesos/" + nombreArchivo;
                proceso.setUrl_ubicacion(rutaWeb);
            }

            Device device = DeviceService.findOne(id);
            proceso.setDevice(device);
            processDao.save(proceso);
            return "redirect:/maintenance";  // Redirige a donde necesites

        } catch (IOException e) {
            model.addAttribute("error", "Error al guardar el archivo PDF.");
            return "fragments/formProceso :: formProceso";
        }
    }
    @GetMapping("/buscarProcesoDinamico/{id}")
	@ResponseBody
	public Object[] getProcesoDinamico(
			@PathVariable(value = "id") Long id,
			@RequestParam(value="fecha_reporte",defaultValue = "2024-11-11")String fecha_reporte
			){
		LocalDate fechareporte= LocalDate.parse(fecha_reporte);
	    Object[] process = ProcessService.findProcessbyIdDate(Date.valueOf(fechareporte), id);
    	return process;
	}

    
}
