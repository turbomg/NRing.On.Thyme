package com.katamlek.nringthymeleaf.frontend.forms;

import com.katamlek.nringthymeleaf.repositories.BookingCarRepository;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.VerticalLayout;

@SpringView
@UIScope
public class BookingCarForm extends VerticalLayout implements View {
    private BookingCarRepository bookingCarRepository;

}
