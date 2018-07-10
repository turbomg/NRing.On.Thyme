package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Data
public class DayNote extends Note {
    @Temporal(TemporalType.DATE)
    private Date concernsDay;
}
