package com.katamlek.nringthymeleaf.frontend.grids;

import com.katamlek.nringthymeleaf.domain.*;
import com.katamlek.nringthymeleaf.frontend.forms.BookingForm;
import com.katamlek.nringthymeleaf.frontend.frontutils.BookingDuplicator;
import com.katamlek.nringthymeleaf.frontend.navigation.NavigationManager;
import com.katamlek.nringthymeleaf.frontend.views.WelcomeView;
import com.katamlek.nringthymeleaf.repositories.BookingRepository;
import com.katamlek.nringthymeleaf.repositories.UserRepository;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.assertj.core.util.Lists;
import org.vaadin.gridutil.cell.CellFilterComponent;
import org.vaadin.gridutil.cell.GridCellFilter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@UIScope
@SpringView
public class BookingGridView extends VerticalLayout implements View {
    private GridCellFilter filter;
    private BookingRepository bookingRepository;
    private UserRepository userRepository;
    private NavigationManager navigationManager;
    private Grid<Booking> bookingGrid;
    private BookingDuplicator bookingDuplicator;

    public BookingGridView(BookingRepository bookingRepository, UserRepository userRepository, NavigationManager navigationManager, BookingDuplicator bookingDuplicator) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.navigationManager = navigationManager;
        this.bookingDuplicator = bookingDuplicator;
        addComponent(buildBookingGridView());
        setMargin(false);
    }

    // Build the grid with bookings
    // This one with Booking.class as I don't need any related member fields
    public Grid<Booking> buildBookingGrid() {
        bookingGrid = new Grid<>(Booking.class);

        bookingGrid.setItems(Lists.newArrayList(bookingRepository.findAll()));

        // Setting visible colums according to specs
        bookingGrid.setColumns("createDate", "paymentStatus", "bookingStatus", "signatureStatus", "emailConfirmationSent", "emailReminderSendDate", "emailReminderSent");

        // Setting column order
        bookingGrid.setColumnOrder("createDate", "paymentStatus", "bookingStatus", "signatureStatus", "emailConfirmationSent", "emailReminderSendDate", "emailReminderSent");

        // Set columns hideable, reordering etc.
        bookingGrid.getColumns().forEach(column -> column.setSortable(true));
        bookingGrid.setColumnReorderingAllowed(true);
        bookingGrid.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        // Set open details for editing on double click
        bookingGrid.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                navigationManager.navigateTo(BookingForm.class, event.getItem().getId());
            }
        });

        // Inline editor
        // bookingGrid.getEditor().setEnabled(true);

        // Extra buttons - cancel booking, duplicate
        bookingGrid.addComponentColumn(this::buildCancelButton);
        bookingGrid.addComponentColumn(this::buildDuplicateButton);
        bookingGrid.addComponentColumn(this::buildDeleteButton);

        // Inline filtering
        this.filter = new GridCellFilter(bookingGrid);

      //  CellFilterComponent<ComboBox<User>> createdByFilter = this.filter.setComboBoxFilter("createdBy", User.class, Arrays.asList(userRepository.findAll())); //todo may not work
        this.filter.setDateFilter("createDate", new SimpleDateFormat("HH:mm:ss dd.MM.yyyy"), true);
        CellFilterComponent<ComboBox<PaymentStatus>> paymentStatusFilter = this.filter.setComboBoxFilter("paymentStatus", PaymentStatus.class, Arrays.asList(PaymentStatus.values()));
        CellFilterComponent<ComboBox<BookingStatus>> bookingStatusFilter = this.filter.setComboBoxFilter("bookingStatus", BookingStatus.class, Arrays.asList(BookingStatus.values()));
        CellFilterComponent<ComboBox<SignatureStatus>> signatureStatusFilter = this.filter.setComboBoxFilter("signatureStatus", SignatureStatus.class, Arrays.asList(SignatureStatus.values()));

        this.filter.setBooleanFilter("emailConfirmationSent");
        this.filter.setDateFilter("emailReminderSendDate", new SimpleDateFormat("HH:mm:ss dd.MM.yyyy"), true);
        this.filter.setBooleanFilter("emailReminderSent");

     //   bookingGrid.setStyleName(ValoTheme.TABLE_BORDERLESS);

        return bookingGrid;
    }

    // Build the buttons row: Add, Clear filters
    public HorizontalLayout buildBookingButtons() {
        HorizontalLayout buttonsBookingHL = new HorizontalLayout();
        buttonsBookingHL.setMargin(false);

        Button addBookingBtn = new Button("Add booking"); // add new booking
        addBookingBtn.addClickListener(e -> navigationManager.navigateTo(BookingForm.class));
//        addBookingBtn.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED);
//        addBookingBtn.setIcon(VaadinIcons.PLUS);
        addBookingBtn.setWidth("200px");

        Button clearAllFilters = new Button("Remove filters", e -> {
            filter.clearAllFilters();
        });
//        clearAllFilters.setStyleName(ValoTheme.BUTTON_BORDERLESS);
//        clearAllFilters.setIcon(VaadinIcons.ERASER);
        clearAllFilters.setWidth("200px");

        Button backToDashboard = new Button("Back to dashboard");
        backToDashboard.addClickListener(e -> navigationManager.navigateTo(WelcomeView.class));
//        backToDashboard.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED);
//        backToDashboard.setIcon(VaadinIcons.DASHBOARD);
        backToDashboard.setWidth("200px");

        // for testing purposes only - comment out when done
//        Button showMeTestForm = new Button("Show test booking form");
//        showMeTestForm.setStyleName(ValoTheme.BUTTON_BORDERLESS);
//        showMeTestForm.setIcon(VaadinIcons.EYE);
//        showMeTestForm.addClickListener(e -> navigationManager.navigateTo(BookingForm.class));

        buttonsBookingHL.addComponents(addBookingBtn, clearAllFilters, backToDashboard);

        return buttonsBookingHL;
    }

    // Put all together
    public VerticalLayout buildBookingGridView() {
        Label label = new Label("Booking List");
        label.addStyleNames(ValoTheme.LABEL_NO_MARGIN, ValoTheme.LABEL_LARGE);

        VerticalLayout bookingGridViewVL = new VerticalLayout();
        bookingGridViewVL.setMargin(false);
        bookingGridViewVL.addComponents(label, buildBookingButtons(), buildBookingGrid()); //todo resize the grid

        return bookingGridViewVL;
    }

    // Helper methods
    private Button buildCancelButton(Booking booking) {
        Button cancelButton = new Button(VaadinIcons.DOT_CIRCLE);
        cancelButton.addStyleNames(ValoTheme.BUTTON_SMALL);
        cancelButton.addClickListener(e -> {
            booking.setBookingStatus(BookingStatus.CANCELLED);
            bookingRepository.save(booking);
            Notification.show("Booking was cancelled.");
        });
        return cancelButton;
    }

    private Button buildDuplicateButton(Booking booking) {
        Button duplicateButton = new Button(VaadinIcons.COPY);
        duplicateButton.setStyleName(ValoTheme.BUTTON_TINY);
        duplicateButton.addClickListener(e -> bookingDuplicator.duplicateBooking(booking));
        return duplicateButton;
    }

    private Button buildDeleteButton(Booking booking) {
        Button deleteBButton = new Button(VaadinIcons.MINUS_CIRCLE);
        deleteBButton.addStyleNames(ValoTheme.BUTTON_SMALL);
        deleteBButton.addClickListener(e -> {

            if (booking.isUnderEditing()) {
                Notification.show("Someone's working with this booking. I can't delete it now.");
            } else {

                //Confirmation popup
                Window window = new Window("Do you really want to delete this booking?");

                //Popup contents
                VerticalLayout confirmationVL = new VerticalLayout();
                confirmationVL.addComponent(new Label("There's no undo option and your developer won't help you either."));

                // And buttons
                Button yesButton = new Button("Proceed");
                yesButton.addClickListener(event1 -> {
                    bookingRepository.delete(booking);
                    bookingGrid.setItems(Lists.newArrayList(bookingRepository.findAll()));
                    // todo clear filters ??? MATEUSZ? JONO?
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

        return deleteBButton;
    }
}

