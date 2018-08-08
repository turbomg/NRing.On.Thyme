package com.katamlek.nringthymeleaf.frontend.views;

import com.katamlek.nringthymeleaf.domain.BookingCar;
import com.katamlek.nringthymeleaf.domain.Car;
import com.katamlek.nringthymeleaf.domain.Event;
import com.katamlek.nringthymeleaf.frontend.forms.BookingForm;
import com.katamlek.nringthymeleaf.frontend.forms.CustomerForm;
import com.katamlek.nringthymeleaf.frontend.navigation.NavigationManager;
import com.katamlek.nringthymeleaf.repositories.EventRepository;
import com.katamlek.nringthymeleaf.repositories.UserRepository;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.assertj.core.util.Lists;

import java.text.SimpleDateFormat;
import java.util.Date;

@UIScope
@SpringView
public class WelcomeView extends VerticalLayout implements View {

    private UserRepository userRepository;
    private EventRepository eventRepository;
    private NavigationManager navigationManager;
    // private EventGridView eventGridView;
    // Set the dates
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final Date today = new Date();
    private final Date tomorrow = today; //todo import DateUtils, use addDays


    public WelcomeView(UserRepository userRepository, EventRepository eventRepository, NavigationManager navigationManager) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        //      this.eventGridView = eventGridView;
        this.navigationManager = navigationManager;
        setMargin(false);
        addComponent(buildWelcomeView());
    }

    // Today/ Tomorrow boxes - designed to accept any date
    public HorizontalLayout buildDayOverviews() {
        HorizontalLayout dayOverview = new HorizontalLayout();
        dayOverview.setMargin(false);

        // Grids
        Grid<com.katamlek.nringthymeleaf.domain.Event> eventsToday = new Grid<>(com.katamlek.nringthymeleaf.domain.Event.class);
        eventsToday.setItems(Lists.newArrayList(eventRepository.findByEventDate(today)));
        Grid<com.katamlek.nringthymeleaf.domain.Event> eventsTomorrow = new Grid<>(com.katamlek.nringthymeleaf.domain.Event.class);
        eventsTomorrow.setItems(Lists.newArrayList(eventRepository.findByEventDate(tomorrow)));

        eventsToday.setHeaderVisible(false);
        eventsToday.setColumns("eventName", "eventDate", "eventStartTime", "eventEndTime");
//        eventsToday.addColumn(com.katamlek.nringthymeleaf.domain.Event::getEventName);
//        eventsToday.addColumn(com.katamlek.nringthymeleaf.domain.Event::getEventDate);
//        eventsToday.addColumn(com.katamlek.nringthymeleaf.domain.Event::getEventStartTime);
//        eventsToday.addColumn(com.katamlek.nringthymeleaf.domain.Event::getEventEndTime);
        eventsToday.addComponentColumn(this::buildCountTodayColumn);

        eventsToday.setHeightByRows(4);
        eventsToday.setCaption("Today " + today);

        eventsTomorrow.setHeaderVisible(false);
        eventsTomorrow.setColumns("eventName", "eventDate", "eventStartTime", "eventEndTime");
//        eventsTomorrow.addColumn(com.katamlek.nringthymeleaf.domain.Event::getEventName);
//        eventsTomorrow.addColumn(com.katamlek.nringthymeleaf.domain.Event::getEventDate);
//        eventsTomorrow.addColumn(com.katamlek.nringthymeleaf.domain.Event::getEventStartTime);
//        eventsTomorrow.addColumn(com.katamlek.nringthymeleaf.domain.Event::getEventEndTime);
        eventsTomorrow.addComponentColumn(this::buildCountTomorrowColumn);

        eventsTomorrow.setHeightByRows(4);
        eventsTomorrow.setCaption("Tomorrow " + tomorrow);

        // Buttons
        Button goToTodayOverview = new Button("Show overview");
        goToTodayOverview.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_SMALL);
        goToTodayOverview.setIcon(VaadinIcons.ARROW_RIGHT);
        goToTodayOverview.addClickListener(e -> {
            Notification.show("Not yet"); //todo
        });

        Button goToTomorrowOverview = new Button("Show overview");
        goToTomorrowOverview.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_SMALL);
        goToTomorrowOverview.setIcon(VaadinIcons.ARROW_RIGHT);
        goToTomorrowOverview.addClickListener(e -> Notification.show("Not yet")); //todo

        // Put together
        VerticalLayout todayVL = new VerticalLayout(eventsToday, goToTodayOverview);
        todayVL.setMargin(false);

        VerticalLayout tomorrowVL = new VerticalLayout(eventsTomorrow, goToTomorrowOverview);
        tomorrowVL.setMargin(false);


        dayOverview.addComponents(todayVL, tomorrowVL);

        return dayOverview;
    }

    // Buttons row
    public HorizontalLayout buildButtonsRow() {
        HorizontalLayout buttonsRowHL = new HorizontalLayout();

        Button addBookingBtn = new Button("Add booking");
        addBookingBtn.addClickListener(e -> {
            //todo - fix the problem with saving new entities
            navigationManager.navigateTo(BookingForm.class);
        });
        addBookingBtn.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_LARGE);

        Button addCustomerBtn = new Button("Add customer");
        addCustomerBtn.addClickListener(e -> Notification.show("Not yet")); //todo
        addCustomerBtn.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_LARGE);

        // todo - all of the below
        Button printIndemnityForm = new Button("Print blank idemnity form");
        printIndemnityForm.addClickListener(e -> Notification.show("Can't do this for you yet."));
        printIndemnityForm.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_LARGE);

        Button printMileageSheet = new Button("Print mileage sheet");
        printMileageSheet.addClickListener(e -> Notification.show("Can't do this for you yet."));
        printMileageSheet.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_LARGE);

        Button printCarChecklist = new Button("Print car checklist");
        printCarChecklist.addClickListener(e -> Notification.show("Can't do this for you yet."));
        printCarChecklist.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_LARGE);

        buttonsRowHL.addComponents(addBookingBtn, addCustomerBtn, printIndemnityForm, printMileageSheet, printCarChecklist);

        buttonsRowHL.setWidth("100%");

        return buttonsRowHL;
    }

    public HorizontalLayout buildProgressSection() {
        HorizontalLayout progressHL = new HorizontalLayout();
        //      bookingsSectionVL.setCaption("My bookings");

        // todo fill the magic grids with data - use queries

        // The magic grids
        //todo increase the spacing
        // Bookings
        GridLayout bookingsProgress = new GridLayout(4, 3);
        bookingsProgress.setSpacing(true);

        bookingsProgress.setCaption("My bookings");
        Label bookingsMadeL = new Label("Bookings made");
        bookingsMadeL.addStyleNames(ValoTheme.LABEL_BOLD);
        Label unpaidL = new Label("Unpaid");
        unpaidL.addStyleNames(ValoTheme.LABEL_BOLD);
        Label averageL = new Label("Average");
        averageL.addStyleNames(ValoTheme.LABEL_BOLD);

        bookingsProgress.addComponent(bookingsMadeL, 1, 0);
        bookingsProgress.addComponent(unpaidL, 2, 0);
        bookingsProgress.addComponent(averageL, 3, 0);

        Label thisMonthB = new Label("This month");
        thisMonthB.addStyleNames(ValoTheme.LABEL_BOLD);
        Label ytdB = new Label("This year");
        ytdB.addStyleNames(ValoTheme.LABEL_BOLD);

        bookingsProgress.addComponent(thisMonthB, 0, 1);
        bookingsProgress.addComponent(ytdB, 0, 2);

        bookingsProgress.addComponent(new Label("PLACEHOLDER"), 2, 1);

        bookingsProgress.setSpacing(true);

        // Turnover
        GridLayout turnoverProgress = new GridLayout(4, 3);
        turnoverProgress.setWidth(100, Unit.PERCENTAGE);
        turnoverProgress.setCaption("My turnover progress");

        Label targetL = new Label("Target");
        targetL.addStyleNames(ValoTheme.LABEL_BOLD);
        Label bookedL = new Label("Booked");
        bookedL.addStyleNames(ValoTheme.LABEL_BOLD);
        Label paidL = new Label("Paid");
        paidL.addStyleNames(ValoTheme.LABEL_BOLD);

        Label thisMonthP = new Label("This month");
        thisMonthP.addStyleNames(ValoTheme.LABEL_BOLD);
        Label ytdP = new Label("This year");
        ytdP.addStyleNames(ValoTheme.LABEL_BOLD);

        turnoverProgress.addComponent(thisMonthP, 0, 1);
        turnoverProgress.addComponent(ytdP, 0, 2);
        turnoverProgress.addComponent(targetL, 1, 0);
        turnoverProgress.addComponent(bookedL, 2, 0);
        turnoverProgress.addComponent(paidL, 3, 0);

        turnoverProgress.addComponent(new Label("PLACEHOLDER"), 2, 1);
        turnoverProgress.setSpacing(true);

        VerticalLayout bookingsVL = new VerticalLayout();
        bookingsVL.setMargin(false);
        Button allBookingsBtn = new Button("View all bookings");
        allBookingsBtn.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_SMALL);
        allBookingsBtn.setIcon(VaadinIcons.ARROW_RIGHT);
        allBookingsBtn.addClickListener(e -> Notification.show("Can't do this yet."));
        bookingsVL.addComponents(bookingsProgress, allBookingsBtn);

        VerticalLayout progressVL = new VerticalLayout();
        progressVL.setMargin(false);
        Button overviewBtn = new Button("View details");
        overviewBtn.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_SMALL);
        overviewBtn.setIcon(VaadinIcons.ARROW_RIGHT);
        overviewBtn.addClickListener(e -> Notification.show("Can't do this yet."));
        progressVL.addComponents(turnoverProgress, overviewBtn);

        progressHL.addComponent(bookingsVL);
        progressHL.addComponent(progressVL);

        progressHL.setSizeFull();
        progressHL.setExpandRatio(bookingsVL, 0.50f);
        progressHL.setExpandRatio(progressVL, 0.50f);

        //todo resize the columns

        return progressHL;
    }

    public VerticalLayout buildCarsToCheckList() {
        VerticalLayout carsToCheck = new VerticalLayout();

        // Today grid
        Grid<BookingCar> todayCarGrid = new Grid<>(BookingCar.class);
        todayCarGrid.setCaption("Cars to check today");
        todayCarGrid.setHeightByRows(4);
        todayCarGrid.setColumns("model", "plate", "remarks");
//todo        todayCarGrid.setItems();

        // Tommorow grid
        Grid<BookingCar> tomorrowCarGrid = new Grid<>(BookingCar.class);
        tomorrowCarGrid.setCaption("Cars to check tomorrow");
        tomorrowCarGrid.setHeightByRows(4);
        tomorrowCarGrid.setColumns("model", "plate", "remarks");
//todo setItems()

        carsToCheck.addComponents(todayCarGrid, tomorrowCarGrid);

        return carsToCheck;
    }


    // Put it all together
    public VerticalLayout buildWelcomeView() {
        VerticalLayout welcomeVL = new VerticalLayout();
//        welcomeVL.addComponent(new Label("Welcome"));
        welcomeVL.setMargin(false);

        Label welcome = new Label("Welcome! Great to see you!");
        welcome.addStyleNames(ValoTheme.LABEL_NO_MARGIN, ValoTheme.LABEL_LARGE);

        welcomeVL.addComponents(welcome, buildDayOverviews(), buildButtonsRow(), buildProgressSection(), buildCarsToCheckList());

//        // Horizontal Layout under daily overviews
//        Calendar today = Calendar.getInstance();
//
//        HorizontalLayout overviewsHL = new HorizontalLayout();
//        overviewsHL.addComponents(buildDayOverviews(today), buildDayOverviews(today));
//        //  overviewsHL.setHeight("300px");

        //welcomeVL.addComponents(overviewsHL, buildButtonsRow());

        HorizontalLayout bookingAndProgressHL = new HorizontalLayout();
        //   bookingAndProgressHL.addComponents(buildMyBookingsSectionVL(), buildTurnoverSectionVL());

//        welcomeVL.addComponents(overviewsHL, buildButtonsRow(), bookingAndProgressHL, buildCarsToCheckList());

        //     ), bookingAndProgress, buildCarsToCheckList());

        return welcomeVL;
    }

    // Helpers
    private Button buildCountTomorrowColumn(com.katamlek.nringthymeleaf.domain.Event event) {
        Button todayCountsB = new Button();
//todo get Name from row, today        todayCountsL.setCaption(eventRepository.countEventByEventDateAAndEventName());
        todayCountsB.addStyleNames(ValoTheme.BUTTON_SMALL, ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_QUIET);
        return todayCountsB;
    }

    private Button buildCountTodayColumn(com.katamlek.nringthymeleaf.domain.Event event) {
        Button tomorrowCountB = new Button();
        // todo as above tomorrowCountL.setCaption(eventRepository.countEventByEventDateAAndEventName());
        tomorrowCountB.addStyleNames(ValoTheme.BUTTON_SMALL, ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_QUIET);
        return tomorrowCountB;
    }

}
