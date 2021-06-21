package com.juanseb.bank.backend.service;

import org.springframework.stereotype.Service;

import com.juanseb.bank.backend.model.Movimiento;
import com.juanseb.bank.backend.payload.filter.MovimientoMesFilter;
import com.juanseb.bank.backend.payload.filter.MovimientosFilter;

import java.time.LocalDate;
import java.util.List;

@Service
public interface MovimientoService {
	
	public List<Movimiento> obtenerMovimientosDeTarjeta(Long idTarjeta);
	
	public List<Movimiento> obtenerMovimientosDeTarjetaByUsuario(Long idTarjeta, Long idUsuario);
	
	public List<Movimiento> obtenerMovimientosDeCuenta(Long idCuenta);

	public List<Movimiento> obtenerMovimientosFiltrados(MovimientosFilter filtro);
	
	public List<Movimiento> obtenerMovimientoFechaCuenta(Long idCuenta,LocalDate fechaInit, LocalDate fechaFin);

	public List<Movimiento> obtenerMovimientoFechaTarjeta(Long idTarjeta,LocalDate fechaInit, LocalDate fechaFin);

//	public List<Movimiento> obtenerMovimientosCuentaByCategoria(Long idCuenta, MovimientoMesFilter filtroMovimiento);

	public List<Movimiento> obtenerMovimientosTarjetaByCategoria(Long idTarjeta, MovimientoMesFilter filtroMovimiento);

	public List<Movimiento> obtenerMovimientosUsuarioByCategoria(Long idUsuario, MovimientoMesFilter filtroMovimiento);

	public Movimiento crearMovimiento(Movimiento movimientoNuevo) throws Exception;

	public List<Movimiento> obtenerMovimientosDeCuentaOrdenadosFecha(Long idCuenta);

	public List<Movimiento> obtenerMovimientosDeCuentaByUsuarioOrdenadosFecha(Long idCuenta, Long id);

	public List<Movimiento> obtenerMovimientoFechaCuentaByUsuario(Long idCuenta, Long id, LocalDate of, LocalDate of2);

	public List<Movimiento> obtenerMovimientosDeCuentaByCuentaAhorro(Long idCuentaAhorro, LocalDate of, LocalDate of2);

	public Movimiento save(Movimiento movimientoEditable);

	List<Movimiento> obtenerMovimientosCuentaByCategoriaAndUsuario(Long idCuenta, Long idUsuario, MovimientoMesFilter filtroMovimiento);

	public void eliminar(Movimiento movimiento);

	public Movimiento actualizarMovimiento(Movimiento movimiento);

	public Movimiento obtenerMovimientoId(Long id);
}
