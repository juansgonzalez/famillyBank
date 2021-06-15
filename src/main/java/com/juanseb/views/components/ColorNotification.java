package com.juanseb.views.components;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;

public class ColorNotification extends Notification{


	public ColorNotification (String messagge, String color) {
		super();
		Span error = new Span(messagge);
		error.getStyle().set("color", color);
		this.add(error);
		this.setDuration(3000);
	}


}
