package com.juanseb.bank.backend.service.impl;

import java.util.List;
import java.util.Optional;

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
	public Optional<UsuarioCuenta> obtenerDatosUsuarioCuenta(UsuarioCuentaId id) {
		return usuarioCuentaRepository.findById(id);
	}

	@Override
	public UsuarioCuenta save(UsuarioCuenta usuarioCuenta) {
		return usuarioCuentaRepository.save(usuarioCuenta);
	}

	@Override
	public boolean exist(UsuarioCuentaId id) {
		return usuarioCuentaRepository.existsById(id);
	}

	@Override
	public List<UsuarioCuenta> obtenerCuentasDeUsuario(Long idUsuario) {
		return usuarioCuentaRepository.obtenerCuentasByUsuario(idUsuario);
	}

	@Override
	public List<UsuarioCuenta> obtenerUsuariosIgualesByCuenta(Long idCuentaActual) {
		return usuarioCuentaRepository.obtenerUsuarioCuentaIgualesByCuenta(idCuentaActual);
	}

}
