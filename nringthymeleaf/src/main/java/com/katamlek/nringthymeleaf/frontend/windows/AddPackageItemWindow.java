//package com.katamlek.nringthymeleaf.frontend.windows;
//
//import com.katamlek.nringthymeleaf.domain.*;
//import com.katamlek.nringthymeleaf.repositories.BookingPackageItemRepository;
//import com.vaadin.data.Binder;
//import com.vaadin.icons.VaadinIcons;
//import com.vaadin.navigator.View;
//import com.vaadin.navigator.ViewChangeListener;
//import com.vaadin.spring.annotation.SpringComponent;
//import com.vaadin.ui.*;
//import com.vaadin.ui.themes.ValoTheme;
//
//import java.util.Date;
//
//@SpringComponent
//public class AddPackageItemWindow extends Window implements View {
//
//    private BookingPackageItemRepository bookingPackageItemRepository;
//    //  private ConfirmationWindow confirmationWindow;
//
//    public AddPackageItemWindow(BookingPackageItemRepository bookingPackageItemRepository) {
//        this.bookingPackageItemRepository = bookingPackageItemRepository;
//        //   this.confirmationWindow = confirmationWindow;
//        this.setContent(buildBookingPackageItemWindow());
//        this.setCaption("Package item");
//        this.setClosable(true);
//        this.setDraggable(true);
//        this.center();
//    }
//
//    private Button save;
//    private Button cancel;
//
//    Binder<BookingPackageItem> bookingPackageItemBinder = new Binder<>(BookingPackageItem.class);
//
//    private VerticalLayout buildBookingPackageItemWindow() {
//        VerticalLayout bookingNoteForm = new VerticalLayout();
//
//        // Adding items by category - dropdowns
////        ComboBox<com.katamlek.nringthymeleaf.domain.Event> eventComboBox = new ComboBox<>("Select event to add");
////        ComboBox<Car> carComboBox = new ComboBox<>("Select car to add");
////        ComboBox<OthersPricing> othersComboBox = new ComboBox<>("Select miscalenious to add");
////
////        eventComboBox.setScrollToSelectedItem(true);
////        carComboBox.setScrollToSelectedItem(true);
////        othersComboBox.setScrollToSelectedItem(true);
//
//        //todo when the user picks another category, remove previous selection - Vaadin I guess does it automatically
//        //todo discuss with Jono
//
////        eventComboBox.setItems();
////        carComboBox.setItems();
////        othersComboBox.setItems();
//
//
//    //    HorizontalLayout combosHL = new HorizontalLayout(eventComboBox, carComboBox, othersComboBox);
//
//        save = new Button("Save item");
//        save.addStyleNames(ValoTheme.BUTTON_BORDERLESS_COLORED, ValoTheme.BUTTON_SMALL);
//        save.addClickListener(e -> {
//            try {
//                bookingPackageItemRepository.save(bookingPackageItemBinder.getBean());
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            } finally {
//                bookingPackageItemBinder.getBean().setUnderEditing(false);
//                Notification.show("Your data was saved");
//                this.close();
//            }
//        });
//
//        cancel = new Button("Cancel");
//        cancel.addStyleNames(ValoTheme.BUTTON_BORDERLESS, ValoTheme.BUTTON_SMALL);
//        cancel.addClickListener(e -> {
//            this.close();
//        });
//
//        HorizontalLayout buttonsHL = new HorizontalLayout(save, cancel);
//
//        bookingNoteForm.addComponents(buttonsHL);
//
//        return bookingNoteForm;
//    }
//
//    // On enter
//    @Override
//    public void enter(ViewChangeListener.ViewChangeEvent event) {
//        String eventId = event.getParameters();
//        if ("".equals(eventId)) {
//            enterView(null);
//        } else {
//            enterView(Long.valueOf(eventId));
//        }
//    }
//
//    // Called when user enters note from the list or adding a new note
//    public void enterView(Long id) {
//        BookingPackageItem bookingItem;
//        if (id == null) {
//            // New
//            bookingItem = new BookingPackageItem();
//
//            bookingItem.setDate(new Date()); // todo the app doesn't know I entered the window. will probably need to extend Vertical here and call window with setContent in the BookingForm
//            bookingItem.setCancelled(false);
//
//            bookingItem.setUnderEditing(true);
//        } else {
//            bookingItem = bookingPackageItemRepository.findById(id).get();
//            if (bookingItem.isUnderEditing()) {
//                Notification.show("Someone is editing this item now. Please try again in a moment."); //todo and close the window?
//            } else {
//                bookingItem.setUnderEditing(true);
//                if (bookingItem == null) {
//                    Notification.show("I don't have an item like that.");
//                    return;
//                }
//            }
//        }
//        bookingPackageItemBinder.setBean(bookingItem);
//    }
//}
