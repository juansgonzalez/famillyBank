package com.juanseb.bank.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.juanseb.bank.backend.model.CuentaAhorro;

@Service
public interface CuentaAhorroService {

	CuentaAhorro crearActualizarCuentaAhorro(CuentaAhorro cuentaAhorro);

	CuentaAhorro obtenerCuentaAhorroById(Long idCuenta);

	CuentaAhorro obtenerCuentaAhorroByNombre(String nombre);

	List<CuentaAhorro> obtenerCuentaAhorroByCuenta(Long idCuenta);

	

}
