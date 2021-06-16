package com.juanseb.bank.backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juanseb.bank.backend.model.CuentaAhorro;
import com.juanseb.bank.backend.repository.CuentaAhorroRepository;
import com.juanseb.bank.backend.service.CuentaAhorroService;

@Service
public class CuentaAhorroServiceImpl implements CuentaAhorroService {

	@Autowired
	CuentaAhorroRepository cuentaAhorroRepository;
	
	@Override
	public CuentaAhorro crearActualizarCuentaAhorro(CuentaAhorro cuentaAhorro) {
		return cuentaAhorroRepository.save(cuentaAhorro);
	}

	@Override
	public CuentaAhorro obtenerCuentaAhorroById(Long idCuentaAhorro) {
		return cuentaAhorroRepository.findById(idCuentaAhorro).get();
	}

	@Override
	public CuentaAhorro obtenerCuentaAhorroByNombre(String nombre) {
		return cuentaAhorroRepository.obtenerCuentaAhorroByName(nombre);
	}

	@Override
	public List<CuentaAhorro> obtenerCuentaAhorroByCuenta(Long idCuenta) {
		return cuentaAhorroRepository.obtenerCuentaAhorroByCuenta(idCuenta);
	}

}
