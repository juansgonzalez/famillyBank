package com.juanseb.views.components;

import com.vaadin.flow.component.html.Span;

public class Divider extends Span {

    public Divider() {
        getStyle().set("background-color", "grey");
        getStyle().set("opacity", "0.3");
        getStyle().set("flex", "0 0 1px");
        getStyle().set("align-self", "stretch");
    }
}