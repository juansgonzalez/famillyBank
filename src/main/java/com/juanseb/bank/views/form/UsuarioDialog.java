package com.juanseb.bank.views.form;

import java.text.SimpleDateFormat;
import java.util.List;

import com.juanseb.bank.backend.model.Cuenta;
import com.juanseb.bank.backend.model.Movimiento;
import com.juanseb.bank.backend.model.Usuario;
import com.juanseb.bank.backend.service.CuentaService;
import com.juanseb.bank.backend.service.MovimientoService;
import com.juanseb.bank.backend.service.UsuarioService;
import com.juanseb.bank.components.IconoMovimientoTarjeta;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.SortDirection;

public class UsuarioDialog extends Dialog{
	
	private UsuarioService usuarioService;
    private MovimientoService movimientoService;

    private Grid<Movimiento> grid;

    private Usuario usuario;
    
    private List<Movimiento> movimientosList;

    private FormLayout usuarioData = new FormLayout();
    private TextField nombreUsuario;
    private TextField usuarioSaldo;


    public UsuarioDialog(UsuarioService usuarioService, MovimientoService movimientoService,Long idCuenta, Long idUsuario) {
        super();
        this.movimientoService = movimientoService;
        this.usuarioService = usuarioService;
        this.usuario = usuarioService.obtenerUsuarioById(idUsuario).get();

    	this.movimientosList = movimientoService.obtenerMovimientosDeCuentaByUsuarioOrdenadosFecha(idCuenta,idUsuario);        	

        setCloseOnEsc(true);
        setWidth("50%");


        createFormTarjeta();
        createGrid();

        add(new H3("Datos Cuenta"),usuarioData, new Hr(),new H3("Movimientos"),grid);

    }

    private void createFormTarjeta() {

        nombreUsuario = new TextField("Cuenta");
        nombreUsuario.setId("cuentaIban");
        nombreUsuario.setEnabled(false);
        nombreUsuario.setValue(usuario.getNombreCompleto());
        setColspan(nombreUsuario, 1);

        usuarioSaldo = new TextField("Saldo");
        usuarioSaldo.setId("saldo");
        usuarioSaldo.setEnabled(false);
        usuarioSaldo.setValue(usuario.getSaldo().toString());
        setColspan(usuarioSaldo, 1);

        usuarioData = new FormLayout(nombreUsuario, usuarioSaldo);
        usuarioData.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
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
     * Crea un grid con los moviemintos de una cuenta
     * @return grid
     */
    private Grid<Movimiento> createGrid() {
        grid = new Grid<>();
        grid.setWidthFull();
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,GridVariant.LUMO_ROW_STRIPES);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        grid.addComponentColumn(movimiento -> new IconoMovimientoTarjeta(movimiento)).setHeader("Tarjeta").setFlexGrow(1);
        grid.addColumn(movimiento -> movimiento.getCantidad()+" €").setHeader("Cantidad").setFlexGrow(1);
        grid.addColumn(movimiento -> movimiento.getConcepto()).setHeader("Concepto").setFlexGrow(1);
        grid.addColumn(movimiento -> dateFormat.format(movimiento.getFecha())).setHeader("Fecha").setWidth("125px").setFlexGrow(0);

        setDataProvider();

        return grid;
    }


    /**
     * Establece el data provider y ordena por fecha los movimientos.
     */
    private void setDataProvider(){
        ListDataProvider<Movimiento> listDataProvider;
        listDataProvider = new ListDataProvider<>(movimientosList);
        listDataProvider.setSortOrder(Movimiento::getFecha, SortDirection.DESCENDING);
        this.grid.setDataProvider(listDataProvider);
    }


}
