package com.management.app.Entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "types")
public class Types implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_type")
	private Long id_Type;
	
	@NotNull
	@Column(name = "nombre_tipo")
	private String Nombre_tipo;
	
	@Column(name = "material_consumible")
	private String Material_consumible;
	
	@Column(name = "herramienta")
	private String Herramienta;
	
	@Column(name = "tiempo_minutos")
	private int Tiempo_minutos;
	
	@Column(name = "actividad")
	private String Actividad;
	
	
	/********************* GET Y SET *****************************/
	
	public static long getSerialVersionUID(){
		return serialVersionUID;
	}

	public Long getId_Type() {
		return id_Type;
	}

	public void setId_Type(Long id_Type) {
		this.id_Type = id_Type;
	}

	public String getNombre_tipo() {
		return Nombre_tipo;
	}

	public void setNombre_tipo(String nombre_tipo) {
		Nombre_tipo = nombre_tipo;
	}

	public String getMaterial_consumible() {
		return Material_consumible;
	}

	public void setMaterial_consumible(String material_consumible) {
		Material_consumible = material_consumible;
	}

	public String getHerramienta() {
		return Herramienta;
	}

	public void setHerramienta(String herramienta) {
		Herramienta = herramienta;
	}

	public int getTiempo_minutos() {
		return Tiempo_minutos;
	}

	public void setTiempo_minutos(int tiempo_minutos) {
		Tiempo_minutos = tiempo_minutos;
	}

	public String getActividad() {
		return Actividad;
	}

	public void setActividad(String actividad) {
		Actividad = actividad;
	}
	
	
}
