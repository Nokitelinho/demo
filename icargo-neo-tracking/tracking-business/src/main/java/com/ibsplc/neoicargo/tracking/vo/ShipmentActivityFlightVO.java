package com.ibsplc.neoicargo.tracking.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentActivityFlightVO extends AbstractVO {

    private String flightCarrierCode;
    private String flightNumber;
    private String originAirportCode;
    private String destinationAirportCode;

}
