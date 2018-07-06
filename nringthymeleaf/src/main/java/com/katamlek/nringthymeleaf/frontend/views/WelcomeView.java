package com.katamlek.nringthymeleaf.frontend.views;

import com.katamlek.nringthymeleaf.domain.Car;
import com.katamlek.nringthymeleaf.frontend.forms.BookingForm;
import com.katamlek.nringthymeleaf.frontend.forms.CustomerForm;
import com.katamlek.nringthymeleaf.frontend.grids.EventGridView;
import com.katamlek.nringthymeleaf.repositories.UserRepository;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;

//
//@SpringComponent
//@UIScope
@SpringView
   //     (name = WelcomeView.VIEW_NAME)
public class WelcomeView extends VerticalLayout implements View {
    // public static final String VIEW_NAME = "welcome";

    private UserRepository userRepository;
    // private EventGridView eventGridView;

    public WelcomeView(UserRepository userRepository, EventGridView eventGridView) {
        this.userRepository = userRepository;
        //      this.eventGridView = eventGridView;
        addComponent(buildWelcomeView());
    }

    // Today/ Tomorrow boxes - designed to accept any date
    public VerticalLayout buildDayOverviewVL(Calendar date) {
        VerticalLayout dayOverview = new VerticalLayout();

        SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
        Calendar now = Calendar.getInstance();
        String convertedNow = dateFormat.format(now.getTime());
        String convertedParameter = dateFormat.format(date.getTime());

        if (convertedNow.equals(convertedParameter)) {
            dayOverview.setCaption("Today");
        } else setCaption("Tomorrow");

        Grid<com.katamlek.nringthymeleaf.domain.Event> events = new Grid<>();
        events.setHeaderVisible(false);
        events.addColumn(com.katamlek.nringthymeleaf.domain.Event::getEventName);
        events.addColumn(com.katamlek.nringthymeleaf.domain.Event::getEventStartDateTime);
        events.addColumn(com.katamlek.nringthymeleaf.domain.Event::getEventEndDateTime);
        events.addColumn(event -> {
            int placeholder = 0;
            //todo count the bookings and show totals
            return placeholder;
        });

        // Button directing to calendar
        Button goToCalendarBtn = new Button("Show calendar");
        goToCalendarBtn.addClickListener(e -> {
            //todo
        });

        dayOverview.addComponents(events, goToCalendarBtn);

        return dayOverview;
    }

    // Buttons row
    public HorizontalLayout buildButtonsRow() {
        HorizontalLayout buttonsRowHL = new HorizontalLayout();

        Button addBookingBtn = new Button("Add booking");
        addBookingBtn.addClickListener(e -> {
            //todo - fix the problem with saving new entities
            UI.getCurrent().getNavigator().navigateTo(BookingForm.VIEW_NAME);
        });

        Button addCustomerBtn = new Button("Add customer");
        addBookingBtn.addClickListener(e -> {
            //todo - fix the problem with saving new entities
            UI.getCurrent().getNavigator().navigateTo(CustomerForm.VIEW_NAME);
        });

        // todo - all of the below
        Button printIndemnityForm = new Button("Print blank idemnity form");
        Button printMileageSheet = new Button("Print mileage sheet");
        Button printCarChecklist = new Button("Print car checklist");

        buttonsRowHL.addComponents(addBookingBtn, addCustomerBtn, printIndemnityForm, printMileageSheet, printCarChecklist);

        return buttonsRowHL;
    }

    public VerticalLayout buildMyBookingsSectionVL() {
        VerticalLayout bookingsSectionVL = new VerticalLayout();
        bookingsSectionVL.setCaption("My bookings");

        // The magic grid
        //todo insert data via booking service class with standard SQL I suppose
        // Row with monthly data
        // todo separate stats table?
        Grid<String> bookingsMonthly = new Grid<>();
        bookingsMonthly.addColumn(eventM -> {
            String labelM = "This month";
            //todo count the bookings and show totals
            //todo 2 new entities, one for month row, one for year
            return labelM;
        });
        // Row with yearly data
        Grid<String> bookingsYtd = new Grid<>();
        bookingsYtd.addColumn(eventYtd -> {
            String labelYtd = "This month";
            //todo count the bookings and show totals
            //todo 2 new entities, one for month row, one for year
            return labelYtd;
        });

        Button myBookings = new Button("View my bookings");
        myBookings.addClickListener(e -> {
           // UI.getCurrent().getNavigator().navigateTo(BookingGridView.VIEW_NAME);
            //todo set filter to current user
            //todo or add a new view?
        });

        bookingsSectionVL.addComponents(bookingsMonthly, bookingsYtd, myBookings);

        return bookingsSectionVL;
    }

    public VerticalLayout buildTurnoverSectionVL() {
        VerticalLayout turnoverSectionVL = new VerticalLayout();
        turnoverSectionVL.setCaption("Turnover progress");

        // The magic grid
        //todo insert data via booking service class with standard SQL I suppose
        // Row with monthly data
        // todo separate stats table?
        Grid<String> turnoverMonthly = new Grid<>();
        turnoverMonthly.addColumn(eventM -> {
            String labelM = "This month";
            //todo count the bookings and show totals
            //todo 2 new entities, one for month row, one for year
            return labelM;
        });
        // Row with yearly data
        Grid<String> turnoverYtd = new Grid<>();
        turnoverYtd.addColumn(eventYtd -> {
            String labelYtd = "Overall";
            //todo count the bookings and show totals
            //todo 2 new entities, one for month row, one for year
            return labelYtd;
        });

        Button progressOverview = new Button("To overview");
        progressOverview.addClickListener(e -> {
            //todo where to go? what to do?
            //todo set filter to current user
        });

        turnoverSectionVL.addComponents(turnoverMonthly, turnoverYtd, progressOverview);

        return turnoverSectionVL;
    }

    public VerticalLayout buildCarsToCheckVL() {
        VerticalLayout carsToCheck = new VerticalLayout();
        carsToCheck.setCaption("Cars to check today");

        // Today grid
        Label todayL = new Label("Today");

        Grid<Car> todayCarGrid = new Grid<>();
        //todo - maybe after calendar?

        // Tommorow grid
        Label tomorrowL = new Label("Tomorrow");

        Grid<Car> tomorrowCarGrid = new Grid<>();
        //todo - maybe after calendar?

        carsToCheck.addComponents(todayL, todayCarGrid, tomorrowL, tomorrowCarGrid);

        return carsToCheck;
    }

    // Put it all together
    public VerticalLayout buildWelcomeView() {
        VerticalLayout welcomeVL = new VerticalLayout();
        welcomeVL.addComponent(new Label("Welcome"));

        // Horizontal Layout under daily overviews
        Calendar today = Calendar.getInstance();
        // todo deal with the damn dates !!!!!

        HorizontalLayout overviewsHL = new HorizontalLayout();
        overviewsHL.addComponents(buildDayOverviewVL(today), buildDayOverviewVL(today));
      //  overviewsHL.setHeight("300px");

        //welcomeVL.addComponents(overviewsHL, buildButtonsRow());

        HorizontalLayout bookingAndProgressHL = new HorizontalLayout();
        bookingAndProgressHL.addComponents(buildMyBookingsSectionVL(), buildTurnoverSectionVL());

        welcomeVL.addComponents(overviewsHL, buildButtonsRow(), bookingAndProgressHL, buildCarsToCheckVL());

   //     ), bookingAndProgress, buildCarsToCheckVL());

        return welcomeVL;
    }
}
