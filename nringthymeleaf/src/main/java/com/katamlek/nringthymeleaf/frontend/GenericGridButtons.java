package com.katamlek.nringthymeleaf.frontend;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

public class GenericGridButtons {

    public HorizontalLayout buildGenericButtons(String sourceClass) {
        HorizontalLayout genericButtonsHL = new HorizontalLayout();

        Button addItem = new Button("Add");
            addItem.addClickListener(e -> {
               switch (sourceClass) {
                   //todo when navigators are ready
                   //todo complete the list as you go
                   case "car" :
                       break;
                   case "event" :
                       break;
                   case "customer" :
                       break;
                   case "user" :
                       break;
                   case "booking" :
                       break;
                   case "payment" :
                       break;
               }
            });

        Button viewItem = new Button("View");
        //todo as above
        Button editItem = new Button("Edit");
        //todo as above
        Button deleteItem = new Button("Delete");
        //todo as above

        return genericButtonsHL;
    }
}
