package com.katamlek.nringthymeleaf.frontend.forms;

import com.katamlek.nringthymeleaf.domain.*;
import com.katamlek.nringthymeleaf.frontend.grids.BookingGridView;
import com.katamlek.nringthymeleaf.frontend.grids.CustomerGridView;
import com.katamlek.nringthymeleaf.frontend.navigation.NavigationManager;
import com.katamlek.nringthymeleaf.frontend.windows.BookingNoteWindow;
import com.katamlek.nringthymeleaf.repositories.*;
import com.vaadin.data.Binder;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
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

    // TODO define the binding

    // The constructor
    private BookingRepository bookingRepository;
    private CustomerRepository customerRepository;
    private NavigationManager navigationManager;
    private BookingPackageItemRepository bookingPackageItemRepository;
    private BookingPaymentRepository bookingPaymentRepository;
    private BookingDocumentRepository bookingDocumentRepository;
    private BookingNoteWindow bookingNoteWindow;
    private PaymentForm paymentForm;
    private BookingNoteRepository bookingNoteRepository;
    private BookingPackageItemCarRepository bookingPackageItemCarRepository;

    public BookingForm(BookingRepository bookingRepository, NavigationManager navigationManager, BookingPackageItemRepository bookingPackageItemRepository, BookingPaymentRepository bookingPaymentRepository, BookingDocumentRepository bookingDocumentRepository, BookingNoteWindow bookingNoteWindow, PaymentForm paymentForm, BookingNoteRepository bookingNoteRepository, BookingPackageItemCarRepository bookingPackageItemCarRepository, CustomerRepository customerRepository) {
        this.bookingRepository = bookingRepository;
        this.navigationManager = navigationManager;
        this.bookingPackageItemRepository = bookingPackageItemRepository;
        this.bookingPaymentRepository = bookingPaymentRepository;
        this.bookingDocumentRepository = bookingDocumentRepository;
        this.bookingNoteWindow = bookingNoteWindow;
        this.paymentForm = paymentForm;
        this.bookingNoteRepository = bookingNoteRepository;
        this.bookingPackageItemCarRepository = bookingPackageItemCarRepository;
        this.customerRepository = customerRepository;
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

    // Booking cars
    private Grid<BookingPackageItemCar> bookingCarsG;
    private Button addBookingCarB;

    // Binder
    private Binder<Booking> bookingBinder;

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
        emailReminderDateDF.setDefaultValue(LocalDate.now());

        noteG = new Grid<>(BookingNote.class);
        noteG.setColumns("enteredOn", "text");


        //todo aks JONO if they need the deleted notes on the screen / at all
        //todo all notes here - set items, check if done

        noteG.setColumnOrder("enteredOn", "text");
        noteG.setItems(bookingNoteRepository.findByBooking(bookingBinder.getBean()));

        noteG.getColumns().forEach(column -> column.setSortable(true));
        noteG.setColumnReorderingAllowed(true);
        noteG.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        // allow inline editing
        // noteG.getEditor().setEnabled(true);

        // Extra button delete
        noteG.addComponentColumn(this::deleteBookingNoteButton);

        noteG.setHeightByRows(4);
        noteG.addStyleNames(ValoTheme.TABLE_BORDERLESS, ValoTheme.TABLE_COMPACT);
        noteG.setCaption("Booking notes");

        noteG.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                Notification.show("Please be patient, will open the form soon.");
                // todo goto bookingNote(Long id) when the form is ready;
            }
        });

        addBookingNoteB = new Button("Add booking note");
        addBookingNoteB.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED, ValoTheme.BUTTON_SMALL);
        addBookingNoteB.setIcon(VaadinIcons.PLUS);
        addBookingNoteB.addClickListener(e -> {
            UI.getCurrent().addWindow(bookingNoteWindow);
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
        HorizontalLayout userDateHL = new HorizontalLayout(bookedByTF, bookedAtDF, signatureStatusCB, paymentStatusCB);
        // HL with statuses - other combos
        HorizontalLayout combosHL = new HorizontalLayout(emailConfirmationCB, emailReminderCB, emailReminderDateDF); // todo align the damned date field!
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

        driverG.setItems(customerRepository.findDistinctByBookings(bookingBinder.getBean()));

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
        //todo remove from grid, not database - ask MARCIN

        driverG.setHeightByRows(4);

        findDriverB = new Button("Search for driver");
        findDriverB.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_SMALL);
        findDriverB.addClickListener(e -> {
            navigationManager.navigateTo(CustomerGridView.class);
        }); //todo - how to move data from this list to the form?
        findDriverB.setIcon(VaadinIcons.SEARCH);

        addNewDriverB = new Button("Add driver");

        addNewDriverB.addClickListener(e -> navigationManager.navigateTo(CustomerForm.class));
        //todo when done with adding new customer move data to this form
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
        packageG.setColumns("date", "startTime", "description", "unitPrice", "quantity", "cancelled");

        packageG.setItems(bookingPackageItemRepository.findDistinctByBooking(bookingBinder.getBean()));

        packageG.addColumn(packageItem -> {
            BigDecimal total = packageItem.getQuantity().multiply(packageItem.getUnitPrice());
            return total;
        }).setCaption("Total").setId("total");


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

        packageG.setColumnOrder("date", "startTime", "description", "unitPrice", "quantity", "total", "cancelled");
        packageG.addStyleNames(ValoTheme.TABLE_COMPACT);

        Button addItem = new Button("Add item");
        addItem.addClickListener(e -> navigationManager.navigateTo(BookingPackageItemForm.class));
        addItem.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED, ValoTheme.BUTTON_SMALL);
        addItem.setIcon(VaadinIcons.PLUS);

        packageVL.addComponents(packageG, addItem);
        packageVL.setCaption("Package details");
        return packageVL;
    }

    // Payments
    public VerticalLayout buildPaymentsSection() {
        VerticalLayout paymentsVL = new VerticalLayout();
        paymentsVL.setMargin(false);

        paymentG = new Grid<>(BookingPayment.class);
        paymentG.setColumns("paymentDate", "paymentNote", "paymentAmount");

        paymentG.setItems(bookingPaymentRepository.findDistinctByBooking(bookingBinder.getBean()));

        paymentG.addColumn(bookingPayment -> {
            String method = bookingPayment.getPaymentDefinition().getPaymentName();
            return method;
        }).setCaption("Method").setId("method");


        paymentG.setColumns("paymentDate", "paymentNote", "method", "paymentAmount");

        // extra button delete
        paymentG.addComponentColumn(this::deletePaymentButton);

        paymentG.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                navigationManager.navigateTo(PaymentForm.class, event.getItem().getId()); // todo new view or add the sexction to this form?
            }
        });

        paymentG.setHeightByRows(4);

        addPaymentB = new Button("Add payment");
        addPaymentB.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED, ValoTheme.BUTTON_SMALL);
        addPaymentB.setIcon(VaadinIcons.PLUS);
        addPaymentB.addClickListener(e -> paymentsVL.addComponents(paymentForm)); //todo - navigate to new form? add form to this? ask Pawel?

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

        documentG.setItems(bookingDocumentRepository.findDistinctByBooking(bookingBinder.getBean()));

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
        addDocumentB.addClickListener(e -> Notification.show("Can't do this yet, see you later.")); //todo

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

        bookingForm.addComponents(bookingFormL, buttonsHL, buildDetailsSection(), buildDriversSection(), buildPackageSection(), buildPaymentsSection(), buildDocumentsSection());

        bookingForm.setMargin(false);

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
                    noteG.setItems(bookingNoteRepository.findByBooking(bookingBinder.getBean()));
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

    private Button deletePackageItemButton(BookingPackageItem packageItem) {
        Button deletePIButton = new Button(VaadinIcons.MINUS_CIRCLE);
        deletePIButton.addStyleNames(ValoTheme.BUTTON_SMALL);
        deletePIButton.addClickListener(e -> {
            if (packageItem.isUnderEditing()) {
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
                    bookingPackageItemRepository.delete(packageItem);
                    packageG.setItems(bookingPackageItemRepository.findDistinctByBooking(bookingBinder.getBean()));
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
                    paymentG.setItems(bookingPaymentRepository.findDistinctByBooking(bookingBinder.getBean()));
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
                    documentG.setItems(bookingDocumentRepository.findDistinctByBooking(bookingBinder.getBean()));
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

            booking.setUnderEditing(true);
            // todo more setters
        } else {
            booking = bookingRepository.findById(id).get();
            if (booking.isUnderEditing()) {
                Notification.show("Someone is editing this one now. Come back later.");
                navigationManager.navigateTo(BookingGridView.class); //todo navigate to the previous view!!!
            } else {
                booking.setUnderEditing(true);
                if (booking == null) {
                    showNotFound();
                    return;
                }
            }
        }
        bookingBinder.setBean(booking);
        // todo ??.focus();
    }

    // Won't hopefully happen
    public void showNotFound() {
        removeAllComponents();
        addComponent(new Label("Booking not found"));
    }

}
