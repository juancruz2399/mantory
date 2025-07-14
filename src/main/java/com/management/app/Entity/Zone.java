package com.management.app.Entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "zone")
public class Zone implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_zone")
	private Long id_Zone;
	
	@Column(name = "nombre_zone")
	private String Nombre_zone;
	
	/********************* GET Y SET *****************************/
	
	public static long getSerialVersionUID(){
		return serialVersionUID;
	}

	public Long getId_Zone() {
		return id_Zone;
	}

	public void setId_Zone(Long id_Zone) {
		this.id_Zone = id_Zone;
	}

	public String getNombre_zone() {
		return Nombre_zone;
	}

	public void setNombre_zone(String nombre_zone) {
		Nombre_zone = nombre_zone;
	}
	
	
}
