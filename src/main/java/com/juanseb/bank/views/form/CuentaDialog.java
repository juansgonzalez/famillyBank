package com.juanseb.bank.views.form;

import com.juanseb.bank.backend.model.Cuenta;
import com.juanseb.bank.backend.model.Movimiento;
import com.juanseb.bank.backend.service.CuentaService;
import com.juanseb.bank.backend.service.MovimientoService;
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

import java.text.SimpleDateFormat;
import java.util.List;

public class CuentaDialog extends Dialog {

    private CuentaService cuentaService;
    private MovimientoService movimientoService;

    private Grid<Movimiento> grid;

    private Cuenta cuenta;
    private List<Movimiento> movimientosList;

    private FormLayout cuentaData = new FormLayout();
    private TextField cuentaIban;
    private TextField cuentaSaldo;


    public CuentaDialog(CuentaService cuentaService, MovimientoService movimientoService, Long idCuenta) {
        super();
        this.movimientoService = movimientoService;
        this.cuentaService = cuentaService;
        this.movimientosList = movimientoService.obtenerMovimientosDeCuenta(idCuenta);
        setCloseOnEsc(true);
        setWidth("50%");

        this.cuenta = cuentaService.obtenerCuentaById(idCuenta);

        createFormTarjeta();
        createGrid();

        add(new H3("Datos Cuenta"),cuentaData, new Hr(),new H3("Movimientos"),grid);

    }

    private void createFormTarjeta() {

        cuentaIban = new TextField("Cuenta");
        cuentaIban.setId("cuentaIban");
        cuentaIban.setEnabled(false);
        cuentaIban.setValue(cuenta.getIban());
        setColspan(cuentaIban, 1);

        cuentaSaldo = new TextField("Saldo");
        cuentaSaldo.setId("saldo");
        cuentaSaldo.setEnabled(false);
        cuentaSaldo.setValue(cuenta.getSaldo().toString());
        setColspan(cuentaSaldo, 1);

        cuentaData = new FormLayout(cuentaIban, cuentaSaldo);
        cuentaData.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
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
