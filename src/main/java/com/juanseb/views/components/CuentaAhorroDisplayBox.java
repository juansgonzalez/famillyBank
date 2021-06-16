package com.juanseb.views.components;

import java.text.DecimalFormat;

import com.github.appreciated.card.ClickableCard;
import com.juanseb.bank.backend.model.CuentaAhorro;
import com.juanseb.bank.backend.service.MovimientoService;
import com.juanseb.bank.backend.utils.Utils;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;

public class CuentaAhorroDisplayBox extends ClickableCard {
	
	private static final long serialVersionUID = 270718588005160260L;
	
	private MovimientoService movimientoService;

	private CuentaAhorro cuentaAhorro;

	public CuentaAhorroDisplayBox(CuentaAhorro cuentaAhorro, MovimientoService movimientoService) {
		super(componentEvent -> new Dialog().open());
		this.movimientoService = movimientoService;
		this.cuentaAhorro = cuentaAhorro;
		
		this.setWidth("255px");
		this.setHeight("150px");
		this.getElement().getStyle().set("radius", "24px");
		
		// Create layout
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        
        H2 nombreCuentaAhorro = new H2(cuentaAhorro.getNombre());
        
        layout.setHorizontalComponentAlignment(Alignment.CENTER,nombreCuentaAhorro);
        layout.add(nombreCuentaAhorro);

        DecimalFormat df = new DecimalFormat("#,###.##");
        Span saldoTexto = new Span();
        saldoTexto.getElement().getStyle().set("color", "#D01E69");    
    	saldoTexto.add(df.format(cuentaAhorro.getSaldo())+" €");        	        	
        saldoTexto.getElement().getStyle().set("font-weight", "bold");
        saldoTexto.getElement().getStyle().set("font-size", "1.5em");
        
        layout.setHorizontalComponentAlignment(Alignment.CENTER,saldoTexto);
        layout.add(saldoTexto);
		
        add(layout);
	}


}
