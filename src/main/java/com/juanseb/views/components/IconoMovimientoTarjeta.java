package com.juanseb.views.components;

import com.juanseb.bank.backend.model.Movimiento;
import com.juanseb.bank.backend.model.TipoMovimiento;
import com.juanseb.bank.backend.utils.Utils;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class IconoMovimientoTarjeta extends HorizontalLayout{
	public IconoMovimientoTarjeta(Movimiento movimiento) {
		super();
		Image img ;
		if(movimiento.getTipo().equals(TipoMovimiento.INGRESO)) {
			img = new Image("images/profit.svg", "Profit");
		}else {
			img = new Image("images/devaluation.svg", "visa");
		}
		img.setWidth("20px");
		img.setHeight("20px");				
        add(img, new Span(movimiento.getTarjeta() != null ? Utils.enmascararNumeroTarjeta(movimiento.getTarjeta().getNumero()) : ""));
        
		
	}
}
