package com.juanseb.bank.views.cuentaAhorro;

import java.util.ArrayList;
import java.util.List;

import com.juanseb.bank.backend.model.Cuenta;
import com.juanseb.bank.backend.model.CuentaAhorro;
import com.juanseb.bank.backend.service.CategoriaService;
import com.juanseb.bank.backend.service.CuentaAhorroService;
import com.juanseb.bank.backend.service.CuentaService;
import com.juanseb.bank.backend.service.MovimientoService;
import com.juanseb.bank.backend.service.UsuarioService;
import com.juanseb.bank.views.enums.FORM_ACTION;
import com.juanseb.bank.views.form.CuentaAhorroForm;
import com.juanseb.bank.views.main.MainView;
import com.juanseb.views.components.ColorNotification;
import com.juanseb.views.components.CuentaAhorroDisplayBox;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dialog.GeneratedVaadinDialog;
import com.vaadin.flow.component.dialog.GeneratedVaadinDialog.OpenedChangeEvent;
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
	
	private UsuarioService usuarioService;
	
	private CategoriaService categoriaService;
	
	private CuentaService cuentaService;
	
	private CuentaAhorroForm cuentaAhorroForm;
	
	private List<CuentaAhorro> listaCuentasAhorroByCuenta = new ArrayList<>(0);
	
	private Cuenta cuenta;
	
	private Long idCuenta;

	public CuentaAhorroView(CuentaAhorroService cuentaAhorroService, MovimientoService movimientoService, UsuarioService usuarioService, CategoriaService categoriaService, CuentaService cuentaService) {
		this.cuentaAhorroService = cuentaAhorroService;
		this.movimientoService = movimientoService;
		this.usuarioService = usuarioService;
		this.categoriaService = categoriaService;
		this.cuentaService = cuentaService;
		
		init();
		
		HorizontalLayout cabecera = new HorizontalLayout();
        cabecera.setWidthFull();
        cabecera.add(new H2("Cuentas Ahorro"));
        Button botonCrear = new Button("Nueva Cuenta Ahorro", ClickEvent ->{
    		openCuentaAhorroForm();
    	});
        botonCrear.getElement().getStyle().set("margin-left", "auto");
        botonCrear.getElement().getStyle().set("margin-top", "auto");
        botonCrear.getElement().getStyle().set("background-color", "#D01E69");
        botonCrear.getElement().getStyle().set("color", "white");
        botonCrear.getElement().getStyle().set("cursor", "pointer");
        
        cabecera.add(botonCrear);
		add(cabecera, crearCartasCuentaAhorro());

	}

	private void openCuentaAhorroForm() {
		this.cuenta = cuentaService.obtenerCuentaById(idCuenta);
		cuentaAhorroForm = new CuentaAhorroForm(this.cuenta);
		
		cuentaAhorroForm.open();
		
		cuentaAhorroForm.addOpenedChangeListener(new ComponentEventListener<GeneratedVaadinDialog.OpenedChangeEvent<Dialog>>() {

			private static final long serialVersionUID = 8812340255137528127L;

			@Override
			public void onComponentEvent(OpenedChangeEvent<Dialog> event) {
				if(!event.isOpened()) { // Check if the form was closed
					if(FORM_ACTION.SAVE.equals(cuentaAhorroForm.getAction())) { // Check if the form was closed with the save button

						try {
							CuentaAhorro cuentaAhorroEditable = cuentaAhorroForm.getCuentaAhorro();
							
							cuentaAhorroService.crearActualizarCuentaAhorro(cuentaAhorroEditable);
							
							new ColorNotification("Se ha creado el movimiento con exito","green").open();
							
							UI.getCurrent().getPage().reload();
						}catch(Exception e) {
							new ColorNotification("Ha ocurrido un error al crear movimiento intentelo mas tarde. Si el problema persiste notifique al administrador","red").open();
						}
					}
				}
			}
		});
		
	}

	private VerticalLayout crearCartasCuentaAhorro() {
		VerticalLayout vl = new VerticalLayout();
        HorizontalLayout hl = new HorizontalLayout();
        int numCardsPerRow = 4;
        int contador = 0;

        for (CuentaAhorro cuentaAhorro: this.listaCuentasAhorroByCuenta){
            contador++;
            if(contador <= numCardsPerRow){
                hl.add(new CuentaAhorroDisplayBox(this.cuentaAhorroService,cuentaAhorro,this.usuarioService, this.movimientoService, this.categoriaService));
            }else{
                contador = 1;
                vl.add(hl);
                hl = new HorizontalLayout();
                hl.add(new CuentaAhorroDisplayBox(this.cuentaAhorroService,cuentaAhorro,this.usuarioService, this.movimientoService, this.categoriaService));
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
