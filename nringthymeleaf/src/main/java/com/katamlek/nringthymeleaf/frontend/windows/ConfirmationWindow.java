//package com.katamlek.nringthymeleaf.frontend.windows;
//
//import com.vaadin.spring.annotation.SpringComponent;
//import com.vaadin.ui.*;
//import com.vaadin.ui.themes.ValoTheme;
//
//@SpringComponent
//public class ConfirmationWindow extends Window {
//
//    public ConfirmationWindow() {
//        setCaption("Confirm the operation");
//        addStyleNames(ValoTheme.WINDOW_TOP_TOOLBAR);
//        setDraggable(true);
//        setClosable(true);
//        center();
//
//        setContent(buildConfirmationWindow());
//    }
//
//    public VerticalLayout buildConfirmationWindow() {
//
//        VerticalLayout areYouSure = new VerticalLayout(new Label("Are you sure? All the data will be lost."));
//        areYouSure.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
//
//        Button stopThis = new Button("Stay here");
//        stopThis.addStyleNames(ValoTheme.BUTTON_SMALL, ValoTheme.BUTTON_BORDERLESS);
//        stopThis.addClickListener(clickEvent -> this.close());
//
//        Button proceed = new Button("Proceed");
//        proceed.addStyleNames(ValoTheme.BUTTON_SMALL, ValoTheme.BUTTON_BORDERLESS);
//        proceed.addClickListener(clickEvent -> {
//            this.close(); //todo detach?
//            detach();
//        });
//
//        HorizontalLayout buttons = new HorizontalLayout(stopThis, proceed);
//        buttons.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
//        areYouSure.addComponents(buttons);
//
//        return areYouSure;
//    }
//
//}