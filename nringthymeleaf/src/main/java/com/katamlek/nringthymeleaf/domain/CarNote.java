package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Car history entry.
 */

@Entity
@Data
public class CarNote extends Note {
    private String carNotetype;
    private Long carMileageOut;
    private Long carMileageIn;
    private Long carMileageTotalKM;
    private Long carMileageTotalMIL;

    @ManyToOne
    private Car car;
}
