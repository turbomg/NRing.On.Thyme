//package com.katamlek.nringthymeleaf.frontend.grids;
//
//import com.katamlek.nringthymeleaf.domain.BookingDocument;
//import com.katamlek.nringthymeleaf.repositories.BookingDocumentRepository;
//import com.vaadin.icons.VaadinIcons;
//import com.vaadin.navigator.View;
//import com.vaadin.shared.ui.grid.ColumnResizeMode;
//import com.vaadin.spring.annotation.SpringComponent;
//import com.vaadin.spring.annotation.UIScope;
//import com.vaadin.ui.*;
//import com.vaadin.ui.themes.ValoTheme;
//import org.assertj.core.util.Lists;
//import org.vaadin.gridutil.cell.GridCellFilter;
//
//import java.text.SimpleDateFormat;
//
///**
// * List of all documents related to a booking.
// * See: booking add/ edit form.
// */
//
//@SpringComponent
//@UIScope
//public class BookingDocumentsGridView extends VerticalLayout implements View {
//    private BookingDocumentRepository bookingDocumentRepository;
//    private GridCellFilter filter;
//    private Grid<BookingDocument> bookingDocumentGrid;
//
//    public BookingDocumentsGridView(BookingDocumentRepository bookingDocumentRepository) {
//        this.bookingDocumentRepository = bookingDocumentRepository;
//        this.addComponent(buildBookingDocumentGrid());
//    }
//
//    // Build the grid with booking documents
//    public Grid<BookingDocument> buildBookingDocumentGrid() {
//        // Setting items - full CarRepository
//        bookingDocumentGrid = new Grid<>(BookingDocument.class);
//
////        List<BookingDocument> bookingDocumentList = Lists.newArrayList(bookingDocumentRepository.findAll());
//        bookingDocumentGrid.setItems(Lists.newArrayList(bookingDocumentRepository.findAll()));
//
//        // Setting visible colums according to specs
//        bookingDocumentGrid.getColumn("id").setHidden(true);
//        bookingDocumentGrid.getColumn("booking").setHidden(true);
//
//        // Setting column order
//        bookingDocumentGrid.setColumnOrder("date", "bookingDocumentDescription", "bookingDocumentAdditionalInfo");
//
//        // Set columns hideable, reordering etc.
//        bookingDocumentGrid.getColumns().forEach(column -> column.setSortable(true));
//        bookingDocumentGrid.setColumnReorderingAllowed(true);
//        bookingDocumentGrid.setColumnResizeMode(ColumnResizeMode.ANIMATED);
//
//        // Set open document details for editing on double click
//        bookingDocumentGrid.addItemClickListener(event -> {
//            if (event.getMouseEventDetails().isDoubleClick()) {
//                UI.getCurrent().getNavigator().navigateTo("xxx");
//                // todo set navigator to the selected car
//                // todo NOT_PAID A NAVIGATOR ! New carEditForm(Long id);
//            }
//        });
//
//        // Inline filtering
//        this.filter = new GridCellFilter(bookingDocumentGrid);
//        this.filter.setDateFilter("date", new SimpleDateFormat("HH:mm:ss dd.MM.yyyy"), true);
//        this.filter.setTextFilter("bookingDocumentDescription", true, true);
//        this.filter.setTextFilter("bookingDocumentAdditionalInfo", true, true);
//
////todo change all Time types to Date to preserve consistency
//
//        // Inline editor
//        bookingDocumentGrid.getEditor().setEnabled(true);
//
//        // Extra column delete
//        bookingDocumentGrid.addComponentColumn(this::deleteBookingDocumentButton);
//
//        bookingDocumentGrid.setSizeFull();
//
//        return bookingDocumentGrid;
//    }
//
//    // Build the buttons' row: Add, Clear filters
//    public HorizontalLayout buildCarButtons() {
//        HorizontalLayout buttonsBookingDocumentHL = new HorizontalLayout();
//
//        Button addBookingDocumentBtn = new Button("Add a document"); // add new document
//        addBookingDocumentBtn.addClickListener(e -> UI.getCurrent().getNavigator().navigateTo("aaa"));
//        //todo add navigator -- see line 86, not navigator, but new form with the given id
//
//        Button clearAllFilters = new Button("Remove filters", e -> {
//            filter.clearAllFilters();
//        });
//
//        buttonsBookingDocumentHL.addComponents(addBookingDocumentBtn, clearAllFilters);
//        return buttonsBookingDocumentHL;
//    }
//
//    // Put all together
//    public VerticalLayout buildBookingDocumentGridView() {
//        VerticalLayout bookingDocumentGridViewVL = new VerticalLayout();
//        bookingDocumentGridViewVL.addComponents(buildCarButtons(), buildBookingDocumentGrid());
//        return bookingDocumentGridViewVL;
//    }
//
//    // Helpers
//    private Button deleteBookingDocumentButton(BookingDocument document) {
//        Button deleteBDutton = new Button(VaadinIcons.MINUS_CIRCLE);
//        deleteBDutton.addStyleNames(ValoTheme.BUTTON_SMALL);
//        deleteBDutton.addClickListener(e -> {
//            //Confirmation popup
//            Window window = new Window("Do you really want to delete this document?");
//
//            //Popup contents
//            VerticalLayout confirmationVL = new VerticalLayout();
//            confirmationVL.addComponent(new Label("There's no undo option and your developer won't help you either."));
//
//            // And buttons
//            Button yesButton = new Button("Proceed");
//            yesButton.addClickListener(event1 -> {
//                bookingDocumentRepository.delete(document);
//                bookingDocumentGrid.setItems(Lists.newArrayList(bookingDocumentRepository.findAll()));
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
//        return deleteBDutton;
//    }
//
//}
