package com.katamlek.nringthymeleaf.frontend.views;

import com.katamlek.nringthymeleaf.domain.BookingPackageItem;
import com.katamlek.nringthymeleaf.domain.BookingPackageItemCar;
import com.katamlek.nringthymeleaf.domain.Car;
import com.katamlek.nringthymeleaf.frontend.forms.BookingForm;
import com.katamlek.nringthymeleaf.frontend.forms.CustomerForm;
import com.katamlek.nringthymeleaf.frontend.forms.EventForm;
import com.katamlek.nringthymeleaf.frontend.navigation.NavigationManager;
import com.katamlek.nringthymeleaf.repositories.EventRepository;
import com.katamlek.nringthymeleaf.repositories.UserRepository;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.lang3.time.DateUtils;
import org.assertj.core.util.Lists;

import java.text.SimpleDateFormat;
import java.util.Date;

@UIScope
@SpringView
public class WelcomeView extends VerticalLayout implements View {

    private UserRepository userRepository;
    private EventRepository eventRepository;
    private NavigationManager navigationManager;

    // Set the dates
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final Date today = new Date();
    private final Date tomorrow = DateUtils.addDays(today, 1);


    public WelcomeView(UserRepository userRepository, EventRepository eventRepository, NavigationManager navigationManager) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.navigationManager = navigationManager;
        setMargin(false);
        addComponent(buildWelcomeView());
    }

    // Today/ Tomorrow boxes
    public HorizontalLayout buildDayOverviews() {
        HorizontalLayout dayOverview = new HorizontalLayout();
        dayOverview.setMargin(false);

        // Grids
        Grid<com.katamlek.nringthymeleaf.domain.Event> eventsToday = new Grid<>(com.katamlek.nringthymeleaf.domain.Event.class);
        eventsToday.setItems(Lists.newArrayList(eventRepository.findByEventDate(today)));
        Grid<com.katamlek.nringthymeleaf.domain.Event> eventsTomorrow = new Grid<>(com.katamlek.nringthymeleaf.domain.Event.class);
        eventsTomorrow.setItems(Lists.newArrayList(eventRepository.findByEventDate(tomorrow)));

        eventsToday.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                navigationManager.navigateTo(EventForm.class, event.getItem().getId());
            }
        });

        eventsToday.setHeaderVisible(false);
        eventsToday.setColumns("eventName", "eventDate", "eventStartTime", "eventEndTime");
        eventsToday.addComponentColumn(this::buildCountTodayColumn);

        eventsToday.setHeightByRows(4);
        eventsToday.setCaption("Today " + (dateFormat.format(today)));

        eventsTomorrow.setHeaderVisible(false);
        eventsTomorrow.setColumns("eventName", "eventDate", "eventStartTime", "eventEndTime");
        eventsTomorrow.addComponentColumn(this::buildCountTomorrowColumn);

        eventsTomorrow.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                navigationManager.navigateTo(EventForm.class, event.getItem().getId());
            }
        });

        eventsTomorrow.setHeightByRows(4);
        eventsTomorrow.setCaption("Tomorrow " + (dateFormat.format(tomorrow)));

        // Buttons
        Button goToTodayOverview = new Button("Show overview");
        goToTodayOverview.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_SMALL);
        goToTodayOverview.setIcon(VaadinIcons.ARROW_RIGHT);
        goToTodayOverview.addClickListener(e -> {
            navigationManager.navigateTo(DayOverview.class); //todo
        });

        Button goToTomorrowOverview = new Button("Show overview");
        goToTomorrowOverview.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_SMALL);
        goToTomorrowOverview.setIcon(VaadinIcons.ARROW_RIGHT);
        goToTomorrowOverview.addClickListener(e ->
                navigationManager.navigateTo(DayOverview.class)); //todo

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
            navigationManager.navigateTo(BookingForm.class);
        });
        addBookingBtn.setIcon(VaadinIcons.OPEN_BOOK);
        addBookingBtn.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_LARGE, ValoTheme.BUTTON_ICON_ALIGN_TOP);

        Button addCustomerBtn = new Button("Add customer");
        addCustomerBtn.addClickListener(e -> navigationManager.navigateTo(CustomerForm.class));
        addCustomerBtn.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_LARGE, ValoTheme.BUTTON_ICON_ALIGN_TOP);
        addCustomerBtn.setIcon(VaadinIcons.USER_STAR);

        // todo - all of the below
        Button printIndemnityForm = new Button("Print blank idemnity form");
        printIndemnityForm.addClickListener(e -> Notification.show("Can't do this for you yet."));
        printIndemnityForm.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_LARGE, ValoTheme.BUTTON_ICON_ALIGN_TOP);
        printIndemnityForm.setIcon(VaadinIcons.PRINT);

        Button printMileageSheet = new Button("Print mileage sheet");
        printMileageSheet.addClickListener(e -> Notification.show("Can't do this for you yet."));
        printMileageSheet.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_LARGE, ValoTheme.BUTTON_ICON_ALIGN_TOP);
        printMileageSheet.setIcon(VaadinIcons.CALC);

        Button printCarChecklist = new Button("Print car checklist");
        printCarChecklist.addClickListener(e -> Notification.show("Can't do this for you yet."));
        printCarChecklist.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_LARGE, ValoTheme.BUTTON_ICON_ALIGN_TOP);
        printCarChecklist.setIcon(VaadinIcons.CHECK_CIRCLE);

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

        bookingsProgress.addComponent(new Label("100"), 1, 1);
        bookingsProgress.addComponent(new Label("200"), 2, 1);
        bookingsProgress.addComponent(new Label("300"), 3, 1);
        bookingsProgress.addComponent(new Label("400"), 1, 2);
        bookingsProgress.addComponent(new Label("500"), 2, 2);
        bookingsProgress.addComponent(new Label("600"), 3, 2);

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

        turnoverProgress.addComponent(new Label("100"), 1, 1);
        turnoverProgress.addComponent(new Label("200"), 2, 1);
        turnoverProgress.addComponent(new Label("300"), 3, 1);
        turnoverProgress.addComponent(new Label("400"), 1, 2);
        turnoverProgress.addComponent(new Label("500"), 2, 2);
        turnoverProgress.addComponent(new Label("600"), 3, 2);

        turnoverProgress.setSpacing(true);

        VerticalLayout bookingsVL = new VerticalLayout();
        bookingsVL.setMargin(false);
        Button allBookingsBtn = new Button("View all bookings");
        allBookingsBtn.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_SMALL);
        allBookingsBtn.setIcon(VaadinIcons.ARROW_RIGHT);
        allBookingsBtn.addClickListener(e -> Notification.show("As I don't keep your name in memory yet, I can't do this."));
        bookingsVL.addComponents(bookingsProgress, allBookingsBtn);

        VerticalLayout progressVL = new VerticalLayout();
        progressVL.setMargin(false);
        Button overviewBtn = new Button("View details");
        overviewBtn.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_SMALL);
        overviewBtn.setIcon(VaadinIcons.ARROW_RIGHT);
        overviewBtn.addClickListener(e -> Notification.show("As I don't keep your name in memory yet, I can't do this."));
        progressVL.addComponents(turnoverProgress, overviewBtn);

        progressHL.addComponent(bookingsVL);
        progressHL.addComponent(progressVL);

        progressHL.setSizeFull();
        progressHL.setExpandRatio(bookingsVL, 0.50f);
        progressHL.setExpandRatio(progressVL, 0.50f);

        //todo resize the columns

        return progressHL;
    }

    public HorizontalLayout buildCarsToCheckList() {
        HorizontalLayout carsToCheck = new HorizontalLayout();
        carsToCheck.setMargin(false);

        // Today grid
        Grid<BookingPackageItemCar> todayCarGrid = new Grid<>(BookingPackageItemCar.class);
        todayCarGrid.setCaption("Cars to check today");
        todayCarGrid.setHeightByRows(4);
        // todayCarGrid.setColumns("model", "plate", "remarks");
        // select car from carRepo where ids of a car and a booking match
        // and booking contains packageItemCar match and date in packageItemCar is today
        todayCarGrid.setItems();
        //todo todayCarGrid.setItems();
     //   todayCarGrid.setItems();
        // todo set colums with single table mode

        // Tommorow grid
        Grid<BookingPackageItemCar> tomorrowCarGrid = new Grid<>(BookingPackageItemCar.class);
        tomorrowCarGrid.setCaption("Cars to check tomorrow");
        tomorrowCarGrid.setHeightByRows(4);
     //   tomorrowCarGrid.setColumns("model", "plate", "remarks");
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
