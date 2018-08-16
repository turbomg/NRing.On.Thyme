package com.katamlek.nringthymeleaf.frontend.forms;

import com.katamlek.nringthymeleaf.domain.*;
import com.katamlek.nringthymeleaf.frontend.grids.BookingGridView;
import com.katamlek.nringthymeleaf.frontend.grids.CustomerGridView;
import com.katamlek.nringthymeleaf.frontend.navigation.NavigationManager;
import com.katamlek.nringthymeleaf.repositories.*;
import com.katamlek.nringthymeleaf.vaadinutils.CustomStringToBigDecimalConverter;
import com.katamlek.nringthymeleaf.vaadinutils.CustomStringToIntegerConverter;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.converter.LocalDateToDateConverter;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

/**
 * Builds booking form for already registered booking or from scratch.
 */

@UIScope
@SpringView
public class BookingForm extends VerticalLayout implements View {
    // The constructor
    private BookingRepository bookingRepository;
    private CustomerRepository customerRepository;
    private UserRepository userRepository;
    private NavigationManager navigationManager;
    private BookingPackageItemRepository bookingPackageItemRepository;
    private BookingPaymentRepository bookingPaymentRepository;
    private BookingDocumentRepository bookingDocumentRepository;
    private BookingNoteRepository bookingNoteRepository;
    private BookingPackageItemCarRepository bookingPackageItemCarRepository;
    private TemporaryPackageItemRepository temporaryPackageItemRepository;

    public BookingForm(BookingRepository bookingRepository, NavigationManager navigationManager, BookingPackageItemRepository bookingPackageItemRepository, BookingPaymentRepository bookingPaymentRepository, BookingDocumentRepository bookingDocumentRepository, BookingNoteRepository bookingNoteRepository, BookingPackageItemCarRepository bookingPackageItemCarRepository, CustomerRepository customerRepository, UserRepository userRepository, TemporaryPackageItemRepository temporaryPackageItemRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.navigationManager = navigationManager;
        this.bookingPackageItemRepository = bookingPackageItemRepository;
        this.bookingPaymentRepository = bookingPaymentRepository;
        this.bookingDocumentRepository = bookingDocumentRepository;
        this.bookingNoteRepository = bookingNoteRepository;
        this.bookingPackageItemCarRepository = bookingPackageItemCarRepository;
        this.customerRepository = customerRepository;
        this.temporaryPackageItemRepository = temporaryPackageItemRepository;
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
    private ComboBox<User> bookedByCB;
    private DateField bookedAtDF;
    private Grid<BookingNote> noteG;
    private Button cancelB;
    private Button addBookingNoteB;
    private ComboBox<SignatureStatus> signatureStatusCB;
    private ComboBox<PaymentStatus> paymentStatusCB;
    private CheckBox emailConfirmationCB;
    private CheckBox emailReminderCB;
    private DateField emailReminderDateDF;
    private ComboBox<BookingStatus> bookingStatusCB;

    // Drivers

    private Grid<Customer> driverG;
    private Button findDriverB;
    private Button addNewDriverB;

    // Package
    private Grid<TemporaryPackageItem> packageG;
    private Button addPackageItemB;

    // Payments
    private Grid<BookingPayment> paymentG;
    private Button addPaymentB;

    // Documents
    private Grid<BookingDocument> documentG;
    private Button addDocumentB;

    // Booking cars
    private Grid<BookingPackageItemCar> bookingCarsG;
    private Button addBookingCarB;

    // Sub - forms
    // booking note
    private VerticalLayout bookingNoteVL = buildBookingNoteForm();
    private DateField noteDateDF;
    private TextField noteTF;

    // payment
    private VerticalLayout bookingPaymentVL = buildBookingPaymentForm();
    private DateField paymentDateDF;
    private TextField paymentAmountTF;
    private TextField paymentRemarksTF;

    // customer
    private VerticalLayout customersVL = buildCustomerForm();
    private ComboBox<Customer> customerPickCB;
    private TextField customerId;
    private TextField customerName;
    private TextField customerSurname;
    private TextField customerGroup;
    private TextField customerGeneralInformation;

    private Binder<Customer> customerBinder; // just sets the relationship between booking and customer

    // package item // todo exchange temp solution to target one
    private VerticalLayout tempPackageItemVL = buildTempPackageItemForm();
    private DateField itemDateDF;
    private TextField itemTimeTF;
    private TextField itemDescriptionTF;
    private TextField itemPriceTF;
    private TextField itemQuantityTF;
    private TextField itemTotalTF;
    private CheckBox isItemCancelledCB;

    // booking document
    private VerticalLayout bookingDocumentVL = buildBookingDocumentForm();
    private DateField bookingDocumentDateDF;
    private TextField bookingDocumentDescriptionTF;
    private TextField bookingDocumentInfoTF;

    // Binder
    private Binder<Booking> bookingBinder;

    // Methods that construct sections
    // Details
    public VerticalLayout buildDetailsSection() {
        VerticalLayout detailsVL = new VerticalLayout();
        detailsVL.setMargin(false);

        bookedByCB = new ComboBox<>("Booked by");
        bookedByCB.setItems(Lists.newArrayList(userRepository.findAll()));

        bookedAtDF = new DateField("Booked on");

        signatureStatusCB = new ComboBox<SignatureStatus>("Signature status");
        signatureStatusCB.setItems(Arrays.asList(SignatureStatus.values()));

        paymentStatusCB = new ComboBox<PaymentStatus>("Payment status");
        paymentStatusCB.setItems(Arrays.asList(PaymentStatus.values()));

        emailConfirmationCB = new CheckBox("E-mail confirmation");
        // emailConfirmationCB.setItems("Sent", "Not sent", "Not required");
        emailReminderCB = new CheckBox("E-mail reminder");
        // emailReminderCB.setItems("Sent", "Not sent", "Not required");
        emailReminderDateDF = new DateField();
        emailReminderDateDF.setDefaultValue(LocalDate.now());
        bookingStatusCB = new ComboBox<>("Booking status");
        bookingStatusCB.setItems(BookingStatus.values());

        noteG = new Grid<>(BookingNote.class);
        noteG.setColumns("enteredOn", "text");


        //todo aks JONO if they need the deleted notes on the screen / at all
        //todo all notes here - set items, check if done

        noteG.setColumnOrder("enteredOn", "text");
//        noteG.setItems(bookingNoteRepository.findByBooking(bookingBinder.getBean()));

        noteG.getColumns().forEach(column -> column.setSortable(true));
        noteG.setColumnReorderingAllowed(true);
        noteG.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        // allow inline editing
        // noteG.getEditor().setEnabled(true);

        // Extra button delete
// Deliberately commented out
        // noteG.addComponentColumn(this::deleteBookingNoteButton);

        noteG.setHeightByRows(4);
        noteG.addStyleNames(ValoTheme.TABLE_BORDERLESS, ValoTheme.TABLE_COMPACT);
        noteG.setCaption("Booking notes");

// Deliberately turning this option off
//        noteG.addItemClickListener(event -> {
//            if (event.getMouseEventDetails().isDoubleClick()) {
//                Notification.show("Please be patient, will open the form soon.");
//                // todo goto bookingNote(Long id) when the form is ready;
//            }
//        });

        addBookingNoteB = new Button("Add booking note");
        addBookingNoteB.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED, ValoTheme.BUTTON_SMALL);
        addBookingNoteB.setIcon(VaadinIcons.PLUS);
        addBookingNoteB.addClickListener(e -> {
            bookingNoteVL.setVisible(true);
            addBookingNoteB.setVisible(false);
        });

        printBookingFormB = new Button("Print\nbooking\nform");
        printBookingFormB.addClickListener(e -> Notification.show("Well, maybe later...")); //todo
        printBookingFormB.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_LARGE, ValoTheme.BUTTON_ICON_ALIGN_TOP);
        printBookingFormB.setIcon(VaadinIcons.PRINT);

