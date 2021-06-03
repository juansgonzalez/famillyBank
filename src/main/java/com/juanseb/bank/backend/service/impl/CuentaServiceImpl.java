package com.juanseb.bank.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juanseb.bank.backend.model.Cuenta;
import com.juanseb.bank.backend.repository.CuentaRepository;
import com.juanseb.bank.backend.service.CuentaService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CuentaServiceImpl implements CuentaService {

	@Autowired
	CuentaRepository cuentaRepository;
	
	
	@Override
	public Cuenta crearCuenta(Cuenta cuenta) {
		 if(cuentaRepository.existsByIban(cuenta.getIban())){
			 return cuenta;
		 }
		return cuentaRepository.save(cuenta);
	}

	@Override
	public Cuenta obtenerCuentaById(Long idCuenta) {
		return cuentaRepository.findById(idCuenta).orElseThrow(()
				-> new EntityNotFoundException("No se ha encontrado categoria con id: "+idCuenta));
	}

	@Override
	public List<Cuenta> obtenerTodasCuentasByUsuarioId(Long idUsuario) {
		return cuentaRepository.obtenerCuentasByUserId(idUsuario);
	}

	@Override
	public Cuenta save(Cuenta cuentaObtenida) {
		return cuentaRepository.save(cuentaObtenida);
	}

}
