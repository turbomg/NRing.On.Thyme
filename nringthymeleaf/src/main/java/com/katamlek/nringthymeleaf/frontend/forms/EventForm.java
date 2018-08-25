package com.katamlek.nringthymeleaf.frontend.forms;

import com.katamlek.nringthymeleaf.domain.*;
import com.katamlek.nringthymeleaf.frontend.grids.EventGridView;
import com.katamlek.nringthymeleaf.frontend.navigation.NavigationManager;
import com.katamlek.nringthymeleaf.repositories.*;
import com.katamlek.nringthymeleaf.vaadinutils.CustomStringToBigDecimalConverter;
import com.katamlek.nringthymeleaf.vaadinutils.CustomStringToIntegerConverter;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.LocalDateTimeToDateConverter;
import com.vaadin.data.converter.LocalDateToDateConverter;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.assertj.core.util.Lists;
import org.vaadin.gridutil.renderer.BooleanRenderer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * Used to enter new events/ edit existing events.
 */

@SpringView
@UIScope
public class EventForm extends VerticalLayout implements View {
    private EventRepository eventRepository;
    private UserRepository userRepository;
    private PriceListRepository priceListRepository;
    private EventNoteRepository eventNoteRepository;
    private PriceListEventRepository priceListEventRepository;
    private PriceList priceList;
    private NoteRepository noteRepository;

    private LocationDefinitionRepository locationDefinitionRepository;
    private final NavigationManager navigationManager;

    public EventForm(EventRepository eventRepository, UserRepository userRepository, LocationDefinitionRepository locationDefinitionRepository, NavigationManager navigationManager, PriceListEventRepository priceListEventRepository, PriceListRepository priceListRepository, NoteRepository noteRepository, EventNoteRepository eventNoteRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.locationDefinitionRepository = locationDefinitionRepository;
        this.navigationManager = navigationManager;
        this.priceListEventRepository = priceListEventRepository;
        this.priceList = priceList;
        this.noteRepository = noteRepository;
        this.eventNoteRepository = eventNoteRepository;
        addComponent(eventWindowTitleL);
        eventWindowTitleL.addStyleNames(ValoTheme.LABEL_NO_MARGIN, ValoTheme.LABEL_LARGE);
        addComponent(buildEventForm());
        setMargin(false);
    }

    //Window title
    private Label eventWindowTitleL = new Label("Event Form");

    // Section labels + fields
    // Details
    //todo refactor field names -- are crap now
    private Label eventDetailsL = new Label("Event details");
    private RadioButtonGroup<EventType> eventTypeRBG;
    private TextField eventNameTF;
    private DateTimeField eventStartDateTimeDTF;
    private DateTimeField eventEndDateTimeDTF;
    private ComboBox<User> eventResponsibleUserCB;
    private ComboBox<LocationDefinition> eventLocationCB;
    private TextField eventTrackTF;
    private TextField eventStartTimeTF;
    private TextField eventEndTimeTF;
    private CheckBox hideWhenNoBookingsCB;
    private CheckBox hideFromPublicCalendarCB;

    // Internal information
    private Label eventInternalInfoL = new Label("Internal information");
    private TextField eventOrganizerTF;
    private TextField eventCostToRSRTF;
    private TextField eventSpacesBookedTF;
    //    private Grid<EventNote> eventInternalNoteG;
//    private Button addNewInternalInfoNoteBTN;
    private TextField eventCurrentEntriesPaidTF;
    private TextField eventCurrentEntriesUnpaidTF;
    private TextField eventCurrentValuePaidTF;
    private TextField eventCurrentValueUnpaidTF;

    // Public pricing
    private Label eventPublicPriceL = new Label("Public pricing");
    private TextField eventEntryFeeTF;
    private TextField eventAdditionalDriverFeeTF;

    // Public information
    private Label eventPublicInfoL = new Label("Public information");
    private TextField eventCarTypesTF;
    private TextField eventTyreTypesTF;
    private TextField eventNoiseLimitTF;
    private TextField eventTrackdayFormatTF;
    private TextField eventPitInUseTF;
    private Grid<EventNote> eventNoteG;
    private Button addNoteBTN;

    // Note form (visible/ not visible)
    private VerticalLayout eventNoteVL = buildEventNoteForm();
    private DateField noteDateDF;
    private TextField noteTF;
    private CheckBox internalCB;

