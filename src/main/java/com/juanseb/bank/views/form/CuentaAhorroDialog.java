package com.juanseb.bank.views.form;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import com.juanseb.bank.backend.model.CuentaAhorro;
import com.juanseb.bank.backend.model.Movimiento;
import com.juanseb.bank.backend.service.CategoriaService;
import com.juanseb.bank.backend.service.MovimientoService;
import com.juanseb.bank.backend.service.UsuarioService;
import com.juanseb.bank.views.enums.FORM_ACTION;
import com.juanseb.views.components.IconoMovimientoTarjeta;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dialog.GeneratedVaadinDialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.SortDirection;

public class CuentaAhorroDialog extends Dialog{

	private static final long serialVersionUID = -6101667485866619594L;
	
	private MovimientoService movimientoService;
	private UsuarioService usuarioService;
	private CategoriaService categoriaservice;
	
	private Grid<Movimiento> grid;

	private List<Movimiento> movimientosList;
	
	private FormLayout cuentaAhorroData = new FormLayout();

	private CuentaAhorro cuentaAhorro;
	
	private MovimientoCuentaAhorroForm movimientoForm;
	
	private TextField nombreCuentaAhorro;
	private NumberField saldoCuentaAhorro;
	private Button botonCrearNuevo;

	public CuentaAhorroDialog(MovimientoService movimientoService, UsuarioService usuarioService,CategoriaService categoriaservice, CuentaAhorro cuentaAhorro) {
		this.movimientoService = movimientoService;
		this.cuentaAhorro = cuentaAhorro;
		this.usuarioService = usuarioService;
		this.categoriaservice = categoriaservice;
		
		init();
		createGrid();
		
		add(new H3("Cuenta Ahorro"),cuentaAhorroData, new Hr(),new H3("Movimientos"),grid);
	}

	private void init() {
		LocalDate init = LocalDate.of(LocalDate.now().getYear(), 1, 1);
		LocalDate fin = LocalDate.of(LocalDate.now().getYear(), 12, 31);
		
		movimientosList = movimientoService.obtenerMovimientosDeCuentaByCuentaAhorro(cuentaAhorro.getId(), init, fin);
		
		movimientoForm = new MovimientoCuentaAhorroForm(this.cuentaAhorro, this.usuarioService, this.categoriaservice);
		movimientoForm.addOpenedChangeListener(new ComponentEventListener<GeneratedVaadinDialog.OpenedChangeEvent<Dialog>>() {

			private static final long serialVersionUID = 28693697138061208L;

			@Override
			public void onComponentEvent(OpenedChangeEvent<Dialog> event) {
				if(movimientoForm.getAction().equals(FORM_ACTION.SAVE)) {
					movimientoService.save(movimientoForm.getMovimiento());
					refreshGrid();
				}
			}
		});
		
		nombreCuentaAhorro = new TextField("Nombre Cuenta Ahorro");
		nombreCuentaAhorro.setId("nombreCuentaAhorro");
		nombreCuentaAhorro.setValue(this.cuentaAhorro.getNombre());
		nombreCuentaAhorro.setEnabled(false);
		
		saldoCuentaAhorro = new NumberField("Saldo Cuenta Ahorro");
		saldoCuentaAhorro.setId("saldoCuentaAhorro");
		saldoCuentaAhorro.setValue(this.cuentaAhorro.getSaldo());
		saldoCuentaAhorro.setEnabled(false);
		
		botonCrearNuevo = new Button("Crear movimiento",clickAction -> {
			movimientoForm.open();
		});
        botonCrearNuevo.getElement().getStyle().set("background-color", "#D01E69");
        botonCrearNuevo.getElement().getStyle().set("color", "white");
        botonCrearNuevo.getElement().getStyle().set("cursor", "pointer");
        
	
        
        cuentaAhorroData = new FormLayout(nombreCuentaAhorro, saldoCuentaAhorro,botonCrearNuevo);
        cuentaAhorroData.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 3));
	}
	
	private void createGrid() {
		this.grid = new Grid<>();
		this.grid.setWidthFull();
		this.grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,GridVariant.LUMO_ROW_STRIPES);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		this.grid.addComponentColumn(c -> new IconoMovimientoTarjeta(c))
		.setWidth("100px").setHeader("Tarjeta").setFlexGrow(1);
		this.grid.addColumn(c -> c.getCantidad()+" €").setHeader("Cantidad").setFlexGrow(1);
		this.grid.addColumn(c -> c.getConcepto()).setHeader("Concepto").setFlexGrow(1);
		this.grid.addColumn(c -> dateFormat.format(c.getFecha())).setHeader("Fecha").setWidth("125px").setFlexGrow(0);

        setDataProvider();
	}
	
	
	private void setDataProvider(){
		ListDataProvider<Movimiento> listDataProvider;
		listDataProvider = new ListDataProvider<>(movimientosList);
		listDataProvider.setSortOrder(Movimiento::getId, SortDirection.DESCENDING);
		this.grid.setDataProvider(listDataProvider);
	}
	
	
	private void refreshGrid() {
		LocalDate init = LocalDate.of(LocalDate.now().getYear(), 1, 1);
		LocalDate fin = LocalDate.of(LocalDate.now().getYear(), 12, 31);
    	this.grid.setDataProvider(new ListDataProvider<>(this.movimientoService.obtenerMovimientosDeCuentaByCuentaAhorro(cuentaAhorro.getId(), init, fin)));
        
	}

}
