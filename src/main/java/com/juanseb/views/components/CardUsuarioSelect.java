package com.juanseb.views.components;

import java.text.DecimalFormat;

import com.github.appreciated.card.ClickableCard;
import com.juanseb.bank.backend.model.Cuenta;
import com.juanseb.bank.backend.model.Usuario;
import com.juanseb.bank.backend.model.UsuarioCuenta;
import com.juanseb.bank.backend.model.UsuarioCuentaId;
import com.juanseb.bank.backend.service.MovimientoService;
import com.juanseb.bank.backend.service.UsuarioCuentaService;
import com.juanseb.bank.backend.service.UsuarioService;
import com.juanseb.bank.backend.utils.Utils;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CardUsuarioSelect extends ClickableCard{
	
	private static final long serialVersionUID = 4194699125726794904L;
	

	public CardUsuarioSelect(Cuenta cuenta,Usuario usuario, UsuarioService usuarioService, MovimientoService movimientoService, UsuarioCuentaService usuarioCuentaService) {
		super(event -> {
        	addUsuarioCuenta(usuarioService,usuarioCuentaService,usuario,cuenta);
        	UI.getCurrent().getPage().reload();
        });

        // estilo del card
        this.setWidth("300px");
        this.setHeight("220px");
        this.getElement().getStyle().set("radius", "24px");

        // layout principal que contendrá los layouts posteriores
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();

        // layout con el logo de ingenia bank
        HorizontalLayout imagenLayout = new HorizontalLayout();
        Image ingeniaImage = new Image(usuario.getImage(), usuario.getNombreCorto());
        ingeniaImage.setWidth("40%");
        imagenLayout.add(ingeniaImage);

        // layout con el Nombre de ingenia bank
        HorizontalLayout nombrelayout = new HorizontalLayout();
        Span nombreSpan = new Span();
        nombreSpan.add(usuario.getNombreCompleto());
        nombreSpan.getElement().getStyle().set("color", "black");
        nombreSpan.getElement().getStyle().set("font-weight", "bold");
        nombrelayout.add(nombreSpan);
        
        // layout con el saldo de la cuenta
        HorizontalLayout saldoLayout = new HorizontalLayout();
        Span saldoSpan = new Span();
        Double saldo = Utils.obtenerSaldoEnCuenta(cuenta.getId(), usuario.getId(), usuarioCuentaService);
        
        
        DecimalFormat df = new DecimalFormat("#,###.##");
        saldoSpan.add(df.format(saldo) +" €");
        saldoSpan.getElement().getStyle().set("color", "#D01E69");
        saldoSpan.getElement().getStyle().set("font-weight", "bold");
        if(saldo.equals(-1d)) {
        	saldoSpan.setVisible(false);
        }
        saldoLayout.add(saldoSpan);


        // añadimos todos los layouts horizontales al layout principal
        layout.add(imagenLayout, nombrelayout, saldoLayout);

        // cambio propiedad cursor
        layout.getElement().getStyle().set("cursor", "pointer");

        add(layout);
    }

	private static void addUsuarioCuenta(UsuarioService usuarioService, UsuarioCuentaService usuarioCuentaService, Usuario usuario, Cuenta cuenta) {
		UsuarioCuenta uc = new UsuarioCuenta();
		UsuarioCuentaId ucId = new UsuarioCuentaId(cuenta, usuario);
		
		uc.setId(ucId);
		if(!usuarioCuentaService.exist(ucId)) {
			uc.setSaldoEnCuenta(0d);
			usuarioCuentaService.save(uc);			
		}
	}


}
