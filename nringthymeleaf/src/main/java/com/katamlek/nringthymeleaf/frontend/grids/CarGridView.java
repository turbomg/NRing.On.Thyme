package com.katamlek.nringthymeleaf.frontend.grids;

import com.katamlek.nringthymeleaf.domain.*;
import com.katamlek.nringthymeleaf.frontend.forms.CarForm;
import com.katamlek.nringthymeleaf.frontend.navigation.NavigationManager;
import com.katamlek.nringthymeleaf.repositories.CarRepository;
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
import java.util.Arrays;
import java.util.List;

@SpringView
@UIScope
public class CarGridView extends VerticalLayout implements View {
    private GridCellFilter filter;
    private final CarRepository carRepository;
    private final LocationDefinitionRepository locationDefinitionRepository;
    private NavigationManager navigationManager;
    private Grid<Car> carGrid;

    public CarGridView(CarRepository carRepository, LocationDefinitionRepository locationDefinitionRepository, NavigationManager navigationManager) {
        this.carRepository = carRepository;
        this.locationDefinitionRepository = locationDefinitionRepository;
        this.navigationManager = navigationManager;
        this.addComponent(buildCarGridView());
        setMargin(false);
    }

    // Build the grid with cars
    public Grid<Car> buildCarGrid() {
        // Setting items - full CarRepository
        carGrid = new Grid<>(Car.class);
        List<Car> carList = Lists.newArrayList(carRepository.findAll());
        carGrid.setItems(carList);

        // Setting visible colums according to specs
        carGrid.setColumnOrder("model", "plate", "carColor", "lastMileage", "firstBooking", "lastBooking", "mileageType", "carFleet", "nextTUV", "nextServiceAtKm", "currentLocation", "carStatus");

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
                navigationManager.navigateTo(CarForm.class, event.getItem().getId());
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
        // carGrid.getEditor().setEnabled(true);

        carGrid.addComponentColumn(this::deleteCarButton);

        carGrid.setSizeFull();

        return carGrid;
    }

    // Build buttons row: Add, Clear filters
    public HorizontalLayout buildCarButtons() {
        HorizontalLayout buttonsCarHL = new HorizontalLayout();

        Button addCarBtn = new Button("Add car"); // add new car
        addCarBtn.addClickListener(e -> Notification.show("Let me work on it!")); //todo
        //   addCarBtn.addClickListener(e -> navigationManager.navigateTo(CarForm.class));
        addCarBtn.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED);
        addCarBtn.setIcon(VaadinIcons.PLUS);

        Button clearAllFilters = new Button("Remove filters", e -> {
            filter.clearAllFilters();
        });
        clearAllFilters.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        clearAllFilters.setIcon(VaadinIcons.ERASER);

        buttonsCarHL.addComponents(addCarBtn, clearAllFilters);

        return buttonsCarHL;
    }

    // Put all together
    public VerticalLayout buildCarGridView() {
        Label label = new Label("Car List");
        label.addStyleNames(ValoTheme.LABEL_NO_MARGIN, ValoTheme.LABEL_LARGE);

        VerticalLayout carGridViewVL = new VerticalLayout();
        carGridViewVL.setMargin(false);
        carGridViewVL.addComponents(label, buildCarButtons(), buildCarGrid());
        return carGridViewVL;
    }

    // Helpers
    private Button deleteCarButton(Car car) {
        Button deleteCButton = new Button(VaadinIcons.MINUS_CIRCLE);
        deleteCButton.addStyleNames(ValoTheme.BUTTON_SMALL);
        deleteCButton.addClickListener(e -> {

            if (car.isUnderEditing()) {
                Notification.show("I can't delete this car now, as someone is working on it.");
            } else {

                //Confirmation popup
                Window window = new Window("Do you really want to delete this car?");

                //Popup contents
                VerticalLayout confirmationVL = new VerticalLayout();
                confirmationVL.addComponent(new Label("There's no undo option and your developer won't help you either."));

                // And buttons
                Button yesButton = new Button("Proceed");
                yesButton.addClickListener(event1 -> {
                    carRepository.delete(car);
                    carGrid.setItems(Lists.newArrayList(carRepository.findAll()));
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

            }
        });

        return deleteCButton;
    }
}
