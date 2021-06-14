package com.juanseb.bank.views.tarjeta;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.juanseb.bank.backend.model.Cuenta;
import com.juanseb.bank.backend.model.Tarjeta;
import com.juanseb.bank.backend.model.Usuario;
import com.juanseb.bank.backend.service.MovimientoService;
import com.juanseb.bank.backend.service.TarjetaService;
import com.juanseb.bank.backend.service.UsuarioCuentaService;
import com.juanseb.bank.backend.service.UsuarioService;
import com.juanseb.bank.backend.utils.Utils;
import com.juanseb.bank.views.form.TarjetaDialog;
import com.juanseb.bank.views.main.MainView;
import com.juanseb.views.components.CardCuenta;
import com.juanseb.views.components.TarjetasDisplayBox;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "tarjetas", layout = MainView.class)
@PageTitle("Tarjetas")
public class TarjetasView extends VerticalLayout {
	
	private Long idCuenta;
	
	@Autowired
	private TarjetaService tarjetaService;
	
	@Autowired
	private MovimientoService movimientoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
private UsuarioCuentaService usuarioCuentaService;
	private Usuario usuario;
	
	private List<Tarjeta> tarjetasList;
	
	public TarjetasView(TarjetaService tarjetaService, MovimientoService movimientoService, UsuarioService usuarioService, UsuarioCuentaService usuarioCuentaService) {
		
		this.movimientoService = movimientoService;
		this.tarjetaService = tarjetaService;
		this.usuarioService = usuarioService;
		this.usuarioCuentaService = usuarioCuentaService;
		
		Optional<Usuario> user = Utils.getCurrentUser(this.usuarioService);
		this.usuario = user.get();
		setSizeFull();
		
		idCuenta = (Long) UI.getCurrent().getSession().getAttribute("idCuenta");
		
		tarjetasList = tarjetaService.obtenerTarjetaByCuenta(idCuenta);
		
		add(new H2("Tarjetas"),createCardCuentas());
	}
	
    /**
     * Crea un componente de cards con todas las tarjetas de la cuenta. Los cards se muestran en una misma fila hasta llegar a 4,
     * por lo que si hay más de 4 tarjetas, se crearán varias filas de cards.
     * @return componente con todas las tarjetas en formato card
     */
    private VerticalLayout createCardCuentas(){

        VerticalLayout vl = new VerticalLayout();
        HorizontalLayout hl = new HorizontalLayout();
        int numCardsPerRow = 4;
        int contador = 0;

        for (Tarjeta tarjeta: this.tarjetasList){
            contador++;

            if(contador <= numCardsPerRow){
                hl.add(new TarjetasDisplayBox(tarjeta, this.usuario, this.movimientoService, this.tarjetaService, this.usuarioCuentaService));
            }else{
                contador = 1;
                vl.add(hl);
                hl = new HorizontalLayout();
                hl.add(new TarjetasDisplayBox(tarjeta, this.usuario, this.movimientoService, this.tarjetaService, this.usuarioCuentaService));
            }
        }

        vl.add(hl);
        return vl;
    }

}
