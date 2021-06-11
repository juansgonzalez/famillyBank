package com.juanseb.bank.views.main;

import java.util.List;
import java.util.Optional;

import com.juanseb.bank.backend.model.Cuenta;
import com.juanseb.bank.backend.model.Usuario;
import com.juanseb.bank.backend.service.CuentaService;
import com.juanseb.bank.backend.service.UsuarioService;
import com.juanseb.bank.backend.utils.Utils;
import com.juanseb.bank.components.CuentaSelectComponent;
import com.juanseb.bank.views.cuenta.UsuariosView;
import com.juanseb.bank.views.inicio.InicioView;
import com.juanseb.bank.views.movimiento.MovimientosView;
import com.juanseb.bank.views.tarjeta.TarjetasView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dialog.GeneratedVaadinDialog;
import com.vaadin.flow.component.dialog.GeneratedVaadinDialog.OpenedChangeEvent;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.router.PageTitle;

/**
 * The main view is a top-level placeholder for other views.
 */
@PWA(name = "Bank App", shortName = "Bank App", enableInstallPrompt = false)
@Theme(themeFolder = "bankapp")
public class MainView extends AppLayout {

    private Tabs menu;
    private H1 viewTitle;

    private UsuarioService usuarioService;
    private CuentaService cuentaService;
    private Long idUsuarioPrincipal;
	private CuentaSelectComponent cuentaSelect;


    public MainView(UsuarioService usuarioService, CuentaService cuentaService) {
        this.menu = new Tabs();
		this.usuarioService = usuarioService;
        this.cuentaService = cuentaService;
        
        Optional<Usuario> user = Utils.getCurrentUser(this.usuarioService);
        
        if(user.isPresent()) {
        	List<Cuenta> listaCuentasUsuario = this.cuentaService.obtenerTodasCuentasByUsuarioId(user.get().getId());
        	if(UI.getCurrent().getSession().getAttribute("idCuenta") == null) {
        		if(listaCuentasUsuario.size() > 1) {
        			cuentaSelect = new CuentaSelectComponent(listaCuentasUsuario);
        			cuentaSelect.open();
        			
        			cuentaSelect.addOpenedChangeListener(new ComponentEventListener<GeneratedVaadinDialog.OpenedChangeEvent<Dialog>>() {
        				
        				@Override
        				public void onComponentEvent(OpenedChangeEvent<Dialog> event) {
        					if(!event.isOpened()) { // Check if the form was closed
        						UI.getCurrent().getSession().setAttribute("idCuenta", cuentaSelect.getCuenta().getId());     
        						UI.getCurrent().getSession().setAttribute("idUsuarioPrincipal", cuentaSelect.getCuenta().getUsuarioPrincipal().getId());     
        						UI.getCurrent().getPage().reload();
        					}
        					
        				}
        			});
        			
        		}else if(listaCuentasUsuario.size() == 1) {
        			Cuenta cuenta = listaCuentasUsuario.get(0);
        			UI.getCurrent().getSession().setAttribute("idCuenta", cuenta.getId());
        			UI.getCurrent().getSession().setAttribute("idUsuarioPrincipal", cuenta.getUsuarioPrincipal().getId()); 
        			UI.getCurrent().getPage().reload();
        		}else {
        			Notification.show("Error al obtener Cuentas del usuario.");
        		}        		
        	}else {
        		this.idUsuarioPrincipal = (long) UI.getCurrent().getSession().getAttribute("idUsuarioPrincipal");

                setPrimarySection(Section.DRAWER);
                addToNavbar(true, createHeaderContent());
                this.menu = createMenu();
                addToDrawer(createDrawerContent(menu));
        	}
        }
	
    }

    private Component createHeaderContent() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setId("header");
        
//        layout.getThemeList().set("dark", true);
        layout.getElement().getStyle().set("backgorund-color", "#E5E5E5");
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.add(new DrawerToggle());
        viewTitle = new H1();
        layout.add(viewTitle);
        
        Avatar avatar = new Avatar();
        avatar.setImage("images/"+getImageCurrentUser());
        avatar.setName(getFullNameCurrentUser());
        layout.add(avatar);
        layout.add(createAvatarMenu());

        return layout;
    }

    private Component createDrawerContent(Tabs menu) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        layout.getThemeList().set("spacing-s", true);
        layout.setAlignItems(FlexComponent.Alignment.STRETCH);
        HorizontalLayout logoLayout = new HorizontalLayout();
        logoLayout.setId("logo");
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        Image ingeniaImage = new Image("images/bankname.png", "Imagin");
        ingeniaImage.setWidth("70%");
        logoLayout.add(ingeniaImage);
        //logoLayout.add(new H1("Bank App"));
        layout.add(logoLayout, menu);
        return layout;
    }

    private Tabs createMenu() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        tabs.setId("tabs");
        tabs.add(createMenuItems());
        return tabs;
    }

    private Component[] createMenuItems() {
        return new Tab[]{createTab("Inicio", InicioView.class,true),
                createTab("Usuarios", UsuariosView.class,Utils.isPrincipal(getCurrentUser().get(),idUsuarioPrincipal)),
                createTab("Tarjetas", TarjetasView.class,true),
                createTab("Movimientos", MovimientosView.class,true)
        };
    }

    private Tab createTab(String text, Class<? extends Component> navigationTarget, boolean visible) {
        final Tab tab = new Tab();
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        tab.setVisible(visible);
        return tab;
    }

    @Override
    protected void afterNavigation() {
    	if(idUsuarioPrincipal != null) {
    		super.afterNavigation();
    		getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);
    		viewTitle.setText(getCurrentPageTitle());    		
    	}
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }

    private Component createAvatarMenu() {

        HorizontalLayout hl = new HorizontalLayout();

        Icon icon = new Icon(VaadinIcon.ANGLE_DOWN);

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.setOpenOnClick(true);
        contextMenu.setTarget(hl);

        contextMenu.addItem("Logout", e -> {
            contextMenu.getUI().ifPresent(ui -> ui.getPage().setLocation("/logout"));
        });

        hl.add(new Text(getFullNameCurrentUser()));
        hl.add(icon);
        hl.setPadding(true);
        hl.getElement().getStyle().set("cursor","pointer");
        hl.setAlignItems(FlexComponent.Alignment.AUTO);

        return hl;
    }

    private Optional<Usuario> getCurrentUser(){
        return usuarioService.obtenerUsuarioActualConectado();
    }

    private String getFullNameCurrentUser(){
        Optional<Usuario> usuarioActual = getCurrentUser();
        if(usuarioActual.isPresent()){
            return usuarioActual.get().getNombreCompleto();
        }else{
            return "";
        }
    }
    
    private String getImageCurrentUser(){
        Optional<Usuario> usuarioActual = getCurrentUser();
        if(usuarioActual.isPresent()){
            return usuarioActual.get().getImage();
        }else{
            return "";
        }
    }
    
    private Cuenta getCurrentCuenta() {
    	Long idCuenta = (long) UI.getCurrent().getSession().getAttribute("idCuenta");
    	Cuenta cuenta = null;
    	if(idCuenta == null) {
    		System.out.println("ERROR OBTENER CUENTA INICIAL");
    	}else {
    		cuenta = cuentaService.obtenerCuentaById(idCuenta);    		
    	}
    	
    	return cuenta;
    }
}
