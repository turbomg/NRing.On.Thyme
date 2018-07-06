package com.katamlek.nringthymeleaf.frontend.views;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringView
@UIScope
public class MyAccountView extends VerticalLayout implements View {

    public MyAccountView() {
        addComponent(new Label("This is a placeholder view for my account view"));
    }
}
