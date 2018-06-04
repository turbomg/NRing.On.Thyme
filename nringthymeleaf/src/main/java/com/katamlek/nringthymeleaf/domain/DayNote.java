package com.katamlek.nringthymeleaf.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Data
public class DayNote extends Note {
    @Temporal(TemporalType.DATE)
    private Calendar concernsDay;
}
