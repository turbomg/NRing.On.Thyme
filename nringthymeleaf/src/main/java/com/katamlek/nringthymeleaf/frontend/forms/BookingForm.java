package com.katamlek.nringthymeleaf.frontend.forms;

import com.katamlek.nringthymeleaf.domain.*;
import com.katamlek.nringthymeleaf.frontend.grids.BookingDocumentsGridView;
import com.katamlek.nringthymeleaf.frontend.grids.BookingGridView;
import com.katamlek.nringthymeleaf.frontend.navigation.NavigationManager;
import com.katamlek.nringthymeleaf.frontend.views.WelcomeView;
import com.katamlek.nringthymeleaf.repositories.BookingDocumentRepository;
import com.katamlek.nringthymeleaf.repositories.BookingPackageItemRepository;
import com.katamlek.nringthymeleaf.repositories.BookingPaymentRepository;
import com.katamlek.nringthymeleaf.repositories.BookingRepository;
import com.vaadin.event.dd.acceptcriteria.Not;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Builds booking form for already registered booking or from scratch.
 */

@UIScope
@SpringView
public class BookingForm extends VerticalLayout implements View {
    // TODO Very Important - set items for all the grids

    // The constructor
    private BookingRepository bookingRepository;
    private NavigationManager navigationManager;
    private BookingPackageItemRepository bookingPackageItemRepository;
    private BookingPaymentRepository bookingPaymentRepository;
    private BookingDocumentRepository bookingDocumentRepository;

    public BookingForm(BookingRepository bookingRepository, NavigationManager navigationManager, BookingPackageItemRepository bookingPackageItemRepository, BookingPaymentRepository bookingPaymentRepository, BookingDocumentRepository bookingDocumentRepository) {
        this.bookingRepository = bookingRepository;
        this.navigationManager = navigationManager;
        this.bookingPackageItemRepository = bookingPackageItemRepository;
        this.bookingPaymentRepository = bookingPaymentRepository;
        this.bookingDocumentRepository = bookingDocumentRepository;
        addComponent(buildBookingForm());
        setMargin(false);
    }

    // Window label & buttons
    private Label bookingFormL = new Label("Booking Form");
    private Button saveAll;
    private Button cancelAll;
    private Button backToList;
    private Button printBookingFormB;
    private Button printReturnFormB;
    private Button cancelBookingB;
    private Button duplicateB;


    // Section properties
    // Details
    private TextField bookedByTF;
    private DateField bookedAtDF;
    private Grid<BookingNote> noteG;
    private Button cancelB;
    private Button addBookingNoteB;
    private ComboBox<SignatureStatus> signatureStatusCB;
    private ComboBox<PaymentStatus> paymentStatusCB;
    private ComboBox<String> emailConfirmationCB;
    private ComboBox<String> emailReminderCB;
    private DateField emailReminderDateDF;

    // Drivers
    private Grid<Customer> driverG;
    private Button findDriverB;
    private Button addNewDriverB;

    // Package
    private Grid<BookingPackageItem> packageG;
    private Button addPackageItemB;

    // Payments
    private Grid<BookingPayment> paymentG;
    private Button addPaymentB;

    // Documents
    private Grid<BookingDocument> documentG;
    private Button addDocumentB;

    // Methods that construct sections
    // Details
    public VerticalLayout buildDetailsSection() {
        VerticalLayout detailsVL = new VerticalLayout();
        detailsVL.setMargin(false);

        bookedByTF = new TextField("Booked by");
        bookedAtDF = new DateField("Booked on");

        signatureStatusCB = new ComboBox<SignatureStatus>("Signature status");
        signatureStatusCB.setItems(Arrays.asList(SignatureStatus.values()));

        paymentStatusCB = new ComboBox<PaymentStatus>("Payment status");
        paymentStatusCB.setItems(Arrays.asList(PaymentStatus.values()));

        emailConfirmationCB = new ComboBox<>("E-mail confirmation");
        emailConfirmationCB.setItems("Sent", "Not sent", "Not required");
        emailReminderCB = new ComboBox<>("E-mail reminder");
        emailReminderCB.setItems("Sent", "Not sent", "Not required");
        emailReminderDateDF = new DateField();
        //todo emailReminderDateDF.setDefaultValue();

        noteG = new Grid<>(BookingNote.class);
        noteG.setColumns("enteredOn", "text");
        //todo for all note grids if note status==deleted change background / cross the text; quick solution: show status column

        noteG.setColumnOrder("enteredOn", "text");

        noteG.getColumns().forEach(column -> column.setSortable(true));
        noteG.setColumnReorderingAllowed(true);
        noteG.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        // allow inline editing
        noteG.getEditor().setEnabled(true); //todo allow or not?

        // Extra button delete
        noteG.addComponentColumn(this::deleteBookingNoteButton);

        noteG.setHeightByRows(4);
        noteG.addStyleNames(ValoTheme.TABLE_BORDERLESS, ValoTheme.TABLE_COMPACT);
        noteG.setCaption("Booking notes");

        addBookingNoteB = new Button("Add booking note");
        addBookingNoteB.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED, ValoTheme.BUTTON_SMALL);
        addBookingNoteB.setIcon(VaadinIcons.PLUS);
        addBookingNoteB.addClickListener(e -> Notification.show("Can't do this yet, sorry")); //todo

