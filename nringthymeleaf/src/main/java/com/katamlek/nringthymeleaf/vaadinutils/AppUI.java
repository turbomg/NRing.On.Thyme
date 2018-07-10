package com.katamlek.nringthymeleaf.vaadinutils;

import com.katamlek.nringthymeleaf.frontend.main.MainView;
import com.katamlek.nringthymeleaf.frontend.navigation.NavigationManager;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Viewport;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

@Theme("valo")
@SpringUI
@Viewport("width=device-width,initial-scale=1.0,user-scalable=no")
@Title("Booking Management System")
public class AppUI extends UI implements HasLogger {

//	private final SpringViewProvider viewProvider;
	private final NavigationManager navigationManager;

	private final MainView mainView;

	@Autowired
	public AppUI(NavigationManager navigationManager, MainView mainView) {
//		this.viewProvider = viewProvider;
		this.navigationManager = navigationManager;
		this.mainView = mainView;
	}

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		setErrorHandler(event -> {
			Throwable t = DefaultErrorHandler.findRelevantThrowable(event.getThrowable());
			getLogger().error("Error during request", t);
		});

		// Set the theme ("globally") for all Charts
	//	ChartOptions.get(this).setTheme(new ChartsTheme());
		//todo later on when working on reports

	//	viewProvider.setAccessDeniedViewClass(AccessDeniedView.class);
		// todo above, when working on security
		setContent(mainView);

		navigationManager.navigateToDefaultView();
	}

}
