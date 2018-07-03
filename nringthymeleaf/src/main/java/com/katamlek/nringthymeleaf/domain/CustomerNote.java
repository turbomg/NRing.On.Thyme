package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Ordinary notes plus history entries (see isHistory flag in Note).
 */
@Entity
@Data
public class CustomerNote extends Note {
    private String value;

    @ManyToOne
    private Customer customer;
}
