package com.juanseb.bank.views.perfil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.juanseb.bank.backend.model.Usuario;
import com.juanseb.bank.backend.service.UsuarioService;
import com.juanseb.bank.backend.utils.Utils;
import com.juanseb.bank.views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.internal.MessageDigestUtil;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

@Route(value = "perfil", layout = MainView.class)
@PageTitle("Perfil")
public class Perfil extends VerticalLayout{

	private static final long serialVersionUID = 8472539192142301855L;
	
	private UsuarioService usuarioService;

	private String urlFoto;
	
	private Usuario usuario;
	
	public Perfil(UsuarioService usuarioService) {
		this.setSizeFull();
		this.setPadding(true);
		this.usuarioService = usuarioService;
		
		usuario = Utils.getCurrentUser(this.usuarioService).get();
		
		HorizontalLayout vl = new HorizontalLayout();
		vl.add(new H2("Hola"), new H2(usuario.getNombreCompleto()));
		
		
		
		HorizontalLayout div = crearDivCentrar();
		
		add(vl);
		
		add(div);
		
	}

	private HorizontalLayout crearDivCentrar() {
//		Div div = new Div();
		HorizontalLayout div = new HorizontalLayout();
		div.getElement().getStyle().set("position", "absolute");
		div.getElement().getStyle().set("top", "50%");
		div.getElement().getStyle().set("left", "40%");
		div.getElement().getStyle().set("margin-top", "-50px");
		div.getElement().getStyle().set("margin-left", "-50px");

		FormLayout form = new FormLayout();
		form.setSizeFull();
		
		TextField nombre = new TextField("Nombre");
		nombre.setId("nombreCompleto");
		nombre.setValue(usuario.getNombreCompleto());
		Utils.setColspan(nombre, 2);
		
		TextField userName = new TextField("Nombre de usuario");
		userName.setId("nombreUsuario");
		userName.setValue(usuario.getUsername());
		Utils.setColspan(userName, 2);
		
		PasswordField password = new PasswordField("Contraseña");
		password.setId("password");
		password.setValue(usuario.getPassword());
		password.setRevealButtonVisible(false);
		Utils.setColspan(password, 2);
		
		Image image = new Image(usuario.getImage(), usuario.getNombreCorto());
		image.setHeight("200px");
		image.setWidth("200px");

		MemoryBuffer buffer = new MemoryBuffer();
		Upload upload = new Upload(buffer);
		Div output = new Div();
		upload.setAcceptedFileTypes("image/jpeg", "image/png");
		
		upload.addSucceededListener(event -> {
		    File file = new File(event.getFileName());
		    try {
				FileUtils.copyInputStreamToFile(buffer.getInputStream(), file);
			} catch (IOException e) {
			}

		    urlFoto = Utils.uploadImg(file);
		    
		    file.delete();
		    System.out.println(urlFoto);
		    output.removeAll();
		    showOutput(event.getFileName(), null,output);
		});

		upload.addFileRejectedListener(event -> {
		    Paragraph component = new Paragraph();
		    output.removeAll();
		    showOutput("Tipo de archivo no permitido", component, output);
		});
		
		upload.getElement().addEventListener("file-remove", event -> {
			urlFoto = null;
		    output.removeAll();
		});
		
		form.add(nombre,userName,password);
		div.add(form,new VerticalLayout(image,upload));
		return div;
	}
	

	private void showOutput(String text, Component content,
	        HasComponents outputContainer) {
	    HtmlComponent p = new HtmlComponent(Tag.P);
	    p.getElement().setText(text);
	    outputContainer.add(p);
	    if(content!=null) {
	    	outputContainer.add(content);	    	
	    }
	}
}
