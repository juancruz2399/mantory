package com.management.app.Entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "maintenance")
public class Maintenance  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_maintenance")
	private Long id_Maintenance;
	
	@Column(name = "fecha_realizacion")
	private Date Fecha_realizacion;
	
	@Column(name = "dias")
	private String Dias;
	
	
	@Column(name = "mes")
	private int Mes;
	
	@Column(name = "ano")
	private int Ano;
	
	@Column(name = "tiempo_realizacion")
	private Time Tiempo_realizacion;
	
	@Column(name = "realizado")
	private boolean Realizado;
	
	@Column(name = "rutina")
	private String Rutina;
	
	@Column(name ="version")
	private int Version;
	
	@JoinColumn(name ="id_type",referencedColumnName ="id_Type")
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Types types;
	
	@JoinColumn(name ="id_device_fk",referencedColumnName ="id_Device")
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Device device;
	
	@JoinColumn(name ="id_user_fk",referencedColumnName ="id_Usuario")
	@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private User user;
	
	@JoinColumn(name ="id_report_fk ",referencedColumnName ="id_Report")
	@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Report report;
	
	/********************* GET Y SET *****************************/
	
	public static long getSerialVersionUID(){
		return serialVersionUID;
	}

	public Long getId_Maintenance() {
		return id_Maintenance;
	}

	public void setId_Maintenance(Long id_Maintenance) {
		this.id_Maintenance = id_Maintenance;
	}

	public Date getFecha_realizacion() {
		return Fecha_realizacion;
	}

	public void setFecha_realizacion(Date fecha_realizacion) {
		Fecha_realizacion = fecha_realizacion;
	}

	public String getDias() {
		return Dias;
	}

	public void setDias(String dias) {
		Dias = dias;
	}

	public int getMes() {
		return Mes;
	}

	public void setMes(int mes) {
		Mes = mes;
	}

	public int getAno() {
		return Ano;
	}

	public void setAno(int ano) {
		Ano = ano;
	}

	public Time getTiempo_realizacion() {
		return Tiempo_realizacion;
	}

	public void setTiempo_realizacion(Time tiempo_realizacion) {
		Tiempo_realizacion = tiempo_realizacion;
	}

	public boolean isRealizado() {
		return Realizado;
	}

	public void setRealizado(boolean realizado) {
		Realizado = realizado;
	}

	public Types getTypes() {
		return types;
	}

	public void setTypes(Types types) {
		this.types = types;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public String getRutina() {
		return Rutina;
	}

	public void setRutina(String rutina) {
		Rutina = rutina;
	}

	public int getVersion() {
		return Version;
	}

	public void setVersion(int version) {
		Version = version;
	}
	

}
