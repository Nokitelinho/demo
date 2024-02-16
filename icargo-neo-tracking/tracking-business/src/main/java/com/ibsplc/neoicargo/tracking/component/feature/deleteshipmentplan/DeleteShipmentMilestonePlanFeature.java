package com.ibsplc.neoicargo.tracking.component.feature.deleteshipmentplan;

import com.ibsplc.neoicargo.tracking.dao.TrackingDAO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanDeletedVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component("deleteShipmentMilestonePlanFeature")
public class DeleteShipmentMilestonePlanFeature {

    private final TrackingDAO trackingDAO;

    @Transactional
    public void perform(ShipmentMilestonePlanDeletedVO event) {
        log.info("Invoked Delete Shipment Milestone Plan Feature!");
        trackingDAO.deletePlan(event);
    }
}

