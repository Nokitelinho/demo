package com.ibsplc.neoicargo.tracking.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ShipmentMilestonePlanCreatedVO extends AbstractVO {
    private List<ShipmentMilestonePlanVO> planVOs;
}