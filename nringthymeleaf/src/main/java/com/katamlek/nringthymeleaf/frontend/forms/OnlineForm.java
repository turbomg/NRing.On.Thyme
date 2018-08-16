package com.katamlek.nringthymeleaf.frontend.forms;

import com.katamlek.nringthymeleaf.domain.Booking;
import com.katamlek.nringthymeleaf.domain.Customer;
import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;

@SpringView
@UIScope
public class OnlineForm extends VerticalLayout implements View {
    private TextField yourNameTF;
    private TextField yourSurnameTF;
    private TextField yourEmailTF;
    private TextField yourPhoneTF; // todo indicate that with prefix
    private TextField yourChoiceTF; // just the description
    private DateField yourDateTF; // the date customer is interested in

    private Button sendB;
    private Button cancelB;

    Binder<Booking> bookingBinder;
    Binder<Customer>  customerBinder;

    // todo decide if we place it here or in html code
    private Label informationOnTheSiteL = new Label("Here you can book the services of RSR.");
    private Label informationOnTheProcedureL = new Label("After sending the form someone from RSR will contact you to confirm your booking.");
    private Label underConstructionL = new Label("This site is under construction. We promise to deliver more in the nearest future!");

    // todo when saving to database set status to ACCEPTED

    public OnlineForm() {
        addComponent(buildOnlineForm());
        setMargin(false);
    }

    public VerticalLayout buildOnlineForm() {
        VerticalLayout onlineFormVL = new VerticalLayout();

        // Fields
        yourNameTF = new TextField("Your name");
        yourSurnameTF = new TextField("Your surname");
        yourEmailTF = new TextField("Your email");
        yourPhoneTF = new TextField("Your phone number");

        yourDateTF = new DateField("Preferred date");
        yourChoiceTF = new TextField("What do you want to do with us?");

        HorizontalLayout customerData = new HorizontalLayout(yourNameTF, yourSurnameTF, yourEmailTF, yourPhoneTF);
        HorizontalLayout customerChoice = new HorizontalLayout(yourDateTF, yourChoiceTF);
        HorizontalLayout buttons = new HorizontalLayout(sendB, cancelB);

        // Binder for the customer
        customerBinder.bind(yourNameTF, "customerFirstName");
        customerBinder.bind(yourSurnameTF, "customerLastName");
        customerBinder.bind(yourEmailTF, "customerEmail");
        customerBinder.bind(yourPhoneTF, "customerPhoneNumber");

        // Create a customer of the above and save it as newWebCustomer

        // Binder for the booking
        // todo


        // add customer to the booking
        // add package items

        // Buttons
        sendB.addClickListener(e -> {
           // save the data to database
           // if saved well redirect to thank you page
        });


        cancelB = new Button("Cancel");
        cancelB.addClickListener(e -> getUI().getPage().setLocation("http://www.google.com")); //todo change URL!

        onlineFormVL.addComponents(informationOnTheSiteL, informationOnTheProcedureL, underConstructionL);

        onlineFormVL.addComponents(customerData, customerChoice, buttons);


        // todo for Mateusz (I just call  getUI().getPage().setLocation("http://www.google.com");)
        // todo after Send redirect to another page with thank you and PDF download or summary? and Contact data
        // todo After cancel redirect to page with contact data

        return onlineFormVL;
    }
}
