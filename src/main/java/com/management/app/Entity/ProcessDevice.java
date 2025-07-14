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
@Table(name = "process")
public class ProcessDevice implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_process")
	private Long id_Process;
	
	@Column(name = "name_process")
	private String Name_process;
	
	@Column(name = "Programming")
	private Date Programming;
	
	@Column(name = "certificate")
	private String Certificate;
	
	@Column(name = "conditions")
	private String Conditions;
	
	@Column(name = "company")
	private String Company;
	
	@Column(name = "date_process")
	private Date Date_process;
	
	@Column(name = "reception")
	private Date Reception;
	
	@Column(name = "number")
	private String Number;
	
	@Column(name = "approved")
	private String Approved;
	
	@Column(name = "performer")
	private String Performer;
	
	@Column(name = "url_ubicacion")
	private String Url_ubicacion;
	
	@JoinColumn(name ="device_fk",referencedColumnName ="id_Device")
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Device device;
	
	/********************* GET Y SET *****************************/
	
	public static long getSerialVersionUID(){
		return serialVersionUID;
	}

	public Long getId_Process() {
		return id_Process;
	}

	public void setId_Process(Long id_Process) {
		this.id_Process = id_Process;
	}

	public String getName_process() {
		return Name_process;
	}

	public void setName_process(String name_process) {
		Name_process = name_process;
	}

	public Date getProgramming() {
		return Programming;
	}

	public void setProgramming(Date programming) {
		Programming = programming;
	}

	public String getCertificate() {
		return Certificate;
	}

	public void setCertificate(String certificate) {
		Certificate = certificate;
	}

	public String getConditions() {
		return Conditions;
	}

	public void setConditions(String conditions) {
		Conditions = conditions;
	}

	public String getCompany() {
		return Company;
	}

	public void setCompany(String company) {
		Company = company;
	}

	public Date getDate_process() {
		return Date_process;
	}

	public void setDate_process(Date date_process) {
		Date_process = date_process;
	}

	public Date getReception() {
		return Reception;
	}

	public void setReception(Date reception) {
		Reception = reception;
	}

	public String getNumber() {
		return Number;
	}

	public void setNumber(String number) {
		Number = number;
	}

	public String getApproved() {
		return Approved;
	}

	public void setApproved(String approved) {
		Approved = approved;
	}

	public String getPerformer() {
		return Performer;
	}

	public void setPerformer(String performer) {
		Performer = performer;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public String getUrl_ubicacion() {
		return Url_ubicacion;
	}

	public void setUrl_ubicacion(String url_ubicacion) {
		Url_ubicacion = url_ubicacion;
	}
	
	
}
