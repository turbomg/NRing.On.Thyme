package com.katamlek.nringthymeleaf.frontend;

import com.katamlek.nringthymeleaf.domain.Car;
import com.katamlek.nringthymeleaf.repositories.CarRepository;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

@SpringComponent
@UIScope
public class CarGridView {
    private final CarRepository carRepository;
    final Grid<Car> carGrid;

    public CarGridView(CarRepository carRepository, Grid<Car> carGrid) {
        this.carRepository = carRepository;
        this.carGrid = carGrid;
    }

    public VerticalLayout buildCarGridView() {
        VerticalLayout carGridViewVL = new VerticalLayout();

        List<Car> carList = (List<Car>) carRepository.findAll();
        carGrid.setItems(carList);
        carGridViewVL.addComponent(carGrid);

        return carGridViewVL;

    }
}
