package com.management.app.Entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "protocols")
public class Protocols implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_protocols")
	private Long id_Protocols;
	
	@Column(name = "paso")
	private String Paso;
	
	@Column(name = "version")
	private int Version;
	
	@Column(name = "observacion")
	private String Observacion;
	
	@JoinColumn(name ="id_type",referencedColumnName ="id_Type")
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
	private Types type;
	
	@JoinColumn(name = "id_device_fk", referencedColumnName = "id_Device")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private Device device;

	/********************* GET Y SET *****************************/
	
	public static long getSerialVersionUID(){
		return serialVersionUID;
	}

	public Long getId_Protocols() {
		return id_Protocols;
	}

	public void setId_Protocols(Long id_Protocols) {
		this.id_Protocols = id_Protocols;
	}

	public String getPaso() {
		return Paso;
	}

	public void setPaso(String paso) {
		Paso = paso;
	}

	public Types getType() {
		return type;
	}

	public void setType(Types type) {
		this.type = type;
	}

	public int getVersion() {
		return Version;
	}

	public void setVersion(int version) {
		Version = version;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public String getObservacion() {
		return Observacion;
	}

	public void setObservacion(String observacion) {
		Observacion = observacion;
	}
	
	
	
}
