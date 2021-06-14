package com.juanseb.bank.backend.service.impl;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.juanseb.bank.backend.model.UsuarioCuenta;
import com.juanseb.bank.backend.model.UsuarioCuentaId;
import com.juanseb.bank.backend.repository.UsuarioCuentasRepository;
import com.juanseb.bank.backend.service.UsuarioCuentaService;

@Service
public class UsuarioCuentaServiceImpl implements UsuarioCuentaService{
	
	UsuarioCuentasRepository usuarioCuentaRepository;
	
	public UsuarioCuentaServiceImpl(UsuarioCuentasRepository usuarioCuentaRepository) {
		this.usuarioCuentaRepository = usuarioCuentaRepository;
	}

	@Override
	public UsuarioCuenta obtenerDatosUsuarioCuenta(UsuarioCuentaId id) {
		return usuarioCuentaRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("No se encontro datos para el usuario y la cuenta"));
	}

	@Override
	public UsuarioCuenta save(UsuarioCuenta usuarioCuenta) {
		return usuarioCuentaRepository.save(usuarioCuenta);
	}

}
