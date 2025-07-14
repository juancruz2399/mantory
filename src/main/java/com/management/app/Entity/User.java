package com.management.app.Entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "user")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_usuario")
	private Long id_Usuario;
	
	@Column(name = "nombre")
	private String Nombre;
	
	@Column(name = "apellido")
	private String Apellido;
	
	@Column(name = "password")
	private String Password;		
	
	@Column(name = "estado")
	private boolean Estado;
	
	@Column(name ="cargo")
	private int Cargo;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_authority",joinColumns = @JoinColumn(name = "id_usuario_fk"),inverseJoinColumns = @JoinColumn(name = "authority_id"))
	private Set<Authority> roles = new HashSet<>();

	@JoinColumn(name = "id_institution_fk",referencedColumnName="id_Institution")
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL )
	private Institution institution;
	
	///get && set


	public static long getSerialVersionUID(){
		return serialVersionUID;
	}
	
	public Long getId_Usuario() {
		return id_Usuario;
	}



	public void setId_Usuario(Long id_Usuario) {
		this.id_Usuario = id_Usuario;
	}



	public String getNombre() {
		return Nombre;
	}



	public void setNombre(String nombre) {
		Nombre = nombre;
	}



	public String getApellido() {
		return Apellido;
	}



	public void setApellido(String apellido) {
		Apellido = apellido;
	}



	public String getPassword() {
		return Password;
	}



	public void setPassword(String password) {
		Password = password;
	}



	public boolean isEstado() {
		return Estado;
	}



	public void setEstado(boolean estado) {
		Estado = estado;
	}



	public Set<Authority> getRoles() {
		return roles;
	}



	public void setRoles(Set<Authority> roles) {
		this.roles = roles;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public int getCargo() {
		return Cargo;
	}

	public void setCargo(int cargo) {
		Cargo = cargo;
	}

	
}

