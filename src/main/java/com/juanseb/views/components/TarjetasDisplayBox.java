package com.juanseb.views.components;

import java.text.DecimalFormat;

import com.github.appreciated.card.ClickableCard;
import com.juanseb.bank.backend.model.Tarjeta;
import com.juanseb.bank.backend.model.Usuario;
import com.juanseb.bank.backend.service.MovimientoService;
import com.juanseb.bank.backend.service.TarjetaService;
import com.juanseb.bank.backend.service.UsuarioCuentaService;
import com.juanseb.bank.backend.utils.Utils;
import com.juanseb.bank.views.form.TarjetaDialog;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class TarjetasDisplayBox extends ClickableCard {

	private static final long serialVersionUID = -7764591521651167968L;

	
	public TarjetasDisplayBox(Tarjeta tarjeta,Usuario usuarioActual, MovimientoService movimientoService,TarjetaService tarjetaService, UsuarioCuentaService usuarioCuentaService) {
		super(componentEvent -> new TarjetaDialog(movimientoService, tarjetaService, usuarioActual ,tarjeta.getId()).open());

		Long idCuenta = (Long) UI.getCurrent().getSession().getAttribute("idCuenta");

		// Set some style
		this.setWidth("255px");
		this.setHeight("150px");
		this.getElement().getStyle().set("radius", "24px");
		
		// Create layout
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        
        HorizontalLayout icon = new HorizontalLayout();
        // Icono entidad
        Image imgLogo1 = new Image("images/bankname.png", "Imagine Bank");
        imgLogo1.setWidth("50px");
        imgLogo1.setHeight("25px");
        icon.add(imgLogo1);

        layout.add(icon);
        
      
        DecimalFormat df = new DecimalFormat("#,###.##");
        Span saldoTexto = new Span();
        saldoTexto.getElement().getStyle().set("color", "#D01E69");    
    	saldoTexto.add(df.format(Utils.obtenerSaldoEnCuenta(idCuenta,usuarioActual.getId(),usuarioCuentaService))+" €");        	        	
        saldoTexto.getElement().getStyle().set("font-weight", "bold");
        saldoTexto.getElement().getStyle().set("font-size", "1.5em");
        
        layout.setHorizontalComponentAlignment(Alignment.CENTER,saldoTexto);
        layout.add(saldoTexto);

        
        HorizontalLayout downPart = new HorizontalLayout();
        downPart.setWidthFull();
        Image img = new Image("images/Vector.png", "visa");
        img.setWidth("35px");
        img.setHeight("15px");
        img.getElement().getStyle().set("padding-top", "10px");
        
        downPart.add(img);
                

        Span tarjetaNumber = new Span(Utils.enmascararNumeroTarjeta(tarjeta.getNumero()));
        tarjetaNumber.getElement().getStyle().set("margin-left", "auto");
        tarjetaNumber.getElement().getStyle().set("color", "#D01E69");
        tarjetaNumber.getElement().getStyle().set("font-weight", "bold");
        downPart.add(tarjetaNumber);
        
        downPart.getElement().getStyle().set("margin-top", "auto");
        downPart.setPadding(true);
        
        layout.add(downPart);
        layout.getElement().getStyle().set("cursor", "pointer");
        add(layout);
        
    }
	
}
