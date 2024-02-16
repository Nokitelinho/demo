package com.ibsplc.neoicargo.tracking.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.types.Weight;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ShipmentActivityVO extends AbstractVO {

    private String event;
    private Integer pieces;
    private String airportCode;
    private LocalDateTime eventTime;
    private LocalDateTime eventTimeUTC;
    private String reason;
    private ShipmentActivityFlightVO flightData;
    private Quantity<Weight> weight;
    private String fromCarrier;
    private String toCarrier;

}
