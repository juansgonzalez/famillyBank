package com.juanseb.bank.backend.service;

import java.util.List;
import java.util.Optional;

import com.juanseb.bank.backend.model.UsuarioCuenta;
import com.juanseb.bank.backend.model.UsuarioCuentaId;

public interface UsuarioCuentaService {
	
	public Optional<UsuarioCuenta> obtenerDatosUsuarioCuenta(UsuarioCuentaId id);
	
	public List<UsuarioCuenta> obtenerCuentasDeUsuario(Long idUsuario);

	public UsuarioCuenta save(UsuarioCuenta usuarioCuenta);
	
	public boolean exist(UsuarioCuentaId id);

	public List<UsuarioCuenta> obtenerUsuariosIgualesByCuenta(Long idCuentaActual);

}
