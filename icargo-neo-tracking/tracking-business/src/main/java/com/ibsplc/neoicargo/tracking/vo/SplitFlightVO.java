package com.ibsplc.neoicargo.tracking.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.neoicargo.tracking.dao.entity.ShipmentMilestonePlan;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SplitFlightVO  extends AbstractVO {
    private ShipmentMilestonePlanVO dep;
    private ShipmentMilestonePlanVO arr;
}
