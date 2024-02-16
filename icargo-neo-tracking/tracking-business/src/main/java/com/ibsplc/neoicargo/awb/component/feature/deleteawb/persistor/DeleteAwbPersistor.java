package com.ibsplc.neoicargo.awb.component.feature.deleteawb.persistor;

import com.ibsplc.neoicargo.awb.dao.AwbDAO;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.awb.vo.AwbValidationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("deleteAwbPersistor")
@RequiredArgsConstructor
public class DeleteAwbPersistor {

    private final AwbDAO awbDAO;

    public void persist(AwbValidationVO awbValidationVO) {
        log.info("AWB persistor for void event");
        var possibleAwb = awbDAO.findAwbByShipmentKey(
                new ShipmentKey(awbValidationVO.getShipmentPrefix(),
                        awbValidationVO.getMasterDocumentNumber()));
        if (possibleAwb.isPresent()) {
            var awb = possibleAwb.get();
            log.info("Remove AWB:, {}-{}", awbValidationVO.getShipmentPrefix(), awbValidationVO.getMasterDocumentNumber());
            awbDAO.deleteAwb(awb);
        }
    }
}