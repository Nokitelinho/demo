package com.ibsplc.neoicargo.tracking.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SplitPlanCombinationMatchVO extends AbstractVO {
    private Integer cost;
    private SplitPlansCombinationVO arr;
    private SplitPlansCombinationVO dep;
}