        printReturnFormB = new Button("Print return form");
        printReturnFormB.addClickListener(e -> Notification.show("Can't do this, but let me learn"));
        printReturnFormB.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_LARGE, ValoTheme.BUTTON_ICON_ALIGN_TOP);
        printReturnFormB.setIcon(VaadinIcons.PRINT);

        cancelB = new Button("Cancel booking");
        cancelB.addClickListener(e -> {
            bookingBinder.getBean().setBookingStatus(BookingStatus.CANCELLED);
            bookingRepository.save(bookingBinder.getBean());
        });

        cancelB.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_LARGE, ValoTheme.BUTTON_ICON_ALIGN_TOP);
        cancelB.setIcon(VaadinIcons.CLOSE);

        duplicateB = new Button("Duplicate booking");
        duplicateB.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_LARGE, ValoTheme.BUTTON_ICON_ALIGN_TOP);
        duplicateB.setIcon(VaadinIcons.COPY);
        duplicateB.addClickListener(e -> {
            Booking duplicateBooking = new Booking();
            BeanUtils.copyProperties(bookingBinder.getBean(), duplicateBooking);
            bookingRepository.save(duplicateBooking);
            navigationManager.navigateTo(BookingForm.class, duplicateBooking.getId());
        });

        // HL with user and date and something else
        HorizontalLayout userDateHL = new HorizontalLayout(bookedByCB, bookedAtDF, signatureStatusCB, paymentStatusCB);
        // HL with statuses - other combos
        HorizontalLayout combosHL = new HorizontalLayout(emailConfirmationCB, emailReminderCB, emailReminderDateDF, bookingStatusCB); // todo align the damned date field!
        // HL with print buttons
        HorizontalLayout buttonsHL = new HorizontalLayout(printBookingFormB, printReturnFormB, cancelB, duplicateB);

        detailsVL.addComponents(userDateHL, combosHL, buttonsHL, noteG, addBookingNoteB, bookingNoteVL);
        detailsVL.setCaption("Booking details");
        return detailsVL;
    }

    // Drivers
    public VerticalLayout buildDriversSection() {
        VerticalLayout driversVL = new VerticalLayout();
        driversVL.setMargin(false);

        driverG = new Grid<>(Customer.class);
        driverG.setColumns("documentId", "customerFirstName", "customerLastName", "customerGroup", "customerGeneralInformation");
        driverG.setColumnOrder("documentId", "customerFirstName", "customerLastName", "customerGroup", "customerGeneralInformation");

//        driverG.setItems(customerRepository.findDistinctByBookings(bookingBinder.getBean()));

        driverG.getColumns().forEach(column -> column.setSortable(true));
        driverG.setColumnReorderingAllowed(true);
        driverG.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        driverG.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                navigationManager.navigateTo(CustomerForm.class, event.getItem().getId());
            }
        });

        // allow inline editing
        // driverG.getEditor().setEnabled(true); //todo allow or not?, no id editing!!!!

        // extra button delete
        driverG.addComponentColumn(this::deleteDriverButton);
        //todo remove from grid, not database - remove from collection, save

        driverG.setHeightByRows(4);

        findDriverB = new Button("Search for driver");
        findDriverB.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_SMALL);
        findDriverB.addClickListener(e -> {
            navigationManager.navigateTo(CustomerGridView.class);
        }); //todo - how to move data from this list to the form?
        findDriverB.setIcon(VaadinIcons.SEARCH);

        addNewDriverB = new Button("Add driver");

        addNewDriverB.addClickListener(e -> {
            customersVL.setVisible(true);
            addNewDriverB.setVisible(false);
        });

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

        packageG = new Grid<>(TemporaryPackageItem.class);
        packageG.setColumns("itemDate", "startTime", "itemDescription", "itemUnitPrice", "itemQuantity", "itemTotal", "cancelled");

