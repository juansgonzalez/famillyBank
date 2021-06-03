package com.juanseb.bank.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juanseb.bank.backend.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    Boolean existsByUsername(String username);

}
