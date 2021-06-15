package com.juanseb.bank.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuario", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username") })
public class Usuario {

//	CREATE TABLE `usuario` (
//			  `id` bigint NOT NULL AUTO_INCREMENT,
//			  `nombre_completo` varchar(255) DEFAULT NULL,
//			  `nombre_corto` varchar(50) DEFAULT NULL,
//			  `image` varchar(45) NOT NULL,
//			  `rol` varchar(45) NOT NULL,
//			  `saldo` double DEFAULT NULL,
//			  `password` varchar(255) DEFAULT NULL,
//			  `username` varchar(255) DEFAULT NULL,
//			  PRIMARY KEY (`id`),
//			  UNIQUE KEY `UK_863n1y3x0jalatoir4325ehal` (`username`)
//			);
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;   // para el login

    private String nombreCompleto;
    
    private String nombreCorto;
    
    private String image;
    
    private boolean resetearPassword;

    @JsonIgnore
    private String password;

    // relaciones
    
    @OneToMany(mappedBy = "usuarioPrincipal")
    @JsonIgnore
    List<Cuenta> cuentasPrincipales = new ArrayList<>();

//    CREATE TABLE `usuario_cuenta` (
//    		  `usuario_id` bigint NOT NULL,
//    		  `cuenta_id` bigint NOT NULL,
//    		  FOREIGN KEY (`cuenta_id`) REFERENCES `cuenta` (`id`),
//    		  FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)
//    		);
    
//    @ManyToMany
//    @JoinTable(
//            name = "usuario_cuenta",
//            joinColumns = {@JoinColumn(name="usuario_id", referencedColumnName = "id")},
//            inverseJoinColumns = {@JoinColumn(name="cuenta_id", referencedColumnName = "id")}
//    )
//    List<Cuenta> cuentas = new ArrayList<>();
    @OneToMany(mappedBy = "id.usuario")
    private Set<UsuarioCuenta> cuentas = new HashSet<UsuarioCuenta>(0);

    
    
    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    List<Movimiento> movimientos = new ArrayList<>();


    public Usuario() {
    }

    public Usuario(String username,String image, String nombreCompleto, String nombreCorto, String password) {
        this.username = username;
        this.image = image;
        this.nombreCompleto = nombreCompleto;
        this.nombreCorto = nombreCorto;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}


	public List<Cuenta> getCuentasPrincipales() {
		return cuentasPrincipales;
	}

	public void setCuentasPrincipales(List<Cuenta> cuentasPrincipales) {
		this.cuentasPrincipales = cuentasPrincipales;
	}

	public Set<UsuarioCuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(Set<UsuarioCuenta> cuentas) {
		this.cuentas = cuentas;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Movimiento> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(List<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}

	public boolean isResetearPassword() {
		return resetearPassword;
	}

	public void setResetearPassword(boolean resetearPassword) {
		this.resetearPassword = resetearPassword;
	}
    
    
}
