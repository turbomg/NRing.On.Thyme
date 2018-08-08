//package com.katamlek.nringthymeleaf.frontend.grids;
//
//import com.katamlek.nringthymeleaf.domain.*;
//import com.katamlek.nringthymeleaf.repositories.BookingCarRepository;
//import com.vaadin.icons.VaadinIcons;
//import com.vaadin.navigator.View;
//import com.vaadin.shared.ui.grid.ColumnResizeMode;
//import com.vaadin.spring.annotation.SpringComponent;
//import com.vaadin.spring.annotation.UIScope;
//import com.vaadin.ui.*;
//import com.vaadin.ui.renderers.ButtonRenderer;
//import com.vaadin.ui.themes.ValoTheme;
//import org.assertj.core.util.Lists;
//import org.vaadin.gridutil.cell.CellFilterComponent;
//import org.vaadin.gridutil.cell.GridCellFilter;
//
//import java.math.BigDecimal;
//import java.util.Arrays;
//
///**
// * Grid placed on the booking form, listing the cars.
// */
//
//@SpringComponent
//@UIScope
//public class BookingCarGridView extends VerticalLayout implements View {
//    private BookingCarRepository bookingCarRepository;
//    private GridCellFilter filter;
//    private Grid<BookingCar> bookingCarGrid;
//
//    public BookingCarGridView(BookingCarRepository bookingCarRepository) {
//        this.bookingCarRepository = bookingCarRepository;
//        this.addComponent(buildBookingCarGridView());
//    }
//
//    // Build the grid with cars
//    public Grid<BookingCar> buildBookingCarGrid() {
//        // Setting items - only this booking cars, selected by booking id
//        bookingCarGrid = new Grid<>(BookingCar.class);
//        //todo how to pass the booking id parameter?
//        bookingCarGrid.setItems(Lists.newArrayList(bookingCarRepository.findBookingCarByBookingId(12345l)));
//
//        // Setting visible colums according to specs
//        bookingCarGrid.getColumn("id").setHidden(true);
//        bookingCarGrid.getColumn("booking").setHidden(true);
//
//        bookingCarGrid.addColumn(bookingCar -> {
//            BigDecimal kmTotal = (bookingCar.getKmOut().subtract(bookingCar.getKmIn()));
//            return kmTotal;
//        }).setCaption("Km Total").setId("kmTotal");
//
//        // Setting column order - leaving default
//
//        // Set columns hideable, reordering etc.
//        bookingCarGrid.getColumns().forEach(column -> column.setSortable(true));
//        bookingCarGrid.setColumnReorderingAllowed(true);
//        bookingCarGrid.setColumnResizeMode(ColumnResizeMode.ANIMATED);
//
//        // Set open car details for editing on double click
//        bookingCarGrid.addItemClickListener(event -> {
//            if (event.getMouseEventDetails().isDoubleClick()) {
//                UI.getCurrent().getNavigator().navigateTo("xxx");
//                // todo set navigator to the selected car
//                // todo NOT A NAVIGATOR ! New carEditForm(Long id);
//            }
//        });
//
//        // Inline filtering - without Color as it's an icon
//        this.filter = new GridCellFilter(bookingCarGrid);
//        this.filter.setTextFilter("model", true, true);
//        this.filter.setTextFilter("plate", true, true);
//
//        this.filter.setNumberFilter("kmIn", BigDecimal.class);
//        this.filter.setNumberFilter("kmOut", BigDecimal.class);
//
//        CellFilterComponent<ComboBox<CarStatus>> statusFilter = this.filter.setComboBoxFilter("carStatus", CarStatus.class, Arrays.asList(CarStatus.values()));
//        this.filter.setBooleanFilter("currentlyInUse");
//
//        this.filter.setNumberFilter("kmTotal", BigDecimal.class);
//
//        // Inline editor
//        //    bookingCarGrid.getEditor().setEnabled(true);
//
//        // Extra column delete
//        bookingCarGrid.addComponentColumn(this::deleteBookingCarButton);
//
//        bookingCarGrid.setSizeFull();
//
//        return bookingCarGrid;
//    }
//
//    // Build the buttons row: Add, Clear filters
//    public HorizontalLayout buildBookingCarButtons() {
//        HorizontalLayout buttonsBookingCarHL = new HorizontalLayout();
//
//        Button addBookingCarBtn = new Button("Add a car"); // add new car
//        addBookingCarBtn.addClickListener(e -> UI.getCurrent().getNavigator().navigateTo("aaa"));
//        //todo add navigator
//
//        Button clearAllFilters = new Button("Remove filters", e -> {
//            filter.clearAllFilters();
//        });
//
//        buttonsBookingCarHL.addComponents(addBookingCarBtn, clearAllFilters);
//        return buttonsBookingCarHL;
//    }
//
//    // Put all together
//    public VerticalLayout buildBookingCarGridView() {
//        VerticalLayout bookingCarGridViewVL = new VerticalLayout();
//        bookingCarGridViewVL.addComponents(buildBookingCarButtons(), buildBookingCarGrid());
//        return bookingCarGridViewVL;
//    }
//
//    // Helpers
//    private Button deleteBookingCarButton(BookingCar bookingCar) {
//        Button deleteBCutton = new Button(VaadinIcons.MINUS_CIRCLE);
//        deleteBCutton.addStyleNames(ValoTheme.BUTTON_SMALL);
//        deleteBCutton.addClickListener(e -> {
//            //Confirmation popup
//            Window window = new Window("Do you really want to delete this booking car?");
//
//            //Popup contents
//            VerticalLayout confirmationVL = new VerticalLayout();
//            confirmationVL.addComponent(new Label("There's no undo option and your developer won't help you either."));
//
//            // And buttons
//            Button yesButton = new Button("Proceed");
//            yesButton.addClickListener(event1 -> {
//                bookingCarRepository.delete(bookingCar);
//                bookingCarGrid.setItems(Lists.newArrayList(bookingCarRepository.findAll()));
//                // todo clear filters ??? MATEUSZ? JONO?
//                window.close();
//            });
//
//            Button noButton = new Button("Give the delete up");
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
//        return deleteBCutton;
//    }
//
//}
