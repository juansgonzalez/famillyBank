package com.juanseb.views.components;

import java.text.DecimalFormat;

import com.github.appreciated.card.ClickableCard;
import com.juanseb.bank.backend.model.Cuenta;
import com.juanseb.bank.backend.model.Usuario;
import com.juanseb.bank.backend.model.UsuarioCuenta;
import com.juanseb.bank.backend.model.UsuarioCuentaId;
import com.juanseb.bank.backend.model.enumerado.TipoUsuarioCuenta;
import com.juanseb.bank.backend.service.MovimientoService;
import com.juanseb.bank.backend.service.UsuarioCuentaService;
import com.juanseb.bank.backend.service.UsuarioService;
import com.juanseb.bank.backend.utils.Utils;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dialog.GeneratedVaadinDialog;
import com.vaadin.flow.component.dialog.GeneratedVaadinDialog.OpenedChangeEvent;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CardUsuarioSelect extends ClickableCard{
	
	private static final long serialVersionUID = 4194699125726794904L;
	
	private static TipoUsuarioCuenta tipo = TipoUsuarioCuenta.SUBCUENTA;

	public CardUsuarioSelect(Cuenta cuenta,Usuario usuario, UsuarioService usuarioService, MovimientoService movimientoService, UsuarioCuentaService usuarioCuentaService) {
		super(event -> {
			Dialog dialogoTipo = new Dialog();
			Button igual = new Button(TipoUsuarioCuenta.IGUAL.toString(),e->{
				tipo = TipoUsuarioCuenta.IGUAL;
				dialogoTipo.close();
			});
			Button subCuenta = new Button(TipoUsuarioCuenta.SUBCUENTA.toString(), e ->{
				tipo = TipoUsuarioCuenta.SUBCUENTA;
				dialogoTipo.close();
			});
			subCuenta.getElement().getStyle().set("margin-left", "10px");
			VerticalLayout tipoCuenta = new VerticalLayout();
			tipoCuenta.setWidthFull();
			tipoCuenta.add(new H3("Tipo de relacion"),new HorizontalLayout(igual,subCuenta));
			dialogoTipo.add(tipoCuenta);
			dialogoTipo.setCloseOnEsc(false);
			dialogoTipo.setCloseOnOutsideClick(false);
			dialogoTipo.addOpenedChangeListener(new ComponentEventListener<GeneratedVaadinDialog.OpenedChangeEvent<Dialog>>() {

				private static final long serialVersionUID = 7681516423230679676L;

				@Override
				public void onComponentEvent(OpenedChangeEvent<Dialog> event) {
					if(!event.isOpened()) {
						addUsuarioCuenta(usuarioService,usuarioCuentaService,usuario,cuenta);
						UI.getCurrent().getPage().reload();											
					}
				}				
				
			});
        	dialogoTipo.open();
        });

        // estilo del card
        this.setWidth("300px");
        this.setHeight("220px");
        this.getElement().getStyle().set("radius", "24px");

        // layout principal que contendrá los layouts posteriores
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();

        // layout con el logo de ingenia bank
        HorizontalLayout imagenLayout = new HorizontalLayout();
        Image ingeniaImage = new Image(usuario.getImage(), usuario.getNombreCorto());
        ingeniaImage.setWidth("40%");
        imagenLayout.add(ingeniaImage);

        // layout con el Nombre de ingenia bank
        HorizontalLayout nombrelayout = new HorizontalLayout();
        Span nombreSpan = new Span();
        nombreSpan.add(usuario.getNombreCompleto());
        nombreSpan.getElement().getStyle().set("color", "black");
        nombreSpan.getElement().getStyle().set("font-weight", "bold");
        nombrelayout.add(nombreSpan);
        
        // layout con el saldo de la cuenta
        HorizontalLayout saldoLayout = new HorizontalLayout();
        Span saldoSpan = new Span();
        Double saldo = Utils.obtenerSaldoEnCuenta(cuenta.getId(), usuario.getId(), usuarioCuentaService);
        
        
        DecimalFormat df = new DecimalFormat("#,###.##");
        saldoSpan.add(df.format(saldo) +" €");
        saldoSpan.getElement().getStyle().set("color", "#D01E69");
        saldoSpan.getElement().getStyle().set("font-weight", "bold");
        if(saldo.equals(-1d)) {
        	saldoSpan.setVisible(false);
        }
        saldoLayout.add(saldoSpan);


        // añadimos todos los layouts horizontales al layout principal
        layout.add(imagenLayout, nombrelayout, saldoLayout);

        // cambio propiedad cursor
        layout.getElement().getStyle().set("cursor", "pointer");

        add(layout);
    }

	private static void addUsuarioCuenta(UsuarioService usuarioService, UsuarioCuentaService usuarioCuentaService, Usuario usuario, Cuenta cuenta) {
		UsuarioCuenta uc = new UsuarioCuenta();
		UsuarioCuentaId ucId = new UsuarioCuentaId(cuenta, usuario);
		
		uc.setId(ucId);
		if(!usuarioCuentaService.exist(ucId)) {
			if(TipoUsuarioCuenta.SUBCUENTA.equals(tipo)) {
				uc.setSaldoEnCuenta(0d);
			}else {
				UsuarioCuentaId ucPrincipalId = new UsuarioCuentaId(cuenta, usuario);
				ucPrincipalId.setUsuario(cuenta.getUsuarioPrincipal());
				ucPrincipalId.setCuenta(cuenta);
				UsuarioCuenta usuarioCuenta = usuarioCuentaService.obtenerDatosUsuarioCuenta(ucPrincipalId).get();
				uc.setSaldoEnCuenta(usuarioCuenta.getSaldoEnCuenta());				
			}
			uc.setTipoUsuarioCuenta(tipo);
			usuarioCuentaService.save(uc);			
		}
	}


}
