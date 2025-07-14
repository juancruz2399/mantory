package com.management.app.Service;

import java.util.List;
import java.util.Locale;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.app.Dao.IDeviceDao;
import com.management.app.Dao.IProcessDao;
import com.management.app.Dao.IReportDao;
import com.management.app.Dto.IndicadorMantenimientoDTO;
import com.management.app.Dto.IndicadorProcesoDTO;

@Service
public class KpiService {

    @Autowired
    private IReportDao reportDao;

    @Autowired
    private IProcessDao processDao;

    @Autowired
    private IDeviceDao deviceDao;

    // KPI 1: Mantenimientos Preventivos
    public List<Map<String, Object>> getMantenimientosPreventivos() {
        List<Object[]> raw = reportDao.countMantenimientosPreventivosPorMes();
        return convertToKpi(raw, "mes", "año", "total");
    }

    // KPI 2: Mantenimientos Correctivos
    public List<Map<String, Object>> getMantenimientosCorrectivos() {
        List<Object[]> raw = reportDao.countMantenimientosCorrectivosPorMes();
        return convertToKpi(raw, "mes", "año", "total");
    }

    // KPI 3: Total horas mantenimiento
    public List<Map<String, Object>> getHorasMantenimiento() {
        List<Object[]> raw = reportDao.sumaHorasMantenimientoPorMes();
        return convertToKpi(raw, "mes", "año", "totalHoras");
    }

    // KPI 4: Procesos aprobados
    public List<Map<String, Object>> getProcesosAprobados() {
        List<Object[]> raw = processDao.countProcesosAprobadosPorTipo();
        return convertToKpi(raw, "mes", "año", "total");
    }

    // KPI 5: Procesos ejecutados por tipo (por name_process)
    public List<Map<String, Object>> getProcesosEjecutadosPorTipo() {
        List<Object[]> raw = processDao.countProcesosEjecutadosPorTipo();
        return convertToKpiByTipo(raw, "tipo_proceso", "mes", "año", "ejecutados");
    }

    // KPI 6: Procesos programados por tipo desde Device
    public List<IndicadorProcesoDTO> calcularIndicadoresPorTipo() {
        List<Object[]> ejecutados = processDao.countProcesosEjecutadosPorTipo();
        List<Object[]> programados = new ArrayList<>();

        for (int mes = 1; mes <= 12; mes++) {
            for (int anio = 2023; anio <= 2025; anio++) {
                String nombreMes = Month.of(mes).getDisplayName(TextStyle.FULL, new Locale("es")).toUpperCase();
                programados.addAll(processDao.countProcesosProgramadosPorTipo(mes, anio, nombreMes));
            }
        }

        Map<String, IndicadorProcesoDTO> mapa = new HashMap<>();

        for (Object[] row : programados) {
            String tipo = ((String) row[2]).trim();
            String key = row[1] + "-" + row[0] + "-" + tipo;
            mapa.put(key, new IndicadorProcesoDTO((Integer) row[0], (Integer) row[1], tipo, 0, ((Number) row[3]).intValue()));
        }

        for (Object[] row : ejecutados) {
            String tipo = ((String) row[2]).trim();
            String key = row[1] + "-" + row[0] + "-" + tipo;
            mapa.computeIfAbsent(key, k -> new IndicadorProcesoDTO((Integer) row[0], (Integer) row[1], tipo, 0, 0));
            mapa.get(key).setEjecutados(((Number) row[3]).intValue());
        }

        return new ArrayList<>(mapa.values());
    }

    public List<IndicadorMantenimientoDTO> calcularIndicadoresMantenimiento() {
        List<Object[]> prev = reportDao.countMantenimientosPreventivosPorMes();
        List<Object[]> corr = reportDao.countMantenimientosCorrectivosPorMes();
        List<Object[]> horas = reportDao.sumaHorasMantenimientoPorMes();

        Map<String, IndicadorMantenimientoDTO> mapa = new HashMap<>();

        for (Object[] row : prev) {
            String key = row[1] + "-" + row[0];
            mapa.put(key, new IndicadorMantenimientoDTO((Integer) row[0], (Integer) row[1], ((Number) row[2]).intValue(), 0, 0));
        }

        for (Object[] row : corr) {
            String key = row[1] + "-" + row[0];
            mapa.computeIfAbsent(key, k -> new IndicadorMantenimientoDTO((Integer) row[0], (Integer) row[1], 0, 0, 0));
            mapa.get(key).setCorrectivos(((Number) row[2]).intValue());
        }

        for (Object[] row : horas) {
            String key = row[1] + "-" + row[0];
            mapa.computeIfAbsent(key, k -> new IndicadorMantenimientoDTO((Integer) row[0], (Integer) row[1], 0, 0, 0));
            mapa.get(key).setTotalHoras(((Number) row[2]).doubleValue());
        }

        return new ArrayList<>(mapa.values());
    }

    

    // Utilidad genérica para convertir resultados de KPIs simples
    private List<Map<String, Object>> convertToKpi(List<Object[]> raw, String mesKey, String añoKey, String valueKey) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Object[] row : raw) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put(mesKey, row[0]);
            map.put(añoKey, row[1]);
            map.put(valueKey, row[2]);
            list.add(map);
        }
        return list;
    }

    // Utilidad para KPIs agrupados por tipo de proceso
    private List<Map<String, Object>> convertToKpiByTipo(List<Object[]> raw, String tipoKey, String mesKey, String añoKey, String valueKey) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Object[] row : raw) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put(tipoKey, row[0]);
            map.put(mesKey, row[1]);
            map.put(añoKey, row[2]);
            map.put(valueKey, row[3]);
            list.add(map);
        }
        return list;
    }
    
}