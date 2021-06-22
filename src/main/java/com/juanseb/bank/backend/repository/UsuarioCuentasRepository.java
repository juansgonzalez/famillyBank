package com.juanseb.bank.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.juanseb.bank.backend.model.UsuarioCuenta;
import com.juanseb.bank.backend.model.UsuarioCuentaId;

public interface UsuarioCuentasRepository extends JpaRepository<UsuarioCuenta, UsuarioCuentaId> {

	@Query("SELECT uc FROM UsuarioCuenta uc WHERE uc.id.usuario.id =:idUsuario")
	List<UsuarioCuenta> obtenerCuentasByUsuario(@Param("idUsuario")Long idUsuario);
	
	

}
