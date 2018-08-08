package com.katamlek.nringthymeleaf.frontend.windows;

/*
Temporary form to be used until the price list is implemented in full
 */

import com.katamlek.nringthymeleaf.domain.BookingPackageItem;
import com.katamlek.nringthymeleaf.repositories.BookingPackageItemRepository;
import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
public class TempPackageItemWindow extends Window implements View {
    private BookingPackageItemRepository bookingPackageItemRepository;

    public TempPackageItemWindow(BookingPackageItemRepository bookingPackageItemRepository) {
        this.bookingPackageItemRepository = bookingPackageItemRepository;
        this.setContent(buildAddPackageItemWindow());
        this.setCaption("Package item");
        this.setClosable(true);
        this.setDraggable(true);
        this.center();
    }

    private DateField date;
    private DateField startTime;
    private TextField description;
    private TextField unitPrice;
    private TextField quantity;
    private TextField total;
    //    private TextField total;
    private ComboBox<Boolean> cancelled;

    private Button save;
    private Button cancel;

    Binder<BookingPackageItem> packageItemBinder = new Binder<>(BookingPackageItem.class);

    private VerticalLayout buildAddPackageItemWindow() {
        VerticalLayout packageItemWindow = new VerticalLayout();

        cancelled = new ComboBox<Boolean>("Item status");

        date = new DateField("Booked for");
        startTime = new DateField("Time");
        description = new TextField("Description");
        HorizontalLayout dateAndDescriptionHL = new HorizontalLayout(date, startTime, description);

        unitPrice = new TextField("Unit price");
        quantity = new TextField("Quantity");
        total = new TextField("Total");
        //todo calculate total - on the fly or in the db? ask Marcin
//        int uP = Integer.parseInt(unitPrice.getValue());
//        int q = Integer.parseInt(quantity.getValue());
//        int t = uP * q;
//        total.setValue(String.valueOf(t));

        HorizontalLayout pricesHL = new HorizontalLayout(unitPrice, quantity, total);

        // Bind fields
        // No relationship to car, even whatsoever at this stage
        // todo test if works - how to bind from window?
        // todo set relationship to booking
// todo        packageItemBinder.bindInstanceFields(this);

        // Save and cancel buttons
        save = new Button("Save note");
        save.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED, ValoTheme.BUTTON_SMALL);
        save.addClickListener(e -> {
            try {
                // Set the total when writing to database
                bookingPackageItemRepository.save(packageItemBinder.getBean());
            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                packageItemBinder.getBean().setUnderEditing(false);
                Notification.show("Your data was saved");
                this.close();
            }
        });

        cancel = new Button("Cancel");
        cancel.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_SMALL);
        cancel.addClickListener(e -> {
            this.close();
        });

        HorizontalLayout buttonsHL = new HorizontalLayout(save, cancel);

        packageItemWindow.addComponents(cancelled, dateAndDescriptionHL, pricesHL, buttonsHL);

        return packageItemWindow;
    }
}
