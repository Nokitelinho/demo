package com.ibsplc.neoicargo.tracking.component.feature.deleteshipment.persistor;

import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.awb.vo.AwbValidationVO;
import com.ibsplc.neoicargo.tracking.dao.TrackingDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Slf4j
@Component("deleteShipmentPersistor")
@RequiredArgsConstructor
public class DeleteShipmentPersistor {

    private final TrackingDAO trackingDAO;

    @Transactional
    public void persist(AwbValidationVO awbValidationVO) {
        var shipmentKey = new ShipmentKey(awbValidationVO.getShipmentPrefix(), awbValidationVO.getMasterDocumentNumber()).toString();
        trackingDAO.deleteEventByShipmentKey(shipmentKey);
        trackingDAO.deletePlanByShipmentKey(shipmentKey);
    }
}
