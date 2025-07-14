package com.management.app.Entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "provider")
public class Provider implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_provider")
	private Long id_Provider;
	

	@Column(name = "nombre_proveedor")
	private String Nombre_proveedor;
	
	@Column(name = "tipo_proveedor")
	private String Tipo_proveedor;
	
	@Column(name = "representante_proveedor")
	private String Representante_proveedor;
	
	@Column(name = "celular_proveedor")
	private String Celular_proveedor;
	
	@Column(name = "telefono_proveedor")
	private String Telefono_proveedor;
	
	@Column(name = "correo_proveedor")
	private String Correo_proveedor;
	
	@Column(name = "numero_cuenta")
	private int Numero_cuenta;
	
	@Column(name = "banco")
	private String Banco;
	
	@Column(name = "tipo_cuenta")
	private String Cuenta;
	
	@Column(name = "cedula_titular")
	private int Cedula_titular;
	
	@Column(name = "nombre_titular")
	private String Nombre_titular;
	
	@Column(name = "nit_proveedor")
	private String Nit_proveedor;
	
	@Column(name = "anticipo")
	private boolean Anticipo;
	
	@Column(name = "puntuacion")
	private int Puntuacion;
	
	@Column(name = "departamento")
	private String Departamento;
	
	@Column(name = "municipio")
	private String Municipio;
	
	@Column(name = "condiciones_pago")
	private String Condiciones_pago;
	
	
	/********************* GET Y SET *****************************/
	
	public static long getSerialVersionUID(){
		return serialVersionUID;
	}


	public Long getId_Provider() {
		return id_Provider;
	}


	public void setId_Provider(Long id_Provider) {
		this.id_Provider = id_Provider;
	}


	public String getNombre_proveedor() {
		return Nombre_proveedor;
	}


	public void setNombre_proveedor(String nombre_proveedor) {
		Nombre_proveedor = nombre_proveedor;
	}


	public String getTipo_proveedor() {
		return Tipo_proveedor;
	}


	public void setTipo_proveedor(String tipo_proveedor) {
		Tipo_proveedor = tipo_proveedor;
	}


	public String getRepresentante_proveedor() {
		return Representante_proveedor;
	}


	public void setRepresentante_proveedor(String representante_proveedor) {
		Representante_proveedor = representante_proveedor;
	}


	public String getCelular_proveedor() {
		return Celular_proveedor;
	}


	public void setCelular_proveedor(String celular_proveedor) {
		Celular_proveedor = celular_proveedor;
	}


	public String getTelefono_proveedor() {
		return Telefono_proveedor;
	}


	public void setTelefono_proveedor(String telefono_proveedor) {
		Telefono_proveedor = telefono_proveedor;
	}


	public String getCorreo_proveedor() {
		return Correo_proveedor;
	}


	public void setCorreo_proveedor(String correo_proveedor) {
		Correo_proveedor = correo_proveedor;
	}


	public int getNumero_cuenta() {
		return Numero_cuenta;
	}


	public void setNumero_cuenta(int numero_cuenta) {
		Numero_cuenta = numero_cuenta;
	}


	public String getBanco() {
		return Banco;
	}


	public void setBanco(String banco) {
		Banco = banco;
	}


	public String getCuenta() {
		return Cuenta;
	}


	public void setCuenta(String cuenta) {
		Cuenta = cuenta;
	}


	public int getCedula_titular() {
		return Cedula_titular;
	}


	public void setCedula_titular(int cedula_titular) {
		Cedula_titular = cedula_titular;
	}


	public String getNombre_titular() {
		return Nombre_titular;
	}


	public void setNombre_titular(String nombre_titular) {
		Nombre_titular = nombre_titular;
	}


	public String getNit_proveedor() {
		return Nit_proveedor;
	}


	public void setNit_proveedor(String nit_proveedor) {
		Nit_proveedor = nit_proveedor;
	}


	public boolean isAnticipo() {
		return Anticipo;
	}


	public void setAnticipo(boolean anticipo) {
		Anticipo = anticipo;
	}


	public int getPuntuacion() {
		return Puntuacion;
	}


	public void setPuntuacion(int puntuacion) {
		Puntuacion = puntuacion;
	}


	public String getDepartamento() {
		return Departamento;
	}


	public void setDepartamento(String departamento) {
		Departamento = departamento;
	}


	public String getMunicipio() {
		return Municipio;
	}


	public void setMunicipio(String municipio) {
		Municipio = municipio;
	}


	public String getCondiciones_pago() {
		return Condiciones_pago;
	}


	public void setCondiciones_pago(String condiciones_pago) {
		Condiciones_pago = condiciones_pago;
	}
	
}
