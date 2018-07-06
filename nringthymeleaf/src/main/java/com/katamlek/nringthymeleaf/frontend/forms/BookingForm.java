package com.katamlek.nringthymeleaf.frontend.forms;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * Builds booking form for already registered booking or from scratch.
 */

@SpringComponent
@UIScope
@SpringView(name = BookingForm.VIEW_NAME)
public class BookingForm extends VerticalLayout implements View {
    public static final String VIEW_NAME = "booking-form";

// build form
// call method when you know the id

    public VerticalLayout buildBookingForm() {
        VerticalLayout bookingForm = new VerticalLayout();
        // Booking details section


        // Driver section

        // Package section

        // Payment section

        // Booking documents section

        // Put it all together one after another...


        return bookingForm;
    }

//    public VerticalLayout buildBookingForm(Long id) {
//        //TODO as above, this is an overloaded form displayed when openining existing booking - probably :)
//    }

}
