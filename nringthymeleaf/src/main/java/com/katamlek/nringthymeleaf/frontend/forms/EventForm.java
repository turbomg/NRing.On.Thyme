package com.katamlek.nringthymeleaf.frontend.forms;

import com.katamlek.nringthymeleaf.domain.Event;
import com.katamlek.nringthymeleaf.domain.EventType;
import com.katamlek.nringthymeleaf.domain.LocationDefinition;
import com.katamlek.nringthymeleaf.domain.User;
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
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.assertj.core.util.Lists;

import java.time.ZoneId;
import java.util.Date;

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
    private Label eventDetailsL = new Label("Event details");
    RadioButtonGroup<EventType> eventTypeRBGroup;
    TextField eventNameTF;
    DateField eventDateDF;
    ComboBox<User> eventResponsibleUserCB;
    ComboBox<LocationDefinition> eventLocationCB;
    TextField eventTrackTF;
    TextField eventStartTimeTF;
    TextField eventEndTimeTF;
    CheckBox hideWhenNoBookings;
    CheckBox hideFromPublicCalendar;


    private Label eventInternalInfoL;
    private Label eventPublicPriceL;
    private Label eventPublicInfoL;

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

    public VerticalLayout buildInternalInfoSectionVL() {
        VerticalLayout internalInfoVL = new VerticalLayout();

        return internalInfoVL;
    }


    // Put it all together
    public VerticalLayout buildEventForm() {
        VerticalLayout eventForm = new VerticalLayout();
        // Details - label + body
        eventForm.addComponents(eventDetailsL, buildDetailsSectionVL());

        // Internal info - label + body
        //      eventForm.addComponents(eventInternalInfoL, buildInternalInfoSectionVL());


        // Pricing - label + body

        // Public info - label + body


        // Binder for Event class, binding event details
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
        Button cancel = new Button("Cancel");
        cancel.addClickListener(e -> navigationManager.navigateTo(EventGridView.class));
        Button save = new Button("Save");

        save.addClickListener(e -> {
            eventRepository.save(eventBinder.getBean());

        });

        //eventRepository.save(event)); -- ln 212 Order Edit Presenter

        buttonsHL.addComponents(cancel, save);

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
    public void enterView(Long id) {
        com.katamlek.nringthymeleaf.domain.Event event;
        if (id == null) {
            // New
            event = new com.katamlek.nringthymeleaf.domain.Event();
            event.setEventDate(new Date());
            event.setEventType(EventType.EVENT);
            // todo more setters
        } else {
            event = eventRepository.findById(id).get();
            if (event == null) {


                // view.showNotFound();
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
