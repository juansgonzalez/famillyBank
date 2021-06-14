package com.juanseb.views.components;

import java.util.List;

import com.juanseb.bank.backend.model.Cuenta;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.data.provider.ListDataProvider;

public class CuentaSelectComponent extends Dialog{

	private static final long serialVersionUID = -8925013148024248375L;

	private Grid<Cuenta> grid;
	
	private Cuenta cuenta = null;
	
	public CuentaSelectComponent(List<Cuenta> listaCuentas, String nombreUsuario) {
		super();
		setWidth("70%");
		setCloseOnEsc(false);
		setCloseOnOutsideClick(false);
		createGrid();
		
		grid.setDataProvider(new ListDataProvider<>(listaCuentas));
		
		grid.addItemClickListener(event -> {
			cuenta = event.getItem();
			this.close();
		});
		
		
		
		add(new H1("Hola "+ nombreUsuario +" :"), new H2("Seleccione la cuenta a la que quiere acceder:"),grid);
	}

    /**
     * Método que se encarga de crear un Grid con los datos de las cuentas
     * @return Devuelve un grid con las cuentas
     */
	private Grid<Cuenta> createGrid() {
		grid = new Grid<>();
		grid.setWidthFull();
		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,GridVariant.LUMO_ROW_STRIPES);
		
		grid.addColumn(c -> c.getUsuarioPrincipal().getNombreCorto()).setHeader("Usuario Principal").setFlexGrow(1);
		grid.addColumn(c -> c.getIban()).setHeader("Iban").setFlexGrow(1);
        grid.addColumn(c -> c.getSaldo()).setHeader("Saldo").setFlexGrow(1);

        return grid;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}
	
}
