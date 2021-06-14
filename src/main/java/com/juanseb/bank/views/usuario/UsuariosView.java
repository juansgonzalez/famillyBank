package com.juanseb.bank.views.usuario;

import com.juanseb.bank.backend.model.Cuenta;
import com.juanseb.bank.backend.model.Usuario;
import com.juanseb.bank.backend.service.CuentaService;
import com.juanseb.bank.backend.service.MovimientoService;
import com.juanseb.bank.backend.service.UsuarioCuentaService;
import com.juanseb.bank.backend.service.UsuarioService;
import com.juanseb.bank.views.main.MainView;
import com.juanseb.views.components.CardCuenta;
import com.juanseb.views.components.CardUsuario;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

import javax.swing.plaf.basic.BasicToggleButtonUI;

@Route(value = "usuarios", layout = MainView.class)
@PageTitle("Usuarios")
public class UsuariosView extends VerticalLayout {

	private static final long serialVersionUID = -6987631109273915138L;

	private List<Usuario> usuariosList;

    private CuentaService cuentaService;
    private MovimientoService movimientoService;
    private UsuarioService usuarioService;
    private UsuarioCuentaService usuarioCuentaService;
    
    private Cuenta cuenta;


    public UsuariosView(CuentaService cuentaService, MovimientoService movimientoService, UsuarioService usuarioService, UsuarioCuentaService usuarioCuentaService) {

        this.movimientoService = movimientoService;
        this.cuentaService = cuentaService;
        this.usuarioService = usuarioService;
        this.usuarioCuentaService = usuarioCuentaService;
        
        Long idCuenta = (Long) UI.getCurrent().getSession().getAttribute("idCuenta");
        this.usuariosList = usuarioService.obtenerUsuarioByCuenta(idCuenta);
        cuenta = this.cuentaService.obtenerCuentaById(idCuenta);

        setSizeFull();
        
        HorizontalLayout title = new HorizontalLayout();
        title.setWidthFull();
        title.add(new H2("Usuarios"));
        Button botonadd = new Button("Agregar Usuario a Cuenta", ClickEvent ->{
    		abrirSeleccionadorUsuario();
    	});
        botonadd.getElement().getStyle().set("margin-left", "auto");
        botonadd.getElement().getStyle().set("margin-top", "auto");
        botonadd.getElement().getStyle().set("background-color", "#D01E69");
        botonadd.getElement().getStyle().set("color", "white");
        botonadd.getElement().getStyle().set("cursor", "pointer");
        
        title.add(botonadd);
        
        add(title, createCardCuentas());
    }

    private void abrirSeleccionadorUsuario() {
		// TODO Auto-generated method stub
		
	}

	/**
     * Crea un componente de cards con todas las cuentas del usuario. Los cards se muestran en una misma fila hasta llegar a 3,
     * por lo que si hay más de 3 cuentas, se crearán varias filas de cards.
     * @return componente con todas las cuentas en formato card
     */
    private VerticalLayout createCardCuentas(){

        VerticalLayout vl = new VerticalLayout();
        HorizontalLayout hl = new HorizontalLayout();
        int numCardsPerRow = 3;  // en cada layout horizontal habrá 3 cards como máximo.
        int contador = 0;

        for (Usuario usuario: this.usuariosList){
            contador++;

            if(contador <= numCardsPerRow){
                hl.add(new CardUsuario(cuenta, usuario, this.usuarioService, this.movimientoService, this.usuarioCuentaService));
            }else{
                contador = 1;
                vl.add(hl);
                hl = new HorizontalLayout();
                hl.add(new CardUsuario(cuenta, usuario, this.usuarioService, this.movimientoService,this.usuarioCuentaService));
            }
        }

        vl.add(hl);
        return vl;
    }
}
