package com.ibsplc.neoicargo.tracking.component.feature.saveshipmentplan;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import com.ibsplc.neoicargo.tracking.component.feature.saveshipmentplan.persistor.ShipmentMilestonePlanPersistor;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanCreatedVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component("saveShipmentMilestonePlanFeature")
@FeatureConfigSource("feature/shipment/saveshipmentmilestoneplan")
public class SaveShipmentMilestonePlanFeature extends AbstractFeature<ShipmentMilestonePlanCreatedVO> {

    private final ShipmentMilestonePlanPersistor persistor;

    @Override
    protected Void perform(ShipmentMilestonePlanCreatedVO planCreatedVO) throws BusinessException {
        log.info("Invoked Capture Shipment Milestone Plan Feature!");
        persistor.persist(planCreatedVO.getPlanVOs());
        return null;
    }

}