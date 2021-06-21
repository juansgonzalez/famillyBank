package com.juanseb.bank.backend.model;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.juanseb.bank.backend.model.enumerado.TipoUsuarioCuenta;


@Entity
@Table(name = "usuario_cuenta")
@AssociationOverrides({
        @AssociationOverride(name = "id.usuario", 
            joinColumns = @JoinColumn(name = "usuario_id")),
        @AssociationOverride(name = "id.cuenta", 
            joinColumns = @JoinColumn(name = "cuenta_id")) })
public class UsuarioCuenta {
	
	@EmbeddedId
    private UsuarioCuentaId id = new UsuarioCuentaId();
	
	@Column(nullable = false)
	private Double saldoEnCuenta;
	
	@Column(nullable = false)
	private TipoUsuarioCuenta tipoUsuarioCuenta;

	public UsuarioCuentaId getId() {
		return id;
	}

	public void setId(UsuarioCuentaId id) {
		this.id = id;
	}

	@Transient
    public Usuario getUsuario() {
        return getId().getUsuario();
    }

    public void setUsuario(Usuario usuario) {
        getId().setUsuario(usuario);
    }

    @Transient
    public Cuenta getCuenta() {
        return getId().getCuenta();
    }

    public void setCuenta(Cuenta cuenta) {
        getId().setCuenta(cuenta);
    }
	
	public Double getSaldoEnCuenta() {
		return saldoEnCuenta;
	}

	public void setSaldoEnCuenta(Double saldoEnCuenta) {
		this.saldoEnCuenta = saldoEnCuenta;
	}

	public TipoUsuarioCuenta getTipoUsuarioCuenta() {
		return tipoUsuarioCuenta;
	}

	public void setTipoUsuarioCuenta(TipoUsuarioCuenta tipoUsuarioCuenta) {
		this.tipoUsuarioCuenta = tipoUsuarioCuenta;
	}

	 
}
