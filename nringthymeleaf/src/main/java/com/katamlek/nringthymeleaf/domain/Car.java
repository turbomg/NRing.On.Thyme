package com.katamlek.nringthymeleaf.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private CarStatus carStatus;
    private boolean currentlyInUse;
    private int kmOut;
    private int kmIn;
    private List<Entry> mechanicNotes;
    private boolean substitute; // is the car a substitute for another (broken) one

}
