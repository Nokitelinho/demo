package com.ibsplc.neoicargo.tracking.component.feature.saveshipmentplan.enricher;

import com.ibsplc.neoicargo.awb.dao.AwbDAO;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanCreatedVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Transactional(readOnly = true)
@Component("shipmentSequenceNumberPlanEnricher")
@RequiredArgsConstructor
public class ShipmentSequenceNumberPlanEnricher extends Enricher<ShipmentMilestonePlanCreatedVO> {

    private final AwbDAO awbDAO;

    @Override
    public void enrich(ShipmentMilestonePlanCreatedVO planCreatedVO) {

        log.info("Enriching tracking shipment milestone plans");

        var shipmentKeyOpt = planCreatedVO.getPlanVOs().stream().map(ShipmentMilestonePlanVO::getShipmentKey).filter(StringUtils::isNotBlank).findFirst();

        if (shipmentKeyOpt.isEmpty()) {
            throw new RuntimeException("Shipment key is null or blank");
        }
        var shipmentKeyString = shipmentKeyOpt.get().split("-");
        var shipmentKey = new ShipmentKey(shipmentKeyString[0], shipmentKeyString[1]);

        var awb = awbDAO.findAwbByShipmentKey(shipmentKey);
        if (awb.isEmpty()) {
            throw new RuntimeException(String.format("No AWB found for a shipmentKey : {%s}", shipmentKey));
        }

        var shipmentSequenceNumber = awb.get().getShipmentSequenceNumber();
        planCreatedVO.getPlanVOs().stream().forEach(p -> p.setShipmentSequenceNumber(shipmentSequenceNumber.intValue()));
    }
}
