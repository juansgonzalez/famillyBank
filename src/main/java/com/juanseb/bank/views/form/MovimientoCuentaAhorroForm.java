package com.juanseb.bank.views.form;

import java.time.LocalDate;

import com.juanseb.bank.backend.model.Categoria;
import com.juanseb.bank.backend.model.CuentaAhorro;
import com.juanseb.bank.backend.model.Movimiento;
import com.juanseb.bank.backend.model.Usuario;
import com.juanseb.bank.backend.model.enumerado.TipoMovimiento;
import com.juanseb.bank.backend.service.CategoriaService;
import com.juanseb.bank.backend.service.UsuarioService;
import com.juanseb.bank.backend.utils.Utils;
import com.juanseb.bank.views.enums.FORM_ACTION;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

public class MovimientoCuentaAhorroForm extends Dialog{

	private static final long serialVersionUID = 4578892836029812434L;
	
	private FORM_ACTION action = FORM_ACTION.CANCEL;
	
	private UsuarioService usuarioService;
	private CategoriaService categoriaService;
	
	private Movimiento movimiento = new Movimiento();;
	private Usuario usuario;
	private CuentaAhorro cuentaAhorro;
	
	private FormLayout movimientoData = new FormLayout();
	
	Binder<Movimiento> binder = new BeanValidationBinder<Movimiento>(Movimiento.class);

	private ComboBox<TipoMovimiento> tipoMovimineto;
    private TextField conceptoMovimiento;
	private ComboBox<Categoria> categoriaMovimiento;
	private DatePicker fechaMovimiento;
    private NumberField cantidadMovimiento;

	public MovimientoCuentaAhorroForm(CuentaAhorro cuentaAhorro, UsuarioService usuarioService, CategoriaService categoriaService) {
		super();
		this.usuarioService = usuarioService;
		this.categoriaService = categoriaService;
		init(cuentaAhorro);
		binder.bindInstanceFields(movimientoData);
		add(movimientoData,confirmButtons());
	}

	private void init(CuentaAhorro cuentaAhorro) {
    	action = FORM_ACTION.CANCEL;
		setCloseOnEsc(true);
		setCloseOnOutsideClick(false);
		
		
		this.cuentaAhorro = cuentaAhorro;
		this.usuario = Utils.getCurrentUser(usuarioService).get();
		
		tipoMovimineto = new ComboBox<TipoMovimiento>();
		tipoMovimineto.setId("tipo");
		tipoMovimineto.setLabel("Tipo Movimineto");
		tipoMovimineto.setItems(TipoMovimiento.INGRESO,TipoMovimiento.GASTO);
		tipoMovimineto.setValue(TipoMovimiento.GASTO);
        tipoMovimineto.setRequiredIndicatorVisible(true);
        setColspan(tipoMovimineto, 1);
        binder.forField(tipoMovimineto).asRequired("El tipo de movimiento es obligatoria")
        .bind(Movimiento::getTipo,Movimiento::setTipo);

		conceptoMovimiento = new TextField("Concepto");
		conceptoMovimiento.setRequired(false);
		conceptoMovimiento.setId("concepto");
        setColspan(conceptoMovimiento, 2);
        binder.forField(conceptoMovimiento)
        .bind(Movimiento::getConcepto,Movimiento::setConcepto);
        
        cantidadMovimiento = new NumberField("Cantidad");
        cantidadMovimiento.setId("cantidad");
        cantidadMovimiento.setPrefixComponent(new Icon(VaadinIcon.EURO));
        cantidadMovimiento.setRequiredIndicatorVisible(true);
        binder.forField(cantidadMovimiento).asRequired("La Cantidad en obligatoria")
        	.bind(Movimiento::getCantidad,Movimiento::setCantidad);
        setColspan(cantidadMovimiento, 1);
        
        categoriaMovimiento = new ComboBox<Categoria>();
        categoriaMovimiento.setId("categoria");
        categoriaMovimiento.setLabel("Categoria");
        categoriaMovimiento.setItemLabelGenerator(Categoria::getNombre);
        categoriaMovimiento.setItems(this.categoriaService.obtenerTodasCategorias());
        categoriaMovimiento.setRequiredIndicatorVisible(true);
        categoriaMovimiento.setVisible(false);
        categoriaMovimiento.setValue(categoriaService.obtenerCategoriaById(6l).get());
        setColspan(categoriaMovimiento, 1);
        binder.forField(categoriaMovimiento).asRequired("La categoria es obligatoria")
        	.bind(Movimiento::getCategoria,Movimiento::setCategoria);
        
        fechaMovimiento = new DatePicker();
        fechaMovimiento.setLabel("Fecha");
        fechaMovimiento.setValue(LocalDate.now());
        setColspan(fechaMovimiento, 1);
        fechaMovimiento.setRequiredIndicatorVisible(true);
        binder.forField(fechaMovimiento).asRequired("La fecha el obligatoria")
        	.bind(Movimiento::getFechaLocal,Movimiento::setFechaLocal);
        
        movimientoData = new FormLayout(tipoMovimineto,conceptoMovimiento,cantidadMovimiento,categoriaMovimiento,
        		fechaMovimiento);
        movimientoData.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 3));

	}
	
	private void setColspan(Component component, int colspan) {
		component.getElement().setAttribute("colspan", Integer.toString(colspan));
	}
	
	private HorizontalLayout confirmButtons() {
		// Initialize the layout and setting some properties
		HorizontalLayout botones = new HorizontalLayout();
    	botones.setWidthFull();
    	
    	// Create the cancel button with the click event
    	Button botonCancelar = new Button("Cancel", ClickEvent ->{
			// set the action of the form to save
    		setAction(FORM_ACTION.CANCEL);
			close();
    	});
    	
    	// Set some style to the cancel button
    	botonCancelar.getElement().getStyle().set("margin-top", "auto");
    	botonCancelar.getElement().getStyle().set("margin-right", "auto");
    	botonCancelar.getElement().getStyle().set("cursor", "pointer");
    	botonCancelar.getElement().getStyle().set("color", "red");
    	

    	// Create the Save button with the click event
    	Button botonAceptar = new Button("Save", ClickEvent ->{
    			// fill the product with the new data
				if(binder.writeBeanIfValid(this.movimiento)) {
					this.movimiento.setCuentaAhorro(this.cuentaAhorro);
					this.movimiento.setUsuario(this.usuario);
					this.movimiento.setCuenta(cuentaAhorro.getCuenta());
					this.movimiento.setSaldoActual(cuentaAhorro.getCuenta().getSaldo());
					// set the action of the form to save
					setAction(FORM_ACTION.SAVE);
					
					// close the Dialog
					close();					
				}
				
		   
    	});
    	
    	// Set some style to the cancel button
    	botonAceptar.getElement().getStyle().set("margin-left", "auto");
    	botonAceptar.getElement().getStyle().set("cursor", "pointer");
    	botonAceptar.getElement().getStyle().set("color", "green");

    	// Add the buttons to the layout
    	botones.add(botonCancelar);
    	botones.add(botonAceptar);
    	
		return botones;
	}


	public FORM_ACTION getAction() {
		return action;
	}

	public void setAction(FORM_ACTION action) {
		this.action = action;
	}

	public Movimiento getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(Movimiento movimiento) {
		this.movimiento = movimiento;
	}

	
}
