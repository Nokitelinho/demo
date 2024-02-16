package com.ibsplc.neoicargo.mail.model;


import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.neoicargo.common.modal.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class MailStatusModel extends BaseModel{

    private LocalDate flightDate;
    private String flightCarrierCode;
    private String flightNumber;

    private String incommingFlightCarrierCode;
    private String incommingFlightNumber;
    private LocalDate incommingFlightDate;
    private String pol;
    private String pou;
    private String dsn;
    private String mailBagId;
    private Measure weight;
    private String carditAvailable;
    private String companyCode;
    private LocalDate scheduledDepartureTime;
    private String legStatus;
    private String flightRoute;
    private String containerNumber;
}
