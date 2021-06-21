package com.juanseb.bank.views.form;

import java.time.LocalDate;

import com.juanseb.bank.backend.model.Categoria;
import com.juanseb.bank.backend.model.Movimiento;
import com.juanseb.bank.backend.model.Tarjeta;
import com.juanseb.bank.backend.model.TipoMovimiento;
import com.juanseb.bank.backend.model.Usuario;
import com.juanseb.bank.backend.service.CategoriaService;
import com.juanseb.bank.backend.service.TarjetaService;
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

public class MovimientoForm extends Dialog{

	private static final long serialVersionUID = -1752806202024951575L;

	private CategoriaService categoriaService;
	private UsuarioService usuarioService;
	private TarjetaService tarjetaService;
	
	public FORM_ACTION action;
	
	private Movimiento movimiento = new Movimiento();
	private FormLayout movimientoData = new FormLayout();
	
	private Usuario usuarioActual;
	
	private Long idCuenta;
	
	public boolean editar = false;
	
	
	Binder<Movimiento> binder = new BeanValidationBinder<Movimiento>(Movimiento.class);

	private ComboBox<TipoMovimiento> tipoMovimineto;
    private TextField conceptoMovimiento;
	private ComboBox<Categoria> categoriaMovimiento;
	private DatePicker fechaMovimiento;
	private ComboBox<Usuario> usuarioMovimiento;
    private NumberField cantidadMovimiento;
    private ComboBox<Tarjeta> tarjetaMovimiento;
    
    public MovimientoForm(Long idCuenta, CategoriaService categoriaService, UsuarioService usuarioService, TarjetaService tarjetaService,Movimiento movimiento) {
    	super();
    	action = FORM_ACTION.CANCEL;
		setCloseOnEsc(true);
		setCloseOnOutsideClick(false);
		
		this.usuarioActual = usuarioService.obtenerUsuarioActualConectado().get();
		this.idCuenta = idCuenta;
    	this.categoriaService = categoriaService;
    	this.usuarioService = usuarioService;
    	this.tarjetaService = tarjetaService;
    	
    	
    	createMoviminetoEditor();
    	
    	createBinder();
    	if(movimiento != null) {
    		this.movimiento = movimiento;
    		binder.readBean(this.movimiento);
    		this.editar = true;
    	}
    	
    	HorizontalLayout confirmLayout = confirmButtons();
    	
    	// Add the form layout and the button layout to the Dialog
		 add(movimientoData,confirmLayout);
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
    
	private void createBinder() {
			binder.bindInstanceFields(this);
		}

	private void createMoviminetoEditor() {
		
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
        
        tarjetaMovimiento = new ComboBox<Tarjeta>();
        tarjetaMovimiento.setId("tarjeta");
        tarjetaMovimiento.setLabel("Tarjeta");
        tarjetaMovimiento.setItemLabelGenerator(Tarjeta::getNumeroEnmascarado);
        tarjetaMovimiento.setItems(this.tarjetaService.obtenerTarjetaByCuenta(this.idCuenta));
        setColspan(tarjetaMovimiento, 1);
        binder.forField(tarjetaMovimiento).bind(Movimiento::getTarjeta,Movimiento::setTarjeta);
        
        usuarioMovimiento = new ComboBox<Usuario>();
        usuarioMovimiento.setId("usuario");
        usuarioMovimiento.setLabel("Usuario");
        usuarioMovimiento.setItemLabelGenerator(Usuario::getNombreCorto);
        usuarioMovimiento.setItems(this.usuarioService.obtenerTodosUsuariosEnCuenta(this.idCuenta));
        usuarioMovimiento.setRequiredIndicatorVisible(Utils.isPrincipal(this.usuarioActual));
        usuarioMovimiento.setValue(this.usuarioActual);
        usuarioMovimiento.setVisible(Utils.isPrincipal(this.usuarioActual));
        setColspan(usuarioMovimiento, 1);
        binder.forField(usuarioMovimiento).asRequired("El usuario es obligatoria")
        	.bind(Movimiento::getUsuario,Movimiento::setUsuario);
        
        movimientoData = new FormLayout(tipoMovimineto,conceptoMovimiento,cantidadMovimiento,categoriaMovimiento,fechaMovimiento,
        		tarjetaMovimiento,usuarioMovimiento);
        movimientoData.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 4));
	}
	

	/**
	 * Set the col span from the fields into the form
	 * @param component component to set the col span
	 * @param colspan size of the col span
	 */
	private void setColspan(Component component, int colspan) {
		component.getElement().setAttribute("colspan", Integer.toString(colspan));
	}

	public Movimiento getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(Movimiento movimiento) {
		this.movimiento = movimiento;
	}

	public FORM_ACTION getAction() {
		return action;
	}

	public void setAction(FORM_ACTION action) {
		this.action = action;
	}

	
}
