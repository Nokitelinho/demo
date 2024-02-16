package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

@Setter
@Getter

public class MailStatusVO extends AbstractVO{

    private ZonedDateTime flightDate;
    private String flightCarrierCode;
    private String flightNumber;
    private String incommingFlightCarrierCode;
    private String incommingFlightNumber;
    private ZonedDateTime incommingFlightDate;
    private String pol;
    private String pou;
    private String dsn;
    private String mailBagId;
    private Quantity weight;
    private String carditAvailable;
    private String companyCode;
    private ZonedDateTime scheduledDepartureTime;
    private String legStatus;
    private String flightRoute;
    private String containerNumber;
}
