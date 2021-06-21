package com.juanseb.views.components;

import com.juanseb.bank.backend.model.Movimiento;
import com.juanseb.bank.backend.model.enumerado.TipoMovimiento;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class IconoMovimiento extends HorizontalLayout{

	private static final long serialVersionUID = 5917663772937816645L;

	public IconoMovimiento(Movimiento movimiento) {

        super();
        Image img;

        if(movimiento.getTipo().equals(TipoMovimiento.INGRESO)) {
            img = new Image("images/profit.svg", "Profit");
        }else {
            img = new Image("images/devaluation.svg", "visa");
        }

        img.setWidth("20px");
        img.setHeight("20px");

        add(img);
    }
}