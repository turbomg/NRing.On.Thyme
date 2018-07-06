package com.katamlek.nringthymeleaf.frontend.views;

import com.vaadin.navigator.View;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.addon.calendar.Calendar;

public class CalendarView extends VerticalLayout implements View {
    Calendar calendar = new Calendar();

    public CalendarView() {
        addComponent(calendar);
        calendar.setSizeFull();
    }
}
