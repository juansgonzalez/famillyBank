package com.juanseb.bank.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juanseb.bank.backend.model.Categoria;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Optional<Categoria> findByNombre(String nombre);

    Boolean existsByNombre(String nombre);
}
