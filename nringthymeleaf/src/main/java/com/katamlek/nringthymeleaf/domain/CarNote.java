package com.katamlek.nringthymeleaf.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;

@Entity
@Data
@NoArgsConstructor
public class CarNote extends Note {

    private Car carInUse;
    private CarStatus carStatus;

    private Long mileageOut;
    private Long mileageIn;
    private Long mileageTotalKM;
    private Long mileageTotalMIL;

}
