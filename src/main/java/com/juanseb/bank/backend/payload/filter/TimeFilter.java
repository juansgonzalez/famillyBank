package com.juanseb.bank.backend.payload.filter;

import java.time.LocalDate;

public class TimeFilter {
	LocalDate fechaInit;
	LocalDate fechaFin;
	
	public TimeFilter(LocalDate fechaInit, LocalDate fechaFin) {
		super();
		this.fechaInit = fechaInit;
		this.fechaFin = fechaFin;
	}
	public LocalDate getFechaInit() {
		return fechaInit;
	}
	public void setFechaInit(LocalDate fechaInit) {
		this.fechaInit = fechaInit;
	}
	public LocalDate getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	

}
