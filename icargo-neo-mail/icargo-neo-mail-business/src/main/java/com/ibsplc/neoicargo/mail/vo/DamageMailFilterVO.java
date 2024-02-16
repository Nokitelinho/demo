package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

@Setter
@Getter


public class DamageMailFilterVO extends AbstractVO{
    private ZonedDateTime fromDate;
    private ZonedDateTime toDate;
    private String airport;
    private String damageCode;
    private String airline;
    private Integer airlineId;
    private String flightCarrierCode;
    private String flightNumber;
    private ZonedDateTime flightDate;
    private String flightOrigin;
    private String flightDestination;
    private String gpaCode;
    private String originOE;
    private String destinationOE;
    private String subClassGroup;
    private String subClassCode;
    private String companyCode;
}
