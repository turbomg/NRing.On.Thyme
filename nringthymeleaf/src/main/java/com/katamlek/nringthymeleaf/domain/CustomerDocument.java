package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Data
public class CustomerDocument {
    @Id
    @GeneratedValue
    private Long id;

    @Temporal(TemporalType.DATE)
    private Calendar documentedEnteredOn;
    private User documentEnteredByUser;
    private String documentType;
}
