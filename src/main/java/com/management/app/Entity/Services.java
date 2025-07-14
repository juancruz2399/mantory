package com.management.app.Entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "services")
public class Services implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_service")
	private Long id_Service;

	@Column(name = "nombre_servicio")
	private String Nombre_servicio;
	
	@Column(name = "ubicacion_servicio")
	private String Ubicacion_servicio;
	
	/********************* GET Y SET *****************************/
	
	public static long getSerialVersionUID(){
		return serialVersionUID;
	}

	public Long getId_Service() {
		return id_Service;
	}

	public void setId_Service(Long id_Service) {
		this.id_Service = id_Service;
	}

	public String getNombre_servicio() {
		return Nombre_servicio;
	}

	public void setNombre_servicio(String nombre_servicio) {
		Nombre_servicio = nombre_servicio;
	}

	public String getUbicacion_servicio() {
		return Ubicacion_servicio;
	}

	public void setUbicacion_servicio(String ubicacion_servicio) {
		Ubicacion_servicio = ubicacion_servicio;
	}
	
}
