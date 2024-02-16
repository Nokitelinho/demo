package com.ibsplc.neoicargo.tracking.component.feature.saveshipmentevent.enricher;

import com.ibsplc.neoicargo.awb.dao.AwbDAO;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestoneEventVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Transactional(readOnly = true)
@Component("shipmentSequenceNumberEventEnricher")
@RequiredArgsConstructor
public class ShipmentSequenceNumberEventEnricher extends Enricher<ShipmentMilestoneEventVO> {

    private final AwbDAO awbDAO;

    @Override
    public void enrich(ShipmentMilestoneEventVO eventVO) {

        log.info("Enriching tracking shipment milestone event");

        String shipmentKeyString = eventVO.getShipmentKey();

        var shipmentKeySplit = shipmentKeyString.split("-");
        var shipmentKey = new ShipmentKey(shipmentKeySplit[0], shipmentKeySplit[1]);

        var awb = awbDAO.findAwbByShipmentKey(shipmentKey);
        if (awb.isEmpty()) {
            throw new RuntimeException(String.format("No AWB found for a shipmentKey : {%s}", shipmentKey));
        }

        var shipmentSequenceNumber = awb.get().getShipmentSequenceNumber();
        eventVO.setShipmentSequenceNumber(shipmentSequenceNumber.intValue());
    }
}
