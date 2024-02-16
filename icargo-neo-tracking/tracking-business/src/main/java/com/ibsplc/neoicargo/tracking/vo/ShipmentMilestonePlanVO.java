package com.ibsplc.neoicargo.tracking.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ShipmentMilestonePlanVO extends AbstractVO {
    private Long serialNumber;
    private String companyCode;
    private String shipmentKey;
    private String shipmentType;
    private Integer shipmentSequenceNumber;
    private String airportCode;
    private MilestoneCodeEnum milestoneCode;
    private Integer pieces;
    private LocalDateTime milestoneTime;
    private LocalDateTime milestoneTimeUTC;
    private String source;
    private String operationType;
    private String flightNumber;
    private String flightCarrierCode;
    private LocalDate flightDate;
}