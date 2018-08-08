package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
public class BookingPackageItemNote extends Note {
    @ManyToOne
    private BookingPackageItem packageItem;
}
