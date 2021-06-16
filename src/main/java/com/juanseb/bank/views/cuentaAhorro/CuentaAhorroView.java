package com.juanseb.bank.views.cuentaAhorro;

import com.juanseb.bank.views.main.MainView;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "cuentaAhorro", layout = MainView.class)
@PageTitle("Cuentas Ahorro")
public class CuentaAhorroView extends HorizontalLayout{

	private static final long serialVersionUID = -3620817856420812391L;

	public CuentaAhorroView() {
		this.setSizeFull();

	}
	
}
