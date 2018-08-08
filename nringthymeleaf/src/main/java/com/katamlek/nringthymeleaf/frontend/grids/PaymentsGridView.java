//package com.katamlek.nringthymeleaf.frontend.grids;
//
//import com.katamlek.nringthymeleaf.domain.Booking;
//import com.katamlek.nringthymeleaf.domain.BookingDocument;
//import com.katamlek.nringthymeleaf.domain.BookingPayment;
//import com.katamlek.nringthymeleaf.domain.PaymentDefinition;
//import com.katamlek.nringthymeleaf.repositories.BookingPaymentRepository;
//import com.vaadin.navigator.View;
//import com.vaadin.shared.ui.grid.ColumnResizeMode;
//import com.vaadin.spring.annotation.SpringComponent;
//import com.vaadin.spring.annotation.UIScope;
//import com.vaadin.ui.*;
//import com.vaadin.ui.renderers.ButtonRenderer;
//import org.assertj.core.util.Lists;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.vaadin.gridutil.cell.GridCellFilter;
//
//import java.text.SimpleDateFormat;
//import java.util.List;
//
///**
// * List of the payments regarding the booking.
// * See: booking add/ edit form.
// */
//
//@SpringComponent
//@UIScope
//public class PaymentsGridView extends VerticalLayout implements View {
//    private BookingPaymentRepository bookingPaymentRepository;
//    private GridCellFilter filter;
//
//    public PaymentsGridView(BookingPaymentRepository bookingPaymentRepository) {
//        this.bookingPaymentRepository = bookingPaymentRepository;
//        this.addComponent(buildBookingPaymentGrid());
//    }
//
//    // Build the grid with booking payments
//    public Grid<BookingPayment> buildBookingPaymentGrid() {
//
//        Grid<BookingPayment> paymentGrid = new Grid<>(BookingPayment.class);
//        paymentGrid.setItems(Lists.newArrayList(bookingPaymentRepository.findAll()));
//
//
//
//        // Setting visible colums according to specs
//
//        paymentGrid.getColumn("id").setHidden(true);
//        paymentGrid.getColumn("paymentDefinition").setHidden(true);
//
//        paymentGrid.addColumn(bookingPayment -> {
//            String accountCode = bookingPayment.getPaymentDefinition().getAccountDefinition().getAccountCode();
//            return accountCode;
//        }).setCaption("Account Code").setId("accountCode");
//
//        paymentGrid.addColumn(bookingPayment -> {
//            String method = bookingPayment.getPaymentDefinition().getPaymentName();
//            return method;
//        }).setCaption("Method").setId("method");
//
//
//
//        // Setting column order
//        paymentGrid.setColumnOrder("paymentDate", "method", "paymentNote", "paymentAmount", "accountCode");
//
//        // Set columns hideable, reordering etc.
//        paymentGrid.getColumns().forEach(column -> column.setSortable(true));
//        paymentGrid.setColumnReorderingAllowed(true);
//        paymentGrid.setColumnResizeMode(ColumnResizeMode.ANIMATED);
//
//        // Set open document details for editing on double click
//        paymentGrid.addItemClickListener(event -> {
//            if (event.getMouseEventDetails().isDoubleClick()) {
//                UI.getCurrent().getNavigator().navigateTo("xxx");
//                // todo set navigator to the selected car
//                // todo NOT A NAVIGATOR ! New carEditForm(Long id);
//            }
//        });
//
//      // Inline filtering
//        this.filter = new GridCellFilter(paymentGrid);
//        this.filter.setDateFilter("paymentDate", new SimpleDateFormat("HH:mm:ss dd.MM.yyyy"), true);
//        this.filter.setTextFilter("paymentNote", true, true);
//        this.filter.setNumberFilter("paymentAmount", Long.class);
//        this.filter.setTextFilter("method", true, true);
//        this.filter.setTextFilter("accountCode", true, true);
//
//        // Inline editor
//        paymentGrid.getEditor().setEnabled(true);
//
//        // Extra columns: edit, delete
//        paymentGrid.addColumn(car -> "Edit", new ButtonRenderer(clickEvent -> {
//            //todo navigator
//        }));
//
//        paymentGrid.addColumn(car -> "Delete", new ButtonRenderer(clickEvent -> {
//            //todo check if works, switch to id?
//            bookingPaymentRepository.delete((BookingPayment) clickEvent.getItem());
//        }));
//
//        paymentGrid.setSizeFull();
//
////        filter = new GridCellFilter(paymentGrid);
//
//        return paymentGrid;
//    }
//
//    // Build the buttons' row: Add, Clear filters
//    public HorizontalLayout buildCarButtons() {
//        HorizontalLayout buttonsBookingPaymentHL = new HorizontalLayout();
//
//        Button addBookingPaymentBtn = new Button("Add a payment"); // add new document
//        addBookingPaymentBtn.addClickListener(e -> UI.getCurrent().getNavigator().navigateTo("aaa"));
//        //todo add navigator -- see line 86, not navigator, but new form with the given id
//
//        Button clearAllFilters = new Button("Remove filters", e -> {
//            filter.clearAllFilters();
//        });
//
//        buttonsBookingPaymentHL.addComponents(addBookingPaymentBtn, clearAllFilters);
//        return buttonsBookingPaymentHL;
//    }
//
//    // Put all together
//    public VerticalLayout buildBookingDocumentGridView() {
//        VerticalLayout bookingDocumentGridViewVL = new VerticalLayout();
//        bookingDocumentGridViewVL.addComponents(buildCarButtons(), buildBookingPaymentGrid());
//        return bookingDocumentGridViewVL;
//    }
//
//
//}
