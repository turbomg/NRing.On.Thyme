package com.katamlek.nringthymeleaf.frontend.views;

import com.katamlek.nringthymeleaf.frontend.grids.PriceListCarGridView;
import com.katamlek.nringthymeleaf.frontend.grids.PriceListEventGridView;
import com.katamlek.nringthymeleaf.frontend.grids.PriceListOtherGridView;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Covers all three price list types: car, event and others.
 * Consists of separate tables for each of the above categories.
 */

@SpringView
@UIScope
public class PriceListView extends VerticalLayout implements View {
    private PriceListCarGridView priceListCarGridView;
    private PriceListEventGridView priceListEventGridView;
    private PriceListOtherGridView priceListOtherGridView;

    public PriceListView(PriceListCarGridView priceListCarGridView,
                         PriceListEventGridView priceListEventGridView,
                         PriceListOtherGridView priceListOtherGridView) {
        this.priceListCarGridView = priceListCarGridView;
        this.priceListEventGridView = priceListEventGridView;
        this.priceListOtherGridView = priceListOtherGridView;

        Label label = new Label("Price Lists by Category");
        label.addStyleNames(ValoTheme.LABEL_NO_MARGIN, ValoTheme.LABEL_LARGE);

        Label warning = new Label("Please treat this screen as a mockup!");
        warning.addStyleNames(ValoTheme.LABEL_COLORED);

        addComponents(label, warning, priceListCarGridView, priceListEventGridView, priceListOtherGridView);

        setMargin(false);
    }
}
