package com.katamlek.nringthymeleaf.frontend.grids;

import com.katamlek.nringthymeleaf.domain.LocationDefinition;
import com.katamlek.nringthymeleaf.repositories.EventRepository;
import com.katamlek.nringthymeleaf.repositories.LocationDefinitionRepository;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import org.assertj.core.util.Lists;
import org.vaadin.gridutil.cell.CellFilterComponent;
import org.vaadin.gridutil.cell.GridCellFilter;

import java.text.SimpleDateFormat;

@SpringView
@UIScope
public class EventGridView extends VerticalLayout implements View {

    //todo finish this class

    private final EventRepository eventRepository;
    private LocationDefinitionRepository locationDefinitionRepository;

    private GridCellFilter filter;
//    final Grid<Event> eventGrid;

    public EventGridView(EventRepository eventRepository, LocationDefinitionRepository locationDefinitionRepository) {
        this.eventRepository = eventRepository;
        this.locationDefinitionRepository = locationDefinitionRepository;
        addComponent(buildEventGridView());
//        this.eventGrid = new Grid<>(Event.class);
    }

    public Grid<com.katamlek.nringthymeleaf.domain.Event> buildEventGrid() {

        Grid<com.katamlek.nringthymeleaf.domain.Event> eventGrid = new Grid<>(com.katamlek.nringthymeleaf.domain.Event.class);
        eventGrid.setItems(Lists.newArrayList(eventRepository.findAll()));

        // Setting visible colums according to specs
        eventGrid.getColumn("id").setHidden(true);
        eventGrid.getColumn("visibleWhenNoBookings").setHidden(true);
        eventGrid.getColumn("visibleInPublicCalendar").setHidden(true);
        eventGrid.getColumn("eventPublicPricing").setHidden(true);
        eventGrid.getColumn("eventResponsibleUser").setHidden(true);

        eventGrid.addColumn(event -> {
            String location = event.getEventLocation().getLocationName();
            return location;
        }).setCaption("Location").setId("location");

        // Setting column order
        eventGrid.setColumnOrder("eventName", "eventType", "location", "eventTrack", "eventDate", "eventStartTime", "eventEndTime");

        // Set columns hideable, reordering etc.
        eventGrid.getColumns().forEach(column -> column.setSortable(true));
        eventGrid.setColumnReorderingAllowed(true);
        eventGrid.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        // Set open document details for editing on double click
        eventGrid.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                UI.getCurrent().getNavigator().navigateTo("xxx");
                // todo set navigator to the selected car
                // todo NOT A NAVIGATOR ! New carEditForm(Long id);
            }
        });

        // Inline filtering
        this.filter = new GridCellFilter(eventGrid);
        this.filter.setTextFilter("eventName", true, true);
        this.filter.setTextFilter("eventType", true, true);
        CellFilterComponent<ComboBox<LocationDefinition>> locationFilter = this.filter.setComboBoxFilter("eventLocation", LocationDefinition.class, Lists.newArrayList(locationDefinitionRepository.findAll()));

        this.filter.setTextFilter("eventTrack", true, true);

        this.filter.setDateFilter("eventDate", new SimpleDateFormat("HH:mm:ss dd.MM.yyyy"), true);
       this.filter.setTextFilter("eventStartTime", true, true);
       this.filter.setTextFilter("eventEndTime", true, true);


        // Inline editor
        eventGrid.getEditor().setEnabled(true);

        // Extra columns: edit, delete, view documents
        eventGrid.addColumn(driver -> "Edit", new ButtonRenderer(clickEvent -> {
            //todo navigator
        }));

        eventGrid.addColumn(driver -> "Delete", new ButtonRenderer(clickEvent -> {
            //todo check if works, switch to id?
            eventRepository.delete((com.katamlek.nringthymeleaf.domain.Event) clickEvent.getItem());
        }));

        eventGrid.setSizeFull();

//        filter = new GridCellFilter(paymentGrid);

        return eventGrid;
    }

    // Build the buttons' row: Add, Clear filters
    public HorizontalLayout buildEventButtons() {
        HorizontalLayout buttonsEventHL = new HorizontalLayout();

        Button addEventBtn = new Button("Add an event"); // add new event
        addEventBtn.addClickListener(e -> UI.getCurrent().getNavigator().navigateTo("aaa"));
        //todo add navigator -- see line 86, not navigator, but new form with the given id

        Button clearAllFilters = new Button("Remove filters", e -> {
            filter.clearAllFilters();
        });

        buttonsEventHL.addComponents(addEventBtn, clearAllFilters);
        return buttonsEventHL;
    }

    // Put all together
    public VerticalLayout buildEventGridView() {
        VerticalLayout eventGridViewVL = new VerticalLayout();
        eventGridViewVL.addComponents(buildEventButtons(), buildEventGrid());
        return eventGridViewVL;
    }
}
