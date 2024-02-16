package com.ibsplc.neoicargo.mail.model;


import com.ibsplc.icargo.framework.util.time.LocalDate;
import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
public class DamageMailFilterModel {

    private LocalDate fromDate;
    private LocalDate toDate;
    private String airport;
    private String damageCode;
    private String airline;
    private Integer airlineId;
    private String flightCarrierCode;
    private String flightNumber;
    private LocalDate flightDate;
    private String flightOrigin;
    private String flightDestination;
    private String gpaCode;
    private String originOE;
    private String destinationOE;
    private String subClassGroup;
    private String subClassCode;
    private String companyCode;
}
