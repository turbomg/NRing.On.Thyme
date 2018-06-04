package com.katamlek.nringthymeleaf.domain;

/*
Application user.
 */

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
//    private String login; -- obsolete, will use e-mail
// TODO ask Jono if it's fine
    private String password;
    private String name;
    private String surname;
    private String initials;
    private String phoneNumber;
    private String email;
    private UserBranding branding;
//    private Set<UserRole> userRole; -- see notes in UserRole class.
//    private Set<UserOperations> userOperations; -- as above

    @Autowired
    public User(UserBranding branding, Set<UserRole> userRole) {
        this.branding = branding;
//        this.userRole = userRole; -- as above
    }
}
