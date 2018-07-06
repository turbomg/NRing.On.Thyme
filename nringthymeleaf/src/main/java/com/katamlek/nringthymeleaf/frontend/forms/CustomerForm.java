package com.katamlek.nringthymeleaf.frontend.forms;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.VerticalLayout;

@SpringComponent
@UIScope
@SpringView(name = CustomerForm.VIEW_NAME)
public class CustomerForm extends VerticalLayout implements View {
    public static final String VIEW_NAME = "customer-form";
    //todo
}
