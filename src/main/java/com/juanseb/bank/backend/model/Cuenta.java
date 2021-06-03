package com.juanseb.bank.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Cuenta {
	
//	CREATE TABLE `cuenta` (
//			  `id` bigint NOT NULL AUTO_INCREMENT,
//			  `iban` varchar(255) DEFAULT NULL,
//			  `saldo` double NOT NULL,
//			  PRIMARY KEY (`id`)
//			);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String iban;

    @Column(nullable = false)
    private Double saldo;

    //relaciones

    @ManyToMany(mappedBy = "cuentas")
    @JsonIgnore    // Para evitar en la respuesta json la recursión infinita en relaciones bidireccionales
    private List<Usuario> usuarios = new ArrayList<>();

    @OneToMany(mappedBy = "cuenta")
    @JsonIgnore
    List<Movimiento> movimientos = new ArrayList<>();

    @OneToMany(mappedBy = "cuenta")
    @JsonIgnore
    List<Tarjeta> tarjetas = new ArrayList<>();

    public Cuenta() {
    }

    public Cuenta(String iban, Double saldo) {
        this.iban = iban;
        this.saldo = saldo;
    }

    public Long getId() {
        return id;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    public List<Tarjeta> getTarjetas() {
        return tarjetas;
    }

    public void setTarjetas(List<Tarjeta> tarjetas) {
        this.tarjetas = tarjetas;
    }
}
