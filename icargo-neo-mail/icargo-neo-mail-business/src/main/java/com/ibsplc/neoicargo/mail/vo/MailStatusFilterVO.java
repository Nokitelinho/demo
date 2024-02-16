package com.ibsplc.neoicargo.mail.vo;


import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter


public class MailStatusFilterVO extends AbstractVO {


    private String companyCode;
    private ZonedDateTime fromDate;
    private ZonedDateTime toDate;
    private String carrierCode;
    private String flightNumber;
    private String flightCarrierCode;
    private ZonedDateTime flightDate;
    private int legSerialNumber;
    private long flightSequenceNumber;
    private String pol;
    private String pou;
    private String pacode;
    private String currentStatus;
    private int carrierid;
    private int flightCarrierid;

}
