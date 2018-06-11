package com.katamlek.nringthymeleaf.views;

import com.katamlek.nringthymeleaf.frontend.CarGrid;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.VerticalLayout;

@SpringView
@UIScope
public class CarListView extends VerticalLayout implements View {

    CarGrid carGrid;

    public CarListView(CarGrid carGrid) {
        this.carGrid = carGrid;
    }

}
