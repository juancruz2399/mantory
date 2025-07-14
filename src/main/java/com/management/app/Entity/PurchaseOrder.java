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
@Table(name = "purchase")
public class PurchaseOrder implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_purchase")
	private Long id_Purchase;
	
	@Column(name = "fecha_orden_compra")
	private Date Fecha_orden_compra;
	
	@Column(name = "numero_orden")
	private int Numero_orden;
	
	@Column(name = "valor_flete")
	private double Valor_flete;
	
	@Column(name = "cantidad")
	private double Cantidad;
	
	@Column(name = "valor_total")
	private double Valor_total;
	
	@Column(name = "anticipo")
	private double Anticipo;
	
	@Column(name = "numero_factura")
	private int Numero_factura;
	
	@Column(name = "cantidad_recibida")
	private double Cantidad_recibida;
	
	@JoinColumn(name ="id_provider_fk",referencedColumnName ="id_Provider")
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Provider provider;
	
	@JoinColumn(name ="id_institution_fk",referencedColumnName ="id_Institution")
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Institution institution;
	
	@JoinColumn(name ="id_quote_fk",referencedColumnName ="id_Quote")
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private QuoteOrder quoteOrder;
	
	/********************* GET Y SET *****************************/
	
	public static long getSerialVersionUID(){
		return serialVersionUID;
		
		
	}

	public Long getId_Purchase() {
		return id_Purchase;
	}

	public void setId_Purchase(Long id_Purchase) {
		this.id_Purchase = id_Purchase;
	}

	public Date getFecha_orden_compra() {
		return Fecha_orden_compra;
	}

	public void setFecha_orden_compra(Date fecha_orden_compra) {
		Fecha_orden_compra = fecha_orden_compra;
	}

	public int getNumero_orden() {
		return Numero_orden;
	}

	public void setNumero_orden(int numero_orden) {
		Numero_orden = numero_orden;
	}

	public double getValor_flete() {
		return Valor_flete;
	}

	public void setValor_flete(double valor_flete) {
		Valor_flete = valor_flete;
	}

	public double getCantidad() {
		return Cantidad;
	}

	public void setCantidad(double cantidad) {
		Cantidad = cantidad;
	}

	public double getValor_total() {
		return Valor_total;
	}

	public void setValor_total(double valor_total) {
		Valor_total = valor_total;
	}

	public double getAnticipo() {
		return Anticipo;
	}

	public void setAnticipo(double anticipo) {
		Anticipo = anticipo;
	}

	public int getNumero_factura() {
		return Numero_factura;
	}

	public void setNumero_factura(int numero_factura) {
		Numero_factura = numero_factura;
	}

	public double getCantidad_recibida() {
		return Cantidad_recibida;
	}

	public void setCantidad_recibida(double cantidad_recibida) {
		Cantidad_recibida = cantidad_recibida;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public QuoteOrder getQuoteOrder() {
		return quoteOrder;
	}

	public void setQuoteOrder(QuoteOrder quoteOrder) {
		this.quoteOrder = quoteOrder;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
