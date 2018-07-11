package com.katamlek.nringthymeleaf.frontend.forms;

import com.katamlek.nringthymeleaf.domain.*;
import com.katamlek.nringthymeleaf.frontend.grids.EventGridView;
import com.katamlek.nringthymeleaf.frontend.navigation.NavigationManager;
import com.katamlek.nringthymeleaf.repositories.EventRepository;
import com.katamlek.nringthymeleaf.repositories.LocationDefinitionRepository;
import com.katamlek.nringthymeleaf.repositories.UserRepository;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.LocalDateToDateConverter;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.assertj.core.util.Lists;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@SpringView
@UIScope
public class EventForm extends VerticalLayout implements View {

    private EventRepository eventRepository;
    private UserRepository userRepository;
    private LocationDefinitionRepository locationDefinitionRepository;
    private final NavigationManager navigationManager;

    public EventForm(EventRepository eventRepository, UserRepository userRepository, LocationDefinitionRepository locationDefinitionRepository, NavigationManager navigationManager) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.locationDefinitionRepository = locationDefinitionRepository;
        this.navigationManager = navigationManager;
        addComponent(buildEventForm());
    }

    //Window title
    private Label eventWindowTitleL;

    // Section labels + fields
    // Details
    private Label eventDetailsL = new Label("Event details");
    private RadioButtonGroup<EventType> eventTypeRBGroup;
    private TextField eventNameTF;
    private DateField eventDateDF;
    private ComboBox<User> eventResponsibleUserCB;
    private ComboBox<LocationDefinition> eventLocationCB;
    private TextField eventTrackTF;
    private TextField eventStartTimeTF;
    private TextField eventEndTimeTF;
    private CheckBox hideWhenNoBookings;
    private CheckBox hideFromPublicCalendar;
    // Internal information
    private Label eventInternalInfoL = new Label("Internal information");
    private TextField eventOrganizer;
    private TextField eventCostToRSR;
    private TextField eventSpacesBooked;
    private Grid<EventInternalInfoNote> eventInternalInfoNoteGrid;
    private Button addNewInternalInfoNoteBTN;
    private Label eventCurrentEntriesPaid;
    private Label eventCurrentEntriesUnpaid;
    private Label eventCurrentValuePaid;
    private Label eventCurrentValueUnpaid;
    // Public pricing
    private Label eventPublicPriceL = new Label("Pubic pricing");
    private TextField eventEntryFee;
    private TextField eventAdditionalDriverFee;
    // Public information
    private Label eventPublicInfoL = new Label("Public information");
    private TextField eventCarTypes;
    private TextField eventTyreTypes;
    private TextField eventNoiseLimit;
    private TextField eventTrackdayFormat;
    private TextField eventPitInUse;
    private Grid<EventPublicInfoNote> eventPublicInfoNoteGrid;
    private Button addNewPublicInfoNoteBTN;

    Binder<com.katamlek.nringthymeleaf.domain.Event> eventBinder = new Binder<>();

    // Event details - bind to Event class

    public VerticalLayout buildDetailsSectionVL() {
        VerticalLayout eventDetailsVL = new VerticalLayout();

        eventTypeRBGroup = new RadioButtonGroup<>("", DataProvider.ofItems(EventType.values()));
        eventNameTF = new TextField("Event name");
        eventDateDF = new DateField("Event date");
        eventResponsibleUserCB = new ComboBox<>("Responsible staff", Lists.newArrayList(userRepository.findAll()));
        eventLocationCB = new ComboBox<>("Location", Lists.newArrayList(locationDefinitionRepository.findAll()));
        eventTrackTF = new TextField("Track");
        eventStartTimeTF = new TextField("Start time");
        eventEndTimeTF = new TextField("End time");
        hideWhenNoBookings = new CheckBox("Hide when no bookings");
        hideFromPublicCalendar = new CheckBox("Hide from public calendar");

        eventDetailsVL.addComponents(eventTypeRBGroup, new HorizontalLayout(eventNameTF, eventLocationCB, eventTrackTF), new HorizontalLayout(eventDateDF, eventStartTimeTF, eventEndTimeTF), new HorizontalLayout(eventResponsibleUserCB, hideFromPublicCalendar, hideWhenNoBookings));

        return eventDetailsVL;
    }

    // Event internal info - bind to EventInternalInfo class
    public HorizontalLayout buildInternalInfoSectionVL() {
        HorizontalLayout internalInfoVL = new HorizontalLayout();

        VerticalLayout generalInfoVL = new VerticalLayout();
        eventOrganizer = new TextField("Organizer");
        eventCostToRSR = new TextField("Cost to RSR");
        eventSpacesBooked = new TextField("Spaces booked");

        //todo fix the grid columns - are pretty nonsense 
        eventInternalInfoNoteGrid = new Grid<>(EventInternalInfoNote.class);
        eventInternalInfoNoteGrid.getColumn("id").setHidden(true);
//todo fix this!!! - hide this column        eventInternalInfoNoteGrid.getColumn("isHistoryNote").setHidden(true);
        eventInternalInfoNoteGrid.getColumns().forEach(column -> column.setSortable(true));
        eventInternalInfoNoteGrid.setColumnReorderingAllowed(true);
        eventInternalInfoNoteGrid.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        addNewInternalInfoNoteBTN = new Button("Add a note");
        //todo
        addNewInternalInfoNoteBTN.addClickListener(e -> Notification.show("I can't do this for you yet"));

        generalInfoVL.addComponents(eventOrganizer, eventCostToRSR, eventSpacesBooked, eventInternalInfoNoteGrid, addNewInternalInfoNoteBTN);

        VerticalLayout profitVL = new VerticalLayout();
        eventCurrentEntriesPaid = new Label("Current entries paid:");
        eventCurrentEntriesUnpaid = new Label("Current unpaid entries:");
        eventCurrentValuePaid = new Label("Current amount paid:");
        eventCurrentValueUnpaid = new Label("Outstanding amount:");

        // Query the database for the values
        //todo query the database
        Integer entriesPaid = 10;
        Integer entriesUnpaid = 2;
        Integer amountPaid = 1000;
        Integer amountOutstanding = 400;
        Label paidL = new Label(entriesPaid.toString());
        Label unpaidL = new Label(entriesUnpaid.toString());
        Label paidAmtL = new Label(amountPaid.toString());
        Label unpaidAmtL = new Label(amountOutstanding.toString());

        HorizontalLayout paidEntriesHL = new HorizontalLayout(eventCurrentEntriesPaid, paidL);
        HorizontalLayout unpaidEntriesHL = new HorizontalLayout(eventCurrentEntriesUnpaid, unpaidL);
        HorizontalLayout amtPaidHL = new HorizontalLayout(eventCurrentValuePaid, paidAmtL);
        HorizontalLayout amdUnpaidHL = new HorizontalLayout(eventCurrentValueUnpaid, unpaidAmtL);

        profitVL.addComponents(paidEntriesHL, unpaidEntriesHL, amtPaidHL, amdUnpaidHL);

        internalInfoVL.addComponents(generalInfoVL, profitVL);

        return internalInfoVL;
    }

    // Event public pricing - bind to EventPublicPricing class
    public VerticalLayout buildEventPublicPricingSectionVL() {
        VerticalLayout publicPricingVL = new VerticalLayout();

        publicPricingVL.addComponents();

        return publicPricingVL;
    }

    // Event public info - bind to EventPublicInfo class
    public VerticalLayout buildPublicInfoSectionVL() {
        VerticalLayout publicInfoVL = new VerticalLayout();

        publicInfoVL.addComponents();

        return publicInfoVL;
    }

    // Put it all together
    public VerticalLayout buildEventForm() {
        VerticalLayout eventForm = new VerticalLayout();
        // Details - label + body
        eventForm.addComponents(eventDetailsL, buildDetailsSectionVL());
        eventForm.addComponents(eventInternalInfoL, buildInternalInfoSectionVL());

        // Internal info - label + body
        //      eventForm.addComponents(eventInternalInfoL, buildInternalInfoSectionVL());


        // Pricing - label + body

        // Public info - label + body


        // Binder for event details
        eventBinder.forField(eventTypeRBGroup).bind(com.katamlek.nringthymeleaf.domain.Event::getEventType, com.katamlek.nringthymeleaf.domain.Event::setEventType);

        eventBinder.forField(eventNameTF).bind(com.katamlek.nringthymeleaf.domain.Event::getEventName, com.katamlek.nringthymeleaf.domain.Event::setEventName);


        // todo add validation

        eventBinder.forField(eventDateDF).withConverter(new LocalDateToDateConverter(ZoneId.systemDefault())).bind(com.katamlek.nringthymeleaf.domain.Event::getEventDate, com.katamlek.nringthymeleaf.domain.Event::setEventDate);

        eventBinder.forField(eventResponsibleUserCB).bind(com.katamlek.nringthymeleaf.domain.Event::getEventResponsibleUser, com.katamlek.nringthymeleaf.domain.Event::setEventResponsibleUser);

        eventBinder.forField(eventLocationCB).bind(com.katamlek.nringthymeleaf.domain.Event::getEventLocation, com.katamlek.nringthymeleaf.domain.Event::setEventLocation);

        eventBinder.forField(eventTrackTF).bind(com.katamlek.nringthymeleaf.domain.Event::getEventTrack, com.katamlek.nringthymeleaf.domain.Event::setEventTrack);

        eventBinder.forField(eventStartTimeTF).bind(com.katamlek.nringthymeleaf.domain.Event::getEventStartTime, com.katamlek.nringthymeleaf.domain.Event::setEventStartTime);
        eventBinder.forField(eventEndTimeTF).bind(com.katamlek.nringthymeleaf.domain.Event::getEventEndTime, com.katamlek.nringthymeleaf.domain.Event::setEventEndTime);

        eventBinder.forField(hideWhenNoBookings).bind(com.katamlek.nringthymeleaf.domain.Event::isVisibleWhenNoBookings, com.katamlek.nringthymeleaf.domain.Event::setVisibleWhenNoBookings);

        eventBinder.forField(hideFromPublicCalendar).bind(com.katamlek.nringthymeleaf.domain.Event::isVisibleInPublicCalendar, com.katamlek.nringthymeleaf.domain.Event::setVisibleInPublicCalendar);

        // Binding for event internal info


//todo do it on the eventBinder

        // Binding for public pricing


        // Binding for public information


        // Buttons
        HorizontalLayout buttonsHL = new HorizontalLayout();
        Button cancelBTN = new Button("Cancel");
        cancelBTN.addClickListener(e -> navigationManager.navigateTo(EventGridView.class));
        Button saveEventBTN = new Button("Save");

        saveEventBTN.addClickListener(e -> {
            eventBinder.getBean().setUnderEditing(false);
            eventRepository.save(eventBinder.getBean());

        });

        buttonsHL.addComponents(cancelBTN, saveEventBTN);

        addComponent(buttonsHL);

        return eventForm;

    }


    // Form open processing (new / edit)
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        String orderId = event.getParameters();
        if ("".equals(orderId)) {
            enterView(null);
        } else {
            enterView(Long.valueOf(orderId));
        }
    }

    // Called when user enters view from the list or adding a new event
    //todo set underEditing flag: true - the form won't open, false - let's go;; after save() set it to false;; update the domain
    public void enterView(Long id) {
        com.katamlek.nringthymeleaf.domain.Event event;
        if (id == null) {
            // New
            event = new com.katamlek.nringthymeleaf.domain.Event();
            event.setEventDate(new Date());
            event.setEventType(EventType.EVENT);
            event.setUnderEditing(true);
            // todo more setters
        } else {
            //todo check if not under editing -- guess need to save the flag in the DB ?
            event = eventRepository.findById(id).get();
            event.setUnderEditing(true);
            if (event == null) {
                showNotFound();
                return;
            }
        }
        eventBinder.setBean(event);
        eventNameTF.focus();

    }

    // Won't hopefully happen
    public void showNotFound() {
        removeAllComponents();
        addComponent(new Label("Order not found"));
    }

}
