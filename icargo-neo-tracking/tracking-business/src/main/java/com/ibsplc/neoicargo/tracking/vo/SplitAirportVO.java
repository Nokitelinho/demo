package com.ibsplc.neoicargo.tracking.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SplitAirportVO extends AbstractVO {
    private String itemId;
    private String airportCode;
    private List<ShipmentMilestonePlanVO> arr;
    private List<ShipmentMilestonePlanVO> dep;
}
