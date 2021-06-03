package com.juanseb.bank.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Categoria {
	
//	CREATE TABLE `categoria` (
//			  `id` bigint NOT NULL AUTO_INCREMENT,
//			  `nombre` varchar(255) NOT NULL,
//			  PRIMARY KEY (`id`)
//			);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @OneToMany(mappedBy = "categoria")
    @JsonIgnore
    List<Movimiento> movimientos = new ArrayList<>();


    public Categoria() {
    }

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }
}
