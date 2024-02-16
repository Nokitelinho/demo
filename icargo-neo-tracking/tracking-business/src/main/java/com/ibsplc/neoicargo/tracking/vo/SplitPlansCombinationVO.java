package com.ibsplc.neoicargo.tracking.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SplitPlansCombinationVO  extends AbstractVO {
    private Integer sum;
    private Integer cost;
    private List<ShipmentMilestonePlanVO> plans;
}
