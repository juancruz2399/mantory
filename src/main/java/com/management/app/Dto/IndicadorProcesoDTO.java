package com.management.app.Dto;

public class IndicadorProcesoDTO {
    private int mes;
    private int anio;
    private String tipoProceso;
    private int ejecutados;
    private int programados;

    public IndicadorProcesoDTO(int mes, int anio, String tipoProceso, int ejecutados, int programados) {
        this.mes = mes;
        this.anio = anio;
        this.tipoProceso = tipoProceso;
        this.ejecutados = ejecutados;
        this.programados = programados;
    }

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public String getTipoProceso() {
		return tipoProceso;
	}

	public void setTipoProceso(String tipoProceso) {
		this.tipoProceso = tipoProceso;
	}

	public int getEjecutados() {
		return ejecutados;
	}

	public void setEjecutados(int ejecutados) {
		this.ejecutados = ejecutados;
	}

	public int getProgramados() {
		return programados;
	}

	public void setProgramados(int programados) {
		this.programados = programados;
	}

    // Getters y Setters
    
}
