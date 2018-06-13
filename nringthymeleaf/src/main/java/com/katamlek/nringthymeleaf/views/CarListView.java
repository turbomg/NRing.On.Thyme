package com.katamlek.nringthymeleaf.views;

import com.katamlek.nringthymeleaf.frontend.CarGridView;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.VerticalLayout;

@SpringView
@UIScope
public class CarListView extends VerticalLayout implements View {

    CarGridView carGrid;

    public CarListView(CarGridView carGrid) {
        this.carGrid = carGrid;
    }

}
