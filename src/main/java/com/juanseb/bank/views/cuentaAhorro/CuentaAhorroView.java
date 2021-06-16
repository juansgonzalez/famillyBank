package com.juanseb.bank.views.cuentaAhorro;

import java.util.ArrayList;
import java.util.List;

import com.juanseb.bank.backend.model.CuentaAhorro;
import com.juanseb.bank.backend.service.CuentaAhorroService;
import com.juanseb.bank.backend.service.MovimientoService;
import com.juanseb.bank.views.main.MainView;
import com.juanseb.views.components.CuentaAhorroDisplayBox;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "cuentaAhorro", layout = MainView.class)
@PageTitle("Cuentas Ahorro")
public class CuentaAhorroView extends VerticalLayout{

	private static final long serialVersionUID = -3620817856420812391L;
	
	private CuentaAhorroService cuentaAhorroService;
	
	private MovimientoService movimientoService;
	
	private List<CuentaAhorro> listaCuentasAhorroByCuenta = new ArrayList<>(0);
	
	private Long idCuenta;

	public CuentaAhorroView(CuentaAhorroService cuentaAhorroService, MovimientoService movimientoService) {
		this.cuentaAhorroService = cuentaAhorroService;
		this.movimientoService = movimientoService;
		init();
		add(new H2("Cuentas Ahorro"), crearCartasCuentaAhorro());

	}

	private VerticalLayout crearCartasCuentaAhorro() {
		VerticalLayout vl = new VerticalLayout();
        HorizontalLayout hl = new HorizontalLayout();
        int numCardsPerRow = 4;
        int contador = 0;

        for (CuentaAhorro cuentaAhorro: this.listaCuentasAhorroByCuenta){
            contador++;

            if(contador <= numCardsPerRow){
                hl.add(new CuentaAhorroDisplayBox(cuentaAhorro, this.movimientoService));
            }else{
                contador = 1;
                vl.add(hl);
                hl = new HorizontalLayout();
                hl.add(new CuentaAhorroDisplayBox(cuentaAhorro, this.movimientoService));
            }
        }

        vl.add(hl);
        return vl;		
	}

	private void init() {
		this.idCuenta = (Long) UI.getCurrent().getSession().getAttribute("idCuenta");
		
		this.setSizeFull();
		
		this.listaCuentasAhorroByCuenta = this.cuentaAhorroService.obtenerCuentaAhorroByCuenta(idCuenta);
	}
	
}
