package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
public class BookingNote extends Note {
    @ManyToOne
    private Booking booking;
}
