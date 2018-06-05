package com.katamlek.nringthymeleaf.domain;

/*
Application user.
 */

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Data
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

    @OneToOne
    private Booking booking;

    @OneToOne
    private Customer customer;

    @OneToOne
    private Event event;

    @OneToMany(mappedBy = "user")
    private List<Note> notes;

    @OneToOne
    private CustomerDocument customerDocument;
}
