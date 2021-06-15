package com.juanseb.bank.views.form;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.juanseb.bank.backend.model.Contrasena;
import com.juanseb.bank.backend.model.Usuario;
import com.juanseb.bank.backend.service.UsuarioService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

public class ResetPassword extends Dialog{

	private static final long serialVersionUID = 674294122949828321L;
	
	private UsuarioService usuarioService;
	
	private BCryptPasswordEncoder encoder;
	
	private Usuario usuario;
	private Contrasena contrasena = new Contrasena();
	
	private TextField userName;
	private PasswordField  nuevaContrasena;
	private PasswordField  confirmacionContrasena;
	private Button botonAceptar;
	
	Binder<Contrasena> binder = new BeanValidationBinder<Contrasena>(Contrasena.class);
	
	
	public ResetPassword(Usuario usuario, UsuarioService usuarioService) {
		super();
		this.usuarioService = usuarioService;
		this.usuario = usuario;
		VerticalLayout vl = new VerticalLayout();
		
		bCryptPasswordEncoder();
		inicializarCampos();
		
		vl.add(new H2("Restaurar contraseña"),userName, nuevaContrasena, confirmacionContrasena,botonAceptar);
		add(vl);
	}


	private void inicializarCampos() {
		userName = new TextField("Usuario");
		userName.setValue(usuario.getUsername());
		userName.setId("username");
		userName.setEnabled(false);

		
		nuevaContrasena = new PasswordField("Contrasena");
		nuevaContrasena.setId("contrasena");
		nuevaContrasena.setAutofocus(true);
		binder.forField(nuevaContrasena).withValidator(
		        pass -> pass.equals(confirmacionContrasena.getValue()),
		        "Las Contraseñas han de coincidir").asRequired("La contraseña es obligatoria")
		.bind(Contrasena::getContrasena,Contrasena::setContrasena);
		
		confirmacionContrasena = new PasswordField("Contrasena confirmacion");
		confirmacionContrasena.setId("contrasenaConfirm");
		binder.forField(confirmacionContrasena).withValidator(
		        pass -> pass.equals(nuevaContrasena.getValue()),
		        "Las Contraseñas han de coincidir").asRequired("La confirmacion de la contraseña es obligatoria")
		.bind(Contrasena::getConfirmarContrasena,Contrasena::setConfirmarContrasena);

		botonAceptar = new Button("Guardar", clickEvent ->{
			if(binder.writeBeanIfValid(this.contrasena)) {			
				usuario.setPassword(encoder.encode(nuevaContrasena.getValue()));
				usuario.setResetearPassword(false);
				this.usuarioService.saveUpdateUsuario(usuario);
				this.close();				
			}
		});
	}
	
	public void bCryptPasswordEncoder() {
		  encoder = new BCryptPasswordEncoder();
	}

}
