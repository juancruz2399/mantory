package com.management.app.Controllers;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;
import java.util.HashMap;

import com.management.app.Entity.Device;
import com.management.app.Entity.ProcessDevice;
import com.management.app.Service.IDeviceService;
import com.management.app.Service.IProcessDeviceService;


@Controller
@RequestMapping
public class CalendarController {

	@Autowired
	private IDeviceService DeviceService;
	
	@Autowired
	private IProcessDeviceService ProcessService;
	
	@GetMapping("/calendar")
    public String listdevice(Model model){
    
        return "calendar";
    }
	
	@GetMapping("/api/calendario")
	@ResponseBody
	public List<Map<String, Object>> getEventosCalendario(
	    @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
	    @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end
	) {
	    List<Device> dispositivos = DeviceService.findPrcoess();
	    List<Map<String, Object>> eventos = new ArrayList<>();

	    int mesInicio = start.getMonthValue(); // 1-12
	    int anio = start.getYear();

	    for (Device d : dispositivos) {
	        String[] meses = d.getMeses_procesos().split(",");
	        String[] tipos = d.getTipo_proceso().split(",");

	        for (int i = 0; i < Math.min(meses.length, tipos.length); i++) {
	            String nombreMes = meses[i].trim().toUpperCase();
	            String tipo = tipos[i].trim();

	            int mesNumerico = convertirMesANumero(nombreMes); // ej: "ABRIL" → 4

	            if (mesNumerico == mesInicio) {
	                LocalDate fechaEvento = LocalDate.of(anio, mesNumerico, 5); // Día arbitrario (5)
	                Map<String, Object> evento = new HashMap<>();
	                evento.put("title", tipo + " - " + d.getName_device());
	                evento.put("start", fechaEvento.toString());
	                evento.put("color", tipoColor(tipo));
	                evento.put("url", "/detalleDispositivo/" + d.getId_Device());
	                eventos.add(evento);
	            }
	        }
	    }

	    return eventos;
	}

	private String tipoColor(String tipo) {
	    switch (tipo) {
	        case "mantenimiento":
	            return "#007bff"; // azul
	        case "calibración":
	            return "#28a745"; // verde
	        case "verificación":
	            return "#ffc107"; // amarillo
	        default:
	            return "#6c757d"; // gris
	    }
	}
	private int convertirMesANumero(String nombreMes) {
	    nombreMes = nombreMes.toUpperCase();

	    if (nombreMes.equals("ENERO")) {
	        return 1;
	    } else if (nombreMes.equals("FEBRERO")) {
	        return 2;
	    } else if (nombreMes.equals("MARZO")) {
	        return 3;
	    } else if (nombreMes.equals("ABRIL")) {
	        return 4;
	    } else if (nombreMes.equals("MAYO")) {
	        return 5;
	    } else if (nombreMes.equals("JUNIO")) {
	        return 6;
	    } else if (nombreMes.equals("JULIO")) {
	        return 7;
	    } else if (nombreMes.equals("AGOSTO")) {
	        return 8;
	    } else if (nombreMes.equals("SEPTIEMBRE")) {
	        return 9;
	    } else if (nombreMes.equals("OCTUBRE")) {
	        return 10;
	    } else if (nombreMes.equals("NOVIEMBRE")) {
	        return 11;
	    } else if (nombreMes.equals("DICIEMBRE")) {
	        return 12;
	    } else {
	        return -1; // mes inválido
	    }
	}

	
}
