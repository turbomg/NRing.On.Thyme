package com.katamlek.nringthymeleaf.frontend.main;

import com.katamlek.nringthymeleaf.frontend.forms.EventForm;
import com.katamlek.nringthymeleaf.frontend.grids.BookingGridView;
import com.katamlek.nringthymeleaf.frontend.grids.CarGridView;
import com.katamlek.nringthymeleaf.frontend.grids.EventGridView;
import com.katamlek.nringthymeleaf.frontend.grids.UserGridView;
import com.katamlek.nringthymeleaf.frontend.navigation.NavigationManager;
import com.katamlek.nringthymeleaf.frontend.views.*;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.navigator.ViewLeaveAction;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * The main view containing the menu and the content area where actual views are
 * shown.
 */

@SpringViewDisplay
@UIScope
public class MainView extends VerticalLayout implements ViewDisplay {

    private final Map<Class<? extends View>, Button> navigationButtons = new HashMap<>();
    private final NavigationManager navigationManager;
    //	private final SecuredViewAccessControl viewAccessControl;
    //todo when working on security

    //todo add Back to welcome button

    private VerticalLayout menu;
    private Button myAccount;
    private Button calendar;
    private Button bookings;
    private Button events;
    private Button customers;
    private Button fleet;
    private Button systemUsers;
    private Button reports;
    private Button setup;
    private Button logOut;
    private VerticalLayout displayArea;

    @Autowired
    public MainView(NavigationManager navigationManager) {
        this.navigationManager = navigationManager;
        //	this.viewAccessControl = viewAccessControl;
        addComponent(buildMainView());
    }

    @PostConstruct
    public void init() {
        attachNavigation(myAccount, MyAccountView.class);
        attachNavigation(calendar, CalendarView.class);
        attachNavigation(bookings, BookingGridView.class);
        attachNavigation(events, EventForm.class); //todo change to grid when done with event form
        attachNavigation(customers, CustomerGridView.class);
        attachNavigation(fleet, CarGridView.class);
        attachNavigation(systemUsers, UserGridView.class);
        attachNavigation(reports, ReportsView.class);
        attachNavigation(setup, SetupView.class);

        logOut.addClickListener(e -> logout()); //todo
    }

    //todo: all the below functionality

    /**
     * Makes clicking the given button navigate to the given view if the user
     * has access to the view.
     * <p>
     * If the user does not have access to the view, hides the button.
     *
     * @param navigationButton the button to use for navigatio
     * @param targetView       the view to navigate to when the user clicks the button
     */

    private void attachNavigation(Button navigationButton, Class<? extends View> targetView) {
        //	boolean hasAccessToView = viewAccessControl.isAccessGranted(targetView);
        //	navigationButton.setVisible(hasAccessToView);

        //	if (hasAccessToView) {
        navigationButtons.put(targetView, navigationButton);
        navigationButton.addClickListener(e -> navigationManager.navigateTo(targetView));
        //	}
    }

    @Override
    public void showView(View view) {
        displayArea.removeAllComponents();
        displayArea.addComponent(view.getViewComponent());

        navigationButtons.forEach((viewClass, button) -> button.setStyleName("selected", viewClass == view.getClass()));

        Button menuItem = navigationButtons.get(view.getClass());
        String viewName = "";
        if (menuItem != null) {
            viewName = menuItem.getCaption();
        }
        // activeViewName.setValue(viewName);
    }

    /**
     * Logs the user out after ensuring the currently open view has no unsaved
     * changes.
     */
    public void logout() {
        //TODO KASIA: not working, fix
        ViewLeaveAction doLogout = () -> {
            UI ui = getUI();
            ui.getSession().getSession().invalidate();
            ui.getPage().reload();
        };

        navigationManager.runAfterLeaveConfirmation(doLogout);
    }

    public HorizontalLayout buildMainView() {
        HorizontalLayout mainLayoutVL = new HorizontalLayout();

        // Side menu
        menu = new VerticalLayout();

        Label currentUser = new Label();
        currentUser.setCaption("WHOAMI");
        //TODO KASIA: get the user from session

        myAccount = new Button("My account");
        calendar = new Button("Calendar");
        bookings = new Button("Bookings");
        events = new Button("Events");
        customers = new Button("Customers");
        fleet = new Button("Fleet");
        systemUsers = new Button("User management");
        reports = new Button("Reports");
        setup = new Button("Setup");
        logOut = new Button("Log out");
        menu.addComponents(currentUser, myAccount, calendar, bookings, events, customers, fleet, systemUsers, reports, setup, logOut);

        // Content panel
        displayArea = new VerticalLayout();
        displayArea.setSpacing(false);
        displayArea.setMargin(false);
        displayArea.setWidthUndefined();
        displayArea.setResponsive(true);

        mainLayoutVL.addComponents(menu, displayArea);

        return mainLayoutVL;
    }
}