//        packageG.setItems(bookingPackageItemRepository.findDistinctByBooking(bookingBinder.getBean()));

        packageG.getColumns().forEach(column -> column.setSortable(true));
        packageG.setColumnReorderingAllowed(true);
        packageG.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        // allow inline editing
        //packageG.getEditor().setEnabled(true);

        // extra button delete
        packageG.addComponentColumn(this::deletePackageItemButton);

        packageG.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                navigationManager.navigateTo(BookingPackageItemForm.class, event.getItem().getId());
            }
        });

        packageG.setHeightByRows(4);

        packageG.setColumnOrder("itemDate", "startTime", "itemDescription", "itemUnitPrice", "itemQuantity", "itemTotal", "cancelled");
        packageG.addStyleNames(ValoTheme.TABLE_COMPACT);

//        Button addItem = new Button("Add item");
//        addItem.addClickListener(e -> navigationManager.navigateTo(BookingPackageItemForm.class));
//        addItem.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED, ValoTheme.BUTTON_SMALL);
//        addItem.setIcon(VaadinIcons.PLUS);

        addPackageItemB = new Button("Add item");
        addPackageItemB.addClickListener(e -> {
            tempPackageItemVL.setVisible(true);
            addPackageItemB.setVisible(false);
        });

        packageVL.addComponents(packageG, addPackageItemB);
        packageVL.setCaption("Package details");
        return packageVL;
    }

    // Payments
    public VerticalLayout buildPaymentsSection() {
        VerticalLayout paymentsVL = new VerticalLayout();
        paymentsVL.setMargin(false);

        paymentG = new Grid<>(BookingPayment.class);
        paymentG.setColumns("paymentDate", "paymentRemarks", "paymentAmount");

        //      paymentG.setItems(bookingPaymentRepository.findDistinctByBooking(bookingBinder.getBean()));

        paymentG.addColumn(bookingPayment -> {
            String method = bookingPayment.getPaymentDefinition().getPaymentName();
            return method;
        }).setCaption("Method").setId("method");


        paymentG.setColumns("paymentDate", "paymentRemarks", "method", "paymentAmount");

        // extra button delete
        paymentG.addComponentColumn(this::deletePaymentButton);

        paymentG.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
 //               navigationManager.navigateTo(PaymentForm.class, event.getItem().getId()); // todo show the payment on VL
            }
        });

        paymentG.setHeightByRows(4);

        addPaymentB = new Button("Add payment");
        addPaymentB.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED, ValoTheme.BUTTON_SMALL);
        addPaymentB.setIcon(VaadinIcons.PLUS);
        //     addPaymentB.addClickListener(e -> paymentsVL.addComponents(paymentForm));
        addPaymentB.addClickListener(e -> {
            bookingPaymentVL.setVisible(true);
            addPaymentB.setVisible(false);
        });

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

//        documentG.setItems(bookingDocumentRepository.findDistinctByBooking(bookingBinder.getBean()));

        documentG.getColumns().forEach(column -> column.setSortable(true));
        documentG.setColumnReorderingAllowed(true);
        documentG.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        // allow inline editing
        // documentG.getEditor().setEnabled(true);

        // extra button delete
        documentG.addComponentColumn(this::deleteDocumentButton);

        documentG.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                navigationManager.navigateTo(BookingDocumentForm.class, event.getItem().getId());
            }
        });

        documentG.setSizeFull();
        documentG.setHeightByRows(4);

        documentG.addStyleNames(ValoTheme.TABLE_COMPACT);

        addDocumentB = new Button("Add document");
        addDocumentB.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED, ValoTheme.BUTTON_SMALL);
        addDocumentB.setIcon(VaadinIcons.PLUS);
        // addDocumentB.addClickListener(e -> Notification.show("Can't do this yet, see you later.")); //todo

        addDocumentB.addClickListener(e -> {
            bookingDocumentVL.setVisible(true);
            addDocumentB.setVisible(false);
        });

        documentsVL.addComponents(documentG, addDocumentB);
        documentsVL.setCaption("Booking documents");
        return documentsVL;
    }

