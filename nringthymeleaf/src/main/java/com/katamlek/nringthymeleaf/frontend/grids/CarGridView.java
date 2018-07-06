package com.katamlek.nringthymeleaf.frontend.grids;

import com.katamlek.nringthymeleaf.domain.*;
import com.katamlek.nringthymeleaf.repositories.CarRepository;
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
import java.util.Arrays;
import java.util.List;

@SpringView
@UIScope
public class CarGridView extends VerticalLayout implements View {
    GridCellFilter filter;
    private final CarRepository carRepository;
    private final LocationDefinitionRepository locationDefinitionRepository;

    public CarGridView(CarRepository carRepository, LocationDefinitionRepository locationDefinitionRepository) {
        this.carRepository = carRepository;
        this.locationDefinitionRepository = locationDefinitionRepository;
        this.addComponent(buildCarGridView());
    }

    // Build the grid with cars
    public Grid<Car> buildCarGrid() {
        // Setting items - full CarRepository
        Grid<Car> carGrid = new Grid<>(Car.class);
        List<Car> carList = Lists.newArrayList(carRepository.findAll());
        carGrid.setItems(carList);

        // Setting visible colums according to specs
        carGrid.getColumn("id").setHidden(true);
        carGrid.getColumn("carNotes").setHidden(true);
        carGrid.getColumn("carHistory").setHidden(true);
        carGrid.getColumn("carPricing").setHidden(true);

        // Setting column order
        carGrid.setColumnOrder("model", "plate", "carColor", "lastMileage", "firstBooking", "lastBooking", "mileageType", "carFleet", "nextTUV", "nextServiceAtKm", "currentLocation", "carStatus");

        //todo add renderer of carColors when decided what colors are at disposal

        // Set columns hideable, reordering etc.
        carGrid.getColumns().forEach(column -> column.setSortable(true));
        carGrid.setColumnReorderingAllowed(true);
        carGrid.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        // Set open car details for editing on double click
        carGrid.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                UI.getCurrent().getNavigator().navigateTo("xxx");
                // todo set navigator to the selected car
                // todo NOT A NAVIGATOR ! New carEditForm(Long id);
            }
        });

        // Inline filtering - without Color as it's an icon
        this.filter = new GridCellFilter(carGrid);
        this.filter.setTextFilter("model", true, true);
        this.filter.setTextFilter("plate", true, true);

        CellFilterComponent<ComboBox<CarColor>> colorFilter = this.filter.setComboBoxFilter("carColor", CarColor.class, Arrays.asList(CarColor.values()));

        this.filter.setNumberFilter("lastMileage", Long.class);

        this.filter.setDateFilter("firstBooking", new SimpleDateFormat("HH:mm:ss dd.MM.yyyy"), true);
        this.filter.setDateFilter("lastBooking", new SimpleDateFormat("HH:mm:ss dd.MM.yyyy"), true);

        CellFilterComponent<ComboBox<MileageType>> mileageTypeFilter = this.filter.setComboBoxFilter("mileageType", MileageType.class, Arrays.asList(MileageType.values()));

        CellFilterComponent<ComboBox<CarFleet>> fleetFilter = this.filter.setComboBoxFilter("carFleet", CarFleet.class, Arrays.asList(CarFleet.values()));

        this.filter.setDateFilter("nextTUV", new SimpleDateFormat("HH:mm:ss dd.MM.yyyy"), true);

        this.filter.setNumberFilter("nextServiceAtKm", Long.class);

        CellFilterComponent<ComboBox<LocationDefinition>> locationFilter = this.filter.setComboBoxFilter("currentLocation", LocationDefinition.class, Lists.newArrayList(locationDefinitionRepository.findAll()));

        CellFilterComponent<ComboBox<CarStatus>> statusFilter = this.filter.setComboBoxFilter("carStatus", CarStatus.class, Arrays.asList(CarStatus.values()));

        this.filter.setBooleanFilter("currentlyInUse");

//todo change all Time types to Date to preserve consistency

        // Inline editor
        carGrid.getEditor().setEnabled(true);

        // Extra columns: edit, delete
        carGrid.addColumn(car -> "Edit", new ButtonRenderer(clickEvent -> {
            //todo navigator
        }));

        carGrid.addColumn(car -> "Delete", new ButtonRenderer(clickEvent -> {
            carList.remove(clickEvent.getItem());
            //todo check if works, switch to id?
            carRepository.delete((Car) clickEvent.getItem());
            carGrid.setItems(carList);
        }));

        carGrid.setSizeFull();

        return carGrid;
    }

    // Build the buttons' row: Add, Clear filters
    public HorizontalLayout buildCarButtons() {
        HorizontalLayout buttonsCarHL = new HorizontalLayout();

        Button addCarBtn = new Button("Add a car"); // add new car
        addCarBtn.addClickListener(e -> UI.getCurrent().getNavigator().navigateTo("aaa"));
        //todo add navigator -- see line 86, not navigator, but new form with the given id

        Button clearAllFilters = new Button("Remove filters", e -> {
            filter.clearAllFilters();
        });

        buttonsCarHL.addComponents(addCarBtn, clearAllFilters);
        return buttonsCarHL;
    }

    // Put all together
    public VerticalLayout buildCarGridView() {
        VerticalLayout carGridViewVL = new VerticalLayout();
        carGridViewVL.addComponents(buildCarButtons(), buildCarGrid());
        return carGridViewVL;
    }
}
