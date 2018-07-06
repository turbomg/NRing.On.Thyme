package com.katamlek.nringthymeleaf.frontend.grids;

import com.katamlek.nringthymeleaf.domain.*;
import com.katamlek.nringthymeleaf.repositories.BookingRepository;
import com.katamlek.nringthymeleaf.repositories.UserRepository;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.assertj.core.util.Lists;
import org.vaadin.gridutil.cell.CellFilterComponent;
import org.vaadin.gridutil.cell.GridCellFilter;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@SpringComponent
@UIScope
@SpringView(name = BookingGridView.VIEW_NAME)
public class BookingGridView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "booking-grid";
    private GridCellFilter filter;
    private BookingRepository bookingRepository;
    private UserRepository userRepository;

    public BookingGridView(BookingRepository bookingRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        addComponent(buildBookingGridView());
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

        // Setting column order
        bookingGrid.setColumnOrder("createdBy", "createDate", "paymentStatus", "bookingStatus", "signatureStatus",
                "emailConfirmationSent", "emailReminderSendDate", "emailReminderSent");

        // Set columns hideable, reordering etc.
        bookingGrid.getColumns().forEach(column -> column.setSortable(true));
        bookingGrid.setColumnReorderingAllowed(true);
        bookingGrid.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        // Set open details for editing on double click
        bookingGrid.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                UI.getCurrent().getNavigator().navigateTo("xxx");
                // todo set navigator to the selected car
                // todo NOT A NAVIGATOR ! New carEditForm(Long id); ??
            }
        });

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

        bookingGrid.setSizeFull();

        return bookingGrid;
    }

    // Build the buttons' row: Add, Clear filters
    public HorizontalLayout buildBookingButtons() {
        HorizontalLayout buttonsBookingHL = new HorizontalLayout();

        Button addBookingBtn = new Button("Add booking"); // add new car
        addBookingBtn.addClickListener(e -> UI.getCurrent().getNavigator().navigateTo("aaa"));
        //todo add navigator -- see line 86, not navigator, but new form with the given id

        Button clearAllFilters = new Button("Remove filters", e -> {
            filter.clearAllFilters();
        });

        buttonsBookingHL.addComponents(addBookingBtn, clearAllFilters);
        return buttonsBookingHL;
    }

    // Put all together
    public VerticalLayout buildBookingGridView() {
        VerticalLayout bookingGridViewVL = new VerticalLayout();
        bookingGridViewVL.addComponents(buildBookingButtons(), buildBookingGrid());
        return bookingGridViewVL;
    }
}