//    public VerticalLayout buildBookingCarSection() {
//        VerticalLayout bookingCarsVL = new VerticalLayout();
//
//        bookingCarsVL.setMargin(false);
//
//        bookingCarsG = new Grid<>(BookingPackageItemCar.class);
//        bookingCarsG.setColumns("model", "plate", "carStatus", "currentlyInUse");
//        bookingCarsG.setColumnOrder("model", "plate", "carStatus", "currentlyInUse");
//
//        bookingCarsG.getColumns().forEach(column -> column.setSortable(true));
//        bookingCarsG.setColumnReorderingAllowed(true);
//        bookingCarsG.setColumnResizeMode(ColumnResizeMode.ANIMATED);
//
//        // allow inline editing
//        bookingCarsG.getEditor().setEnabled(true); //todo allow or not?
//
//        // extra button delete
//        bookingCarsG.addComponentColumn(this::deleteCarButton);
//
//        bookingCarsG.addItemClickListener(event -> {
//            if (event.getMouseEventDetails().isDoubleClick()) {
//                Notification.show("Please be patient, will open the form soon.");
//                // todo set navigator to the selected car
//                // todo NOT A NAVIGATOR ! New carEditForm(Long id);
//            }
//        });
//
//        bookingCarsG.setSizeFull();
//        bookingCarsG.setHeightByRows(4);
//
//        bookingCarsG.addStyleNames(ValoTheme.TABLE_COMPACT);
//
//        addBookingCarB = new Button("Add booking car");
//        addBookingCarB.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED, ValoTheme.BUTTON_SMALL);
//        addBookingCarB.setIcon(VaadinIcons.PLUS);
//        addBookingCarB.addClickListener(e -> navigationManager.navigateTo(BookingCarForm.class)); //todo - get the form, add a car
//
//        bookingCarsVL.addComponents(bookingCarsG, addBookingCarB);
//        bookingCarsVL.setCaption("Booking cars");
//        return bookingCarsVL;
//
//    }

    // Put it all together
    public VerticalLayout buildBookingForm() {
        VerticalLayout bookingForm = new VerticalLayout();
        bookingForm.setMargin(false);

        bookingFormL.addStyleNames(ValoTheme.LABEL_NO_MARGIN, ValoTheme.LABEL_LARGE);

        // Form buttons
        saveAll = new Button("Save");
        saveAll.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_BORDERLESS_COLORED);
        saveAll.setIcon(VaadinIcons.PENCIL);
        saveAll.addClickListener(e -> {
            try {
                bookingRepository.save(bookingBinder.getBean());
            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                bookingBinder.getBean().setUnderEditing(false);
                Notification.show("I saved your data.");
            }
        });

        cancelAll = new Button("Cancel");
        cancelAll.setDescription("Caution! Your data will be lost!");
        cancelAll.addStyleNames(ValoTheme.BUTTON_BORDERLESS);
        cancelAll.setIcon(VaadinIcons.ERASER);
        cancelAll.addClickListener(e -> {
            //Confirmation popup
            Window window = new Window("Do you really want to drop the changes?");

            //Popup contents
            VerticalLayout confirmationVL = new VerticalLayout();
            confirmationVL.addComponent(new Label("There's no undo option and your developer won't help you either."));

            // And buttons
            Button yesButton = new Button("Drop the form and take me back");
            yesButton.addClickListener(event1 -> {
                window.close();
                navigationManager.navigateTo(BookingGridView.class);
            });

            Button noButton = new Button("Let's keep on working");
            noButton.addClickListener(event2 -> {
                window.close();
            });

            HorizontalLayout buttonsLayout = new HorizontalLayout(yesButton, noButton);
            confirmationVL.addComponent(buttonsLayout);

            window.setContent(confirmationVL);

            window.center();
            UI.getCurrent().addWindow(window);

        });

        backToList = new Button("Back to list");
        backToList.addStyleNames(ValoTheme.BUTTON_BORDERLESS);
        backToList.setIcon(VaadinIcons.ARROW_LEFT);
        backToList.addClickListener(e -> navigationManager.navigateTo(BookingGridView.class));

        HorizontalLayout buttonsHL = new HorizontalLayout(saveAll, cancelAll, backToList);

        bookingForm.addComponents(bookingFormL, buttonsHL, buildDetailsSection(), buildDriversSection(), customersVL, buildPackageSection(), tempPackageItemVL, buildPaymentsSection(), bookingPaymentVL, buildDocumentsSection(), bookingDocumentVL);

        bookingForm.setMargin(false);


        // Data bindings
        bookingBinder = new Binder<>(Booking.class);

        // Details
        bookingBinder.forField(bookedAtDF).withConverter(new LocalDateToDateConverter()).bind(Booking::getCreateDate, Booking::setCreateDate);

        bookingBinder.bind(bookedByCB, "createdBy");
        bookingBinder.bind(signatureStatusCB, "signatureStatus");
        bookingBinder.bind(paymentStatusCB, "paymentStatus");
        bookingBinder.bind(emailConfirmationCB, "emailConfirmationSent");
        bookingBinder.bind(emailReminderCB, "emailReminderSent");
        bookingBinder.forField(emailReminderDateDF).withConverter(new LocalDateToDateConverter()).bind(Booking::getEmailReminderSendDate, Booking::setEmailReminderSendDate);

        // Booking notes
        // see buildBookingNoteForm()

        return bookingForm;
    }

    // Helper methods
    private Button deleteBookingNoteButton(BookingNote bookingNote) {
        Button deleteBNButton = new Button(VaadinIcons.MINUS_CIRCLE);
        deleteBNButton.addStyleNames(ValoTheme.BUTTON_SMALL);
        deleteBNButton.addClickListener(e -> {

            if (bookingNote.isUnderEditing()) {
                Notification.show("Someone's working with this note. I can't delete it now.");
            } else {

                //Confirmation popup
                Window window = new Window("Do you really want to delete this note?");

                //Popup contents
                VerticalLayout confirmationVL = new VerticalLayout();
                confirmationVL.addComponent(new Label("There's no undo option and your developer won't help you either."));

                // And buttons
                Button yesButton = new Button("Proceed");
                yesButton.addClickListener(event1 -> {
                    bookingNoteRepository.delete(bookingNote);
                    //          noteG.setItems(bookingNoteRepository.findByBooking(bookingBinder.getBean()));
                    window.close();
                });

                Button noButton = new Button("Give the delete up");
                noButton.addClickListener(event2 -> {
                    window.close();
                });

                HorizontalLayout buttonsLayout = new HorizontalLayout(yesButton, noButton);
                confirmationVL.addComponent(buttonsLayout);

                window.setContent(confirmationVL);

                window.center();
                UI.getCurrent().addWindow(window);

            }
        });

        return deleteBNButton;
    }

    private Button deleteDriverButton(Customer customer) {
        Button deleteDButton = new Button(VaadinIcons.MINUS_CIRCLE);
        deleteDButton.addStyleNames(ValoTheme.BUTTON_SMALL);
//        deleteDButton.addClickListener(e -> {
//            driverG.
//        } //todo remove only from the list, not from customer repo

        return deleteDButton;
    }

    private Button deletePackageItemButton(TemporaryPackageItem temporaryPackageItem) {
        Button deletePIButton = new Button(VaadinIcons.MINUS_CIRCLE);
        deletePIButton.addStyleNames(ValoTheme.BUTTON_SMALL);
        deletePIButton.addClickListener(e -> {
            if (temporaryPackageItem.isUnderEditing()) {
                Notification.show("Someone's working with this item. I can't delete it now.");
            } else {

                //Confirmation popup
                Window window = new Window("Do you really want to delete this item?");

                //Popup contents
                VerticalLayout confirmationVL = new VerticalLayout();
                confirmationVL.addComponent(new Label("There's no undo option and your developer won't help you either."));

                // And buttons
                Button yesButton = new Button("Proceed");
                yesButton.addClickListener(event1 -> {
                    temporaryPackageItemRepository.delete(temporaryPackageItem);
                    //     packageG.setItems(bookingPackageItemRepository.findDistinctByBooking(bookingBinder.getBean()));
                    window.close();
                });

                Button noButton = new Button("Give the delete up");
                noButton.addClickListener(event2 -> {
                    window.close();
                });

                HorizontalLayout buttonsLayout = new HorizontalLayout(yesButton, noButton);
                confirmationVL.addComponent(buttonsLayout);

                window.setContent(confirmationVL);

                window.center();
                UI.getCurrent().addWindow(window);
            }
        });
        return deletePIButton;
    }

    private Button deletePaymentButton(BookingPayment bookingPayment) {
        Button deletePButton = new Button(VaadinIcons.MINUS_CIRCLE);
        deletePButton.addStyleNames(ValoTheme.BUTTON_SMALL);
        deletePButton.addClickListener(e -> {
            if (bookingPayment.isUnderEditing()) {
                Notification.show("Someone's working with this payment. I can't delete it now.");
            } else {

                //Confirmation popup
                Window window = new Window("Do you really want to delete this payment?");

                //Popup contents
                VerticalLayout confirmationVL = new VerticalLayout();
                confirmationVL.addComponent(new Label("There's no undo option and your developer won't help you either."));

                // And buttons
                Button yesButton = new Button("Proceed");
                yesButton.addClickListener(event1 -> {
                    bookingPaymentRepository.delete(bookingPayment);
                    //        paymentG.setItems(bookingPaymentRepository.findDistinctByBooking(bookingBinder.getBean()));
                    window.close();
                });

                Button noButton = new Button("Give the delete up");
                noButton.addClickListener(event2 -> {
                    window.close();
                });

                HorizontalLayout buttonsLayout = new HorizontalLayout(yesButton, noButton);
                confirmationVL.addComponent(buttonsLayout);

                window.setContent(confirmationVL);

                window.center();
                UI.getCurrent().addWindow(window);
            }
        });
        return deletePButton;
    }

    private Button deleteDocumentButton(BookingDocument document) {
        Button deleteBDButton = new Button(VaadinIcons.MINUS_CIRCLE);
        deleteBDButton.addStyleNames(ValoTheme.BUTTON_SMALL);
        deleteBDButton.addClickListener(e -> {
            if (document.isUnderEditing()) {
                Notification.show("Someone's working with this document. I can't delete it now.");
            } else {

                //Confirmation popup
                Window window = new Window("Do you really want to delete this document?");

                //Popup contents
                VerticalLayout confirmationVL = new VerticalLayout();
                confirmationVL.addComponent(new Label("There's no undo option and your developer won't help you either."));

                // And buttons
                Button yesButton = new Button("Proceed");
                yesButton.addClickListener(event1 -> {
                    bookingDocumentRepository.delete(document);
                    //        documentG.setItems(bookingDocumentRepository.findDistinctByBooking(bookingBinder.getBean()));
                    window.close();
                });

                Button noButton = new Button("Give the delete up");
                noButton.addClickListener(event2 -> {
                    window.close();
                });

                HorizontalLayout buttonsLayout = new HorizontalLayout(yesButton, noButton);
                confirmationVL.addComponent(buttonsLayout);

                window.setContent(confirmationVL);

                window.center();
                UI.getCurrent().addWindow(window);
            }
        });
        return deleteBDButton;
    }

