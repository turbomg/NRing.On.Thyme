package com.katamlek.nringthymeleaf.domain;

/*
Application user.
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

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

    private boolean isLoggedIn;

    public User(@NotNull String email, String password) {
        this.email = email;
        this.password = password;
    }
}
