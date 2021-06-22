package com.juanseb.bank.views.movimiento;

import java.text.SimpleDateFormat;
import java.util.List;

import com.juanseb.bank.backend.model.Cuenta;
import com.juanseb.bank.backend.model.Movimiento;
import com.juanseb.bank.backend.model.Usuario;
import com.juanseb.bank.backend.model.UsuarioCuenta;
import com.juanseb.bank.backend.model.UsuarioCuentaId;
import com.juanseb.bank.backend.model.enumerado.TipoMovimiento;
import com.juanseb.bank.backend.model.enumerado.TipoUsuarioCuenta;
import com.juanseb.bank.backend.service.CategoriaService;
import com.juanseb.bank.backend.service.CuentaService;
import com.juanseb.bank.backend.service.MovimientoService;
import com.juanseb.bank.backend.service.TarjetaService;
import com.juanseb.bank.backend.service.UsuarioCuentaService;
import com.juanseb.bank.backend.service.UsuarioService;
import com.juanseb.bank.backend.utils.Utils;
import com.juanseb.bank.views.enums.FORM_ACTION;
import com.juanseb.bank.views.form.MovimientoForm;
import com.juanseb.bank.views.main.MainView;
import com.juanseb.views.components.ColorNotification;
import com.juanseb.views.components.IconoMovimientoTarjeta;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dialog.GeneratedVaadinDialog;
import com.vaadin.flow.component.dialog.GeneratedVaadinDialog.OpenedChangeEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "movimientos", layout = MainView.class)
@PageTitle("Movimientos")
public class MovimientosView extends VerticalLayout {

	private static final long serialVersionUID = -5643146991184397098L;
	
	private MovimientoService movimientoService;
    private UsuarioService usuarioService;
    private CategoriaService categoriaService;
    private TarjetaService tarjetaService;
    private CuentaService cuentaService;
    private UsuarioCuentaService usuarioCuentaService;
    
    private Long idCuentaActual;
    
    private Usuario usuarioActual;

    private MovimientoForm movimientoForm;
    private Movimiento movimientoEditable = new Movimiento();
    
    private List<Movimiento> movimientosList;
    private Grid<Movimiento> grid = new Grid<>(Movimiento.class);
    //private Optional<Usuario> currentUser;

    public MovimientosView(MovimientoService movimientoService, UsuarioService usuarioService,CategoriaService categoriaService,TarjetaService tarjetaService, CuentaService cuentaService, UsuarioCuentaService usuarioCuentaService){
        addClassName("movimientos-view");
        setPadding(true);
        setHeightFull();

        this.usuarioService =  usuarioService;
        this.movimientoService = movimientoService;
        this.categoriaService = categoriaService;
        this.tarjetaService = tarjetaService;
        this.cuentaService = cuentaService;
        this.usuarioCuentaService = usuarioCuentaService;
        
        usuarioActual = this.usuarioService.obtenerUsuarioActualConectado().get();
        
        this.idCuentaActual = (Long) UI.getCurrent().getSession().getAttribute("idCuenta");
        if(Utils.isPrincipal(this.usuarioActual)) {
        	this.movimientosList = this.movimientoService.obtenerMovimientosDeCuentaOrdenadosFecha(this.idCuentaActual);        	
        }else {
        	this.movimientosList = this.movimientoService.obtenerMovimientosDeCuentaByUsuarioOrdenadosFecha(this.idCuentaActual,this.usuarioActual.getId());        	        	
        }
        
        HorizontalLayout cabecera = new HorizontalLayout();
        cabecera.setWidthFull();
        cabecera.add(new H2("Movimientos"));
        Button botonCrear = new Button("Nuevo Movimiento", ClickEvent ->{
    		openMovimientoForm(null);
    	});
        botonCrear.getElement().getStyle().set("margin-left", "auto");
        botonCrear.getElement().getStyle().set("margin-top", "auto");
        botonCrear.getElement().getStyle().set("background-color", "#D01E69");
        botonCrear.getElement().getStyle().set("color", "white");
        botonCrear.getElement().getStyle().set("cursor", "pointer");
        
        cabecera.add(botonCrear);
        add(cabecera, createGridMovimientos());
    }


    private void openMovimientoForm(Movimiento movimiento) {
		movimientoForm = new MovimientoForm(idCuentaActual, this.categoriaService, this.usuarioService, this.tarjetaService,movimiento);
		
		movimientoForm.open();
		
		movimientoForm.addOpenedChangeListener(new ComponentEventListener<GeneratedVaadinDialog.OpenedChangeEvent<Dialog>>() {

			private static final long serialVersionUID = 8812340255137528127L;

			@Override
			public void onComponentEvent(OpenedChangeEvent<Dialog> event) {
				if(!event.isOpened()) { // Check if the form was closed
					if(FORM_ACTION.SAVE.equals(movimientoForm.getAction())) { // Check if the form was closed with the save button
						movimientoEditable = movimientoForm.getMovimiento();
						if(movimientoForm.editar) {
							try {
								deshacerMovimiento(null);
								nuevoMovimiento();
								refreshGrid();

								new ColorNotification("Se ha editado el movimiento con exito","green").open();
							} catch (Exception e) {
								new ColorNotification("Ha ocurrido un error al editar movimiento intentelo mas tarde. Si el problema persiste notifique al administrador","red").open();
							}
						}else {
							try {
								
								nuevoMovimiento();
								
								// Refresh the grid to display all the Products
								refreshGrid();
								
								new ColorNotification("Se ha creado el movimiento con exito","green").open();
							}catch(Exception e) {
								new ColorNotification("Ha ocurrido un error al crear movimiento intentelo mas tarde. Si el problema persiste notifique al administrador","red").open();
							}		
						}
					}
				}
			}
		});
		
	}
    
