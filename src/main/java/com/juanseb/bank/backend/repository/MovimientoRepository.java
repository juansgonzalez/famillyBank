package com.juanseb.bank.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.juanseb.bank.backend.model.Movimiento;

import java.util.Date;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
	
	@Query("SELECT m FROM Movimiento m WHERE m.tarjeta.id= :idTarjeta AND m.cuentaAhorro IS NULL")
	List<Movimiento> obtenerMovimientosDeTarjeta(@Param("idTarjeta") Long idTarjeta);
	
	@Query("SELECT m FROM Movimiento m WHERE m.cuenta.id= :idCuenta AND m.cuentaAhorro IS NULL")
	List<Movimiento> obtenerMovimientosDeCuenta(@Param("idCuenta") Long idcuenta);

	@Query("select m from Movimiento m where m.tarjeta.id = :idTarjeta AND m.cuentaAhorro IS NULL AND m.fecha BETWEEN :fechaInit AND :fechaFin ")
	List<Movimiento> obtenerMovimientosDeTarjetaFechas(@Param("idTarjeta")Long idTarjeta, @Param("fechaInit")Date fechaInit, @Param("fechaFin")Date fechaFin);
	
	@Query("select m from Movimiento m where m.categoria.id= :idCategoria AND m.cuentaAhorro IS NULL AND m.cuenta.id = :idCuenta AND m.fecha BETWEEN :fechaInit AND :fechaFin ")
	List<Movimiento> obtenerMovimientosDeCuentaByFechaAndCategoria(@Param("idCuenta")Long idCuenta,@Param("idCategoria")Long idCategoria ,@Param("fechaInit")Date fechaInit, @Param("fechaFin")Date fechaFin);
	
	@Query("select m from Movimiento m where m.categoria.id= :idCategoria AND m.cuentaAhorro IS NULL AND m.tarjeta.id = :idTarjeta AND m.fecha BETWEEN :fechaInit AND :fechaFin ")
	List<Movimiento> obtenerMovimientosDeTarjetaByFechaAndCategoria(@Param("idTarjeta")Long idTarjeta,@Param("idCategoria")Long idCategoria ,@Param("fechaInit")Date fechaInit, @Param("fechaFin")Date fechaFin);
	
	@Query("select m from Movimiento m where m.categoria.id= :idCategoria AND m.cuentaAhorro IS NULL AND m.cuenta.id  in (select c.id from Cuenta c join c.usuarios u where u.id = :idUsuario) and m.fecha BETWEEN :fechaInit AND :fechaFin")
	List<Movimiento> obtenerMovimientosDeUsuarioByFechaAndCategoria(@Param("idUsuario")Long idUsuario,@Param("idCategoria") Long idCategoria, @Param("fechaInit")Date fechaInit, @Param("fechaFin")Date fechaFin);

	@Query("select m from Movimiento m where m.cuenta.id = :idCuenta AND m.cuentaAhorro IS NULL AND  m.fecha BETWEEN :fechaInit AND :fechaFin ORDER BY m.fecha")
	List<Movimiento> obtenerMovimientosDeCuentaFechas(@Param("idCuenta")Long idCuenta, @Param("fechaInit") Date dateInit,@Param("fechaFin") Date datefin);

	@Query("SELECT m FROM Movimiento m WHERE m.cuenta.id= :idCuenta AND m.cuentaAhorro IS NULL ORDER BY m.fecha DESC")
	List<Movimiento> obtenerMovimientosDeCuentaOrdenadosCuenta(Long idCuenta);

	@Query("SELECT m FROM Movimiento m WHERE m.tarjeta.id= :idTarjeta AND m.cuentaAhorro IS NULL AND m.usuario.id =:idUsuario")
	List<Movimiento> obtenerMovimientosDeTarjetaByUsuario(@Param("idTarjeta")Long idTarjeta, @Param("idUsuario")Long idUsuario);

	@Query("SELECT m FROM Movimiento m WHERE m.cuenta.id= :idCuenta AND m.cuentaAhorro IS NULL AND m.usuario.id =:idUsuario ORDER BY m.fecha DESC")
	List<Movimiento> obtenerMovimientosDeCuentaByUsuarioOrdenadosCuenta(@Param("idCuenta")Long idCuenta, @Param("idUsuario")Long idUsuario);

	@Query("select m from Movimiento m WHERE m.cuenta.id = :idCuenta AND m.cuentaAhorro IS NULL AND m.usuario.id =:idUsuario AND m.fecha BETWEEN :fechaInit AND :fechaFin ORDER BY m.fecha")
	List<Movimiento> obtenerMovimientosDeCuentaFechasByUsuario(@Param("idCuenta")Long idCuenta, @Param("idUsuario")Long idUsuario, @Param("fechaInit")Date dateInit, @Param("fechaFin")Date datefin);
	
	@Query("select m from Movimiento m WHERE m.cuentaAhorro =:idCuentaAhorro AND m.fecha BETWEEN :fechaInit AND :fechaFin ORDER BY m.id")
	List<Movimiento> obtenerMovimientosDeCuentaByCuentaAhorro(@Param("idCuentaAhorro")Long idCuentaAhorro, @Param("fechaInit")Date dateInit, @Param("fechaFin")Date datefin);	
	
}
