package com.katamlek.nringthymeleaf.frontend.grids;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Covers other price lists.
 */

@SpringComponent
@UIScope
public class PriceListOtherGridView extends VerticalLayout {

    public PriceListOtherGridView() {

        Label labelEvents = new Label("Others Pricing");
        labelEvents.addStyleNames(ValoTheme.LABEL_NO_MARGIN, ValoTheme.LABEL_BOLD);

        Label info = new Label("Here you'll see the table similar to the car prices table.");
        info.addStyleNames(ValoTheme.LABEL_COLORED);

        addComponents(labelEvents, info);

        setMargin(false);
    }
}
