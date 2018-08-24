package com.katamlek.nringthymeleaf.frontend.frontutils;

import com.katamlek.nringthymeleaf.domain.Booking;
import com.katamlek.nringthymeleaf.domain.TemporaryPackageItem;
import com.katamlek.nringthymeleaf.frontend.forms.BookingForm;
import com.katamlek.nringthymeleaf.frontend.navigation.NavigationManager;
import com.katamlek.nringthymeleaf.repositories.BookingRepository;
import com.katamlek.nringthymeleaf.repositories.TemporaryPackageItemRepository;
import com.vaadin.spring.annotation.SpringComponent;

import java.util.ArrayList;
import java.util.List;

@SpringComponent
public class BookingDuplicator {

    private BookingRepository bookingRepository;
    private TemporaryPackageItemRepository temporaryPackageItemRepository;
    private NavigationManager navigationManager;

    public BookingDuplicator(BookingRepository bookingRepository, TemporaryPackageItemRepository temporaryPackageItemRepository) {
        this.bookingRepository = bookingRepository;
        this.temporaryPackageItemRepository = temporaryPackageItemRepository;
    }

    /**
     * Copies the data from original booking to the copy.
     * @param originalBooking, the booking to copy
     */
    public void duplicateBooking(Booking originalBooking) {
        // This is the new booking I'll return
        Booking newBooking = new Booking();

        // Set the header data
        newBooking.setCreatedBy(originalBooking.getCreatedBy());

        // Save header
        bookingRepository.save(newBooking);

        // Find all package items of the original booking
        List<TemporaryPackageItem> originalPackageItems = new ArrayList<>();
        originalPackageItems.addAll(temporaryPackageItemRepository.findByBooking(originalBooking));

        for (TemporaryPackageItem originalItem : originalPackageItems) {
            // create the new item
            TemporaryPackageItem newItem = new TemporaryPackageItem();
            // set the data of the new item
            newItem.setEvent(originalItem.getEvent());
            newItem.setCar(originalItem.getCar());
            // add new item to booking
            newBooking.addTemporaryPackageItem(newItem);
        }
        bookingRepository.save(newBooking);

        navigationManager.navigateTo(BookingForm.class, newBooking.getId());
    }
}
