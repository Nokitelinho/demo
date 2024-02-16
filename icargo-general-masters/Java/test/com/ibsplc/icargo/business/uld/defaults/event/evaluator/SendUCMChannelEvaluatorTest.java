package com.ibsplc.icargo.business.uld.defaults.event.evaluator;

import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RampHandlingUldVO;
import com.ibsplc.icargo.business.warehouse.defaults.ramp.vo.RampTransferVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SendUCMChannelEvaluatorTest extends AbstractFeatureTest {

    SendUCMChannelEvaluator sendUCMChannelEvaluator;

    @Override
    public void setup() throws Exception {
        sendUCMChannelEvaluator = new SendUCMChannelEvaluator();
    }

    private RampTransferVO populateRampTransferVo() {
        Collection<RampHandlingUldVO> rampHandlingUldVOs = new ArrayList<>();

        RampTransferVO rampTransferVO = new RampTransferVO();
        RampHandlingUldVO rampHandlingUldVO = new RampHandlingUldVO();
        rampHandlingUldVO.setRampHandlingType(RampHandlingUldVO.INBOUND_RAMPHANDLING);
        rampHandlingUldVOs.add(rampHandlingUldVO);

        RampHandlingUldVO rampHandlingUldVO_2 = new RampHandlingUldVO();
        rampHandlingUldVO_2.setRampHandlingType(RampHandlingUldVO.INBOUND_FLT_HANDOVER_OPERATIONID);
        rampHandlingUldVOs.add(rampHandlingUldVO_2);

        rampTransferVO.setRampHandlingUldVOs(rampHandlingUldVOs);

        return rampTransferVO;
    }

    @Test
    public void shouldReturnFalse_If_RampHandlingUldVOsNull() {
        RampTransferVO rampTransferVO = populateRampTransferVo();
        rampTransferVO.setRampHandlingUldVOs(null);
        boolean returnValue = sendUCMChannelEvaluator.evaluate(rampTransferVO);
        assertFalse(returnValue);
    }

    @Test
    public void shouldReturnTrue_If_RampHandlingUldVOsContainsRequiredRampHandlingType() {
        RampTransferVO rampTransferVO = populateRampTransferVo();
        boolean returnValue = sendUCMChannelEvaluator.evaluate(rampTransferVO);
        assertTrue(returnValue);
    }

    @Test
    public void shouldReturnFalse_If_RampHandlingUldVOs_Not_ContainsRequiredRampHandlingType() {
        RampTransferVO rampTransferVO = populateRampTransferVo();
        Collection<RampHandlingUldVO> rampHandlingUldVOs = rampTransferVO.getRampHandlingUldVOs().stream()
                .filter(e->!e.getRampHandlingType().equals(RampHandlingUldVO.INBOUND_RAMPHANDLING))
                .collect(Collectors.toList());
        rampTransferVO.setRampHandlingUldVOs(rampHandlingUldVOs);
        boolean returnValue = sendUCMChannelEvaluator.evaluate(rampTransferVO);
        assertFalse(returnValue);
    }
}