	private void refreshGrid() {
		if(Utils.isPrincipal(this.usuarioActual)) {
        	grid.setDataProvider(new ListDataProvider<>(this.movimientoService.obtenerMovimientosDeCuentaOrdenadosFecha(this.idCuentaActual)));
        }else {
        	grid.setDataProvider(new ListDataProvider<>(this.movimientoService.obtenerMovimientosDeCuentaByUsuarioOrdenadosFecha(this.idCuentaActual,this.usuarioActual.getId())));        	
        }
	}


	/**
     * Carga la lista de movimientos ordenados por fecha en el grid
     */
    private void loadMovimientosGrid() {
        ListDataProvider<Movimiento> movimientosProvider;
        movimientosProvider = DataProvider.ofCollection(this.movimientosList);
        movimientosProvider.setSortOrder(Movimiento::getId, SortDirection.DESCENDING);
        grid.setDataProvider(movimientosProvider);
    }


    /**
     * Crea el grid de movimientos y lo configura
     * @return Component grid
     */
    private Component createGridMovimientos(){

        loadMovimientosGrid();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // indicamos columnas y el orden
        grid.setColumns();
        grid.addComponentColumn(movimiento -> new IconoMovimientoTarjeta(movimiento)).setHeader("Tarjeta").setWidth("150px").setFlexGrow(0);
        grid.addColumn(movimiento -> movimiento.getCantidad()+" €").setHeader("Cantidad").setWidth("125px").setFlexGrow(0).setSortable(true);
        grid.addColumn(movimiento -> movimiento.getConcepto()).setHeader("Concepto").setFlexGrow(1).setSortable(true);
        grid.addColumn(movimiento -> movimiento.getCategoria().getNombre()).setHeader("Categoria").setFlexGrow(1).setSortable(true);
        if(Utils.isPrincipal(usuarioActual)) {
        	grid.addColumn(movimiento -> movimiento.getUsuario().getNombreCorto()).setHeader("Usuario").setFlexGrow(0).setSortable(true);
        }
        grid.addColumn(movimiento -> dateFormat.format(movimiento.getFecha())).setHeader("Fecha").setWidth("125px").setFlexGrow(0).setSortable(true);
        grid.addComponentColumn(movimiento -> botonesEditarMovimiento(movimiento)).setWidth("125px").setFlexGrow(1).setSortable(false);


        // estilos del grid
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS,
                GridVariant.LUMO_ROW_STRIPES); //para que las filas pares e impares tengan colores diferentes
        
