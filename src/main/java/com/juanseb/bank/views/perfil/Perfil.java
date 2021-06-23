package com.juanseb.bank.views.perfil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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

	private Usuario usuario;
	
	public Perfil(UsuarioService usuarioService) {
		this.setSizeFull();
		this.setPadding(true);
		this.usuarioService = usuarioService;
		
		usuario = Utils.getCurrentUser(this.usuarioService).get();
		
		HorizontalLayout vl = new HorizontalLayout();
		vl.add(new H2("Hola"), new H2(usuario.getNombreCompleto()));
		
		MemoryBuffer buffer = new MemoryBuffer();
		Upload upload = new Upload(buffer);
		Div output = new Div();
		upload.setAcceptedFileTypes("image/jpeg", "image/png");
		upload.addSucceededListener(event -> {
		    Component component = createComponent(event.getMIMEType(),
		            event.getFileName(), buffer.getInputStream());
		    File file = new File(event.getFileName());
		    try {
				FileUtils.copyInputStreamToFile(buffer.getInputStream(), file);
			} catch (IOException e) {
			}

		    String url = Utils.uploadImg(file);
		    file.delete();
		    System.out.println(url);
		    output.removeAll();
		    showOutput(event.getFileName(), component, output);
		});

		upload.addFileRejectedListener(event -> {
		    Paragraph component = new Paragraph();
		    output.removeAll();
		    showOutput("Tipo de archivo no permitido", component, output);
		});
		upload.getElement().addEventListener("file-remove", event -> {
		    output.removeAll();
		});

		add(vl);
		add(upload, output);
		
	}
	
	private Component createComponent(String mimeType, String fileName,
	        InputStream stream) {
	    if (mimeType.startsWith("text")) {
	        return createTextComponent(stream);
	    } else if (mimeType.startsWith("image")) {
	        Image image = new Image();
	        try {

	            byte[] bytes = IOUtils.toByteArray(stream);
	            image.getElement().setAttribute("src", new StreamResource(
	                    fileName, () -> new ByteArrayInputStream(bytes)));
	            try (ImageInputStream in = ImageIO.createImageInputStream(
	                    new ByteArrayInputStream(bytes))) {
	                final Iterator<ImageReader> readers = ImageIO
	                        .getImageReaders(in);
	                if (readers.hasNext()) {
	                    ImageReader reader = readers.next();
	                    try {
	                        reader.setInput(in);
	                        image.setWidth(reader.getWidth(0) + "px");
	                        image.setHeight(reader.getHeight(0) + "px");
	                    } finally {
	                        reader.dispose();
	                    }
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        image.setSizeFull();
	        return image;
	    }
	    Div content = new Div();
	    String text = String.format("Mime type: '%s'\nSHA-256 hash: '%s'",
	            mimeType, MessageDigestUtil.sha256(stream.toString()));
	    content.setText(text);
	    return content;

	}

	private Component createTextComponent(InputStream stream) {
	    String text;
	    try {
	        text = IOUtils.toString(stream, StandardCharsets.UTF_8);
	    } catch (IOException e) {
	        text = "exception reading stream";
	    }
	    return new Text(text);
	}

	private void showOutput(String text, Component content,
	        HasComponents outputContainer) {
	    HtmlComponent p = new HtmlComponent(Tag.P);
	    p.getElement().setText(text);
	    outputContainer.add(p);
	    outputContainer.add(content);
	}
}
