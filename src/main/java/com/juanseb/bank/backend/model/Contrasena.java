package com.juanseb.bank.backend.model;

public class Contrasena {

	String contrasena;
	String confirmarContrasena;
	
	public Contrasena(String contrasena, String confirmarContrasena) {
		super();
		this.contrasena = contrasena;
		this.confirmarContrasena = confirmarContrasena;
	}
	
	public Contrasena() {
	
	}

	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public String getConfirmarContrasena() {
		return confirmarContrasena;
	}
	public void setConfirmarContrasena(String confirmarContrasena) {
		this.confirmarContrasena = confirmarContrasena;
	}
	
	
	
}