    // The binders
    BeanValidationBinder<com.katamlek.nringthymeleaf.domain.Event> eventBinder = new BeanValidationBinder<>(com.katamlek.nringthymeleaf.domain.Event.class);

    // Event details
    public VerticalLayout buildDetailsSectionVL() {
        VerticalLayout eventDetailsVL = new VerticalLayout();

        eventTypeRBG = new RadioButtonGroup<>("Event type:", DataProvider.ofItems(EventType.values()));
        eventTypeRBG.setSelectedItem(EventType.EVENT);
        eventTypeRBG.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);

        eventNameTF = new TextField("Event name");
        eventStartDateTimeDTF = new DateTimeField("Event start date & time");
        eventEndDateTimeDTF = new DateTimeField("Event end date & time");
        eventResponsibleUserCB = new ComboBox<>("Responsible staff", Lists.newArrayList(userRepository.findAll()));
        eventResponsibleUserCB.setItemCaptionGenerator(e -> e.getName() + " " + e.getSurname());
        eventResponsibleUserCB.setEmptySelectionAllowed(false);

        eventLocationCB = new ComboBox<>("Location", Lists.newArrayList(locationDefinitionRepository.findAll()));
        eventLocationCB.setItemCaptionGenerator(locationDefinition -> locationDefinition.getLocationName());
        eventLocationCB.setEmptySelectionAllowed(false);

        eventTrackTF = new TextField("Track");
        //  eventStartTimeTF = new TextField("Start time");
        //  eventEndTimeTF = new TextField("End time");
        hideWhenNoBookingsCB = new CheckBox("Hide when no bookings");
        hideFromPublicCalendarCB = new CheckBox("Hide from public calendar");

        eventDetailsVL.addComponents(eventTypeRBG, new HorizontalLayout(eventStartDateTimeDTF, eventEndDateTimeDTF), new HorizontalLayout(eventNameTF, eventLocationCB, eventTrackTF, eventResponsibleUserCB), new HorizontalLayout(hideFromPublicCalendarCB, hideWhenNoBookingsCB));
        eventDetailsVL.setMargin(false);

