//package com.katamlek.nringthymeleaf.frontend.forms;
//
//import com.katamlek.nringthymeleaf.domain.BookingPayment;
//import com.katamlek.nringthymeleaf.domain.PaymentDefinition;
//import com.katamlek.nringthymeleaf.frontend.grids.CustomerGridView;
//import com.katamlek.nringthymeleaf.repositories.BookingPaymentRepository;
//import com.katamlek.nringthymeleaf.repositories.PaymentDefinitionRepository;
//import com.vaadin.data.Binder;
//import com.vaadin.navigator.View;
//import com.vaadin.spring.annotation.SpringComponent;
//import com.vaadin.ui.*;
//import com.vaadin.ui.themes.ValoTheme;
//import org.assertj.core.util.Lists;
//
//import java.util.List;
//
//@SpringComponent
//public class PaymentForm extends VerticalLayout implements View {
//    private BookingPaymentRepository bookingPaymentRepository;
//    private PaymentDefinitionRepository paymentDefinitionRepository;
//
//    public PaymentForm(BookingPaymentRepository bookingPaymentRepository, PaymentDefinitionRepository paymentDefinitionRepository) {
//        this.bookingPaymentRepository = bookingPaymentRepository;
//        this.paymentDefinitionRepository = paymentDefinitionRepository;
//        setMargin(false);
//        addComponent(buildPaymentForm());
//    }
//
//    private DateField paymentDate;
//    private ComboBox<PaymentDefinition> paymentDefinition;
//    private TextField paymentNote;
//    private TextField paymentAmount;
//    private Button save;
//    private Button cancel;
//
//    public VerticalLayout buildPaymentForm() {
//        VerticalLayout paymentForm = new VerticalLayout();
//
//        HorizontalLayout paymentData = new HorizontalLayout();
//
//        paymentDate = new DateField("Date");
//
//        paymentDefinition = new ComboBox<>("Method");
//
//        paymentDefinition.setItems(Lists.newArrayList(paymentDefinitionRepository.findAll()));
//        paymentDefinition.setItemCaptionGenerator(e -> e.getPaymentName());
//
//        paymentNote = new TextField("Notes");
//        paymentAmount = new TextField("Payment amount");
//
//        paymentData.addComponents(paymentDefinition, paymentNote, paymentAmount);
//
//        // Binder
//        Binder<BookingPayment> bookingPaymentBinder = new Binder<>(BookingPayment.class);
//
//        //todo bind manually? or bookingPaymentBinder.bindInstanceFields(this);
//
//        // Save and cancel buttons
//        save = new Button("Save payment");
//        save.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED, ValoTheme.BUTTON_SMALL);
//        save.addClickListener(e -> {
//            try {
//                bookingPaymentRepository.save(bookingPaymentBinder.getBean());
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            } finally {
//                bookingPaymentBinder.getBean().setUnderEditing(false);
//                Notification.show("Your data was saved");
//                this.setVisible(false);
//            }
//        });
//
//        cancel = new Button("Cancel");
//        cancel.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_SMALL);
//        cancel.addClickListener(e -> {
//
//            //Confirmation popup
//            Window window = new Window("Do you really want to drop the changes this note?");
//
//            //Popup contents
//            VerticalLayout confirmationVL = new VerticalLayout();
//            confirmationVL.addComponent(new Label("There's no undo option and your developer won't help you either."));
//
//            // And buttons
//            Button yesButton = new Button("Drop the form and take me back");
//            yesButton.addClickListener(event1 -> {
//                window.close();
//            // todo where do we navigate? do we just close? set visible-false navigationManager.navigateTo(CustomerGridView.class); // todo or to the previous screen, ie Booking Form
//            });
//
//            Button noButton = new Button("Let's keep on working");
//            noButton.addClickListener(event2 -> {
//                window.close();
//            });
//
//            HorizontalLayout buttonsLayout = new HorizontalLayout(yesButton, noButton);
//            confirmationVL.addComponent(buttonsLayout);
//
//            window.setContent(confirmationVL);
//
//            window.center();
//            UI.getCurrent().addWindow(window);
//
//        });
//
//        HorizontalLayout buttons = new HorizontalLayout(save, cancel);
//
//        paymentForm.addComponents(paymentData, buttons);
//
//        return paymentForm;
//    }
//
//}
