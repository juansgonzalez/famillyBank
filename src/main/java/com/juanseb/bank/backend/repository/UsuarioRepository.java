package com.juanseb.bank.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.juanseb.bank.backend.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    Boolean existsByUsername(String username);

	@Query("select u from Usuario u join u.cuentas uc where uc.id.cuenta.id = :idCuenta")
	List<Usuario> obtenerUsuariosByCuenta(Long idCuenta);
	
	@Query("select u from Usuario u where u.username like :userName")
	List<Usuario> obtenerUsuariosFiltradoUserName(String userName);

}
