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
import org.vaadin.gridutil.cell.GridCellFilter;

/**
 * Covers event price lists.
 */

@SpringComponent
@UIScope
public class PriceListEventGridView extends VerticalLayout {

    public PriceListEventGridView() {

        Label labelEvents = new Label("Event Pricing");
        labelEvents.addStyleNames(ValoTheme.LABEL_NO_MARGIN, ValoTheme.LABEL_BOLD);

        Label info = new Label("Here you'll see the table similar to the car prices table.");
        info.addStyleNames(ValoTheme.LABEL_COLORED);

        addComponents(labelEvents, info);

        setMargin(false);
    }
}
