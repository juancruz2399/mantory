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
@Table(name = "institution")
public class Institution implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_institution")
	private Long id_Institution;
	
	@Column(name = "nombre")
	private String Nombre;
	
	@Column(name = "telefono")
	private String Telefono;
	
	@Column(name = "direccion")
	private String Direccion;
	
	@Column(name = "nit")
	private String Nit;
	
	@Column(name = "ciudad")
	private String Ciudad;
	
	@Column(name = "nivel")
	private int Nivel;
	
	@Column(name = "departamento")
	private String Departamento;
	
	@Column(name = "estado")
	private boolean estado;
	
	@Column(name = "codigo_prestador")
	private String Codigo_prestador;
	
	@JoinColumn(name ="id_zone_fk",referencedColumnName ="id_Zone")
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Zone zone;
	
	/********************* GET Y SET *****************************/
	
	public static long getSerialVersionUID(){
		return serialVersionUID;
	}
	

	public Long getId_Institution() {
		return id_Institution;
	}



	public void setId_Institution(Long id_Institution) {
		this.id_Institution = id_Institution;
	}



	public String getNombre() {
		return Nombre;
	}



	public void setNombre(String nombre) {
		Nombre = nombre;
	}



	public String getDireccion() {
		return Direccion;
	}



	public void setDireccion(String direccion) {
		Direccion = direccion;
	}



	public String getNit() {
		return Nit;
	}



	public void setNit(String nit) {
		Nit = nit;
	}



	public String getCiudad() {
		return Ciudad;
	}

	public void setCiudad(String ciudad) {
		Ciudad = ciudad;
	}

	public int getNivel() {
		return Nivel;
	}

	public void setNivel(int nivel) {
		Nivel = nivel;
	}

	public String getDepartamento() {
		return Departamento;
	}

	public void setDepartamento(String departamento) {
		Departamento = departamento;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}


	public Zone getZone() {
		return zone;
	}


	public void setZone(Zone zone) {
		this.zone = zone;
	}


	public String getTelefono() {
		return Telefono;
	}


	public void setTelefono(String telefono) {
		Telefono = telefono;
	}


	public String getCodigo_prestador() {
		return Codigo_prestador;
	}


	public void setCodigo_prestador(String codigo_prestador) {
		Codigo_prestador = codigo_prestador;
	}

	
	
	
}
