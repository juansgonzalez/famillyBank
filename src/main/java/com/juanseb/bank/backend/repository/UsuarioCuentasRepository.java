package com.juanseb.bank.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juanseb.bank.backend.model.UsuarioCuenta;
import com.juanseb.bank.backend.model.UsuarioCuentaId;

public interface UsuarioCuentasRepository extends JpaRepository<UsuarioCuenta, UsuarioCuentaId> {
	
	

}
