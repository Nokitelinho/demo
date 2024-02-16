package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.common.modal.BaseModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class MailStatusFilterModel extends BaseModel{
    private String companyCode;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String carrierCode;
    private String flightNumber;
    private String flightCarrierCode;
    private LocalDate flightDate;
    private int legSerialNumber;
    private long flightSequenceNumber;
    private String pol;
    private String pou;
    private String pacode;
    private String currentStatus;
    private int carrierid;
    private int flightCarrierid;
}
