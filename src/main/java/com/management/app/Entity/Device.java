package com.management.app.Entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;

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
@Table(name = "device")
public class Device implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_device")
	private Long id_Device;
	
	@Column(name = "name_device")
	private String Name_device;

	@Column(name = "marca")
	private String Marca;
	
	@Column(name = "modelo")
	private String Modelo;
	
	@Column(name = "serie")
	private String Serie;
	
	@Column(name = "inventario")
	private String Inventario;	
	
	@Column(name = "ubicacion")
	private String Ubicacion;
	
	@Column(name= "ubicacion_especifica")
	private String Ubicacion_especifica;
	
	@Column(name = "periodicidad")
	private int Periodicidad;
	
	@Column(name = "dias_mantenimiento")
	private String Dias_mantenimiento;
	
	@Column(name = "meses_mantenimiento")
	private String Meses_mantenimiento;
	
	@Column(name = "meses_procesos")
	private String Meses_procesos;
	
	@Column(name = "tipo_proceso")
	private String Tipo_proceso;
	
	@Column(name = "ano_ingreso")
	private int Ano_ingreso;
	
	@Column(name = "riesgo")
	private int Riesgo;
	
	@Column(name = "registro")
	private String Registro_invima;
	
	@Column(name = "garantia")
	private Date Garantia;
	
	@Column(name = "activo")
	private boolean Activo;
	
	@Column(name = "funcion")
	private boolean Funcion;
	
	@Column(name = "estado")
	private int Estado;
	
	@Column(name="guide")
	private String Guide;
	
	@Column(name="manual")
	private String Manual;
	
	@Column(name="foto")
	private String Foto;
	
	@Column(name = "optional_1")
	private String Optional_1;
	
	@Column(name = "optional_2")
	private String Optional_2;
	
	@JoinColumn(name ="id_type_fk",referencedColumnName ="id_Type")
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Types type;
	
	@JoinColumn(name ="id_service_fk",referencedColumnName ="id_Service")
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Services service;
	
	@JoinColumn(name = "id_institution_FK",referencedColumnName="id_Institution")
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL )
	private Institution institution;
	
	/********************* GET Y SET *****************************/
	
	public static long getSerialVersionUID(){
		return serialVersionUID;
	}

	public Long getId_Device() {
		return id_Device;
	}

	public void setId_Device(Long id_Device) {
		this.id_Device = id_Device;
	}

	public String getName_device() {
		return Name_device;
	}

	public void setName_device(String name_device) {
		Name_device = name_device;
	}

	public String getMarca() {
		return Marca;
	}

	public void setMarca(String marca) {
		Marca = marca;
	}

	public String getModelo() {
		return Modelo;
	}

	public void setModelo(String modelo) {
		Modelo = modelo;
	}

	public String getSerie() {
		return Serie;
	}

	public void setSerie(String serie) {
		Serie = serie;
	}

	public String getInventario() {
		return Inventario;
	}

	public void setInventario(String inventario) {
		Inventario = inventario;
	}

	public String getUbicacion() {
		return Ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		Ubicacion = ubicacion;
	}

	public String getUbicacion_especifica() {
		return Ubicacion_especifica;
	}

	public void setUbicacion_especifica(String ubicacion_especifica) {
		Ubicacion_especifica = ubicacion_especifica;
	}

	public int getPeriodicidad() {
		return Periodicidad;
	}

	public void setPeriodicidad(int periodicidad) {
		Periodicidad = periodicidad;
	}

	public String getDias_mantenimiento() {
		return Dias_mantenimiento;
	}

	public void setDias_mantenimiento(String dias_mantenimiento) {
		Dias_mantenimiento = dias_mantenimiento;
	}

	public String getMeses_mantenimiento() {
		return Meses_mantenimiento;
	}

	public void setMeses_mantenimiento(String meses_mantenimiento) {
		Meses_mantenimiento = meses_mantenimiento;
	}

	public int getAno_ingreso() {
		return Ano_ingreso;
	}

	public void setAno_ingreso(int ano_ingreso) {
		Ano_ingreso = ano_ingreso;
	}

	public int getRiesgo() {
		return Riesgo;
	}

	public void setRiesgo(int riesgo) {
		Riesgo = riesgo;
	}

	public String getRegistro_invima() {
		return Registro_invima;
	}

	public void setRegistro_invima(String registro_invima) {
		Registro_invima = registro_invima;
	}

	public Date getGarantia() {
		return Garantia;
	}

	public void setGarantia(Date garantia) {
		Garantia = garantia;
	}

	public boolean isActivo() {
		return Activo;
	}

	public void setActivo(boolean activo) {
		Activo = activo;
	}

	public boolean isFuncion() {
		return Funcion;
	}

	public void setFuncion(boolean funcion) {
		Funcion = funcion;
	}

	public int getEstado() {
		return Estado;
	}

	public void setEstado(int estado) {
		Estado = estado;
	}

	public Types getType() {
		return type;
	}

	public void setType(Types type) {
		this.type = type;
	}

	public Services getService() {
		return service;
	}

	public void setService(Services service) {
		this.service = service;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public String getFoto() {
		return Foto;
	}

	public void setFoto(String foto) {
		Foto = foto;
	}
	/********************* GET Y SET *****************************/
	
	public ArrayList<String> divisiondias(String diasmantenimiento,int Periodicidad){
		String[] splitdias = null;
		if (diasmantenimiento==null) {
			if(Periodicidad == 4) {
				diasmantenimiento = "1-5,1-5,1-5";
				splitdias = diasmantenimiento.split(",");
			}
			else if(Periodicidad ==3) {
				diasmantenimiento = "1-5,1-5,1-5,1-5";
				splitdias = diasmantenimiento.split(",");
			}
			else if(Periodicidad==2) {
				diasmantenimiento = "1-5,1-5";
				splitdias = diasmantenimiento.split(",");
			}
			else if(Periodicidad ==1) {
				diasmantenimiento = "1-5";
				splitdias = diasmantenimiento.split(",");
			}
			else {
				diasmantenimiento = "1-5";
				splitdias = diasmantenimiento.split(",");
			}
			
		}
		else {
			splitdias = diasmantenimiento.split(",");
		}
		ArrayList<String> listdias = new ArrayList<String>(Arrays.asList(splitdias));
		
		return listdias;
	}
	public ArrayList<String> divisionmeses(String mesesmantenimiento,int Periodicidad) {
		String[] splitmeses = null;
		if (mesesmantenimiento==null) {
			if(Periodicidad==4) {
				mesesmantenimiento = "MARZO,JULIO,NOVIEMBRE";
				splitmeses = mesesmantenimiento.split(",");
			}
			else if(Periodicidad==3) {
				mesesmantenimiento = "FEBRERO,MAYO,AGOSTO,NOVIEMBRE";
				splitmeses = mesesmantenimiento.split(",");
			}
			else if(Periodicidad==2) {
				
				mesesmantenimiento = "ABRIL,OCTUBRE";
				splitmeses = mesesmantenimiento.split(",");
							
			}
			else if(Periodicidad==1) {
				mesesmantenimiento = "ENERO";
				splitmeses = mesesmantenimiento.split(",");
				
			}
			else {
				mesesmantenimiento = "ENERO";
				splitmeses = mesesmantenimiento.split(",");
				
			}
			
		}
		else {
			splitmeses = mesesmantenimiento.split(",");
		}
		ArrayList<String> listmeses = new ArrayList<String>(Arrays.asList(splitmeses));
		return listmeses;
	}
	public ArrayList<String> concaten(String diasmantenimiento,String mesesmantenimiento, int Periodicidad) {
		
		ArrayList<String> tabmeses = divisionmeses(mesesmantenimiento,Periodicidad);
		ArrayList<String> diasmeses = divisiondias(diasmantenimiento,Periodicidad);
		ArrayList<String> fechas = new ArrayList<String>();
		for(int indice=0;indice<tabmeses.size();indice++) {			
			fechas.add(tabmeses.get(indice)+':'+diasmeses.get(indice));			
		}
		return fechas;
	}
	
	public ArrayList<Integer> detectarmes_semana(ArrayList<String> fechas,int mes) {
		String Mes = null;
		if( mes==1) {
			Mes = "ENERO";
		}
		else if(mes==2) {
			Mes = "FEBRERO";
		}
		else if(mes==3) {
			Mes = "MARZO";	
		}
		else if(mes==4) {
			Mes = "ABRIL";
		}
		else if(mes==5) {
			Mes = "MAYO";
		}
		else if(mes==6) {
			Mes = "JUNIO";
		}
		
		else if(mes==7) {
			Mes = "JULIO";
		}
		else if(mes==8) {
			Mes = "AGOSTO";
		}
		else if(mes==9) {
			Mes = "SEPTIEMBRE";
		}
		else if(mes==10) {
			Mes = "OCTUBRE";
		}
		else if(mes==11) {
			Mes = "NOVIEMBRE";	
		}
		else if(mes==12) {
			Mes = "DICIEMBRE";
		}
		
		String[] dias = null;
		for(int indice=0;indice<fechas.size();indice++) {
			
			if(fechas.get(indice).contains(Mes)) {
				dias = fechas.get(indice).split(":");
				
			}
		}
		ArrayList<String> diasmtto = new ArrayList<String>(Arrays.asList(dias));
		String diastow = diasmtto.get(1);
		String[] diasseparate = diastow.split("-");
		ArrayList<String> diasitof = new ArrayList<String>(Arrays.asList(diasseparate));
		int diainicial = 1;
		int diafinal  = 30;
		if (diasitof.size()>1) {
			String diai = diasitof.get(0).trim();
			diainicial = Integer.parseInt(diai);
			String diaf = diasitof.get(1).trim();
			diafinal = Integer.parseInt(diaf);
		}
		else {
			String diai = diasitof.get(0).trim();
			diainicial = Integer.parseInt(diai);
			diafinal = 30;
		}
		
		ArrayList<Integer> diasinf = new ArrayList<Integer>();
		
		diasinf.add(diainicial);
		diasinf.add(diafinal);
		return diasinf;
		
		
	}

	public String getGuide() {
		return Guide;
	}

	public void setGuide(String guide) {
		Guide = guide;
	}

	public String getManual() {
		return Manual;
	}

	public void setManual(String manual) {
		Manual = manual;
	}

	public String getMeses_procesos() {
		return Meses_procesos;
	}

	public void setMeses_procesos(String meses_procesos) {
		Meses_procesos = meses_procesos;
	}

	public String getTipo_proceso() {
		return Tipo_proceso;
	}

	public void setTipo_proceso(String tipo_proceso) {
		Tipo_proceso = tipo_proceso;
	}

	public String getOptional_1() {
		return Optional_1;
	}

	public void setOptional_1(String optional_1) {
		Optional_1 = optional_1;
	}

	public String getOptional_2() {
		return Optional_2;
	}

	public void setOptional_2(String optional_2) {
		Optional_2 = optional_2;
	}
	
	
}
