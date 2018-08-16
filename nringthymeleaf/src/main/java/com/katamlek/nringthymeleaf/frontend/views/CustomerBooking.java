package com.katamlek.nringthymeleaf.frontend.views;

import com.katamlek.nringthymeleaf.domain.Booking;
import com.katamlek.nringthymeleaf.domain.Car;
import com.katamlek.nringthymeleaf.domain.PriceListOther;
import com.katamlek.nringthymeleaf.repositories.EventRepository;
import com.vaadin.data.Binder;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;

@UIScope
@SpringComponent
public class CustomerBooking extends VerticalLayout {

    private EventRepository eventRepository;
    private Binder<Booking> binder;

    public CustomerBooking() {
        addComponent(buildCustomerBookingPage());
    }

    public VerticalLayout buildCustomerBookingPage() {
        VerticalLayout bookingPage = new VerticalLayout();
        // Customer fields
        Label data = new Label("Tell us about yourself");

        TextField customerFirstName = new TextField("Your name:");
        TextField customerLastName = new TextField("Your surname:");
        TextField customerEmail = new TextField("Your email:");
        TextField customerPhoneNumber = new TextField("Your phone number:");
        TextField customerRemarks = new TextField("Your special wishes:");

        HorizontalLayout nameAndSurname = new HorizontalLayout(customerFirstName, customerLastName);
        HorizontalLayout phoneAndEmail = new HorizontalLayout(customerPhoneNumber, customerEmail);

        // Event/ car/ other fields (i.e. package)
        Label item = new Label("What do you have in mind?");

        VerticalLayout eventVL = new VerticalLayout();
        eventVL.setCaption("A wild event?");
        ComboBox<Event> eventsCB = new ComboBox<>("Pick the one you fancy:");
        //todo set items and captions
        //todo or maybe set a cool clickable screen with the Vaadin controls under the pictures?
        eventVL.addComponents(eventsCB);

        VerticalLayout carVL = new VerticalLayout();
        carVL.setCaption("A racing car?");
        ComboBox<Car> carsCB = new ComboBox<>("Make a wish:");
        carVL.addComponents(carsCB);

        VerticalLayout elseVL = new VerticalLayout();
        elseVL.setCaption("Anything else?");
        ComboBox<PriceListOther> otherCB = new ComboBox<>("Whatever you need:");
        elseVL.addComponents(otherCB);

        HorizontalLayout packageItems = new HorizontalLayout(eventVL, carVL, elseVL);


        // Bind the data
        // for the event just bind the name

        // for the car just bind the plate

        // for the other just bind description?

        // Put it together
        bookingPage.addComponents(data, nameAndSurname, phoneAndEmail, customerRemarks, item, packageItems);

        // Add buttons and listeners - save to the database like an ordinary booking


        return bookingPage;
    }

    // On enter - for now always display a new form, no searching for old submissions
    // Set the user.name to WEB
    // Set the date to now
    // Set booking status to RECEIVED
    // The status will be changed to CONFIRMED by picking status from drop-down

}
