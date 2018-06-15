package com.katamlek.nringthymeleaf.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;

@Entity
@Data
public class UserSalesTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @Temporal(TemporalType.DATE) // Entered automatically as the last day of the month
    private Calendar periodTo;
    private BigDecimal targetAmount;
}