//    private Button deleteCarButton(BookingPackageItemCar bookingPackageItemCar) {
//        Button deleteBCButton = new Button(VaadinIcons.MINUS_CIRCLE);
//        deleteBCButton.addStyleNames(ValoTheme.BUTTON_SMALL);
//        deleteBCButton.addClickListener(e -> {
//            if (bookingPackageItemCar.isUnderEditing()) {
//                Notification.show("Someone's working with this car. I can't delete it now.");
//            } else {
//
//                //Confirmation popup
//                Window window = new Window("Do you really want to delete this car?");
//
//                //Popup contents
//                VerticalLayout confirmationVL = new VerticalLayout();
//                confirmationVL.addComponent(new Label("There's no undo option and your developer won't help you either."));
//
//                // And buttons
//                Button yesButton = new Button("Proceed");
//                yesButton.addClickListener(event1 -> {
//                    bookingPackageItemCarRepository.delete(bookingPackageItemCar);
//                    bookingCarsG.setItems(Lists.newArrayList(bookingPackageItemCarRepository.findAll())); //todo for the given booking
////                    bookingCarsG.setItems(Lists.newArrayList(bookingCarRepository.findAll()));
//                    // todo clear filters ??? MATEUSZ? JONO?
//                    window.close();
//                });
//
//                Button noButton = new Button("Give the delete up");
//                noButton.addClickListener(event2 -> {
//                    window.close();
//                });
//
//                HorizontalLayout buttonsLayout = new HorizontalLayout(yesButton, noButton);
//                confirmationVL.addComponent(buttonsLayout);
//
//                window.setContent(confirmationVL);
//
//                window.center();
//                UI.getCurrent().addWindow(window);
//            }
//        });
//        return deleteBCButton;
//    }

    // Form open processing (new / edit)
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        String bookingId = event.getParameters();
        if ("".equals(bookingId)) {
            enterView(null);
        } else {
            enterView(Long.valueOf(bookingId));
        }
    }

    // Called when user enters view from the list or adding a new booking
    public void enterView(Long id) {
        Booking booking;
        if (id == null) {
            // New
            booking = new Booking();

            //   booking.setCreateDate(new Date());
            customerPickCB.setItems(Lists.newArrayList(customerRepository.findAll()));
            booking.setUnderEditing(true);
            // todo more setters
        } else {
            booking = bookingRepository.findById(id).get();
            if (booking.isUnderEditing()) {
                Notification.show("Someone is editing this one now. Come back later.");
                navigationManager.navigateTo(BookingGridView.class); //todo navigate to the previous view!!!
            } else {
                booking.setUnderEditing(true);
                noteG.setItems(bookingNoteRepository.findByBooking(booking));
                customerPickCB.setItems(Lists.newArrayList(customerRepository.findAll()));
                paymentG.setItems(bookingPaymentRepository.findByBooking(booking));
                packageG.setItems(temporaryPackageItemRepository.findByBooking(booking));

                driverG.setItems(customerRepository.findByBookings(Arrays.asList(booking)));

                documentG.setItems(bookingDocumentRepository.findByBooking(booking));
                if (booking == null) {
                    showNotFound();
                    return;
                }
            }
        }
        bookingBinder.setBean(booking);

//        // Setting note lists, no way to do it earlier
//        noteG.setItems(bookingNoteRepository.findByBooking(bookingBinder.getBean()));
//        paymentG.setItems(bookingPaymentRepository.findByBooking(bookingBinder.getBean()));
//        documentG.setItems(bookingDocumentRepository.findByBooking(bookingBinder.getBean()));

        bookingStatusCB.focus();
    }

    // Won't hopefully happen
    public void showNotFound() {
        removeAllComponents();
        addComponent(new Label("Booking not found"));
    }

    // Mini-layouts (visible or not): add booking note, add payment, add booking document, add package item
    // Booking note
    public VerticalLayout buildBookingNoteForm() {
        VerticalLayout bookingNoteVL = new VerticalLayout();
        bookingNoteVL.setCaption("Enter the note");

        /**
         * I do not allow to edit notes deliberately - too much a hassle, no real need to do it.
         * If needed, I can get back to this form and implement editing.
         */

        // Fields
        noteDateDF = new DateField("Note date");
        noteTF = new TextField("Text");

        HorizontalLayout fieldsHL = new HorizontalLayout(noteDateDF, noteTF);

        // Separate binder
        Binder<BookingNote> bookingNoteBinder = new Binder<>(BookingNote.class);

        bookingNoteBinder.forField(noteDateDF).withConverter(new LocalDateToDateConverter()).bind(BookingNote::getEnteredOn, BookingNote::setEnteredOn);

        bookingNoteBinder.bind(noteTF, "text");

        BookingNote newBookingNote = new BookingNote();
        bookingNoteBinder.setBean(newBookingNote);

        // Buttons
        Button cancelNote = new Button("Cancel");
        cancelNote.setDescription("Your data will be lost!");
        cancelNote.addClickListener(e -> {
            this.bookingNoteVL.setVisible(false);
            this.addBookingNoteB.setVisible(true);
            noteDateDF.clear();
            noteTF.clear();
        });

        Button saveNote = new Button("Save");
        saveNote.addClickListener(e -> {
            bookingBinder.getBean().addBookingNote(bookingNoteBinder.getBean());

            this.bookingNoteVL.setVisible(false);
            this.addBookingNoteB.setVisible(true);
            bookingRepository.save(bookingBinder.getBean());
            noteDateDF.clear();
            noteTF.clear();
            noteG.setItems(bookingNoteRepository.findByBooking(bookingBinder.getBean()));
        });

        //todo set items on booking note list

        HorizontalLayout documentButtonsHL = new HorizontalLayout(cancelNote, saveNote);

        bookingNoteVL.addComponents(fieldsHL, documentButtonsHL);

        bookingNoteVL.setVisible(false);
        return bookingNoteVL;
    }

    // Customer
    public VerticalLayout buildCustomerForm() {
        VerticalLayout customersVL = new VerticalLayout();
        customersVL.setCaption("Pick the customer");

        // Fields
        customerPickCB = new ComboBox<>("You can search the list by name and surname");
        // customerPickCB.setItems(Lists.newArrayList(this.customerRepository.findAll()));
        customerPickCB.setItemCaptionGenerator(e -> e.getCustomerFirstName() + " " + e.getCustomerLastName());
        customerPickCB.setEmptySelectionAllowed(false);

        // the fields get filled according to the CB selection
        customerId = new TextField("Customer id");
        customerName = new TextField("Customer name");
        customerSurname = new TextField("Customer surname");
        customerGroup = new TextField("Customer group");
        customerGeneralInformation = new TextField("General information");

        // disable the fields - no editing here
        customerId.setEnabled(false);
        customerName.setEnabled(false);
        customerSurname.setEnabled(false);
        customerGroup.setEnabled(false);
        customerGeneralInformation.setEnabled(false);


        // The user doesn't have to delete data from the above fields, they change on the fly - see the listener method

        // what happens when the user picks as item from the combo
        customerPickCB.addValueChangeListener(new HasValue.ValueChangeListener<Customer>() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent<Customer> valueChangeEvent) {
                customerId.setValue(String.valueOf(valueChangeEvent.getValue().getDocumentId()));
                customerName.setValue(valueChangeEvent.getValue().getCustomerFirstName());
                customerSurname.setValue(valueChangeEvent.getValue().getCustomerLastName());
                customerGroup.setValue(String.valueOf(valueChangeEvent.getValue().getCustomerGroup()));
                customerGeneralInformation.setValue(valueChangeEvent.getValue().getCustomerGeneralInformation());
            }
        });

        HorizontalLayout fieldsHL = new HorizontalLayout(customerPickCB);
        HorizontalLayout customerData = new HorizontalLayout(customerId, customerName, customerSurname, customerGroup, customerGeneralInformation);

        // Separate binder >>>>> bind ID only

        Binder<Customer> driverBinder = new Binder<>(Customer.class);
        driverBinder.setBean(customerPickCB.getValue());

        // Buttons
        Button cancelEntry = new Button("Cancel");
        cancelEntry.setDescription("Your data will be lost!");
        cancelEntry.addClickListener(e -> {
            this.customersVL.setVisible(false);
            this.addNewDriverB.setVisible(true);
            customerId.clear();
            customerName.clear();
            customerSurname.clear();
            customerGroup.clear();
            customerGeneralInformation.clear();
        });

        Button saveEntry = new Button("Save");
        saveEntry.addClickListener(e -> {
            //todo Marcin manytomany
//            driverBinder.getBean().addBookingPayment(bookingPaymentBinder.getBean());
//
//            this.bookingPaymentVL.setVisible(false);
//            this.addPaymentB.setVisible(true);
//            bookingRepository.save(bookingBinder.getBean());
//            paymentDateDF.clear();
//            paymentAmountTF.clear();
//            paymentRemarksTF.clear();
//            paymentG.setItems(bookingPaymentRepository.findByBooking(bookingBinder.getBean()));
        });

        HorizontalLayout driverButtonsHL = new HorizontalLayout(cancelEntry, saveEntry);

        customersVL.addComponents(fieldsHL, customerData, driverButtonsHL);

        customersVL.setVisible(false);

        return customersVL;

    }

    // Temporary package item
    public VerticalLayout buildTempPackageItemForm() {
        VerticalLayout tempItemVL = new VerticalLayout();
        tempItemVL.setCaption("Enter the package item");

        // Fields
        itemDateDF = new DateField("Date");
        itemTimeTF = new TextField("Start time");
        itemDescriptionTF = new TextField("Description");
        itemPriceTF = new TextField("Unit price");
        itemQuantityTF = new TextField("Quantity");
        itemTotalTF = new TextField("Total");
        itemTotalTF.setEnabled(false);
        isItemCancelledCB = new CheckBox("Cancelled");

        HorizontalLayout fieldsHL = new HorizontalLayout(itemDateDF, itemTimeTF, itemDescriptionTF, itemPriceTF, itemQuantityTF, itemTotalTF, isItemCancelledCB);

        // Separate binder
        Binder<TemporaryPackageItem> temporaryPackageItemBinder = new Binder<>(TemporaryPackageItem.class);

        temporaryPackageItemBinder.forField(itemDateDF)
                .withConverter(new LocalDateToDateConverter())
                .bind(TemporaryPackageItem::getItemDate, TemporaryPackageItem::setItemDate);

        temporaryPackageItemBinder.bind(itemTimeTF, "startTime");
        temporaryPackageItemBinder.bind(itemDescriptionTF, "itemDescription");
        temporaryPackageItemBinder.forField(itemPriceTF)
                .withConverter(new CustomStringToBigDecimalConverter("Enter a number!"))
                .bind(TemporaryPackageItem::getItemUnitPrice, TemporaryPackageItem::setItemUnitPrice);

        temporaryPackageItemBinder.forField(itemQuantityTF)
                .withConverter(new CustomStringToIntegerConverter("Enter a number!"))
                .bind(TemporaryPackageItem::getItemQuantity, TemporaryPackageItem::setItemQuantity);

        temporaryPackageItemBinder.bind(isItemCancelledCB, "cancelled");

        TemporaryPackageItem newTemporaryPackageItem = new TemporaryPackageItem();
        temporaryPackageItemBinder.setBean(newTemporaryPackageItem);

        //todo fix this damned conversion
//        BigDecimal convertedPrice = new BigDecimal(itemPriceTF.getValue());
//        BigDecimal convertedUnit = new BigDecimal(itemQuantityTF.getValue());
//        BigDecimal total = convertedPrice.multiply(convertedUnit);
//        total.setScale(2, BigDecimal.ROUND_HALF_UP);
//        String totalConverted = total.toString();
//        itemTotalTF.setValue(totalConverted);

        // Buttons
        Button cancelItem = new Button("Cancel");
        cancelItem.setDescription("Your data will be lost!");
        cancelItem.addClickListener(e -> {
            this.tempPackageItemVL.setVisible(false);
            this.addPackageItemB.setVisible(true);
            itemDateDF.clear();
            itemTimeTF.clear();
            itemDescriptionTF.clear();
            itemPriceTF.clear();
            itemQuantityTF.clear();
            itemTotalTF.clear();
            isItemCancelledCB.clear();
        });

        Button saveItem = new Button("Save");
        saveItem.addClickListener(e -> {
            bookingBinder.getBean().addTemporaryPackageItem(temporaryPackageItemBinder.getBean());

            this.tempPackageItemVL.setVisible(false);
            this.addPackageItemB.setVisible(true);
            temporaryPackageItemRepository.save(temporaryPackageItemBinder.getBean());
            itemDateDF.clear();
            itemTimeTF.clear();
            itemDescriptionTF.clear();
            itemPriceTF.clear();
            itemQuantityTF.clear();
            itemTotalTF.clear();
            isItemCancelledCB.clear();
            packageG.setItems(temporaryPackageItemRepository.findByBooking(bookingBinder.getBean()));
        });

        HorizontalLayout packageButtonsHL = new HorizontalLayout(cancelItem, saveItem);

        tempItemVL.addComponents(fieldsHL, packageButtonsHL);

        tempItemVL.setVisible(false);
        return tempItemVL;
    }


    // Booking payment
    public VerticalLayout buildBookingPaymentForm() {
        VerticalLayout bookingPaymentVL = new VerticalLayout();
        bookingPaymentVL.setCaption("Enter the payment");

        // Fields
        paymentDateDF = new DateField("Payment date");
        paymentAmountTF = new TextField("Amount");
        paymentRemarksTF = new TextField("Remarks");

        HorizontalLayout fieldsHL = new HorizontalLayout(paymentDateDF, paymentAmountTF, paymentRemarksTF);

        // Separate binder
        Binder<BookingPayment> bookingPaymentBinder = new Binder<>(BookingPayment.class);

        bookingPaymentBinder.forField(paymentDateDF).withConverter(new LocalDateToDateConverter()).bind(BookingPayment::getPaymentDate, BookingPayment::setPaymentDate);

        bookingPaymentBinder.bind(paymentRemarksTF, "paymentRemarks");

        bookingPaymentBinder.forField(paymentAmountTF).withConverter(new CustomStringToBigDecimalConverter("Enter a number please!")).bind(BookingPayment::getPaymentAmount, BookingPayment::setPaymentAmount);

        BookingPayment newBookingPayment = new BookingPayment();
        bookingPaymentBinder.setBean(newBookingPayment);

        // Buttons
        Button cancelPayment = new Button("Cancel");
        cancelPayment.setDescription("Your data will be lost!");
        cancelPayment.addClickListener(e -> {
            this.bookingPaymentVL.setVisible(false);
            this.addPaymentB.setVisible(true);
            paymentDateDF.clear();
            paymentAmountTF.clear();
            paymentRemarksTF.clear();
        });

        Button savePayment = new Button("Save");
        savePayment.addClickListener(e -> {
            bookingBinder.getBean().addBookingPayment(bookingPaymentBinder.getBean());

            this.bookingPaymentVL.setVisible(false);
            this.addPaymentB.setVisible(true);
            bookingRepository.save(bookingBinder.getBean());
            paymentDateDF.clear();
            paymentAmountTF.clear();
            paymentRemarksTF.clear();
            paymentG.setItems(bookingPaymentRepository.findByBooking(bookingBinder.getBean()));
        });

        HorizontalLayout documentButtonsHL = new HorizontalLayout(cancelPayment, savePayment);

        bookingPaymentVL.addComponents(fieldsHL, documentButtonsHL);

        bookingPaymentVL.setVisible(false);
        return bookingPaymentVL;
    }

    // Booking document
    public VerticalLayout buildBookingDocumentForm() {
        VerticalLayout bookingDocumentVL = new VerticalLayout();
        bookingDocumentVL.setCaption("Enter the document");

        // Fields
        bookingDocumentDateDF = new DateField("Document date");
        bookingDocumentDescriptionTF = new TextField("Document description");
        bookingDocumentInfoTF = new TextField("Additional information");

        HorizontalLayout fieldsHL = new HorizontalLayout(bookingDocumentDateDF, bookingDocumentDescriptionTF, bookingDocumentInfoTF);

        // Separate binder
        Binder<BookingDocument> bookingDocumentBinder = new Binder<>(BookingDocument.class);

        bookingDocumentBinder.forField(bookingDocumentDateDF).withConverter(new LocalDateToDateConverter()).bind(BookingDocument::getDate, BookingDocument::setDate);

        bookingDocumentBinder.bind(bookingDocumentDescriptionTF, "bookingDocumentDescription");
        bookingDocumentBinder.bind(bookingDocumentInfoTF, "bookingDocumentAdditionalInfo");

        BookingDocument newBookingDocument = new BookingDocument();
        bookingDocumentBinder.setBean(newBookingDocument);

        // Buttons
        Button cancelDocument = new Button("Cancel");
        cancelDocument.setDescription("Your data will be lost!");
        cancelDocument.addClickListener(e -> {
            this.bookingDocumentVL.setVisible(false);
            this.addDocumentB.setVisible(true);
            bookingDocumentDateDF.clear();
            bookingDocumentDescriptionTF.clear();
            bookingDocumentInfoTF.clear();
        });

        Button saveDocument = new Button("Save");
        saveDocument.addClickListener(e -> {
            bookingBinder.getBean().addBookingDocument(bookingDocumentBinder.getBean());

            this.bookingDocumentVL.setVisible(false);
            this.addDocumentB.setVisible(true);
            bookingRepository.save(bookingBinder.getBean());
            bookingDocumentDateDF.clear();
            bookingDocumentDescriptionTF.clear();
            bookingDocumentInfoTF.clear();
            documentG.setItems(bookingDocumentRepository.findByBooking(bookingBinder.getBean()));
        });

        HorizontalLayout documentButtonsHL = new HorizontalLayout(cancelDocument, saveDocument);

        bookingDocumentVL.addComponents(fieldsHL, documentButtonsHL);

        bookingDocumentVL.setVisible(false);
        return bookingDocumentVL;
    }


}
