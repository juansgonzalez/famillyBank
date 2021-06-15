package com.juanseb.bank.views.login;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "register")
@PageTitle("Register")
public class Register extends VerticalLayout{
	public Register() {
		add(new H1("REgistrate"));
	}
}
