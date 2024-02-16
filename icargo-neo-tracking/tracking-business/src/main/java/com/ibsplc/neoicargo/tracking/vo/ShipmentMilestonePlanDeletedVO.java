package com.ibsplc.neoicargo.tracking.vo;

import java.util.List;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShipmentMilestonePlanDeletedVO extends AbstractVO {
    private List<ShipmentMilestonePlanVO> planVOs;
}