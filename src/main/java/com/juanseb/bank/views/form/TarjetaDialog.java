package com.juanseb.bank.views.form;

import java.text.SimpleDateFormat;
import java.util.List;

import com.vaadin.flow.data.provider.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;

import com.juanseb.bank.backend.model.Movimiento;
import com.juanseb.bank.backend.model.Tarjeta;
import com.juanseb.bank.backend.model.Usuario;
import com.juanseb.bank.backend.service.MovimientoService;
import com.juanseb.bank.backend.service.TarjetaService;
import com.juanseb.bank.backend.utils.Utils;
import com.juanseb.views.components.IconoMovimientoTarjeta;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;

public class TarjetaDialog extends Dialog{

	private static final long serialVersionUID = -4191342611264243074L;

	@Autowired
	private TarjetaService tarjetaService;

	@Autowired
	private MovimientoService movimientoService;
	
	private Grid<Movimiento> grid;

	private List<Movimiento> movimientosList;
	
	private FormLayout tarjetaData = new FormLayout();
	
	private Tarjeta tarjeta;
	
	private Usuario usuarioActual;
	
	private TextField tarjetaNumber;
	
	private TextField tarjetaCuentaIban;
	
	
	public TarjetaDialog(MovimientoService movimientoService, TarjetaService tarjetaService,Usuario usuarioActual,Long idTarjeta) {
		super();
		this.movimientoService = movimientoService;
		this.tarjetaService = tarjetaService;
		this.usuarioActual = usuarioActual;
		
		if(Utils.isPrincipal(usuarioActual)) {
			this.movimientosList =this.movimientoService.obtenerMovimientosDeTarjeta(idTarjeta);			
		}else {
			this.movimientosList = this.movimientoService.obtenerMovimientosDeTarjetaByUsuario(idTarjeta,this.usuarioActual.getId());
		}
		setCloseOnEsc(true);
		setWidth("50%");

		tarjeta = this.tarjetaService.obtenerTarjetaById(idTarjeta);
		
		createFormTarjeta();
		createGrid();

		add(new H3("Datos Tarjeta"),tarjetaData, new Hr(),new H3("Movimientos"),grid);

		
	}

	private void createFormTarjeta() {
		
		tarjetaNumber = new TextField("Numero Tarjeta");
		tarjetaNumber.setId("tarjetaNumber");
		tarjetaNumber.setEnabled(false);
		tarjetaNumber.setValue(tarjeta.getNumero().toString());
		setColspan(tarjetaNumber, 1);
		
		tarjetaCuentaIban = new TextField("Iban Cuenta");
		tarjetaCuentaIban.setId("iban");
		tarjetaCuentaIban.setEnabled(false);
		tarjetaCuentaIban.setValue(tarjeta.getCuenta().getIban());
		setColspan(tarjetaCuentaIban, 1);
		
		
        tarjetaData = new FormLayout(tarjetaNumber, tarjetaCuentaIban);
        tarjetaData.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
		
	}
	
	/**
	 * Set the col span from the fields into the form
	 * @param component component to set the col span
	 * @param colspan size of the col span
	 */
	private void setColspan(Component component, int colspan) {
		component.getElement().setAttribute("colspan", Integer.toString(colspan));
	}
	
    /**
     * Metodo que se encarga de crear un Grid para la visualizacion de los datos de un movimiento
     * @return Devuelve un grid con los campos para representar Movimientos
     */
	private Grid<Movimiento> createGrid() {
		grid = new Grid<>();
		grid.setWidthFull();
		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,GridVariant.LUMO_ROW_STRIPES);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		grid.addComponentColumn(c -> new IconoMovimientoTarjeta(c))
		.setWidth("100px").setHeader("Tarjeta").setFlexGrow(1);
		grid.addColumn(c -> c.getCantidad()+" €").setHeader("Cantidad").setFlexGrow(1);
        grid.addColumn(c -> c.getConcepto()).setHeader("Concepto").setFlexGrow(1);
        if(Utils.isPrincipal(usuarioActual)) {
        	grid.addColumn(c -> c.getUsuario().getNombreCorto()).setHeader("Usuario").setFlexGrow(1);        	
        }
        grid.addColumn(c -> dateFormat.format(c.getFecha())).setHeader("Fecha").setWidth("125px").setFlexGrow(0);

        setDataProvider();

        return grid;
	}

	/**
	 * Establece el data provider y ordena por fecha los movimientos.
	 */
	private void setDataProvider(){
		ListDataProvider<Movimiento> listDataProvider;
		listDataProvider = new ListDataProvider<>(movimientosList);
		listDataProvider.setSortOrder(Movimiento::getId, SortDirection.DESCENDING);
		this.grid.setDataProvider(listDataProvider);
	}

}
