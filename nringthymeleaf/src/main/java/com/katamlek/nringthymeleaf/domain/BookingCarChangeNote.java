package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class BookingCarChangeNote extends Note {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Car newCar;

    private CarStatus newCarStatus;
    private String notes;
    private Long carMileageOut;
    private Long carMileageIn;
    private Long carMileageTotalKM;
    private Long carMileageTotalMIL;
}
