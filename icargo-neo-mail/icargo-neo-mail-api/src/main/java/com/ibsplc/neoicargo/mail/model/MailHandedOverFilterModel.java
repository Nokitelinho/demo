package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.common.modal.BaseModel;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class MailHandedOverFilterModel extends BaseModel {
    private String companyCode;
    private String carrierCode;
    private String flightNumber;
    private String flightCarrierCode;
    private String scanPort;
    private Integer carrierId;
    private Integer flightCarrierId;
    private Integer ownAirlineId;
    private LocalDate fromDate;
    private LocalDate toDate;

}
