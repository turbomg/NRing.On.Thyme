package com.katamlek.nringthymeleaf.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Data
//todo reapply notnull
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private User createdBy; // if received from the website == "web"

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "booking")
    private List<BookingNote> bookingNotes = new ArrayList<>();

    private Date createDate;

    //  @NotNull
    private SignatureStatus signatureStatus;

    //  @NotNull
    private PaymentStatus paymentStatus;

    private boolean emailConfirmationSent;

    private Date emailReminderSendDate;

    private boolean emailReminderSent;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "booking_customer", joinColumns = @JoinColumn(name = "booking_id"), inverseJoinColumns = @JoinColumn(name = "customer_id"))
    //   @NotNull
    private List<Customer> customers; // drivers

    //    @OneToMany(mappedBy = "booking")
//    private List<BookingCar> bookedCarsList;
//
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "booking")
    private List<BookingPackageItem> packageItems;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "booking")
    private List<BookingPayment> paymentList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "booking")
    private List<BookingDocument> bookingDocumentList = new ArrayList<>();

    private BookingStatus bookingStatus; // allows for booking write-off when paid and car OK

    private boolean isUnderEditing;

    // Temporary solution until the full functionality of booking is implemented
    @OneToMany(cascade = CascadeType.ALL,  mappedBy = "booking")
    private List<TemporaryPackageItem> temporaryPackageItemList = new ArrayList<>();

    public User getCreatedBy() {
        if (createdBy == null) {
            createdBy = new User();
        }
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public List<BookingNote> getBookingNotes() {
        if (bookingNotes == null) {
            bookingNotes = new ArrayList<>();
        }
        return bookingNotes;
    }

    public void addBookingNote(BookingNote bookingNote) {
        if (bookingNote != null) {
            bookingNote.setBooking(this);
            this.bookingNotes.add(bookingNote);
        }
    }

    public void removeBookingNote(BookingNote bookingNote) {
        if (bookingNotes != null && this.bookingNotes.contains(bookingNote)) {
            this.bookingNotes.remove(bookingNote);
            bookingNote.setBooking(null);
        }
    }

    public void setBookingNotes(List<BookingNote> bookingNotes) {
        this.bookingNotes = bookingNotes;
    }

    public List<Customer> getCustomers() {
        if (customers == null) {
            customers = new ArrayList<>();
        }
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }


    //todo it's many to many, ask Marcin how to resolve the issue
//    public void addCustomer(Customer customer) {
//        if (customer != null) {
//            customer.setBookings(Arrays.asList(this));
//            this.customers.add(customer);
//        }
//    }
//
//    public void removeCustomer(Customer customer) {
//        if (customer != null && this.customers.contains(customer)) {
//            this.customers.remove(customer);
//            customer.setBooking(null);
//        }
//    }

    public List<TemporaryPackageItem> getTemporaryPackageItems() {
        if (temporaryPackageItemList == null) {
            temporaryPackageItemList = new ArrayList<>();
        }
        return temporaryPackageItemList;
    }

    public void setTemporaryPackageItems(List<TemporaryPackageItem> packageItems) {
        this.temporaryPackageItemList = temporaryPackageItemList;
    }

    public List<BookingPackageItem> getPackageItems() {
        if (packageItems == null) {
            packageItems = new ArrayList<>();
        }
        return packageItems;
    }

    public void setPackageItems(List<BookingPackageItem> packageItems) {
        this.packageItems = packageItems;
    }

    public List<BookingPayment> getPaymentList() {
        if (paymentList == null) {
            paymentList = new ArrayList<>();
        }
        return paymentList;
    }

    public void setPaymentList(List<BookingPayment> paymentList) {
        this.paymentList = paymentList;
    }

    public void addBookingPayment(BookingPayment bookingPayment) {
        if (bookingPayment != null) {
            bookingPayment.setBooking(this);
            this.paymentList.add(bookingPayment);
        }
    }

    public void removeBookingPayment(BookingPayment bookingPayment) {
        if (paymentList != null && this.paymentList.contains(bookingPayment)) {
            this.paymentList.remove(bookingPayment);
            bookingPayment.setBooking(null);
        }
    }

    public List<BookingDocument> getBookingDocumentList() {
        if (bookingDocumentList == null) {
            bookingDocumentList = new ArrayList<>();
        }
        return bookingDocumentList;
    }

    public void setBookingDocumentList(List<BookingDocument> bookingDocumentList) {
        this.bookingDocumentList = bookingDocumentList;
    }

    public void addBookingDocument(BookingDocument bookingDocument) {
        if (bookingDocument != null) {
            bookingDocument.setBooking(this);
            this.bookingDocumentList.add(bookingDocument);
        }
    }

    public void removeBookingDocument(BookingDocument bookingDocument) {
        if (bookingDocumentList != null && this.bookingDocumentList.contains(bookingDocument)) {
            this.bookingDocumentList.remove(bookingDocument);
            bookingDocument.setBooking(null);
        }
    }

    public List<TemporaryPackageItem> getTemporaryPackageItemList() {
        if (temporaryPackageItemList != null) {
            temporaryPackageItemList = new ArrayList<>();
        }
        return temporaryPackageItemList;
    }

    public void setTemporaryPackageItemList(List<TemporaryPackageItem> temporaryPackageItemList) {
        this.temporaryPackageItemList = temporaryPackageItemList;
    }

    public void addTemporaryPackageItem(TemporaryPackageItem temporaryPackageItem) {
        if (temporaryPackageItem != null) {
            temporaryPackageItem.setBooking(this);
            this.temporaryPackageItemList.add(temporaryPackageItem);
        }
    }

    public void removeTemporaryPackageItem(TemporaryPackageItem temporaryPackageItem) {
        if (temporaryPackageItemList != null && this.temporaryPackageItemList.contains(temporaryPackageItem)) {
            this.temporaryPackageItemList.remove(temporaryPackageItem);
            temporaryPackageItem.setBooking(null);
        }
    }

    @Override
    public String toString() {
        return "Booking{" + "id=" + id + '}';
    }

}


