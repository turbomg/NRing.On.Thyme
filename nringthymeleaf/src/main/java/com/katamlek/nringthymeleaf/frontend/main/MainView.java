package com.katamlek.nringthymeleaf.frontend.main;

import com.katamlek.nringthymeleaf.frontend.grids.BookingGridView;
import com.katamlek.nringthymeleaf.frontend.grids.CarGridView;
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
 * <p>
 * Created as a single View class because the logic is so simple that using a
 * pattern like MVP would add much overhead for little gain. If more complexity
 * is added to the class, you should consider splitting out a presenter.
 */
@SpringViewDisplay
@UIScope
public class MainView extends VerticalLayout implements ViewDisplay {

    private final Map<Class<? extends View>, Button> navigationButtons = new HashMap<>();
    private final NavigationManager navigationManager;
//	private final SecuredViewAccessControl viewAccessControl;
    //todo when working on security

   // private Label activeViewName;
   // private Button menuButton;
    private CssLayout menu;
    private Button myAccount;
    private Button calendar;
    private Button bookings;
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
     	attachNavigation(calendar, CalendarView.class);
		attachNavigation(bookings, BookingGridView.class);
		attachNavigation(customers, CustomerGridView.class);
		attachNavigation(fleet, CarGridView.class);
		attachNavigation(systemUsers, UserGridView.class);
		attachNavigation(reports, ReportsView.class);
		attachNavigation(setup, SetupView.class);

		logOut.addClickListener(e -> logout());
    }

    //todo VVV
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
//todo        activeViewName.setValue(viewName);
    }

    /**
     * Logs the user out after ensuring the currently open view has no unsaved
     * changes.
     */
    public void logout() {
        ViewLeaveAction doLogout = () -> {
            UI ui = getUI();
            ui.getSession().getSession().invalidate();
            ui.getPage().reload();
        };

        navigationManager.runAfterLeaveConfirmation(doLogout);
    }

    public VerticalLayout buildMainView() {
        VerticalLayout mainLayoutVL = new VerticalLayout();

        // Side menu
        menu = new CssLayout();

        Label currentUser = new Label();
        currentUser.setCaption("WHOAMI");
        //todo get the user from session

        myAccount = new Button("My account");
        calendar = new Button("Calendar");
        bookings = new Button("Bookings");
        customers = new Button("Customers");
        fleet = new Button("Fleet");
        systemUsers = new Button("User management");
        reports = new Button("Reports");
        setup = new Button("Setup");
        logOut = new Button("Log out");
        menu.addComponents(currentUser, myAccount, bookings, customers, fleet, systemUsers, reports, setup, logOut);

        // Content panel
        displayArea = new VerticalLayout();

        mainLayoutVL.addComponents(menu, displayArea);

        return mainLayoutVL;
    }
}
