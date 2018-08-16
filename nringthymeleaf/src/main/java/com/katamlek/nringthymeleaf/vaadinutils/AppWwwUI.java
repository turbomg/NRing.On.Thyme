package com.katamlek.nringthymeleaf.vaadinutils;

import com.katamlek.nringthymeleaf.frontend.navigation.NavigationManager;
import com.katamlek.nringthymeleaf.frontend.views.CustomerBooking;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

@Theme("valo")
@SpringUI(path="/bookingplacement")
@Title("Booking Placement System")
public class AppWwwUI extends UI implements HasLogger {

	private final NavigationManager navigationManager;

	@Autowired
	private final CustomerBooking customerBooking;

	@Autowired
	public AppWwwUI(NavigationManager navigationManager, CustomerBooking customerBooking) {
		this.navigationManager = navigationManager;
		this.customerBooking = customerBooking;
	}

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		setErrorHandler(event -> {
			Throwable t = DefaultErrorHandler.findRelevantThrowable(event.getThrowable());
			getLogger().error("Error during request", t);
		});

		//todo welcome the customer here and redirect to booking form later on - just change the last line


		setContent(customerBooking);

//		navigationManager.navigateTo(CustomerBooking.class);
	}

}
