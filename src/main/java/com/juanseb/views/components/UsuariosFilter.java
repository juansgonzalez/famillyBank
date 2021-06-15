package com.juanseb.views.components;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.juanseb.bank.backend.model.Cuenta;
import com.juanseb.bank.backend.model.Usuario;
import com.juanseb.bank.backend.service.MovimientoService;
import com.juanseb.bank.backend.service.UsuarioCuentaService;
import com.juanseb.bank.backend.service.UsuarioService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class UsuariosFilter extends Dialog{

	private static final long serialVersionUID = -3040428320119782876L;
	
	private UsuarioService usuarioService;
	private MovimientoService movimientoService;
	private UsuarioCuentaService usuarioCuentaService;
	
	private VerticalLayout vl = new VerticalLayout();

	private TextField userName;
	private Button buscarButton;

	private List<Usuario> listaUsuarios = new ArrayList<Usuario>();
	private Cuenta cuenta;

	public UsuariosFilter(UsuarioService usuarioService, MovimientoService movimientoService, UsuarioCuentaService usuarioCuentaService, Cuenta cuenta) {
		this.setWidth("70%");
		this.setHeight("70%");
		
		this.cuenta = cuenta;
		
		this.usuarioService = usuarioService;
		this.movimientoService = movimientoService;
		this.usuarioCuentaService = usuarioCuentaService;
		
		this.userName = new TextField("Nombre de usuario");
		this.userName.setAutofocus(true);
		this.userName.setId("username");
		this.userName.getElement().setAttribute("colspan", Integer.toString(1));
		
		this.buscarButton = new Button("Buscar", ClickListener ->{
			try {
				listaUsuarios = usuarioService.obtenerUsuarioFilterUsername(userName.getValue());
				try {
					this.remove(vl);				
				}catch(Exception e) {
					
				}
				pintarCuadrosUsuarioFiltrado();
				this.add(vl);
//			UI.getCurrent().getPage().reload();
				
			}catch(EntityNotFoundException e) {
				new ColorNotification(e.getMessage(), "red").open();
			}
		});
		
		buscarButton.getElement().getStyle().set("margin-left", "auto");
        buscarButton.getElement().getStyle().set("margin-top", "auto");
        buscarButton.getElement().getStyle().set("background-color", "#D01E69");
        buscarButton.getElement().getStyle().set("color", "white");
        buscarButton.getElement().getStyle().set("cursor", "pointer");
		
		pintarCuadrosUsuarioFiltrado();
		
        add(userName,buscarButton,vl);
	}


	private void pintarCuadrosUsuarioFiltrado() {
		vl = new VerticalLayout();
        HorizontalLayout hl = new HorizontalLayout();
        int numCardsPerRow = 3;  // en cada layout horizontal habrá 3 cards como máximo.
        int contador = 0;

        for (Usuario usuario: this.listaUsuarios){
            contador++;

            if(contador <= numCardsPerRow){
            	CardUsuarioSelect nueva = crearTarjeta(usuario);
                hl.add(nueva);
            }else{
                contador = 1;
                vl.add(hl);
                hl = new HorizontalLayout();
                CardUsuarioSelect nueva = crearTarjeta(usuario);
                hl.add(nueva);
            }
        }

        vl.add(hl);		
	}


	private CardUsuarioSelect crearTarjeta(Usuario usuario) {
		CardUsuarioSelect nueva = new CardUsuarioSelect(this.cuenta, usuario, this.usuarioService, this.movimientoService, this.usuarioCuentaService);
		
		return nueva;
	}

}
