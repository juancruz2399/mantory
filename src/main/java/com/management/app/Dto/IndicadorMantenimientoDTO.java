package com.management.app.Dto;

public class IndicadorMantenimientoDTO {
    private int mes;
    private int anio;
    private int preventivos;
    private int correctivos;
    private double totalHoras;

    public IndicadorMantenimientoDTO(int mes, int anio, int preventivos, int correctivos, double totalHoras) {
        this.mes = mes;
        this.anio = anio;
        this.preventivos = preventivos;
        this.correctivos = correctivos;
        this.totalHoras = totalHoras;
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

	public int getPreventivos() {
		return preventivos;
	}

	public void setPreventivos(int preventivos) {
		this.preventivos = preventivos;
	}

	public int getCorrectivos() {
		return correctivos;
	}

	public void setCorrectivos(int correctivos) {
		this.correctivos = correctivos;
	}

	public double getTotalHoras() {
		return totalHoras;
	}

	public void setTotalHoras(double totalHoras) {
		this.totalHoras = totalHoras;
	}

    // Getters y Setters
    
}
