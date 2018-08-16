//package com.katamlek.nringthymeleaf.domain;
//
//import lombok.*;
//
//import javax.persistence.*;
//import java.math.BigDecimal;
//import java.util.List;
//
//@Getter
//@Setter
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//public class EventInternalInfo {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String internalEventOrganizer;
//
//    //todo remove below - replace with dynamic fields on the form?
//    private int internalCurrentEntriesNo;
//    private BigDecimal internalCurrentEntriesValue;
//    //todo end remove
//
//    private BigDecimal internalEventCostToRSR;
//    private Integer internalSpacesBooked;
//
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventInternalInfo")
//    private List<EventInternalInfoNote> eventInternalInfoNote;
//
////    @OneToOne
////    private Event event;
//}
