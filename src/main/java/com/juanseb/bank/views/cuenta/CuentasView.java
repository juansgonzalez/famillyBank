package com.juanseb.bank.views.cuenta;

import com.juanseb.bank.views.main.MainView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "cuentas", layout = MainView.class)
@PageTitle("Cuentas")
public class CuentasView extends HorizontalLayout{

	private static final long serialVersionUID = -5392137733638473893L;

	public CuentasView() {
		this.setSizeFull();
		
		this.add(new H1("Cuentas"));
	}

}
