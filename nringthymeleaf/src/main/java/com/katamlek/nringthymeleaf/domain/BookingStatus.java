package com.katamlek.nringthymeleaf.domain;

/**
 * RECEIVED - received from web page, CONFIRMED BY RSR - rsr confirmed the booking
 * CONFIRMED BY CUSTOMER - customer confirmed the booking
 * TO PAY - payment request sent to customer
 * PAID - customer paid, EXTRA_CHARGE_TO_COLLECT - customer crashed a car and needs to settle the damage
 * CANCELLED - rsr cancelled the booking as the customer won't show up/ didn't pay on time/ etc.
 * CLOSED - no more charges, car returned
 */

public enum BookingStatus {
    RECEIVED, CONFIRMED_BY_RSR, CONFIRMED_BY_CUSTOMER, PAID, TO_PAY, EXTRA_CHARGE_TO_COLLECT, CANCELLED, CLOSED
}
