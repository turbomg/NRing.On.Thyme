package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
public class DayNote extends Note {
    @Temporal(TemporalType.DATE)
    private Date concernsDay;
}
