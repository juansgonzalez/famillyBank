package com.juanseb.bank.views.form;

import com.juanseb.bank.backend.model.Cuenta;
import com.juanseb.bank.backend.model.CuentaAhorro;
import com.juanseb.bank.views.enums.FORM_ACTION;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

public class CuentaAhorroForm extends Dialog{

	private static final long serialVersionUID = -1752806202024951575L;
	
	public FORM_ACTION action;
	
	private CuentaAhorro cuentaAhorro = new CuentaAhorro();
	private FormLayout cuentaAhorroData = new FormLayout();
	
	private Cuenta cuenta;
	
	
	
	Binder<CuentaAhorro> binder = new BeanValidationBinder<CuentaAhorro>(CuentaAhorro.class);

    private TextField nombreCuentaAhorro;
    private NumberField saldoCuentaAhorro;
    
    public CuentaAhorroForm(Cuenta cuenta) {
    	super();
    	action = FORM_ACTION.CANCEL;
		setCloseOnEsc(true);
		setCloseOnOutsideClick(false);
		
		this.cuenta = cuenta;
    	
    	
    	createMoviminetoEditor();
    	
    	createBinder();
    	
    	HorizontalLayout confirmLayout = confirmButtons();
    	
    	// Add the form layout and the button layout to the Dialog
		 add(new H2("Crear Nueva Cuenta Ahorro"),cuentaAhorroData,confirmLayout);
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
				if(binder.writeBeanIfValid(this.cuentaAhorro)) {
					cuentaAhorro.setCuenta(this.cuenta);
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
		
		nombreCuentaAhorro = new TextField("Nombre Cuenta");
		nombreCuentaAhorro.setId("nombreCuenta");
        nombreCuentaAhorro.setRequiredIndicatorVisible(true);
        setColspan(nombreCuentaAhorro, 1);
        binder.forField(nombreCuentaAhorro).asRequired("El nombre de la cuenta de ahorro es obligatoria")
        .bind(CuentaAhorro::getNombre,CuentaAhorro::setNombre);
        
        saldoCuentaAhorro = new NumberField("Cantidad");
        saldoCuentaAhorro.setId("cantidad");
        saldoCuentaAhorro.setPrefixComponent(new Icon(VaadinIcon.EURO));
        saldoCuentaAhorro.setRequiredIndicatorVisible(true);
        binder.forField(saldoCuentaAhorro).asRequired("El saldo en obligatoria")
        	.bind(CuentaAhorro::getSaldo,CuentaAhorro::setSaldo);
        setColspan(saldoCuentaAhorro, 1);
        
        cuentaAhorroData = new FormLayout(nombreCuentaAhorro,saldoCuentaAhorro);
        cuentaAhorroData.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
	}
	

	/**
	 * Set the col span from the fields into the form
	 * @param component component to set the col span
	 * @param colspan size of the col span
	 */
	private void setColspan(Component component, int colspan) {
		component.getElement().setAttribute("colspan", Integer.toString(colspan));
	}

	public CuentaAhorro getCuentaAhorro() {
		return cuentaAhorro;
	}

	public void setCuentaAhorro(CuentaAhorro cuentaAhorro) {
		this.cuentaAhorro = cuentaAhorro;
	}

	public FORM_ACTION getAction() {
		return action;
	}

	public void setAction(FORM_ACTION action) {
		this.action = action;
	}

	
}
