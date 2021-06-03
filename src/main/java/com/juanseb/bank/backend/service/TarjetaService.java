package com.juanseb.bank.backend.service;

import org.springframework.stereotype.Service;

import com.juanseb.bank.backend.model.Tarjeta;

import java.util.List;

@Service
public interface TarjetaService {
	
	public Tarjeta crearTarjeta(Tarjeta tarjetaNueva);
	
	public List<Tarjeta> obtenerTarjetaByCuenta(Long cuentaId);

	public Tarjeta obtenerTarjetaByNumeroTarjeta(Long numeroTarjeta);
	
	public Tarjeta obtenerTarjetaById(Long idTarjeta);
	
}
