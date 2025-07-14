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
@Table(name = "quoteorder")
public class QuoteOrder  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_quote")
	private Long id_Quote;
	
	@Column(name = "numero_cotizacion")
	private String Numero_cotizacion;
	
	@Column(name = "fecha_cotizacion")
	private Date Fecha_cotizacion; 
	
	@Column(name = "autorizacion_envio")
	private boolean Autorizacion_envio;
	
	@Column(name = "cumple_especificacion")
	private boolean Cumple_especificacion;
	
	@Column(name = "valor_unitario")
	private double Valor_unitario;
	
	@Column(name = "valor")
	private double Valor;
	
	@Column(name = "iva")
	private double Iva;
	
	@Column(name = "valor_presupuestado")
	private double Valor_presupuestado;
	
	@Column(name = "vigencia_cotizacion")
	private int vigencia_cotizacion;
	
	@Column(name = "plazo_entrega")
	private int Plazo_entrega;
	
	@Column(name = "flete")
	private boolean Flete;
	
	@Column(name = "condiciones_pago")
	private String Condiciones_pago;
	
	@Column(name = "anticipo")
	private double Anticipo;
	
	@Column(name ="descuento")
	private double Descuento;
	
	@Column(name = "observacion")
	private String Observacion;
	
	@Column(name = "articulo")
	private String Articulo;
	
	@Column(name = "cantidad")
	private double Cantidad;
	
	@Column(name = "ruta_cotizacion")
	private String Ruta_cotizacion;
	
	@JoinColumn(name ="id_institution_fk",referencedColumnName ="id_Institution")
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Institution institution;
	
	@JoinColumn(name ="id_provider_fk",referencedColumnName ="id_Provider")
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Provider provider;
	
	@JoinColumn(name ="id_requirement_fk",referencedColumnName ="id_Requirement")
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Requirement requirement;

	public static long getSerialVersionUID(){
		return serialVersionUID;
	}
	
	public Long getId_Quote() {
		return id_Quote;
	}

	public void setId_Quote(Long id_Quote) {
		this.id_Quote = id_Quote;
	}

	public String getNumero_cotizacion() {
		return Numero_cotizacion;
	}

	public void setNumero_cotizacion(String numero_cotizacion) {
		Numero_cotizacion = numero_cotizacion;
	}

	public Date getFecha_cotizacion() {
		return Fecha_cotizacion;
	}

	public void setFecha_cotizacion(Date fecha_cotizacion) {
		Fecha_cotizacion = fecha_cotizacion;
	}

	public boolean isAutorizacion_envio() {
		return Autorizacion_envio;
	}

	public void setAutorizacion_envio(boolean autorizacion_envio) {
		Autorizacion_envio = autorizacion_envio;
	}

	public boolean isCumple_especificacion() {
		return Cumple_especificacion;
	}

	public void setCumple_especificacion(boolean cumple_especificacion) {
		Cumple_especificacion = cumple_especificacion;
	}

	public double getValor_unitario() {
		return Valor_unitario;
	}

	public void setValor_unitario(double valor_unitario) {
		Valor_unitario = valor_unitario;
	}

	public double getValor() {
		return Valor;
	}

	public void setValor(double valor) {
		Valor = valor;
	}

	public double getIva() {
		return Iva;
	}

	public void setIva(double iva) {
		Iva = iva;
	}

	public double getValor_presupuestado() {
		return Valor_presupuestado;
	}

	public void setValor_presupuestado(double valor_presupuestado) {
		Valor_presupuestado = valor_presupuestado;
	}

	public int getVigencia_cotizacion() {
		return vigencia_cotizacion;
	}

	public void setVigencia_cotizacion(int vigencia_cotizacion) {
		this.vigencia_cotizacion = vigencia_cotizacion;
	}

	public int getPlazo_entrega() {
		return Plazo_entrega;
	}

	public void setPlazo_entrega(int plazo_entrega) {
		Plazo_entrega = plazo_entrega;
	}

	public boolean isFlete() {
		return Flete;
	}

	public void setFlete(boolean flete) {
		Flete = flete;
	}

	public String getCondiciones_pago() {
		return Condiciones_pago;
	}

	public void setCondiciones_pago(String condiciones_pago) {
		Condiciones_pago = condiciones_pago;
	}

	public double getAnticipo() {
		return Anticipo;
	}

	public void setAnticipo(double anticipo) {
		Anticipo = anticipo;
	}

	public double getDescuento() {
		return Descuento;
	}

	public void setDescuento(double descuento) {
		Descuento = descuento;
	}

	public String getObservacion() {
		return Observacion;
	}

	public void setObservacion(String observacion) {
		Observacion = observacion;
	}

	public String getArticulo() {
		return Articulo;
	}

	public void setArticulo(String articulo) {
		Articulo = articulo;
	}

	public double getCantidad() {
		return Cantidad;
	}

	public void setCantidad(double cantidad) {
		Cantidad = cantidad;
	}

	public String getRuta_cotizacion() {
		return Ruta_cotizacion;
	}

	public void setRuta_cotizacion(String ruta_cotizacion) {
		Ruta_cotizacion = ruta_cotizacion;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public Requirement getRequirement() {
		return requirement;
	}

	public void setRequirement(Requirement requirement) {
		this.requirement = requirement;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
}
