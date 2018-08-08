package com.katamlek.nringthymeleaf.frontend.forms;

import com.katamlek.nringthymeleaf.domain.BookingPayment;
import com.katamlek.nringthymeleaf.domain.PaymentDefinition;
import com.katamlek.nringthymeleaf.repositories.BookingPaymentRepository;
import com.katamlek.nringthymeleaf.repositories.PaymentDefinitionRepository;
import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.assertj.core.util.Lists;

import java.util.List;

@SpringComponent
public class PaymentForm extends VerticalLayout implements View {
    private BookingPaymentRepository bookingPaymentRepository;
    private PaymentDefinitionRepository paymentDefinitionRepository;

    public PaymentForm(BookingPaymentRepository bookingPaymentRepository, PaymentDefinitionRepository paymentDefinitionRepository) {
        this.bookingPaymentRepository = bookingPaymentRepository;
        this.paymentDefinitionRepository = paymentDefinitionRepository;
        setMargin(false);
        addComponent(buildPaymentForm());
    }

    private DateField paymentDate;
    private ComboBox<PaymentDefinition> paymentDefinition;
    private TextField paymentNote;
    private TextField paymentAmount;
    private Button save;
    private Button cancel;

    public VerticalLayout buildPaymentForm() {
        VerticalLayout paymentForm = new VerticalLayout();

        HorizontalLayout paymentData = new HorizontalLayout();

 //       paymentDate = new DateField("Date");
        //todo manual binding
        paymentDefinition = new ComboBox<>("Method");
        paymentDefinition.setItems(Lists.newArrayList(paymentDefinitionRepository.findAll())); //todo render the menthod only
        paymentNote = new TextField("Notes");
        paymentAmount = new TextField("Payment amount");

        paymentData.addComponents(paymentDefinition, paymentNote, paymentAmount);

        // Binder
        Binder<BookingPayment> bookingPaymentBinder = new Binder<>(BookingPayment.class);
 //todo bind manually, convert date type       bookingPaymentBinder.bindInstanceFields(this);

        // Save and cancel buttons
        save = new Button("Save note");
        save.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED, ValoTheme.BUTTON_SMALL);
        save.addClickListener(e -> {
            try {
                bookingPaymentRepository.save(bookingPaymentBinder.getBean());
            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                bookingPaymentBinder.getBean().setUnderEditing(false);
                Notification.show("Your data was saved");
                this.setVisible(false);
            }
        });

        cancel = new Button("Cancel");
        cancel.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_SMALL);
        cancel.addClickListener(e -> {
            this.setVisible(false);
        });

        HorizontalLayout buttons = new HorizontalLayout(save, cancel);

        paymentForm.addComponents(paymentData, buttons);

        return paymentForm;
    }

}
