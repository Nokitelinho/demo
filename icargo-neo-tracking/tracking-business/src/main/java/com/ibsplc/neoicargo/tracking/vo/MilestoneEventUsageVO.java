package com.ibsplc.neoicargo.tracking.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MilestoneEventUsageVO extends AbstractVO {
    private ShipmentMilestoneEventVO event;
    private String usedByAirportItemId; //nullable
}