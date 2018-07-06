package com.katamlek.nringthymeleaf.frontend.grids;

import com.katamlek.nringthymeleaf.domain.*;
import com.katamlek.nringthymeleaf.repositories.BookingCarRepository;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import org.assertj.core.util.Lists;
import org.vaadin.gridutil.cell.CellFilterComponent;
import org.vaadin.gridutil.cell.GridCellFilter;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Grid placed on the booking form, listing the cars.
 */

@SpringComponent
@UIScope
public class BookingCarGridView extends VerticalLayout implements View {
    private BookingCarRepository bookingCarRepository;
    private GridCellFilter filter;

    public BookingCarGridView(BookingCarRepository bookingCarRepository) {
        this.bookingCarRepository = bookingCarRepository;
        this.addComponent(buildBookingCarGridView());
    }

    // Build the grid with cars
    public Grid<BookingCar> buildBookingCarGrid() {
        // Setting items - only this booking cars, selected by booking id
        Grid<BookingCar> bookingCarGrid = new Grid<>(BookingCar.class);
        //todo how to pass the booking id parameter?
        bookingCarGrid.setItems(Lists.newArrayList(bookingCarRepository.findBookingCarByBookingId(12345l)));

        // Setting visible colums according to specs
        bookingCarGrid.getColumn("id").setHidden(true);
        bookingCarGrid.getColumn("booking").setHidden(true);

        bookingCarGrid.addColumn(bookingCar -> {
            BigDecimal kmTotal = (bookingCar.getKmOut().subtract(bookingCar.getKmIn()));
            return kmTotal;
        }).setCaption("Km Total").setId("kmTotal");

        // Setting column order - leaving default

        // Set columns hideable, reordering etc.
        bookingCarGrid.getColumns().forEach(column -> column.setSortable(true));
        bookingCarGrid.setColumnReorderingAllowed(true);
        bookingCarGrid.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        // Set open car details for editing on double click
        bookingCarGrid.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                UI.getCurrent().getNavigator().navigateTo("xxx");
                // todo set navigator to the selected car
                // todo NOT A NAVIGATOR ! New carEditForm(Long id);
            }
        });

        // Inline filtering - without Color as it's an icon
        this.filter = new GridCellFilter(bookingCarGrid);
        this.filter.setTextFilter("model", true, true);
        this.filter.setTextFilter("plate", true, true);

        this.filter.setNumberFilter("kmIn", BigDecimal.class);
        this.filter.setNumberFilter("kmOut", BigDecimal.class);

        CellFilterComponent<ComboBox<CarStatus>> statusFilter = this.filter.setComboBoxFilter("carStatus", CarStatus.class, Arrays.asList(CarStatus.values()));
        this.filter.setBooleanFilter("currentlyInUse");

        this.filter.setNumberFilter("kmTotal", BigDecimal.class);

        // Inline editor
        bookingCarGrid.getEditor().setEnabled(true);

        // Extra columns: edit, delete
        bookingCarGrid.addColumn(bookingCar -> "Edit", new ButtonRenderer(clickEvent -> {
            //todo navigator
        }));

        bookingCarGrid.addColumn(bookingCar -> "Delete", new ButtonRenderer(clickEvent -> {
            bookingCarRepository.delete((BookingCar) clickEvent.getItem());
        }));

        bookingCarGrid.setSizeFull();

        return bookingCarGrid;
    }

    // Build the buttons' row: Add, Clear filters
    public HorizontalLayout buildBookingCarButtons() {
        HorizontalLayout buttonsBookingCarHL = new HorizontalLayout();

        Button addBookingCarBtn = new Button("Add a car"); // add new car
        addBookingCarBtn.addClickListener(e -> UI.getCurrent().getNavigator().navigateTo("aaa"));
        //todo add navigator

        Button clearAllFilters = new Button("Remove filters", e -> {
            filter.clearAllFilters();
        });

        buttonsBookingCarHL.addComponents(addBookingCarBtn, clearAllFilters);
        return buttonsBookingCarHL;
    }

    // Put all together
    public VerticalLayout buildBookingCarGridView() {
        VerticalLayout bookingCarGridViewVL = new VerticalLayout();
        bookingCarGridViewVL.addComponents(buildBookingCarButtons(), buildBookingCarGrid());
        return bookingCarGridViewVL;
    }


}
