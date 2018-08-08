package com.katamlek.nringthymeleaf.frontend.grids;

import com.katamlek.nringthymeleaf.domain.PriceListCar;
import com.katamlek.nringthymeleaf.frontend.forms.CarForm;
import com.katamlek.nringthymeleaf.frontend.navigation.NavigationManager;
import com.katamlek.nringthymeleaf.repositories.PriceListCarRepository;
import com.katamlek.nringthymeleaf.repositories.PriceListRepository;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.assertj.core.util.Lists;
import org.vaadin.gridutil.cell.GridCellFilter;

import java.util.List;

/**
 * Covers all three price lists: for cars, for events and for other stuff.
 */

@SpringComponent
@UIScope
public class PriceListCarGridView extends VerticalLayout {
    private PriceListCarRepository priceListCarRepository;
    private NavigationManager navigationManager;

    GridCellFilter filter;

    public PriceListCarGridView(PriceListRepository priceListRepository, NavigationManager navigationManager) {
        this.priceListCarRepository = priceListCarRepository;
        this.navigationManager = navigationManager;

        Label labelCars = new Label("Car Pricing");
        labelCars.addStyleNames(ValoTheme.LABEL_NO_MARGIN, ValoTheme.LABEL_BOLD);
        addComponents(labelCars, buildCarPriceListButtons(), buildCarsPricingList());

        setMargin(false);
    }

    // Grid
    public Grid buildCarsPricingList() {

        // Grid
        Grid<PriceListCar> priceListCarGrid = new Grid<>(PriceListCar.class);
//        List<PriceListCar> pricesList = Lists.newArrayList(priceListCarRepository.findAll());
//        priceListCarGrid.setItems(pricesList);
        //todo  FIX NPE    priceListCarGrid.setItems(Lists.newArrayList(priceListCarRepository.findAll()));

        priceListCarGrid.setColumns("car", "startPrice", "startKM", "per10KM", "instruction");
        //todo get the car id and name, get the pricing group as well

        priceListCarGrid.addComponentColumn(this::deleteCarItemButton);

        priceListCarGrid.setHeightByRows(4);

        priceListCarGrid.getColumns().forEach(column -> column.setSortable(true));
        priceListCarGrid.setColumnReorderingAllowed(true);
        priceListCarGrid.setColumnResizeMode(ColumnResizeMode.ANIMATED);

        // Set open car details for editing on double click
        priceListCarGrid.addItemClickListener(event -> {
            if (event.getMouseEventDetails().isDoubleClick()) {
                Notification.show("Not yet");
                // todo set navigator to the selected item
                // todo NOT A NAVIGATOR ! New carEditForm(Long id);
            }
        });

        // Filtering
        // todo define filters when all the columns are set
        // todo fix -- is not working and throwing NPE this.filter = new GridCellFilter(priceListCarGrid);

        Button addItemBtn = new Button("Add car"); // add new car
        addItemBtn.addClickListener(e -> navigationManager.navigateTo(CarForm.class));
        addItemBtn.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED, ValoTheme.BUTTON_TINY);
        addItemBtn.setIcon(VaadinIcons.PLUS);

        Button clearAllFilters = new Button("Remove filters", e -> {
            filter.clearAllFilters();
        });
        clearAllFilters.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_TINY);
        clearAllFilters.setIcon(VaadinIcons.ERASER);

        return priceListCarGrid;
    }

    public HorizontalLayout buildCarPriceListButtons() {
        HorizontalLayout buttonsCarPriceListHL = new HorizontalLayout();

        Button addCarPriceBtn = new Button("Add item"); // add new car
        addCarPriceBtn.addClickListener(e -> navigationManager.navigateTo(CarForm.class));
        addCarPriceBtn.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED);
        addCarPriceBtn.setIcon(VaadinIcons.PLUS);

        Button clearAllFilters = new Button("Remove filters", e -> {
            filter.clearAllFilters();
        });
        clearAllFilters.setStyleName(ValoTheme.BUTTON_BORDERLESS);
        clearAllFilters.setIcon(VaadinIcons.ERASER);

        buttonsCarPriceListHL.addComponents(addCarPriceBtn, clearAllFilters);

        return buttonsCarPriceListHL;
    }

    // Helpers
    private Button deleteCarItemButton(PriceListCar priceListCar) {
        Button deleteCIButton = new Button(VaadinIcons.MINUS_CIRCLE);
        deleteCIButton.addStyleNames(ValoTheme.BUTTON_SMALL);
        deleteCIButton.addClickListener(e -> {
            //todo carRepository.delete(););
        });
        return deleteCIButton;
    }
}
