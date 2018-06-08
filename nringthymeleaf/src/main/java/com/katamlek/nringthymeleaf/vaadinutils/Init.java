package com.katamlek.nringthymeleaf.vaadinutils;

import com.katamlek.nringthymeleaf.domain.Customer;
import com.katamlek.nringthymeleaf.repositories.CustomerRepository;
import com.katamlek.nringthymeleaf.services.CustomerServiceImpl;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.annotation.WebServlet;

@Theme("valo")
@SpringUI
@ComponentScan({"com.katamlek"})
public class Init extends UI {

  //  CustomerServiceImpl customerServiceImpl;

//    public Init(CustomerServiceImpl customerServiceImpl) {
//        this.customerServiceImpl = customerServiceImpl;
//    }

//    CustomerRepository customerRepository;
//
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        //     setContent(buildCustomerGrid());
//    }
//
//    private Grid<Customer> buildCustomerGrid() {
//        Grid<Customer> customerGrid = new Grid<>();
//        customerGrid.setItems(customerServiceImpl.getCustomers());
//        return customerGrid;
//    }

        setContent(new Button("Click me", e -> Notification.show("Hello Spring")));
    }
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = Init.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {

    }
}
