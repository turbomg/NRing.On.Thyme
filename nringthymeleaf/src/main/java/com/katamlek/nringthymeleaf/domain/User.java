package com.katamlek.nringthymeleaf.domain;

/*
Application user.
 */

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //    private String login; -- obsolete, will use e-mail
    private String password;
    private String name;
    private String surname;

    //todo reapply notNull

    private String initials;

    private String phoneNumber;

    private String email;
    private UserBranding branding;

    private String userRole;

    @OneToMany(mappedBy = "createdBy")
    private List<Booking> bookings;

    @OneToMany(mappedBy = "customerCreatedBy")
    private List<Customer> customers;

    @OneToMany(mappedBy = "eventResponsibleUser")
    private List<Event> events;

    @OneToMany(mappedBy = "user")
    private List<Note> notes;

    @OneToMany(mappedBy = "user")
    private List<CustomerDocument> customerDocuments;

    private boolean isLoggedIn;

    @OneToMany(mappedBy = "user")
    private List<UserSalesTarget> userSalesTarget;

    private boolean isUnderEditing;

    public List<Booking> getBookings() {
        if (bookings == null) {
            bookings = new ArrayList<>();
        }
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @Override
    public String toString() {
        return "User{" + "email='" + email + '\'' + '}';
    }
}
