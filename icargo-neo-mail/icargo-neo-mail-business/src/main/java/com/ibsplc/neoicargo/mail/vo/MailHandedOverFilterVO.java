package com.ibsplc.neoicargo.mail.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
@Setter
@Getter
public class MailHandedOverFilterVO extends AbstractVO {
    private String companyCode;
    private String carrierCode;
    private String flightNumber;
    private String flightCarrierCode;
    private String scanPort;
    private Integer carrierId;
    private Integer flightCarrierId;
    private Integer ownAirlineId;
    private ZonedDateTime fromDate;
    private ZonedDateTime toDate;
}