        return grid;
    }


	private HorizontalLayout botonesEditarMovimiento(Movimiento movimiento) {

	    HorizontalLayout layout =  new HorizontalLayout();
	    layout.setWidthFull();
	    
	    // Create remove button with cick event acction
	    Button buttonRemove = new Button("Eliminar", clickEvent -> {
	        try {
	        	deshacerMovimiento(movimiento);
	        	
				new ColorNotification("Se ha eliminaro el movimiento con exito","green").open();

	        	// Refresh the grid list
	        	refreshGrid();
	        	
	        }catch(Exception e) {
	        	System.out.println(e);
	        }
	    });
	    
	    // Create update button with cick event acction
	    Button buttonUpdate = new Button("Editar", clickEvent -> {
	    	// Open the Dialog with the Product info form
	    	openMovimientoForm(movimiento);
	    });
	    
	    // Set Style to the buttons
	    buttonRemove.getElement().getStyle().set("color", "red");
	    buttonUpdate.getElement().getStyle().set("color", "green");

	    // Add the 2 buttons to the horizontal grid
	    layout.add(buttonUpdate);
	    layout.add(buttonRemove);
		return layout;
	
	}
	private void deshacerMovimiento(Movimiento movimiento) {
		Movimiento movimientoDeshacer = null ;
		if(movimiento != null) {
			movimientoDeshacer = movimiento;										
		}else {
			movimientoDeshacer = movimientoService.obtenerMovimientoId(movimientoEditable.getId());					
		}
		
		Usuario u = new Usuario();
		u.setId(movimientoDeshacer.getUsuario().getId());
		
		Cuenta c = new Cuenta();
		c.setId(idCuentaActual);
		
		UsuarioCuentaId uc = new UsuarioCuentaId();
		uc.setCuenta(c);
		uc.setUsuario(u);
		
		UsuarioCuenta usuarioCuenta = usuarioCuentaService.obtenerDatosUsuarioCuenta(uc).get();
		
		
		Cuenta cuentaBd = movimientoDeshacer.getCuenta();
		
		if(TipoUsuarioCuenta.IGUAL.equals(usuarioCuenta.getTipoUsuarioCuenta())) {
			List<UsuarioCuenta> usuarioCuentaIguales = usuarioCuentaService.obtenerUsuariosIgualesByCuenta(idCuentaActual);
			for (int i = 0; i < usuarioCuentaIguales.size(); i++) {
				UsuarioCuenta usuarioCuentaActual = usuarioCuentaIguales.get(i);
				if(TipoMovimiento.GASTO.equals(movimientoDeshacer.getTipo())) {
					usuarioCuentaActual.setSaldoEnCuenta(usuarioCuentaActual.getSaldoEnCuenta() + movimientoDeshacer.getCantidad());
				}else {
					usuarioCuentaActual.setSaldoEnCuenta(usuarioCuentaActual.getSaldoEnCuenta() - movimientoDeshacer.getCantidad());									
				}			
				usuarioCuentaService.save(usuarioCuentaActual);
			}
		}else {

			// Revertimos los cambios del anterior movimiento
			if(TipoMovimiento.GASTO.equals(movimientoDeshacer.getTipo())) {
				usuarioCuenta.setSaldoEnCuenta(usuarioCuenta.getSaldoEnCuenta() + movimientoDeshacer.getCantidad());
			}else {
				usuarioCuenta.setSaldoEnCuenta(usuarioCuenta.getSaldoEnCuenta() - movimientoDeshacer.getCantidad());									
			}
			
			usuarioCuentaService.save(usuarioCuenta);
		}
		
		
		if(TipoMovimiento.GASTO.equals(movimientoDeshacer.getTipo())) {
			cuentaBd.setSaldo(cuentaBd.getSaldo() + movimientoDeshacer.getCantidad());
		}else {
			cuentaBd.setSaldo(cuentaBd.getSaldo() - movimientoDeshacer.getCantidad());									
		}
		
		Cuenta cuentaGuardada = cuentaService.save(cuentaBd);
		
		movimientoDeshacer.setSaldoActual(cuentaGuardada.getSaldo());

		movimientoService.eliminar(movimientoDeshacer);
	}

	private void nuevoMovimiento() {
		
		Usuario u = new Usuario();
		u.setId(movimientoEditable.getUsuario().getId());
		
		Cuenta c = new Cuenta();
		c.setId(idCuentaActual);
		
		UsuarioCuentaId uc = new UsuarioCuentaId();
		uc.setCuenta(c);
		uc.setUsuario(u);
		
		UsuarioCuenta usuarioCuenta = usuarioCuentaService.obtenerDatosUsuarioCuenta(uc).get();
		Double saldoActualUsuario = 0d;
		Cuenta cuentaObtenida = cuentaService.obtenerCuentaById(idCuentaActual);
		
		if(TipoUsuarioCuenta.IGUAL.equals(usuarioCuenta.getTipoUsuarioCuenta())) {
			List<UsuarioCuenta> usuarioCuentaIguales = usuarioCuentaService.obtenerUsuariosIgualesByCuenta(idCuentaActual);
			for (int i = 0; i < usuarioCuentaIguales.size(); i++) {
				UsuarioCuenta usuarioCuentaActual = usuarioCuentaIguales.get(i);
				saldoActualUsuario = usuarioCuentaActual.getSaldoEnCuenta();
				
				if(movimientoEditable.getTipo().equals(TipoMovimiento.GASTO)) {
					saldoActualUsuario -= movimientoEditable.getCantidad();
				}else {
					saldoActualUsuario += movimientoEditable.getCantidad();
				}
				usuarioCuentaActual.setSaldoEnCuenta(saldoActualUsuario);
				usuarioCuenta = usuarioCuentaService.save(usuarioCuentaActual);				
			}
		}else {
			
			saldoActualUsuario = usuarioCuenta.getSaldoEnCuenta();
			
			if(movimientoEditable.getTipo().equals(TipoMovimiento.GASTO)) {
				saldoActualUsuario -= movimientoEditable.getCantidad();
			}else {
				saldoActualUsuario += movimientoEditable.getCantidad();
			}
			usuarioCuenta.setSaldoEnCuenta(saldoActualUsuario);
			
			usuarioCuenta = usuarioCuentaService.save(usuarioCuenta);
		}
		
		Double saldoCuentaNuevo = cuentaObtenida.getSaldo();
		if(movimientoEditable.getTipo().equals(TipoMovimiento.GASTO)) {
			saldoCuentaNuevo -= movimientoEditable.getCantidad();			
		}else {
			saldoCuentaNuevo += movimientoEditable.getCantidad();			
		}
		cuentaObtenida.setSaldo(saldoCuentaNuevo);	
		
		Cuenta CuentaGuardada = cuentaService.save(cuentaObtenida);
		movimientoEditable.setCuenta(CuentaGuardada);
		
		movimientoEditable.setSaldoActual(CuentaGuardada.getSaldo());
		// Save in the DB 
		movimientoService.save(movimientoEditable);
	}

}
