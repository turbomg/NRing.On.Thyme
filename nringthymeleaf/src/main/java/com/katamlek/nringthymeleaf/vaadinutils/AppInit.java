////package com.katamlek.nringthymeleaf.vaadinutils;
////
////import com.vaadin.annotations.Theme;
////import com.vaadin.annotations.VaadinServletConfiguration;
////import com.vaadin.server.VaadinRequest;
////import com.vaadin.server.VaadinServlet;
////import com.vaadin.spring.annotation.SpringUI;
////import com.vaadin.ui.Button;
////import com.vaadin.ui.Notification;
////import com.vaadin.ui.UI;
////import org.springframework.context.annotation.ComponentScan;
////
////import javax.servlet.annotation.WebServlet;
////
////@Theme("valo")
////@SpringUI
////@ComponentScan({"com.katamlek"})
////public class Init extends UI {
////
////  //  CustomerServiceImpl customerServiceImpl;
////
//////    public Init(CustomerServiceImpl customerServiceImpl) {
//////        this.customerServiceImpl = customerServiceImpl;
//////    }
////
//////    CustomerRepository customerRepository;
//////
////    @Override
////    protected void init(VaadinRequest vaadinRequest) {
////        //     setContent(buildCustomerGrid());
//////    }
//////
//////    private Grid<Customer> buildCustomerGrid() {
//////        Grid<Customer> customerGrid = new Grid<>();
//////        customerGrid.setItems(customerServiceImpl.getCustomers());
//////        return customerGrid;
//////    }
////
////        setContent(new Button("Click me", e -> Notification.show("Hello Spring")));
////    }
////    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
////    @VaadinServletConfiguration(ui = Init.class, productionMode = false)
////    public static class MyUIServlet extends VaadinServlet {
////
////    }
////}
//
//package com.katamlek.nringthymeleaf.vaadinutils;
//
//import com.katamlek.nringthymeleaf.frontend.forms.UserForm;
//import com.katamlek.nringthymeleaf.frontend.grids.*;
//import com.katamlek.nringthymeleaf.repositories.BookingPaymentRepository;
//import com.vaadin.annotations.VaadinServletConfiguration;
//import com.vaadin.server.VaadinRequest;
//import com.vaadin.server.VaadinServlet;
//import com.vaadin.spring.annotation.SpringUI;
//import com.vaadin.spring.annotation.SpringViewDisplay;
//import com.vaadin.ui.UI;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.ComponentScan;
//
//import javax.servlet.annotation.WebServlet;
//
//@SpringUI
//@ComponentScan({"domain", "frontend", "grids"})
//@SpringViewDisplay
//public class AppInit extends UI {
//
////    @Autowired
////    DevBootstrap devBootstrap;
//
//    @Autowired
//    CarGridView carGridView;
//    @Autowired
//    BookingGridView bookingGridView;
//    @Autowired
//    BookingDocumentsGridView bookingDocumentsGridView;
//    @Autowired
//    BookingPaymentRepository bookingPaymentRepository;
//    @Autowired
//    PaymentsGridView paymentsGridView;
//    @Autowired
//    DriversGridView driversGridView;
//    @Autowired
//    UserGridView userGridView;
//    @Autowired
//    UserForm userForm;
//    @Autowired
//    PackageGridView packageGridView;
//
//    @Override
//    protected void init(VaadinRequest vaadinRequest) {
//
//
//        //      devBootstrap.initEventData();
//        // setContent(new Label("I'm here!"));
//        //  setContent(bookingDocumentsGridView);
//
////        Grid<BookingPayment> paymentGrid = new Grid<>(BookingPayment.class);
////        paymentGrid.setItems(Lists.newArrayList(bookingPaymentRepository.findAll()));
////        paymentGrid.setColumns("id");

//
//        setContent(packageGridView);
//    }
//
//
//    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
//    @VaadinServletConfiguration(ui = AppInit.class, productionMode = false)
//    public static class MyUIServlet extends VaadinServlet {
//
//    }
//
//}
