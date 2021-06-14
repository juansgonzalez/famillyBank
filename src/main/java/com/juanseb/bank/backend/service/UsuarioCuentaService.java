package com.juanseb.bank.backend.service;

import com.juanseb.bank.backend.model.UsuarioCuenta;
import com.juanseb.bank.backend.model.UsuarioCuentaId;

public interface UsuarioCuentaService {
	
	public UsuarioCuenta obtenerDatosUsuarioCuenta(UsuarioCuentaId id);

	public UsuarioCuenta save(UsuarioCuenta usuarioCuenta);

}