        return eventDetailsVL;
    }

    // Event internal info
    public HorizontalLayout buildInternalInfoSectionVL() {
        HorizontalLayout internalInfoVL = new HorizontalLayout();

        VerticalLayout generalInfoVL = new VerticalLayout();
        eventOrganizerTF = new TextField("Organizer");
        eventCostToRSRTF = new TextField("Cost to RSR");
        eventSpacesBookedTF = new TextField("Spaces booked");

        // I am commenting this out deliberately, as there's no need to keep 2 grids - the flag will do the job
//        eventInternalNoteG = new Grid<>(EventNote.class);
//
//        eventInternalNoteG.setColumns("enteredOn", "user", "text", "noteStatus");
//        eventInternalNoteG.setColumnOrder("enteredOn", "user", "text", "noteStatus");
//
//        eventInternalNoteG.getColumns().forEach(column -> column.setSortable(true));
//        eventInternalNoteG.setColumnReorderingAllowed(true);
//        eventInternalNoteG.setColumnResizeMode(ColumnResizeMode.ANIMATED);
//
//        addNewInternalInfoNoteBTN = new Button("Add a note");
//        //todo remeber to set isInternal to true
//        addNewInternalInfoNoteBTN.addClickListener(e -> Notification.show("I can't do this for you yet"));

        generalInfoVL.addComponents(eventOrganizerTF, eventCostToRSRTF, eventSpacesBookedTF);
        generalInfoVL.setMargin(false);

        VerticalLayout profitVL = new VerticalLayout();


        //todo set the values in the fields
        eventCurrentEntriesPaidTF = new TextField("Current entries paid:");
        eventCurrentEntriesPaidTF.setEnabled(false);
        eventCurrentEntriesUnpaidTF = new TextField("Current unpaid entries:");
        eventCurrentEntriesUnpaidTF.setEnabled(false);
        eventCurrentValuePaidTF = new TextField("Current amount paid:");
        eventCurrentValuePaidTF.setEnabled(false);
        eventCurrentValueUnpaidTF = new TextField("Outstanding amount:");
        eventCurrentValueUnpaidTF.setEnabled(false);

        VerticalLayout numberOfEntries = new VerticalLayout(eventCurrentEntriesPaidTF, eventCurrentEntriesUnpaidTF);
        VerticalLayout valueOfentries = new VerticalLayout(eventCurrentValuePaidTF, eventCurrentValueUnpaidTF);

        internalInfoVL.addComponents(generalInfoVL, numberOfEntries, valueOfentries);
        internalInfoVL.setMargin(false);

        return internalInfoVL;
    }

    // Event pricing
    public HorizontalLayout buildEventPricingSectionHL() {
        HorizontalLayout publicPricingHL = new HorizontalLayout();

        eventEntryFeeTF = new TextField("Entry fee");
        eventAdditionalDriverFeeTF = new TextField("Additional driver");


        publicPricingHL.addComponents(eventEntryFeeTF, eventAdditionalDriverFeeTF);
        publicPricingHL.setMargin(false);

        return publicPricingHL;
    }

    // Event public info
    public VerticalLayout buildPublicInfoSectionVL() {

        VerticalLayout publicInfoVL = new VerticalLayout();

        eventCarTypesTF = new TextField("Car types");
        eventTyreTypesTF = new TextField("Tyre types");
        eventNoiseLimitTF = new TextField("Noise limit");
        eventTrackdayFormatTF = new TextField("Trackday format");
        eventPitInUseTF = new TextField("Pitlane in use");

        HorizontalLayout fields = new HorizontalLayout(eventCarTypesTF, eventTyreTypesTF, eventNoiseLimitTF, eventTrackdayFormatTF, eventPitInUseTF);

        eventNoteG = new Grid<>(EventNote.class);
        eventNoteG.setCaption("Event notes");
        eventNoteG.setHeightByRows(4);

        eventNoteG.addColumn(EventNote::isInternal, new BooleanRenderer<>()).setCaption("Internal");

        eventNoteG.addColumn(eventNote -> {
            SimpleDateFormat sfd = new SimpleDateFormat("dd.MM.yyyy");
            Date dateNoZeroes = null;
            try {
                dateNoZeroes = sfd.parse(sfd.format((eventNote.getEnteredOn())));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return dateNoZeroes;
        }).setId("entered").setCaption("Entered");

        eventNoteG.setColumns("entered", "user", "text", "noteStatus");
        eventNoteG.setColumnOrder("entered", "user", "text", "noteStatus");

        eventNoteG.addComponentColumn(this::deleteEventNote);


//        eventNoteG.addColumn(eventNote -> {
//            String internalColumn;
//            if (eventNote.isInternal()) {
//                internalColumn = "yes";
//            } else internalColumn = "no";
//            return internalColumn;
//        });
        eventNoteG.setItems(eventNoteRepository.findByEvent(eventBinder.getBean()));

        eventNoteG.getColumns().forEach(column -> column.setSortable(true));
        eventNoteG.setColumnReorderingAllowed(true);
        eventNoteG.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        addNoteBTN = new Button("Add a note");
        addNoteBTN.setWidth("300px");
        addNoteBTN.addClickListener(e -> {
            eventNoteVL.setVisible(true);
            addNoteBTN.setVisible(false);
        });

        publicInfoVL.addComponents(fields, eventNoteG, addNoteBTN);
        publicInfoVL.setMargin(false);

        return publicInfoVL;
    }

    // Put it all together
    public VerticalLayout buildEventForm() {
        VerticalLayout eventForm = new VerticalLayout();
        eventForm.setMargin(false);

        eventForm.addComponents(eventDetailsL, buildDetailsSectionVL());
        eventForm.addComponents(eventInternalInfoL, buildInternalInfoSectionVL());
        eventForm.addComponents(eventPublicPriceL, buildEventPricingSectionHL());
        eventForm.addComponents(eventPublicInfoL, buildPublicInfoSectionVL());
        eventForm.addComponents(eventNoteVL);

        // todo validation binding when RSR tells us how to do it

        // Bind details
        eventBinder.forField(eventNameTF)
                .asRequired()
                .withValidator(new StringLengthValidator("Enter the full event name!", 3, 1000))
                .bind(com.katamlek.nringthymeleaf.domain.Event::getEventName, com.katamlek.nringthymeleaf.domain.Event::setEventName);

        eventBinder.forField(eventStartDateTimeDTF)
                .asRequired("Required")
                .withConverter(new LocalDateTimeToDateConverter(ZoneOffset.systemDefault())).bind(com.katamlek.nringthymeleaf.domain.Event::getEventStartDateTime, com.katamlek.nringthymeleaf.domain.Event::setEventStartDateTime);

        eventBinder.forField(eventEndDateTimeDTF)
                .asRequired("Set the end date.")
                .withConverter(new LocalDateTimeToDateConverter(ZoneOffset.systemDefault())).bind(com.katamlek.nringthymeleaf.domain.Event::getEventStartDateTime, com.katamlek.nringthymeleaf.domain.Event::setEventStartDateTime);

        eventBinder.bind(eventResponsibleUserCB, "eventResponsibleUser");
        eventBinder.bind(eventLocationCB, "eventLocation");
        eventBinder.bind(eventTrackTF, "eventTrack");
//        eventBinder.bind(eventStartDateTimeDTF, "eventStartDateTime");
//        eventBinder.bind(eventEndDateTimeDTF, "eventEndDateTime");
        eventBinder.bind(hideWhenNoBookingsCB, "visibleWhenNoBookings");
        eventBinder.bind(hideFromPublicCalendarCB, "visibleInPublicCalendar");

        // Bind internal info
        eventBinder.bind(eventOrganizerTF, "eventOrganizer");

        eventBinder.forField(eventCostToRSRTF).withConverter(new CustomStringToBigDecimalConverter("Enter the number please!")).bind(com.katamlek.nringthymeleaf.domain.Event::getEventCostToRSR, com.katamlek.nringthymeleaf.domain.Event::setEventCostToRSR);

        eventBinder.forField(eventSpacesBookedTF).withConverter(new CustomStringToIntegerConverter("Enter the number please!")).bind(com.katamlek.nringthymeleaf.domain.Event::getSpacesBooked, com.katamlek.nringthymeleaf.domain.Event::setSpacesBooked);

        // Pricing
        eventBinder.forField(eventEntryFeeTF).withConverter(new CustomStringToBigDecimalConverter("Enter the number please!")).bind(com.katamlek.nringthymeleaf.domain.Event::getEventEntryFee, com.katamlek.nringthymeleaf.domain.Event::setEventEntryFee);

        eventBinder.forField(eventAdditionalDriverFeeTF).withConverter(new CustomStringToBigDecimalConverter("Enter the number please!")).bind(com.katamlek.nringthymeleaf.domain.Event::getEventAdditionalDriverFee, com.katamlek.nringthymeleaf.domain.Event::setEventAdditionalDriverFee);

        // Public information
        eventBinder.bind(eventCarTypesTF, "eventCarTypes");
        eventBinder.bind(eventTyreTypesTF, "eventTyreTypes");
        eventBinder.bind(eventNoiseLimitTF, "eventNoiseLimit");
        eventBinder.bind(eventTrackdayFormatTF, "eventTrackdayFormat");
        eventBinder.bind(eventPitInUseTF, "eventPitlaneInUse");

        // Buttons
        HorizontalLayout buttonsHL = new HorizontalLayout();
        Button cancelBTN = new Button("Cancel");
        cancelBTN.setWidth("150px");
        cancelBTN.setDescription("Be careful, your data may evaporate!");
        cancelBTN.addClickListener(e -> {
            //Confirmation popup
            Window window = new Window("Do you really want to drop the changes?");

            //Popup contents
            VerticalLayout confirmationVL = new VerticalLayout();
            confirmationVL.addComponent(new Label("There's no undo option and your developer won't help you either."));

            // And buttons
            Button yesButton = new Button("Drop the form and take me back");
            yesButton.addClickListener(event1 -> {
                window.close();
                navigationManager.navigateTo(EventGridView.class);
            });

            Button noButton = new Button("Let's keep on working");
            noButton.addClickListener(event2 -> {
                window.close();
            });

            HorizontalLayout buttonsLayout = new HorizontalLayout(yesButton, noButton);
            confirmationVL.addComponent(buttonsLayout);

            window.setContent(confirmationVL);

            window.center();
            UI.getCurrent().addWindow(window);

        });

        Button saveEventBTN = new Button("Save");
        saveEventBTN.setWidth("150px");
        saveEventBTN.addStyleName(ValoTheme.BUTTON_PRIMARY);
        saveEventBTN.addClickListener(e -> {
            try {
                // todo underEditing  false shall be set here, before save
                eventRepository.save(eventBinder.getBean());
            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                eventBinder.getBean().setUnderEditing(false);

                Notification.show("I saved your data.");
            }
        });

        Button toListBTN = new Button("Back to list");
        toListBTN.setWidth("150px");
   //     toListBTN.addStyleNames(ValoTheme.BUTTON_BORDERLESS);
   //     toListBTN.setIcon(VaadinIcons.ARROW_LEFT);
        toListBTN.addClickListener(e -> navigationManager.navigateTo(EventGridView.class));

        buttonsHL.addComponents(saveEventBTN, cancelBTN, toListBTN);

        addComponent(buttonsHL);

//        eventNoteG.setItems(eventNoteRepository.findByEvent(eventBinder.getBean()));

        return eventForm;

    }

    // Form open processing (new / edit)
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        String eventId = event.getParameters();
        if ("".equals(eventId)) {
            enterView(null);
        } else {
            enterView(Long.valueOf(eventId));
        }
    }

    // Called when user enters view from the list or adding a new event
    public void enterView(Long id) {
        com.katamlek.nringthymeleaf.domain.Event event;
        if (id == null) {
            // New
            event = new com.katamlek.nringthymeleaf.domain.Event();

         //   event.setEventName("");
            event.setVisibleInPublicCalendar(true); // will not be visible
            event.setVisibleWhenNoBookings(false);
            event.setEventStartDateTime(new Date());
            event.setEventEndDateTime(new Date());
            event.setEventType(EventType.TOURISTENFAHRTEN);
            event.setUnderEditing(true);
            // todo more setters
        } else {
            event = eventRepository.findById(id).get();
            if (event.isUnderEditing()) {
                Notification.show("Someone is editing this one now. Come back later.");
            } else {
                event.setUnderEditing(true);
                eventNoteG.setItems(eventNoteRepository.findByEvent(event));
                if (event == null) {
                    showNotFound();
                    return;
                }
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

    // Event note form
    public VerticalLayout buildEventNoteForm() {
        VerticalLayout eventNoteVL = new VerticalLayout();
        eventNoteVL.setCaption("Enter the note");

        /**
         * I do not allow to edit notes deliberately - too much a hassle, no real need to do it.
         * If needed, I can get back to this form and implement editing.
         */

        // todo null newEventNote as adding another still works on the same instance
//        moved down EventNote newEventNote = new EventNote();

        // Fields
        noteDateDF = new DateField("Note date");
        noteTF = new TextField("Text");
        internalCB = new CheckBox("This note is internal");

        HorizontalLayout fieldsHL = new HorizontalLayout(noteDateDF, noteTF, internalCB);

        // Separate binder
        Binder<EventNote> eventNoteBinder = new Binder<>(EventNote.class);
        eventNoteBinder.forField(noteDateDF).withConverter(new LocalDateToDateConverter()).bind(EventNote::getEnteredOn, EventNote::setEnteredOn);
        eventNoteBinder.bind(noteTF, "text");
        eventNoteBinder.bind(internalCB, "internal");

        EventNote newEventNote = new EventNote();
        eventNoteBinder.setBean(newEventNote);

        // Buttons
        Button cancelNote = new Button("Cancel");
        cancelNote.setDescription("Your data will be lost!");
        cancelNote.addClickListener(e -> {
            this.eventNoteVL.setVisible(false);
            this.addNoteBTN.setVisible(true);
            noteDateDF.clear();
            noteTF.clear();
            internalCB.clear();
        });

        Button saveNote = new Button("Save");
        saveNote.addClickListener(e -> {
            eventBinder.getBean().addEventNote(eventNoteBinder.getBean());

            this.eventNoteVL.setVisible(false);
            this.addNoteBTN.setVisible(true);
            eventRepository.save(eventBinder.getBean());
            noteDateDF.clear();
            noteTF.clear();
            internalCB.clear();
            eventNoteG.setItems(eventNoteRepository.findByEvent(eventBinder.getBean()));
        });

        HorizontalLayout documentButtonsHL = new HorizontalLayout(cancelNote, saveNote);

        eventNoteVL.addComponents(fieldsHL, documentButtonsHL);

        eventNoteVL.setVisible(false);
        return eventNoteVL;
    }

    // Helpers
    private Button deleteEventNote(EventNote eventNote) {
        Button deleteENutton = new Button(VaadinIcons.MINUS_CIRCLE);
        deleteENutton.setCaption("You're about to delete something! Proceed with caution!");
        deleteENutton.addStyleNames(ValoTheme.BUTTON_SMALL);
        deleteENutton.addClickListener(e -> {
            eventNoteRepository.delete(eventNote);
        });
        return deleteENutton;
    }
}