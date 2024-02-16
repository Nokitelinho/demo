package com.ibsplc.icargo.business.uld.defaults.event.evaluator;

import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RampHandlingUldVO;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RampTransferVO;
import com.ibsplc.icargo.framework.evaluator.Evaluator;

import java.util.Collection;
import java.util.Objects;


public class SendUCMChannelEvaluator implements Evaluator<RampTransferVO> {

    @Override
    public boolean evaluate(RampTransferVO rampTransferVO) {
        Collection<RampHandlingUldVO> rampHandlingUldVOs = rampTransferVO.getRampHandlingUldVOs();

        return Objects.nonNull(rampHandlingUldVOs) && rampHandlingUldVOs.stream()
                .map(RampHandlingUldVO::getRampHandlingType)
                .anyMatch(RampHandlingUldVO.INBOUND_RAMPHANDLING::equals);
    }
}