        printBookingFormB = new Button("Print\nbooking\nform");
        printBookingFormB.addClickListener(e -> Notification.show("Well, maybe later...")); //todo
        printBookingFormB.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_LARGE, ValoTheme.BUTTON_ICON_ALIGN_TOP);
        printBookingFormB.setIcon(VaadinIcons.PRINT);

        printReturnFormB = new Button("Print return form");
        printReturnFormB.addClickListener(e -> Notification.show("Can't do this, but let me learn"));
        printReturnFormB.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_LARGE, ValoTheme.BUTTON_ICON_ALIGN_TOP);
        printReturnFormB.setIcon(VaadinIcons.PRINT);

        cancelB = new Button("Cancel booking");
        //todo   cancelB.addClickListener(e -> setStatus ??? but how, and save changes )
        // define a separate method changeState and call it here?
        cancelB.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_LARGE, ValoTheme.BUTTON_ICON_ALIGN_TOP);
        cancelB.setIcon(VaadinIcons.CLOSE);

        duplicateB = new Button("Duplicate booking");
        //todo   duplicateB.addClickListener()
        duplicateB.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_LARGE, ValoTheme.BUTTON_ICON_ALIGN_TOP);
        duplicateB.setIcon(VaadinIcons.COPY);

        // HL with user and date and something else
        HorizontalLayout userDateHL = new HorizontalLayout(bookedByTF, bookedAtDF, signatureStatusCB, paymentStatusCB);
        // HL with statuses - other combos
        HorizontalLayout combosHL = new HorizontalLayout(emailConfirmationCB, emailReminderCB, emailReminderDateDF); // todo align damned date field!
        // HL with print buttons
        HorizontalLayout buttonsHL = new HorizontalLayout(printBookingFormB, printReturnFormB, cancelB, duplicateB);

        detailsVL.addComponents(userDateHL, combosHL, buttonsHL, noteG, addBookingNoteB);
        detailsVL.setCaption("Booking details");
        return detailsVL;
    }

    // Drivers
    public VerticalLayout buildDriversSection() {
        VerticalLayout driversVL = new VerticalLayout();
        driversVL.setMargin(false);

        driverG = new Grid<>(Customer.class);
        driverG.setColumns("id", "customerFirstName", "customerLastName", "customerGroup", "customerGeneralInformation");
        driverG.setColumnOrder("id", "customerFirstName", "customerLastName", "customerGroup", "customerGeneralInformation");

        driverG.getColumns().forEach(column -> column.setSortable(true));
        driverG.setColumnReorderingAllowed(true);
        driverG.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        // allow inline editing
        driverG.getEditor().setEnabled(true); //todo allow or not?, no id editing!!!!

        // extra button delete
        driverG.addComponentColumn(this::deleteDriverButton);

        driverG.setHeightByRows(4);

        findDriverB = new Button("Search for driver");
        findDriverB.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_SMALL);
        findDriverB.addClickListener(e -> Notification.show("Can't do this right now, get back to me later")); //todo - popup, move data to bean
        findDriverB.setIcon(VaadinIcons.SEARCH);

        addNewDriverB = new Button("Add driver");
        addNewDriverB.addClickListener(e -> Notification.show("Can't do this right now, get back to me later")); //todo - popup with new customer, move data to bean
        addNewDriverB.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED, ValoTheme.BUTTON_SMALL);
        addNewDriverB.setIcon(VaadinIcons.PLUS);

        HorizontalLayout buttonsHL = new HorizontalLayout(addNewDriverB, findDriverB);

        driversVL.addComponents(driverG, buttonsHL);
        driversVL.setCaption("Driver details");
        return driversVL;
    }

    // Package
    public VerticalLayout buildPackageSection() {
        VerticalLayout packageVL = new VerticalLayout();
        packageVL.setMargin(false);

        packageG = new Grid<>(BookingPackageItem.class);
        packageG.setColumns("date", "startTime", "description", "unitPrice", "quantity", "paymentStatus", "cancelled");

        packageG.addColumn(item -> {
            Long total = 100l; //todo multiplication
            return total;
        }).setCaption("Total").setId("total");


        packageG.getColumns().forEach(column -> column.setSortable(true));
        packageG.setColumnReorderingAllowed(true);
        packageG.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        // allow inline editing
        packageG.getEditor().setEnabled(true); //todo allow or not?, no id editing!!!!

        // extra button delete
        packageG.addComponentColumn(this::deletePackageItemButton);

        packageG.setHeightByRows(4);

        packageG.setColumnOrder("date", "startTime", "description", "unitPrice", "quantity", "total", "paymentStatus", "cancelled");
        packageG.addStyleNames(ValoTheme.TABLE_COMPACT);

        addPackageItemB = new Button("Add package item");
        addPackageItemB.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED, ValoTheme.BUTTON_SMALL);
        addPackageItemB.setIcon(VaadinIcons.PLUS);
        addPackageItemB.addClickListener(e -> Notification.show("Sorry, later :)")); //todo

        packageVL.addComponents(packageG, addPackageItemB);
        packageVL.setCaption("Package details");
        return packageVL;
    }

    // Payments
    public VerticalLayout buildPaymentsSection() {
        VerticalLayout paymentsVL = new VerticalLayout();
        paymentsVL.setMargin(false);

        paymentG = new Grid<>(BookingPayment.class);
        paymentG.setColumns("paymentDate", "paymentNote", "paymentAmount");

        paymentG.addColumn(bookingPayment -> {
            String method = bookingPayment.getPaymentDefinition().getPaymentName();
            return method;
        }).setCaption("Method").setId("method");


        paymentG.setColumns("paymentDate", "paymentNote", "method", "paymentAmount");

        // extra button delete
        paymentG.addComponentColumn(this::deletePaymentButton);

        paymentG.setHeightByRows(4);

        addPaymentB = new Button("Add payment");
        addPaymentB.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED, ValoTheme.BUTTON_SMALL);
        addPaymentB.setIcon(VaadinIcons.PLUS);
        addPaymentB.addClickListener(e -> Notification.show("Still trying")); //todo

        paymentsVL.addComponents(paymentG, addPaymentB);
        paymentsVL.setCaption("Payments");
        return paymentsVL;
    }

    // Documents
    public VerticalLayout buildDocumentsSection() {
        VerticalLayout documentsVL = new VerticalLayout();
        documentsVL.setMargin(false);

        documentG = new Grid<>(BookingDocument.class);
        documentG.setColumns("date", "bookingDocumentDescription", "bookingDocumentAdditionalInfo");
        documentG.setColumnOrder("date", "bookingDocumentDescription", "bookingDocumentAdditionalInfo");

        documentG.getColumns().forEach(column -> column.setSortable(true));
        documentG.setColumnReorderingAllowed(true);
        documentG.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        // allow inline editing
        documentG.getEditor().setEnabled(true); //todo allow or not?

        // extra button delete
        documentG.addComponentColumn(this::deleteDocumentButton);

        documentG.setSizeFull();
        documentG.setHeightByRows(4);

        documentG.addStyleNames(ValoTheme.TABLE_COMPACT);

        addDocumentB = new Button("Add document");
        addDocumentB.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED, ValoTheme.BUTTON_SMALL);
        addDocumentB.setIcon(VaadinIcons.PLUS);
        addDocumentB.addClickListener(e -> Notification.show("Can't do this yet, see you later.")); //todo

        documentsVL.addComponents(documentG, addDocumentB);
        documentsVL.setCaption("Booking documents");
        return documentsVL;
    }

    // Put it all together
    public VerticalLayout buildBookingForm() {
        VerticalLayout bookingForm = new VerticalLayout();
        bookingForm.setMargin(false);

        bookingFormL.addStyleNames(ValoTheme.LABEL_NO_MARGIN, ValoTheme.LABEL_LARGE);

        // Form buttons
        saveAll = new Button("Save");
        saveAll.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_BORDERLESS_COLORED);
        saveAll.setIcon(VaadinIcons.PENCIL);
        saveAll.addClickListener(e -> Notification.show("Comming soon")); //todo

        cancelAll = new Button("Cancel");
        cancelAll.addStyleNames(ValoTheme.BUTTON_BORDERLESS);
        cancelAll.setIcon(VaadinIcons.ERASER);
        cancelAll.addClickListener(e -> {

            // Confirmation window
            Window confirm = new Window("Confirm the operation");
            confirm.addStyleNames(ValoTheme.WINDOW_TOP_TOOLBAR);
            VerticalLayout areYouSure = new VerticalLayout(new Label("Are you sure? All the data will be lost."));
            areYouSure.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
            Button stopThis = new Button("Stay here");
            stopThis.addStyleNames(ValoTheme.BUTTON_SMALL, ValoTheme.BUTTON_BORDERLESS);
            stopThis.addClickListener(clickEvent -> confirm.close());

            Button proceed = new Button("Proceed");
            proceed.addStyleNames(ValoTheme.BUTTON_SMALL, ValoTheme.BUTTON_BORDERLESS);
            proceed.addClickListener(clickEvent -> {
                confirm.close(); //todo detach?
                detach();
                navigationManager.navigateTo(BookingGridView.class);
            });

            HorizontalLayout buttons = new HorizontalLayout(stopThis, proceed);
            buttons.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
            areYouSure.addComponents(buttons);
            confirm.setContent(areYouSure);
            confirm.setDraggable(true);
            confirm.setClosable(true);
            confirm.center();
            UI.getCurrent().addWindow(confirm);
        });

        backToList = new Button("Back to list");
        backToList.addStyleNames(ValoTheme.BUTTON_BORDERLESS);
        backToList.setIcon(VaadinIcons.ARROW_LEFT);
        backToList.addClickListener(e -> navigationManager.navigateTo(BookingGridView.class));

        HorizontalLayout buttonsHL = new HorizontalLayout(saveAll, cancelAll, backToList);

        bookingForm.addComponents(bookingFormL, buttonsHL, buildDetailsSection(), buildDriversSection(), buildPackageSection(), buildPaymentsSection(), buildDocumentsSection());

        bookingForm.setMargin(false);

        return bookingForm;
    }

    // Helper methods
    private Button deleteBookingNoteButton(BookingNote bookingNote) {
        Button deleteBNButton = new Button(VaadinIcons.MINUS_CIRCLE);
        deleteBNButton.addStyleNames(ValoTheme.BUTTON_SMALL);
        deleteBNButton.addClickListener(e -> Notification.show("I can cancel this one for you")); //todo
        return deleteBNButton;
    }

    private Button deleteDriverButton(Customer customer) {
        Button deleteDRButton = new Button(VaadinIcons.MINUS_CIRCLE);
        deleteDRButton.addStyleNames(ValoTheme.BUTTON_SMALL);
        deleteDRButton.addClickListener(e -> Notification.show("I can cancel this one for you")); //todo
        return deleteDRButton;
    }

    private Button deletePackageItemButton(BookingPackageItem packageItem) {
        Button deletePIButton = new Button(VaadinIcons.MINUS_CIRCLE);
        deletePIButton.addStyleNames(ValoTheme.BUTTON_SMALL);
        deletePIButton.addClickListener(e -> Notification.show("I can cancel this one for you")); //todo
        return deletePIButton;
    }

    private Button deletePaymentButton(BookingPayment bookingPayment) {
        Button deletePButton = new Button(VaadinIcons.MINUS_CIRCLE);
        deletePButton.addStyleNames(ValoTheme.BUTTON_SMALL);
        deletePButton.addClickListener(e -> Notification.show("I can cancel this one for you")); //todo
        return deletePButton;
    }

    private Button deleteDocumentButton(BookingDocument document) {
        Button deleteBDButton = new Button(VaadinIcons.MINUS_CIRCLE);
        deleteBDButton.addStyleNames(ValoTheme.BUTTON_SMALL);
        deleteBDButton.addClickListener(e -> Notification.show("I can cancel this one for you")); //todo
        return deleteBDButton;
    }


// todo enter/ onEnter, binding

}
