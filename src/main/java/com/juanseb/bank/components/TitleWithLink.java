package com.juanseb.bank.components;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class TitleWithLink extends HorizontalLayout{
	
	public TitleWithLink(String titulo, String textoLink, String link) {
		super();
		setWidthFull();
		H2 tituloMovimiento= new H2(titulo);
		tituloMovimiento.getElement().getStyle().set("margin-top", "0");
		tituloMovimiento.getElement().getStyle().set("margin-right", "auto");

		Anchor anchorMovimientos = new Anchor(link, textoLink);
		anchorMovimientos.getElement().getStyle().set("margin-left", "0");
		anchorMovimientos.getElement().getStyle().set("margin-top", "15px");
		anchorMovimientos.getElement().getStyle().set("color", "#D01E69");
		anchorMovimientos.getElement().getStyle().set("font-weight", "bold");

		
		add(tituloMovimiento,anchorMovimientos);
	}

}
