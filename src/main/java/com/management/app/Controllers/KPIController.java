package com.management.app.Controllers;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.management.app.Service.IDeviceService;
import com.management.app.Service.IProcessDeviceService;
import com.management.app.Service.IReportService;
import com.management.app.Service.KpiService;

@RestController
@RequestMapping("/api/kpi")
public class KPIController {
	
	@Autowired
	private IDeviceService DeviceService;
	
	@Autowired
	private IReportService ReportService;
	
	@Autowired
	private IProcessDeviceService ProcessService;
	
	 @Autowired
	    private KpiService kpiService;
	
	// Mantenimientos Preventivos por mes
    @GetMapping("/mantenimientos/preventivos")
    public List<Map<String, Object>> getPreventivos() {
        return kpiService.getMantenimientosPreventivos();
    }

    // Mantenimientos Correctivos por mes
    @GetMapping("/mantenimientos/correctivos")
    public List<Map<String, Object>> getCorrectivos() {
        return kpiService.getMantenimientosCorrectivos();
    }

    // Total de horas invertidas en mantenimiento por mes
    @GetMapping("/mantenimientos/horas")
    public List<Map<String, Object>> getHorasMantenimiento() {
        return kpiService.getHorasMantenimiento();
    }

    // Procesos aprobados por mes
    @GetMapping("/procesos/aprobados")
    public List<Map<String, Object>> getProcesosAprobados() {
        return kpiService.getProcesosAprobados();
    }

    // Procesos ejecutados por tipo
    @GetMapping("/procesos/ejecutados")
    public List<Map<String, Object>> getProcesosEjecutadosPorTipo() {
        return kpiService.getProcesosEjecutadosPorTipo();
    }

    

}
