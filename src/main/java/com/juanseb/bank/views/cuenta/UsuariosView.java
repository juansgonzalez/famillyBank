package com.juanseb.bank.views.cuenta;

import com.juanseb.bank.backend.model.Cuenta;
import com.juanseb.bank.backend.model.Usuario;
import com.juanseb.bank.backend.service.CuentaService;
import com.juanseb.bank.backend.service.MovimientoService;
import com.juanseb.bank.backend.service.UsuarioService;
import com.juanseb.bank.components.CardCuenta;
import com.juanseb.bank.components.CardUsuario;
import com.juanseb.bank.views.main.MainView;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.Optional;

@Route(value = "usuarios", layout = MainView.class)
@PageTitle("Usuarios")
public class UsuariosView extends VerticalLayout {

    private Optional<Usuario> currentUser;
    private List<Usuario> usuariosList;

    private CuentaService cuentaService;
    private MovimientoService movimientoService;
    private UsuarioService usuarioService;
    
    private Cuenta cuenta;


    public UsuariosView(CuentaService cuentaService, MovimientoService movimientoService, UsuarioService usuarioService) {

        this.movimientoService = movimientoService;
        this.cuentaService = cuentaService;
        this.usuarioService = usuarioService;
        this.currentUser = usuarioService.obtenerUsuarioActualConectado();
        this.usuariosList = usuarioService.obtenerTodosUsuarios();
        cuenta = this.cuentaService.obtenerTodasCuentasByUsuarioId(currentUser.get().getId()).get(0);

        setSizeFull();

        add(new H2("Usuarios"), createCardCuentas());
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
                hl.add(new CardUsuario(cuenta,usuario, this.usuarioService, this.movimientoService));
            }else{
                contador = 1;
                vl.add(hl);
                hl = new HorizontalLayout();
                hl.add(new CardCuenta(cuenta, this.cuentaService, this.movimientoService));
            }
        }

        vl.add(hl);
        return vl;
    }
}
