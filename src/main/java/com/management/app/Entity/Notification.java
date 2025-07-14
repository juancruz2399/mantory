package com.management.app.Entity;

import java.io.Serializable;
import java.sql.Timestamp;

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
@Table(name = "notification")
public class Notification implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_notification")
	private Long id_Notification;
	
	@Column(name = "datecall")
	private Timestamp Datecall;
	
	@Column(name = "palabras_clave")
	private String Palabras_clave;
	
	@Column(name = "classification")
	private int Area;
	
	@Column(name = "priority")
	private int Priority;
	
	@Column(name = "description")
	private String Description;
	
	@Column(name = "caller")
	private String Caller;//Who realize call
	
	@Column(name = "attendance")
	private Timestamp Attendance;
	
	@Column(name = "photo")
	private String Photo;
	
	@Column(name = "datesolution")
	private Timestamp Datesolution;
	
	@Column(name = "solution")
	private String Solution;
	
	@Column(name = "performance")
	private int Performance;
	
	@Column(name = "state")
	private int State;
	
	@JoinColumn(name ="types_fk",referencedColumnName ="id_Type")
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Types types;
	
	@JoinColumn(name ="service_fk",referencedColumnName ="id_Service")
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Services services;
	
	@JoinColumn(name ="device_fk",referencedColumnName ="id_Device")
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Device device;
	
	@JoinColumn(name ="usercall_fk",referencedColumnName ="id_Usuario")
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private User usercall;
	
	@JoinColumn(name ="userrta_fk",referencedColumnName ="id_Usuario")
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private User userrta;
	
	/********************* GET Y SET *****************************/
	
	public static long getSerialVersionUID(){
		return serialVersionUID;
	}

	public Long getId_Notification() {
		return id_Notification;
	}

	public void setId_Notification(Long id_Notification) {
		this.id_Notification = id_Notification;
	}

	public Timestamp getDatecall() {
		return Datecall;
	}

	public void setDatecall(Timestamp datecall) {
		Datecall = datecall;
	}

	public String getPalabras_clave() {
		return Palabras_clave;
	}

	public void setPalabras_clave(String palabras_clave) {
		Palabras_clave = palabras_clave;
	}

	public int getArea() {
		return Area;
	}

	public void setArea(int area) {
		Area = area;
	}

	public int getPriority() {
		return Priority;
	}

	public void setPriority(int priority) {
		Priority = priority;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getCaller() {
		return Caller;
	}

	public void setCaller(String caller) {
		Caller = caller;
	}

	public Timestamp getAttendance() {
		return Attendance;
	}

	public void setAttendance(Timestamp attendance) {
		Attendance = attendance;
	}

	public String getPhoto() {
		return Photo;
	}

	public void setPhoto(String photo) {
		Photo = photo;
	}

	public Timestamp getDatesolution() {
		return Datesolution;
	}

	public void setDatesolution(Timestamp datesolution) {
		Datesolution = datesolution;
	}

	public String getSolution() {
		return Solution;
	}

	public void setSolution(String solution) {
		Solution = solution;
	}

	public int getPerformance() {
		return Performance;
	}

	public void setPerformance(int performance) {
		Performance = performance;
	}

	public int getState() {
		return State;
	}

	public void setState(int state) {
		State = state;
	}

	public Types getTypes() {
		return types;
	}

	public void setTypes(Types types) {
		this.types = types;
	}

	public Services getServices() {
		return services;
	}

	public void setServices(Services services) {
		this.services = services;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public User getUsercall() {
		return usercall;
	}

	public void setUsercall(User usercall) {
		this.usercall = usercall;
	}

	public User getUserrta() {
		return userrta;
	}

	public void setUserrta(User userrta) {
		this.userrta = userrta;
	}
	
	
}
