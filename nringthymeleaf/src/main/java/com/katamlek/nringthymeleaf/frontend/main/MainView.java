package com.katamlek.nringthymeleaf.frontend.main;

import com.katamlek.nringthymeleaf.domain.PriceList;
import com.katamlek.nringthymeleaf.frontend.forms.BookingForm;
import com.katamlek.nringthymeleaf.frontend.forms.EventForm;
import com.katamlek.nringthymeleaf.frontend.grids.*;
import com.katamlek.nringthymeleaf.frontend.navigation.NavigationManager;
import com.katamlek.nringthymeleaf.frontend.views.*;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.navigator.ViewLeaveAction;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
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

    private VerticalLayout menu;
    private Button dashboard;
    private Button myAccount;
    private Button calendar;
    private Button bookings;
    private Button events;
    private Button customers;
    private Button fleet;
    private Button systemUsers;
    private Button reports;
    private Button pricelist;
    private Button setup;
    private Button logOut;

    // this is a test button to plug in forms to be tested

    private VerticalLayout displayArea;

    public MainView(NavigationManager navigationManager) {
        this.navigationManager = navigationManager;
        //	this.viewAccessControl = viewAccessControl;
        addComponent(buildMainView());
    }

    @PostConstruct
    public void init() {
        attachNavigation(dashboard, WelcomeView.class);
        attachNavigation(myAccount, MyAccountView.class);
        attachNavigation(calendar, CalendarView.class);
        attachNavigation(bookings, BookingGridView.class);
        attachNavigation(events, EventGridView.class);
        attachNavigation(customers, CustomerGridView.class);
        attachNavigation(fleet, CarGridView.class);
        attachNavigation(systemUsers, UserGridView.class);
        attachNavigation(reports, ReportsView.class);
        attachNavigation(pricelist, PriceListView.class);
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
        //todo now redirects to welcome view, fix later when security implemented
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
        currentUser.setCaption("XX"); //todo get user initials from session
        currentUser.addStyleNames(ValoTheme.LABEL_COLORED, ValoTheme.LABEL_LARGE);

        dashboard = new Button("Dashboard");
        dashboard.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_ICON_ONLY);
        dashboard.setIcon(VaadinIcons.DASHBOARD);
        dashboard.setDescription("Dashboard");

        myAccount = new Button("My account");
        myAccount.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_ICON_ONLY);
        myAccount.setIcon(VaadinIcons.USER_HEART);
        myAccount.setDescription("My account");

        calendar = new Button("Calendar");
        calendar.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_ICON_ONLY);
        calendar.setIcon(VaadinIcons.CALENDAR);
        calendar.setDescription("Calendar");


        bookings = new Button("Bookings");
        bookings.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_ICON_ONLY);
        bookings.setIcon(VaadinIcons.OPEN_BOOK);
        bookings.setDescription("Bookings");

        events = new Button("Events");
        events.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_ICON_ONLY);
        events.setIcon(VaadinIcons.SPARK_LINE);
        events.setDescription("Events");

        customers = new Button("Customers");
        customers.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_ICON_ONLY);
        customers.setIcon(VaadinIcons.USERS);
        customers.setDescription("Customers");

        fleet = new Button("Fleet");
        fleet.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_ICON_ONLY);
        fleet.setIcon(VaadinIcons.CAR);
        fleet.setDescription("Fleet");

        systemUsers = new Button("User management");
        systemUsers.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_ICON_ONLY);
        systemUsers.setIcon(VaadinIcons.USER_CHECK);
        systemUsers.setDescription("User management");

        reports = new Button("Reports");
        reports.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_ICON_ONLY);
        reports.setIcon(VaadinIcons.PAPERPLANE);
        reports.setDescription("Reports");

        pricelist = new Button("Price list");
        pricelist.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_ICON_ONLY);
        pricelist.setIcon(VaadinIcons.DOLLAR);
        pricelist.setDescription("Reports");

        setup = new Button("Setup");
        setup.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_ICON_ONLY);
        setup.setIcon(VaadinIcons.COGS);
        setup.setDescription("Setup");

        logOut = new Button("Log out");
        logOut.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_ICON_ONLY);
        logOut.setIcon(VaadinIcons.POWER_OFF);
        logOut.setDescription("Log out");

        menu.addComponents(currentUser, dashboard, myAccount, calendar, bookings, events,
                customers, fleet, systemUsers, reports, pricelist, setup, logOut);

        menu.setComponentAlignment(currentUser, Alignment.MIDDLE_CENTER);
        menu.setComponentAlignment(dashboard, Alignment.MIDDLE_CENTER);
        menu.setComponentAlignment(myAccount, Alignment.MIDDLE_CENTER);
        menu.setComponentAlignment(calendar, Alignment.MIDDLE_CENTER);
        menu.setComponentAlignment(bookings, Alignment.MIDDLE_CENTER);
        menu.setComponentAlignment(events, Alignment.MIDDLE_CENTER);
        menu.setComponentAlignment(customers, Alignment.MIDDLE_CENTER);
        menu.setComponentAlignment(fleet, Alignment.MIDDLE_CENTER);
        menu.setComponentAlignment(systemUsers, Alignment.MIDDLE_CENTER);
        menu.setComponentAlignment(reports, Alignment.MIDDLE_CENTER);
        menu.setComponentAlignment(pricelist, Alignment.MIDDLE_CENTER);
        menu.setComponentAlignment(setup, Alignment.MIDDLE_CENTER);
        menu.setComponentAlignment(logOut, Alignment.MIDDLE_CENTER);

        menu.setSpacing(false);
        menu.setMargin(new MarginInfo(false, true, false, false));

        // Contents panel
        displayArea = new VerticalLayout();
        displayArea.setSpacing(false);
        displayArea.setMargin(false);
        displayArea.setResponsive(true);

        mainLayoutVL.addComponents(menu, displayArea);
        return mainLayoutVL;
    }
}
