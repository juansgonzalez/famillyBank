package com.juanseb.bank.backend.model;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Entity
public class Movimiento {
	
//	CREATE TABLE `movimiento` (
//			  `id` bigint NOT NULL AUTO_INCREMENT,
//			  `cantidad` double NOT NULL,
//			  `concepto` varchar(255) DEFAULT NULL,
//			  `usuario_id` bigint NOT NULL,
//			  `fecha` datetime(6) NOT NULL,
//			  `saldo_actual` double NOT NULL,
//			  `tipo` int NOT NULL,
//			  `categoria_id` bigint DEFAULT NULL,
//			  `cuenta_id` bigint NOT NULL,
//			  `tarjeta_id` bigint DEFAULT NULL,
//			  PRIMARY KEY (`id`),
//			  KEY `FKad102770eq5nlbi8ut9ysbuxw` (`categoria_id`),
//			  KEY `FK4ea11fe7p3xa1kwwmdgi9f2fi` (`cuenta_id`),
//			  KEY `FKt0pt6rcowcf3wrk2xj3k0im2p` (`tarjeta_id`),
//			  FOREIGN KEY (`cuenta_id`) REFERENCES `cuenta` (`id`),
//			  FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id`),
//			  FOREIGN KEY (`tarjeta_id`) REFERENCES `tarjeta` (`id`),
//			  FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
//			);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(nullable = false)
    private Date fecha = new Date();;

    @Column(nullable = false)
    private Double cantidad;

    @Column(nullable = false)
    private TipoMovimiento tipo;

    @Column
    private String concepto;

    @Column(nullable = false)
    private Double saldoActual;

    //relaciones

    @ManyToOne
    @JoinColumn(name= "categoria_id")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name="cuenta_id", nullable = false)
    private Cuenta cuenta;

    @ManyToOne
    @JoinColumn(name= "tarjeta_id")
    private Tarjeta tarjeta;
    
    @ManyToOne
    @JoinColumn(name= "usuario_id")
    private Usuario usuario;


    public Movimiento() {
    }

    public Movimiento(Double cantidad, TipoMovimiento tipo, String concepto, Double saldoActual, Categoria categoria, Cuenta cuenta, Tarjeta tarjeta, Usuario usuario) {
        this.cantidad = cantidad;
        this.tipo = tipo;
        this.concepto = concepto;
        this.saldoActual = saldoActual;
        this.categoria = categoria;
        this.cuenta = cuenta;
        this.tarjeta = tarjeta;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public LocalDate getFechaLocal() {
        return Instant.ofEpochMilli(fecha.getTime())
        	      .atZone(ZoneId.systemDefault())
        	      .toLocalDate();

    }

    public void setFechaLocal(LocalDate fecha) {
        this.fecha = Date.from(fecha.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public TipoMovimiento getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimiento tipo) {
        this.tipo = tipo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Double getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(Double saldoActual) {
        this.saldoActual = saldoActual;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
    
}
