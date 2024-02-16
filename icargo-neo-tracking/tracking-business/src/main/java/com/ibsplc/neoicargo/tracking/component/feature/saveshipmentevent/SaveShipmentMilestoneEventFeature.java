package com.ibsplc.neoicargo.tracking.component.feature.saveshipmentevent;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import com.ibsplc.neoicargo.tracking.component.feature.saveshipmentevent.persistor.ShipmentMilestoneEventPersistor;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestoneEventVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
@FeatureConfigSource("feature/shipment/saveshipmentmilestoneevent")
public class SaveShipmentMilestoneEventFeature extends AbstractFeature<ShipmentMilestoneEventVO> {

    private final ShipmentMilestoneEventPersistor persistor;

    @Override
    protected Void perform(ShipmentMilestoneEventVO event) {
        log.info("Invoked Save Shipment Milestone Event Feature!");
        persistor.persist(event);
        return null;
    }

    @Override
    protected void postInvoke(ShipmentMilestoneEventVO featureVO) throws BusinessException {
        // run
        invoke("emailSenderInvoker", featureVO);
    }
}