package com.katamlek.nringthymeleaf.frontend.grids;

import com.katamlek.nringthymeleaf.domain.BookingPayment;
import com.katamlek.nringthymeleaf.domain.Customer;
import com.katamlek.nringthymeleaf.domain.CustomerNote;
import com.katamlek.nringthymeleaf.repositories.CustomerDocumentRepository;
import com.katamlek.nringthymeleaf.repositories.CustomerNoteRepository;
import com.katamlek.nringthymeleaf.repositories.CustomerRepository;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import org.assertj.core.util.Lists;
import org.vaadin.gridutil.cell.GridCellFilter;

import java.text.SimpleDateFormat;

/**
 * List of the drivers related to the booking.
 * See: booking add/ edit form.
 */

@SpringComponent
@UIScope
public class DriversGridView extends VerticalLayout implements View {
    private CustomerRepository customerRepository;
    private CustomerNoteRepository customerNoteRepository;
    private GridCellFilter filter;

    public DriversGridView(CustomerRepository customerRepository, CustomerNoteRepository customerNoteRepository) {
        this.customerRepository = customerRepository;
        this.customerNoteRepository = customerNoteRepository;
        this.addComponent(buildDriverGrid());
    }

    // Build the grid with booking payments
    public Grid<Customer> buildDriverGrid() {

        Grid<Customer> driverGrid = new Grid<>(Customer.class);
        driverGrid.setItems(Lists.newArrayList(customerRepository.findAll()));



        // Setting visible colums according to specs
        driverGrid.getColumn("customerCreateDate").setHidden(true);
        driverGrid.getColumn("customerCreatedBy").setHidden(true);
        driverGrid.getColumn("customerAddress").setHidden(true);
        driverGrid.getColumn("customerPhoneNumber").setHidden(true);
        driverGrid.getColumn("customerEmail").setHidden(true);
        driverGrid.getColumn("customerNewsletter").setHidden(true);
        driverGrid.getColumn("customerAgent").setHidden(true);
        driverGrid.getColumn("customerEmergencyContacts").setHidden(true);
        driverGrid.getColumn("customerDocuments").setHidden(true);
        driverGrid.getColumn("customerHistory").setHidden(true);
        driverGrid.getColumn("customerLastEdit").setHidden(true);
        driverGrid.getColumn("bookings").setHidden(true);
        driverGrid.getColumn("customerNotes").setHidden(true);

        // Setting column order
        driverGrid.setColumnOrder("id", "customerFirstName", "customerLastName", "customerGroup", "customerGeneralInformation");

        // Set columns hideable, reordering etc.
        driverGrid.getColumns().forEach(column -> column.setSortable(true));
        driverGrid.setColumnReorderingAllowed(true);
        driverGrid.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        // Set open document details for editing on double click
        driverGrid.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                UI.getCurrent().getNavigator().navigateTo("xxx");
                // todo set navigator to the selected car
                // todo NOT A NAVIGATOR ! New carEditForm(Long id);
            }
        });

      // Inline filtering
        this.filter = new GridCellFilter(driverGrid);
        this.filter.setTextFilter("id", true, true);
        this.filter.setTextFilter("customerFirstName", true, true);
        this.filter.setTextFilter("customerLastName", true, true);
        this.filter.setTextFilter("customerGroup", true, true);
        this.filter.setTextFilter("customerGeneralInformation", true, true);

        // Inline editor
        driverGrid.getEditor().setEnabled(true);

        // Extra columns: edit, delete, view documents
        driverGrid.addColumn(driver -> "Edit", new ButtonRenderer(clickEvent -> {
            //todo navigator
        }));

        driverGrid.addColumn(driver -> "Delete", new ButtonRenderer(clickEvent -> {
            //todo check if works, switch to id?
            customerRepository.delete((Customer) clickEvent.getItem());
        }));

        driverGrid.addColumn(driver -> "Documents", new ButtonRenderer(clickEvent -> {
            //todo - goto list
        }));

        driverGrid.setSizeFull();

//        filter = new GridCellFilter(paymentGrid);

        return driverGrid;
    }

    // Build the buttons' row: Add, Clear filters
    public HorizontalLayout buildCarButtons() {
        HorizontalLayout buttonsDriverHL = new HorizontalLayout();

        Button addDriverBtn = new Button("Add a payment"); // add new document
        addDriverBtn.addClickListener(e -> UI.getCurrent().getNavigator().navigateTo("aaa"));
        //todo add navigator -- see line 86, not navigator, but new form with the given id

        Button clearAllFilters = new Button("Remove filters", e -> {
            filter.clearAllFilters();
        });

        Button searchForCustomer = new Button("Find Customer", e -> {
            //todo navigator;
        });

        Button addCustomer = new Button("Add Customer", e -> {
            //todo navigator;
        });

        buttonsDriverHL.addComponents(searchForCustomer, addCustomer, addDriverBtn, clearAllFilters);
        return buttonsDriverHL;
    }

    // Put all together
    public VerticalLayout buildDriverGridView() {
        VerticalLayout bookingDriverGridViewVL = new VerticalLayout();
        bookingDriverGridViewVL.addComponents(buildCarButtons(), buildDriverGrid());
        return bookingDriverGridViewVL;
    }


}
