package com.ibsplc.neoicargo.tracking.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.types.Weight;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ShipmentMilestoneEventVO extends AbstractVO {
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
    private Object transactionDetails;
    private LocalDateTime lastUpdateTime;
    private String lastUpdateUser;
    private Quantity<Weight> weight;

}