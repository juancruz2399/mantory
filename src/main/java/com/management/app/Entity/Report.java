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
import javax.persistence.Table;



@Entity
@Table(name = "report")
public class Report  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_report")
	private Long id_Report;
	
	@Column(name = "servicio")
	private String Servicio;
	
	@Column(name = "ubicacion")
	private String Ubicacion;

	@Column(name = "fecha")    
    private Date Fecha;
    
    @Column(name = "hora_llamado")
    private Time Hora_llamado;
    
    @Column(name = "hora_inicio")
    private Time Hora_inicio;
    
    @Column(name = "hora_terminacion")
    private Time Hora_terminacion;

    @Column(name = "total_horas")
    private Time Total_horas;
    
    @Column(name = "tipo_mantenimiento")
    private int Tipo_mantenimiento;
    
    @Column(name = "tipo_falla")
    private String Tipo_falla;
    
    @Column(name = "motivo")
    private String Motivo;
    
    @Column(name = "trabajo_realizado")
    private String Trabajo_realizado;
    
    @Column(name = "repuesto")
    private String Repuesto_cambiado;
    
    @Column(name = "cantidad_repuesto")
    private String Cantidad_repuesto;
    
    @Column(name = "responsable")
    private String Responsable;
    
    @Column(name = "autor_recibido")
    private String Autor_recibido;
    
    @Column(name = "observaciones")
    private String Observaciones;
    
    @Column(name = "tiempo_fuera_servicio")
    private int Tiempo_fuera_servicio;
    
    @Column(name = "rutapdf")
    private String Rutapdf;
    
    @Column(name = "rutapruebas")
    private String Rutapruebas;
    
    @Column(name = "observacionpruebas")
    private String ObservacionPruebas;
    
    @JoinColumn(name ="id_device_fk",referencedColumnName ="id_Device")
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Device device;

    /********************* GET Y SET *****************************/
	
	public static long getSerialVersionUID(){
		return serialVersionUID;
	}

	public Long getId_Report() {
		return id_Report;
	}

	public void setId_Report(Long id_Report) {
		this.id_Report = id_Report;
	}

	public String getServicio() {
		return Servicio;
	}

	public void setServicio(String servicio) {
		Servicio = servicio;
	}

	public String getUbicacion() {
		return Ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		Ubicacion = ubicacion;
	}

	public Date getFecha() {
		return Fecha;
	}

	public void setFecha(Date fecha) {
		Fecha = fecha;
	}

	public Time getHora_llamado() {
		return Hora_llamado;
	}

	public void setHora_llamado(Time hora_llamado) {
		Hora_llamado = hora_llamado;
	}

	public Time getHora_inicio() {
		return Hora_inicio;
	}

	public void setHora_inicio(Time hora_inicio) {
		Hora_inicio = hora_inicio;
	}

	public Time getHora_terminacion() {
		return Hora_terminacion;
	}

	public void setHora_terminacion(Time hora_terminacion) {
		Hora_terminacion = hora_terminacion;
	}

	public Time getTotal_horas() {
		return Total_horas;
	}

	public void setTotal_horas(Time total_horas) {
		Total_horas = total_horas;
	}

	public int getTipo_mantenimiento() {
		return Tipo_mantenimiento;
	}

	public void setTipo_mantenimiento(int tipo_mantenimiento) {
		Tipo_mantenimiento = tipo_mantenimiento;
	}

	public String getTipo_falla() {
		return Tipo_falla;
	}

	public void setTipo_falla(String tipo_falla) {
		Tipo_falla = tipo_falla;
	}

	public String getTrabajo_realizado() {
		return Trabajo_realizado;
	}

	public void setTrabajo_realizado(String trabajo_realizado) {
		Trabajo_realizado = trabajo_realizado;
	}

	public String getRepuesto_cambiado() {
		return Repuesto_cambiado;
	}

	public void setRepuesto_cambiado(String repuesto_cambiado) {
		Repuesto_cambiado = repuesto_cambiado;
	}

	public String getResponsable() {
		return Responsable;
	}

	public void setResponsable(String responsable) {
		Responsable = responsable;
	}

	public String getAutor_recibido() {
		return Autor_recibido;
	}

	public void setAutor_recibido(String autor_recibido) {
		Autor_recibido = autor_recibido;
	}

	public String getObservaciones() {
		return Observaciones;
	}

	public void setObservaciones(String observaciones) {
		Observaciones = observaciones;
	}

	public int getTiempo_fuera_servicio() {
		return Tiempo_fuera_servicio;
	}

	public void setTiempo_fuera_servicio(int tiempo_fuera_servicio) {
		Tiempo_fuera_servicio = tiempo_fuera_servicio;
	}

	public String getRutapdf() {
		return Rutapdf;
	}

	public void setRutapdf(String rutapdf) {
		Rutapdf = rutapdf;
	}


	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public String getRutapruebas() {
		return Rutapruebas;
	}

	public void setRutapruebas(String rutapruebas) {
		Rutapruebas = rutapruebas;
	}

	public String getMotivo() {
		return Motivo;
	}

	public void setMotivo(String motivo) {
		Motivo = motivo;
	}

	public String getCantidad_repuesto() {
		return Cantidad_repuesto;
	}

	public void setCantidad_repuesto(String cantidad_repuesto) {
		Cantidad_repuesto = cantidad_repuesto;
	}

	public String getObservacionPruebas() {
		return ObservacionPruebas;
	}

	public void setObservacionPruebas(String observacionPruebas) {
		ObservacionPruebas = observacionPruebas;
	}
	
	
	
	
}
