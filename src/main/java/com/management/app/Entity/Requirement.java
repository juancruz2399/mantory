package com.management.app.Entity;

import java.io.Serializable;
import java.sql.Date;

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
@Table(name = "requeriment")
public class Requirement implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_requirement")
	private Long id_Requirement;
	
	
	@Column(name = "nombre_requerimiento")
	private String Nombre_requerimiento;
	
	@Column(name = "tipo_requerimiento")
	private int Tipo_requerimiento;
	
	@Column(name = "tipo_requerimiento_compra")
	private String Tipo_requerimiento_compra;
	
	@Column(name = "unidad_requerimiento")
	private String 	Unidad_requerimiento;
	
	@Column(name = "Cantidad")
	private double Cantidad;
	
	@Column(name = "fecha_requerimiento")
	private Date Fecha_requerimiento;
	
	@Column(name = "aprobado")
	private boolean Aprobado;
	
	@Column(name = "justificacion")
	private String Justificacion;
	
	@JoinColumn(name ="id_institution_fk",referencedColumnName ="id_Institution")
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Institution institution;
	
	@JoinColumn(name ="id_user_fk",referencedColumnName ="id_Usuario")
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private User user;

	public Long getId_Requirement() {
		return id_Requirement;
	}

	public void setId_Requirement(Long id_Requirement) {
		this.id_Requirement = id_Requirement;
	}

	public String getNombre_requerimiento() {
		return Nombre_requerimiento;
	}

	public void setNombre_requerimiento(String nombre_requerimiento) {
		Nombre_requerimiento = nombre_requerimiento;
	}

	public int getTipo_requerimiento() {
		return Tipo_requerimiento;
	}

	public void setTipo_requerimiento(int tipo_requerimiento) {
		Tipo_requerimiento = tipo_requerimiento;
	}

	public String getTipo_requerimiento_compra() {
		return Tipo_requerimiento_compra;
	}

	public void setTipo_requerimiento_compra(String tipo_requerimiento_compra) {
		Tipo_requerimiento_compra = tipo_requerimiento_compra;
	}

	public String getUnidad_requerimiento() {
		return Unidad_requerimiento;
	}

	public void setUnidad_requerimiento(String unidad_requerimiento) {
		Unidad_requerimiento = unidad_requerimiento;
	}

	public double getCantidad() {
		return Cantidad;
	}

	public void setCantidad(double cantidad) {
		Cantidad = cantidad;
	}

	public Date getFecha_requerimiento() {
		return Fecha_requerimiento;
	}

	public void setFecha_requerimiento(Date fecha_requerimiento) {
		Fecha_requerimiento = fecha_requerimiento;
	}

	public boolean isAprobado() {
		return Aprobado;
	}

	public void setAprobado(boolean aprobado) {
		Aprobado = aprobado;
	}

	public String getJustificacion() {
		return Justificacion;
	}

	public void setJustificacion(String justificacion) {
		Justificacion = justificacion;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
