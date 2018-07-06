package com.katamlek.nringthymeleaf.frontend.forms;

import com.katamlek.nringthymeleaf.domain.BookingPackageItem;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SpringComponent
@UIScope
@SpringView(name = BookingPackageItemForm.VIEW_NAME)
public class BookingPackageItemForm extends VerticalLayout implements View {
    public static final String VIEW_NAME = "package-item-form";

    public BookingPackageItemForm() {
        addComponent(new Label("Package item form"));
    }

    // todo
}
