package com.juanseb.bank.views.login;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "login")
@PageTitle("Login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {


	private static final long serialVersionUID = -7086516434425397958L;

	private LoginForm login = new LoginForm(); // Instantiates a LoginForm component to capture username and password

    public LoginView(){
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        login.setAction("login");
        login.setForgotPasswordButtonVisible(false);  // not show link forgot password
        
        HorizontalLayout icon = new HorizontalLayout();
        Image imgLogo1 = new Image("images/logoImagin.png", "Imagin Bank");
        imgLogo1.setWidth("250px");
        imgLogo1.setHeight("200px");
        icon.add(imgLogo1);

        add(icon, login);
    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // inform the user about an authentication error
        if(beforeEnterEvent.getLocation()   // Reads query parameters and shows an error if a login attempt fails
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            login.setError(true);
        }
    }

}
