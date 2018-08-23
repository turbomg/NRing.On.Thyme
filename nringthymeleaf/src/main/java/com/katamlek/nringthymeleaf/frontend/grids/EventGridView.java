package com.katamlek.nringthymeleaf.frontend.grids;

import com.katamlek.nringthymeleaf.domain.EventType;
import com.katamlek.nringthymeleaf.domain.LocationDefinition;
import com.katamlek.nringthymeleaf.frontend.forms.EventForm;
import com.katamlek.nringthymeleaf.frontend.navigation.NavigationManager;
import com.katamlek.nringthymeleaf.frontend.views.WelcomeView;
import com.katamlek.nringthymeleaf.repositories.EventRepository;
import com.katamlek.nringthymeleaf.repositories.LocationDefinitionRepository;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.assertj.core.util.Lists;
import org.vaadin.gridutil.cell.CellFilterComponent;
import org.vaadin.gridutil.cell.GridCellFilter;

import java.text.SimpleDateFormat;

/**
 * Full list of events.
 */

@SpringView
@UIScope
public class EventGridView extends VerticalLayout implements View {

    private final EventRepository eventRepository;
    private LocationDefinitionRepository locationDefinitionRepository;
    private NavigationManager navigationManager;

    private GridCellFilter filter;
    private Grid<com.katamlek.nringthymeleaf.domain.Event> eventGrid;

    public EventGridView(EventRepository eventRepository, LocationDefinitionRepository locationDefinitionRepository, NavigationManager navigationManager) {
        this.eventRepository = eventRepository;
        this.locationDefinitionRepository = locationDefinitionRepository;
        this.navigationManager = navigationManager;
        addComponent(buildEventGridView());
        setMargin(false);
    }

    public Grid<com.katamlek.nringthymeleaf.domain.Event> buildEventGrid() {

        eventGrid = new Grid<>(com.katamlek.nringthymeleaf.domain.Event.class);
        eventGrid.setItems(Lists.newArrayList(eventRepository.findAll()));

        // Setting visible colums according to specs
        eventGrid.setColumns("eventName", "eventType", "eventTrack", "eventStartDateTime", "eventEndDateTime");

        eventGrid.addColumn(event -> {
            return "abc";
//            todo what to do in case of NPE String location = event.getEventLocation().getLocationName();
//            return location;
        }).setCaption("Location").setId("location");

        // Setting column order
        eventGrid.setColumnOrder("eventName", "eventType", "location", "eventTrack", "eventStartDateTime", "eventEndDateTime");

        // Set columns hideable, reordering etc.
        eventGrid.getColumns().forEach(column -> column.setSortable(true));
        eventGrid.setColumnReorderingAllowed(true);
        eventGrid.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        // Set open document details for editing on double click
        eventGrid.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                navigationManager.navigateTo(EventForm.class, event.getItem().getId());
            }
        });

        // Inline filtering
        this.filter = new GridCellFilter(eventGrid);
        this.filter.setTextFilter("eventName", true, true);
        CellFilterComponent<ComboBox<EventType>> eventTypeFilter = this.filter.setComboBoxFilter("eventType", EventType.class, Lists.newArrayList(EventType.values()));
        CellFilterComponent<ComboBox<LocationDefinition>> locationFilter = this.filter.setComboBoxFilter("location", LocationDefinition.class, Lists.newArrayList(locationDefinitionRepository.findAll()));

        this.filter.setTextFilter("eventTrack", true, true);

        this.filter.setDateFilter("eventStartDateTime", new SimpleDateFormat("HH:mm dd.MM.yyyy"), true);
        this.filter.setDateFilter("eventEndDateTime", new SimpleDateFormat("HH:mm dd.MM.yyyy"), true);
        // this.filter.setTextFilter("eventEndTime", true, true);
      //  this.filter.setTextFilter("eventEndDateTime", true, true);


        // Inline editor - turning off, way too dangerous
        // eventGrid.getEditor().setEnabled(true);

        // Extra button delete
        eventGrid.addComponentColumn(this::deleteEventButton);

        eventGrid.setSizeFull();

        return eventGrid;
    }

    // Build the buttons row: Add, Clear filters
    public HorizontalLayout buildEventButtons() {
        HorizontalLayout buttonsEventHL = new HorizontalLayout();

        Button addEventBtn = new Button("Add event"); // add new event
        addEventBtn.addClickListener(e -> navigationManager.navigateTo(EventForm.class));
        addEventBtn.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED);
        addEventBtn.setIcon(VaadinIcons.PLUS);

        Button backToDashboard = new Button("Back to dashboard");
        backToDashboard.addClickListener(e -> navigationManager.navigateTo(WelcomeView.class));
        backToDashboard.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED);
        backToDashboard.setIcon(VaadinIcons.DASHBOARD);

        Button clearAllFilters = new Button("Remove filters", e -> {
            filter.clearAllFilters();
        });
        clearAllFilters.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        clearAllFilters.setIcon(VaadinIcons.ERASER);

        buttonsEventHL.addComponents(addEventBtn, clearAllFilters, backToDashboard);

        return buttonsEventHL;
    }

    // Helpers
    private Button deleteEventButton(com.katamlek.nringthymeleaf.domain.Event event) {
        Button deleteEButton = new Button(VaadinIcons.MINUS_CIRCLE);
        deleteEButton.addStyleNames(ValoTheme.BUTTON_SMALL);
        deleteEButton.addClickListener(e -> {
            //Confirmation popup
            Window window = new Window("Do you really want to delete this event?");

            //Popup contents
            VerticalLayout confirmationVL = new VerticalLayout();
            confirmationVL.addComponent(new Label("There's no undo option and your developer won't help you either."));

            // And buttons
            Button yesButton = new Button("Proceed");
            yesButton.addClickListener(event1 -> {
                eventRepository.delete(event);
                eventGrid.setItems(Lists.newArrayList(eventRepository.findAll()));
                // todo clear filters ??? MATEUSZ? JONO?
                window.close();
            });

            Button noButton = new Button("Give the delete up");
            noButton.addClickListener(event2 -> {
                window.close();
            });

            HorizontalLayout buttonsLayout = new HorizontalLayout(yesButton, noButton);
            confirmationVL.addComponent(buttonsLayout);

            window.setContent(confirmationVL);

            window.center();
            UI.getCurrent().addWindow(window);

        });

        return deleteEButton;
    }

    // Put all together
    public VerticalLayout buildEventGridView() {
        Label label = new Label("Event List");
        label.addStyleNames(ValoTheme.LABEL_NO_MARGIN, ValoTheme.LABEL_LARGE);

        VerticalLayout eventGridViewVL = new VerticalLayout();
        eventGridViewVL.setMargin(false);
        eventGridViewVL.addComponents(label, buildEventButtons(), buildEventGrid());
        return eventGridViewVL;
    }
}
