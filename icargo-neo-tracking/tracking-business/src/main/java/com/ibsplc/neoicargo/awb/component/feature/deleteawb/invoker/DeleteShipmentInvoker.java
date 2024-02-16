package com.ibsplc.neoicargo.awb.component.feature.deleteawb.invoker;

import com.ibsplc.neoicargo.awb.vo.AwbValidationVO;
import com.ibsplc.neoicargo.framework.orchestration.Invoker;
import com.ibsplc.neoicargo.tracking.component.TrackingComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("deleteShipmentInvoker")
@RequiredArgsConstructor
public class DeleteShipmentInvoker extends Invoker<AwbValidationVO> {

    private final TrackingComponent trackingComponent;

    @Override
    public void invoke(AwbValidationVO awbValidationVO) {
        trackingComponent.deleteShipment(awbValidationVO);
    }
}
