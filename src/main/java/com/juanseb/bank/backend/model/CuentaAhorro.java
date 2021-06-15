package com.juanseb.bank.backend.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CuentaAhorro {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @Column(nullable = false)
	private double saldo = 0;
	
	@ManyToOne
    @JoinColumn(name= "cuenta_id")
	private Cuenta cuenta;
	
	@OneToMany(mappedBy = "cuentaAhorro")
    @JsonIgnore
    List<Movimiento> movimientos = new ArrayList<>();

	public CuentaAhorro() {
		super();
	}

	public CuentaAhorro(Long id, double saldo, Cuenta cuenta, List<Movimiento> movimientos) {
		super();
		this.id = id;
		this.saldo = saldo;
		this.cuenta = cuenta;
		this.movimientos = movimientos;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Movimiento> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(List<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}

}
