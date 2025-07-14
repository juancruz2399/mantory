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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "resumes")
public class Resume  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_resumes")
	private Long id_Resumes;
	
	@Column(name = "codinternacional")
	private String Codinternacional;
	
	@Column(name="emailinstitucion")
	private String Emailinstitucion;
	
	@Column(name="contrato")
	private String Contrato;
	
	@Column(name = "modo_compra")
	private int Modo_compra;
	
	@Column(name = "fecha_compra")
    private Date Fecha_compra;
	
	@Column(name = "fecha_instalacion")
    private Date Fecha_instalacion;
	
	@Column(name = "fecha_iniciooperacion")
    private Date Fecha_iniciooperacion;
	
	@Column(name = "fecha_vencimientogarantia")
    private Date Fecha_vencimientogarantia;
	
	@Column(name = "fecha_fabricacion")
	private Date Fecha_fabricacion;
	
	@Column(name = "descripcion")
	private String Descripcion;
	
	@Column(name = "recomendacion")
	private String Recomendacion;
	
	@Column(name = "observacion")
	private String Observacion;
	
	@Column(name="costo_compra")
	private String Costo_compra;
	
	@Column(name="fabricante")
	private String Fabricante;
	
	@Column(name="paisfabricante")
	private String Paisfabricante;
	
	@Column(name="proveedor")
	private String Proveedor;
	
	@Column(name="telefonoproveedor")
	private String Telefonoproveedor;
	
	@Column(name = "correoproveedor")
	private String Correoproveedor;
	
	@Column(name="ciudadproveedor")
	private String Ciudadproveedor;
	
	@Column(name="representante")
	private String Representante;
	
	@Column(name="telefonorepresentante")
	private String Telefonorepresentante;
	
	@Column(name="vmaxoperacion")
	private String Vmaxoperacion;
	
	@Column(name = "vminoperacion")
	private String Vminoperacion;
	
	@Column(name = "imaxoperacion")
	private String Imaxoperacion;
	
	@Column(name = "iminoperacion")
	private String Iminoperacion;
	
	@Column(name="wconsumida")
	private String Wconsumida;
	
	@Column(name="frecuencia")
	private String Frecuencia;
	
	@Column(name="presion")
	private String Presion;
	
	@Column(name="velocidad")
	private String Velocidad;
	
	@Column(name="temperatura")
	private String Temperatura;
	
	@Column(name="peso")
	private String Peso;
	
	@Column(name="capacidad")
	private String Capacidad;
	
	@Column(name="otrosdatostecnicos")
	private String Otrosdatostecnicos;
	
	@Column(name = "fuente_energia")
	private int Fuente_energia;
	
	@Column(name="equipotipofijo")
	private boolean Equipotipofijo;
	
	@Column(name = "uso")
	private int Uso;
	
	@Column(name = "clase_equipo")
	private int Clase_equipo;
	
	@Column(name = "clase_biomedica")
	private int Clase_biomedica;
	
	@Column(name="requierecalibracion")
	private boolean Requierecalibracion;
	
	@Column(name="calval")
	private boolean calval;
	
	@Column(name ="accesorio1")
	private String Accesorio1;
	
	@Column(name="accesorio2")
	private String Accesorio2;
	
	@Column(name="accesorio3")
	private String Accesorio3;
	
	@Column(name="accesorio4")
	private String Accesorio4;
	
	@JoinColumn(name ="id_device_fk ",referencedColumnName ="id_Device")
	@OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Device device;
	
	/********************* GET Y SET *****************************/
	
	public static long getSerialVersionUID(){
		return serialVersionUID;
	}

	public Long getId_Resumes() {
		return id_Resumes;
	}

	public void setId_Resumes(Long id_Resumes) {
		this.id_Resumes = id_Resumes;
	}

	public String getCodinternacional() {
		return Codinternacional;
	}

	public void setCodinternacional(String codinternacional) {
		Codinternacional = codinternacional;
	}

	public String getEmailinstitucion() {
		return Emailinstitucion;
	}

	public void setEmailinstitucion(String emailinstitucion) {
		Emailinstitucion = emailinstitucion;
	}

	public String getContrato() {
		return Contrato;
	}

	public void setContrato(String contrato) {
		Contrato = contrato;
	}

	public int getModo_compra() {
		return Modo_compra;
	}

	public void setModo_compra(int modo_compra) {
		Modo_compra = modo_compra;
	}

	public Date getFecha_compra() {
		return Fecha_compra;
	}

	public void setFecha_compra(Date fecha_compra) {
		Fecha_compra = fecha_compra;
	}

	public Date getFecha_instalacion() {
		return Fecha_instalacion;
	}

	public void setFecha_instalacion(Date fecha_instalacion) {
		Fecha_instalacion = fecha_instalacion;
	}

	public Date getFecha_iniciooperacion() {
		return Fecha_iniciooperacion;
	}

	public void setFecha_iniciooperacion(Date fecha_iniciooperacion) {
		Fecha_iniciooperacion = fecha_iniciooperacion;
	}

	public Date getFecha_vencimientogarantia() {
		return Fecha_vencimientogarantia;
	}

	public void setFecha_vencimientogarantia(Date fecha_vencimientogarantia) {
		Fecha_vencimientogarantia = fecha_vencimientogarantia;
	}

	public String getCosto_compra() {
		return Costo_compra;
	}

	public void setCosto_compra(String costo_compra) {
		Costo_compra = costo_compra;
	}

	public String getFabricante() {
		return Fabricante;
	}

	public void setFabricante(String fabricante) {
		Fabricante = fabricante;
	}

	public String getPaisfabricante() {
		return Paisfabricante;
	}

	public void setPaisfabricante(String paisfabricante) {
		Paisfabricante = paisfabricante;
	}

	public String getProveedor() {
		return Proveedor;
	}

	public void setProveedor(String proveedor) {
		Proveedor = proveedor;
	}

	public String getTelefonoproveedor() {
		return Telefonoproveedor;
	}

	public void setTelefonoproveedor(String telefonoproveedor) {
		Telefonoproveedor = telefonoproveedor;
	}

	public String getCorreoproveedor() {
		return Correoproveedor;
	}

	public void setCorreoproveedor(String correoproveedor) {
		Correoproveedor = correoproveedor;
	}

	public String getCiudadproveedor() {
		return Ciudadproveedor;
	}

	public void setCiudadproveedor(String ciudadproveedor) {
		Ciudadproveedor = ciudadproveedor;
	}

	public String getRepresentante() {
		return Representante;
	}

	public void setRepresentante(String representante) {
		Representante = representante;
	}

	public String getTelefonorepresentante() {
		return Telefonorepresentante;
	}

	public void setTelefonorepresentante(String telefonorepresentante) {
		Telefonorepresentante = telefonorepresentante;
	}

	public String getVmaxoperacion() {
		return Vmaxoperacion;
	}

	public void setVmaxoperacion(String vmaxoperacion) {
		Vmaxoperacion = vmaxoperacion;
	}

	public String getVminoperacion() {
		return Vminoperacion;
	}

	public void setVminoperacion(String vminoperacion) {
		Vminoperacion = vminoperacion;
	}

	public String getImaxoperacion() {
		return Imaxoperacion;
	}

	public void setImaxoperacion(String imaxoperacion) {
		Imaxoperacion = imaxoperacion;
	}

	public String getIminoperacion() {
		return Iminoperacion;
	}

	public void setIminoperacion(String iminoperacion) {
		Iminoperacion = iminoperacion;
	}

	public String getWconsumida() {
		return Wconsumida;
	}

	public void setWconsumida(String wconsumida) {
		Wconsumida = wconsumida;
	}

	public String getFrecuencia() {
		return Frecuencia;
	}

	public void setFrecuencia(String frecuencia) {
		Frecuencia = frecuencia;
	}

	public String getPresion() {
		return Presion;
	}

	public void setPresion(String presion) {
		Presion = presion;
	}

	public String getVelocidad() {
		return Velocidad;
	}

	public void setVelocidad(String velocidad) {
		Velocidad = velocidad;
	}

	public String getTemperatura() {
		return Temperatura;
	}

	public void setTemperatura(String temperatura) {
		Temperatura = temperatura;
	}

	public String getPeso() {
		return Peso;
	}

	public void setPeso(String peso) {
		Peso = peso;
	}

	public String getCapacidad() {
		return Capacidad;
	}

	public void setCapacidad(String capacidad) {
		Capacidad = capacidad;
	}

	public String getOtrosdatostecnicos() {
		return Otrosdatostecnicos;
	}

	public void setOtrosdatostecnicos(String otrosdatostecnicos) {
		Otrosdatostecnicos = otrosdatostecnicos;
	}

	public int getFuente_energia() {
		return Fuente_energia;
	}

	public void setFuente_energia(int fuente_energia) {
		Fuente_energia = fuente_energia;
	}

	public boolean isEquipotipofijo() {
		return Equipotipofijo;
	}

	public void setEquipotipofijo(boolean equipotipofijo) {
		Equipotipofijo = equipotipofijo;
	}

	public int getUso() {
		return Uso;
	}

	public void setUso(int uso) {
		Uso = uso;
	}

	public int getClase_equipo() {
		return Clase_equipo;
	}

	public void setClase_equipo(int clase_equipo) {
		Clase_equipo = clase_equipo;
	}

	public int getClase_biomedica() {
		return Clase_biomedica;
	}

	public void setClase_biomedica(int clase_biomedica) {
		Clase_biomedica = clase_biomedica;
	}

	public boolean isRequierecalibracion() {
		return Requierecalibracion;
	}

	public void setRequierecalibracion(boolean requierecalibracion) {
		Requierecalibracion = requierecalibracion;
	}

	public boolean isCalval() {
		return calval;
	}

	public void setCalval(boolean calval) {
		this.calval = calval;
	}

	public String getAccesorio1() {
		return Accesorio1;
	}

	public void setAccesorio1(String accesorio1) {
		Accesorio1 = accesorio1;
	}

	public String getAccesorio2() {
		return Accesorio2;
	}

	public void setAccesorio2(String accesorio2) {
		Accesorio2 = accesorio2;
	}

	public String getAccesorio3() {
		return Accesorio3;
	}

	public void setAccesorio3(String accesorio3) {
		Accesorio3 = accesorio3;
	}

	public String getAccesorio4() {
		return Accesorio4;
	}

	public void setAccesorio4(String accesorio4) {
		Accesorio4 = accesorio4;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public String getRecomendacion() {
		return Recomendacion;
	}

	public void setRecomendacion(String recomendacion) {
		Recomendacion = recomendacion;
	}

	public String getObservacion() {
		return Observacion;
	}

	public void setObservacion(String observacion) {
		Observacion = observacion;
	}

	public Date getFecha_fabricacion() {
		return Fecha_fabricacion;
	}

	public void setFecha_fabricacion(Date fecha_fabricacion) {
		Fecha_fabricacion = fecha_fabricacion;
	}
	
	
	
}
