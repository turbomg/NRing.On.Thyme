package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
}
