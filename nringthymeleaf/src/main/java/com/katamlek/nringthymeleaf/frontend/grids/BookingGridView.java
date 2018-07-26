package com.katamlek.nringthymeleaf.frontend.grids;

import com.katamlek.nringthymeleaf.domain.*;
import com.katamlek.nringthymeleaf.frontend.forms.BookingForm;
import com.katamlek.nringthymeleaf.frontend.navigation.NavigationManager;
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

import java.text.SimpleDateFormat;
import java.util.Arrays;

@UIScope
@SpringView
public class BookingGridView extends VerticalLayout implements View {
    private GridCellFilter filter;
    private BookingRepository bookingRepository;
    private UserRepository userRepository;
    private NavigationManager navigationManager;

    public BookingGridView(BookingRepository bookingRepository, UserRepository userRepository, NavigationManager navigationManager) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.navigationManager = navigationManager;
        addComponent(buildBookingGridView());
        setMargin(false);
    }

    // Build the grid with bookings
    // This one with Booking.class as I don't need any related member fields
    public Grid<Booking> buildBookingGrid() {
        Grid<Booking> bookingGrid = new Grid<>(Booking.class);

        bookingGrid.setItems(Lists.newArrayList(bookingRepository.findAll()));

        // Setting visible colums according to specs
        bookingGrid.getColumn("id").setHidden(true);
        bookingGrid.getColumn("bookingNotes").setHidden(true);
        //    bookingGrid.getColumn("carChangeNotes").setHidden(true);
        bookingGrid.getColumn("customers").setHidden(true);
        bookingGrid.getColumn("bookedCarsList").setHidden(true);
        bookingGrid.getColumn("packageItems").setHidden(true);
        bookingGrid.getColumn("paymentList").setHidden(true);
        bookingGrid.getColumn("bookingDocumentList").setHidden(true);
        bookingGrid.getColumn("bookingCars").setHidden(true);

        // Setting column order
        bookingGrid.setColumnOrder("createdBy", "createDate", "paymentStatus", "bookingStatus", "signatureStatus", "emailConfirmationSent", "emailReminderSendDate", "emailReminderSent");

        // Set columns hideable, reordering etc.
        bookingGrid.getColumns().forEach(column -> column.setSortable(true));
        bookingGrid.setColumnReorderingAllowed(true);
        bookingGrid.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        // Set open details for editing on double click
        bookingGrid.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                UI.getCurrent().getNavigator().navigateTo("xxx");
                // todo set navigator to the selected booking
                // todo NOT A NAVIGATOR ! New carEditForm(Long id); ??
            }
        });

        // Inline editor
        bookingGrid.getEditor().setEnabled(true);

        //TODO
        // Extra buttons - cancel booking, duplicate
        bookingGrid.addComponentColumn(this::buildCancelButton);
        bookingGrid.addComponentColumn(this::buildDuplicateButton);

        // Inline filtering
        this.filter = new GridCellFilter(bookingGrid);

        CellFilterComponent<ComboBox<User>> createdByFilter = this.filter.setComboBoxFilter("createdBy", User.class, Arrays.asList(userRepository.findAll())); //todo may not work
        this.filter.setDateFilter("createDate", new SimpleDateFormat("HH:mm:ss dd.MM.yyyy"), true);
        CellFilterComponent<ComboBox<PaymentStatus>> paymentStatusFilter = this.filter.setComboBoxFilter("paymentStatus", PaymentStatus.class, Arrays.asList(PaymentStatus.values()));
        CellFilterComponent<ComboBox<BookingStatus>> bookingStatusFilter = this.filter.setComboBoxFilter("bookingStatus", BookingStatus.class, Arrays.asList(BookingStatus.values()));
        CellFilterComponent<ComboBox<SignatureStatus>> signatureStatusFilter = this.filter.setComboBoxFilter("signatureStatus", SignatureStatus.class, Arrays.asList(SignatureStatus.values()));

        this.filter.setBooleanFilter("emailConfirmationSent");
        this.filter.setDateFilter("emailReminderSendDate", new SimpleDateFormat("HH:mm:ss dd.MM.yyyy"), true);
        this.filter.setBooleanFilter("emailReminderSent");

        bookingGrid.setStyleName(ValoTheme.TABLE_BORDERLESS);

        return bookingGrid;
    }

    // Build the buttons' row: Add, Clear filters
    public HorizontalLayout buildBookingButtons() {
        HorizontalLayout buttonsBookingHL = new HorizontalLayout();
        buttonsBookingHL.setMargin(false);

        Button addBookingBtn = new Button("Add booking"); // add new booking
        addBookingBtn.addClickListener(e -> navigationManager.navigateTo(BookingForm.class));
        addBookingBtn.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED);
        addBookingBtn.setIcon(VaadinIcons.PLUS);

        Button clearAllFilters = new Button("Remove filters", e -> {
            filter.clearAllFilters();
        });
        clearAllFilters.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        clearAllFilters.setIcon(VaadinIcons.ERASER);

        // for testing purposes only - comment out when done
        Button showMeTestForm = new Button("Show test booking form");
        showMeTestForm.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        showMeTestForm.setIcon(VaadinIcons.EYE);
        showMeTestForm.addClickListener(e -> navigationManager.navigateTo(BookingForm.class));

        buttonsBookingHL.addComponents(addBookingBtn, clearAllFilters, showMeTestForm);

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
        Button cancelButton = new Button(VaadinIcons.MINUS_CIRCLE);
        cancelButton.addStyleNames(ValoTheme.BUTTON_SMALL);
        cancelButton.addClickListener(e -> Notification.show("I can cancel this one for you")); //todo
        return cancelButton;
    }

    private Button buildDuplicateButton(Booking booking) {
        Button duplicateButton = new Button(VaadinIcons.COPY);
        duplicateButton.setStyleName(ValoTheme.BUTTON_TINY);
        duplicateButton.addClickListener(e -> Notification.show("Will try!")); // todo - copy data, save object, open form
        return duplicateButton;
    }

}
