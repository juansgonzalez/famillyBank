package com.juanseb.bank.backend.payload.filter;


public class MovimientoMesFilter {
	String mes;
	Long idCategoria;
	
	public MovimientoMesFilter(String mes, Long idCategoria) {
		super();
		this.mes = mes;
		this.idCategoria = idCategoria;
	}
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public Long getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	
	
	

}
