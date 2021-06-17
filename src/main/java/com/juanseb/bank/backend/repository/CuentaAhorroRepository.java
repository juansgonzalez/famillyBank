package com.juanseb.bank.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.juanseb.bank.backend.model.CuentaAhorro;

public interface CuentaAhorroRepository extends JpaRepository<CuentaAhorro, Long> {

	@Query("SELECT ca FROM CuentaAhorro ca WHERE ca.nombre.id= :nombre")
	CuentaAhorro obtenerCuentaAhorroByName(@Param("nombre")String nombre);

	@Query("SELECT ca FROM CuentaAhorro ca WHERE ca.cuenta.id= :idCuenta")
	List<CuentaAhorro> obtenerCuentaAhorroByCuenta(@Param("idCuenta")Long idCuenta);

}
