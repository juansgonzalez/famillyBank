package com.juanseb.bank.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.juanseb.bank.backend.utils.Utils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tarjeta {
	
//	CREATE TABLE `tarjeta` (
//			  `id` bigint NOT NULL AUTO_INCREMENT,
//			  `numero` bigint NOT NULL,
//			  `cuenta_id` bigint DEFAULT NULL,
//			  PRIMARY KEY (`id`),
//			  FOREIGN KEY (`cuenta_id`) REFERENCES `cuenta` (`id`)
//			);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long numero; 

    // relaciones

    @ManyToOne
    @JoinColumn(name = "cuenta_id")
    private Cuenta cuenta;

    @OneToMany(mappedBy = "tarjeta")
    @JsonIgnore
    List<Movimiento> movimientos = new ArrayList<>();


    public Tarjeta() {
    }

    public Tarjeta(Long numero, Cuenta cuenta) {
        this.numero = numero;
        this.cuenta = cuenta;
    }

    public Long getId() {
        return id;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }
    
    public String getNumeroEnmascarado() {
    	return Utils.enmascararNumeroTarjeta(this.getNumero());
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }
}
