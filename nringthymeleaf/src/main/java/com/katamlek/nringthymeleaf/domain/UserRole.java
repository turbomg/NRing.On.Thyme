package com.katamlek.nringthymeleaf.domain;

/*
System access type. Determines available operations.
Won't probably be implemented.
TODO In my opinion this is obsolete. Give up roles and just set up available operations as there's the CUSTOM type. Jono if he agrees.
 */

public enum UserRole {
    SUPER_ADMIN, MANAGEMENT, OFFICE_FULL, OFFICE_LIMITED, MECHANIC_FULL, MECHANIC_LIMITED, CUSTOMER_SELF_SIGN_IN,
    PUBLIC_BOOKING_OVERVIEW, CUSTOM
}
