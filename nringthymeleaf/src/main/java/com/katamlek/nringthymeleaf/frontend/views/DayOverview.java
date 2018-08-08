package com.katamlek.nringthymeleaf.frontend.views;

import com.katamlek.nringthymeleaf.domain.OfConcern;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Date;

/**
 * Displayed for any day picked from the calendar.
 */

@SpringView
@UIScope
public class DayOverview extends VerticalLayout implements View {

    //todo check if the Calendar offers any components and use them or build the view from scratch

    private Date overviewDate; // the day the overview is constructed for

    public DayOverview() {

        addComponent(buildDayOverview(overviewDate));
    }



    // Put the whole together
    public VerticalLayout buildDayOverview(Date date) {
        VerticalLayout overviewVL = new VerticalLayout();

        // Screen label
        Label overviewL = new Label("Day overview");
        overviewL.addStyleNames(ValoTheme.LABEL_NO_MARGIN, ValoTheme.LABEL_LARGE);
        Label dateL = new Label(String.valueOf(date));

        overviewVL.addComponents(overviewL, dateL);

        return overviewVL;
    }

}
