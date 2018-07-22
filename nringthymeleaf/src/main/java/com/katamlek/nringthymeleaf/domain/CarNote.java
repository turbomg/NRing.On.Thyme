package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

/**
 * FYI: theres the Note class and respective notes extend it.
 * Some notes are just notes, some are history.
 * Some objects do have history, some don't.
 * The solution you see here is intentional, I didn't forget to record history.
 */

@Entity
@Data
public class CarNote extends Note {
    private Long carMileageOut;
    private Long carMileageIn;
    private Long carMileageTotalKM;
    private Long carMileageTotalMIL;

    @ManyToOne
    private Car car;
}
