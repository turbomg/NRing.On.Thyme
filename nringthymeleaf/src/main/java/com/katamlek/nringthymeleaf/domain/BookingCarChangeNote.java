package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class BookingCarChangeNote extends Note {
    @NotNull
    @OneToOne
    private Car car;

    private CarStatus newCarStatus;

    @Lob
    private String notes;
    private Long carMileageOut;
    private Long carMileageIn;
    private Long carMileageTotalKM;
    private Long carMileageTotalMIL;

    @ManyToOne
    private Booking booking;
}
