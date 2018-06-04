package com.katamlek.nringthymeleaf.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Data
public class UserSalesTarget {
    @Id
    @GeneratedValue
    private Long id;

    User user;

    @Temporal(TemporalType.DATE) // Entered automatically as the last day of the month
    private Calendar periodTo;
    private Long targetAmount;
}
