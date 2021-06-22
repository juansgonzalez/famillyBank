package com.juanseb.bank.views.form;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import com.juanseb.bank.backend.model.Movimiento;
import com.juanseb.bank.backend.model.Usuario;
import com.juanseb.bank.backend.service.MovimientoService;
import com.juanseb.bank.backend.service.UsuarioCuentaService;
import com.juanseb.bank.backend.service.UsuarioService;
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
import com.vaadin.flow.data.provider.SortDirection;

public class UsuarioDialog extends Dialog{
	
	private static final long serialVersionUID = -8693423721484026173L;
	
	private UsuarioService usuarioService;
    private MovimientoService movimientoService;
    private UsuarioCuentaService usuarioCuentaService;

    private Grid<Movimiento> grid;

    private Usuario usuario;
    
    private List<Movimiento> movimientosList;

    private FormLayout usuarioData = new FormLayout();
    private TextField nombreUsuario;
    private TextField usuarioSaldo;
    
    private Long idCuenta;
    private Long idUsuario;


    public UsuarioDialog(UsuarioService usuarioService, MovimientoService movimientoService,Long idCuenta, Long idUsuario, UsuarioCuentaService usuarioCuentaService) {
        super();
        this.movimientoService = movimientoService;
        this.usuarioService = usuarioService;
        this.usuarioCuentaService = usuarioCuentaService;
        
        this.idCuenta = idCuenta;
        this.idUsuario = idUsuario;
        
        this.usuario = this.usuarioService.obtenerUsuarioById(idUsuario).get();

    	this.movimientosList = this.movimientoService.obtenerMovimientosDeCuentaByUsuarioOrdenadosFecha(idCuenta,idUsuario);        	

        setCloseOnEsc(true);
        setWidth("50%");


        createFormUsuario();
        createGrid();

        add(new H3("Datos Cuenta"),usuarioData, new Hr(),new H3("Movimientos"),grid);

    }

    private void createFormUsuario() {

        nombreUsuario = new TextField("Usuario");
        nombreUsuario.setId("nombreUsuario");
        nombreUsuario.setEnabled(false);
        nombreUsuario.setValue(usuario.getNombreCompleto());
        setColspan(nombreUsuario, 1);

        usuarioSaldo = new TextField("Saldo");
        usuarioSaldo.setId("saldo");
        usuarioSaldo.setEnabled(false);
        usuarioSaldo.setValue(Utils.formatearSaldo(Utils.obtenerSaldoEnCuenta(idCuenta, idUsuario, usuarioCuentaService)));
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
