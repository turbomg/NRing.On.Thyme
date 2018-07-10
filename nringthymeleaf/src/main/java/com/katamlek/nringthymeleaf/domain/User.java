package com.katamlek.nringthymeleaf.domain;

/*
Application user.
 */

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //    private String login; -- obsolete, will use e-mail
    private String password;
    private String name;
    private String surname;
    private String initials;
    private String phoneNumber;

    @NotNull
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

    public User(@NotNull String email, String password) {
        this.email = email;
        this.password = password;
    }
}
