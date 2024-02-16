package com.ibsplc.neoicargo.awb.component.feature.executeawb.persistor;

import com.ibsplc.neoicargo.awb.dao.AwbDAO;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.awb.vo.AwbStatusEnum;
import com.ibsplc.neoicargo.awb.vo.AwbValidationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("executeAwbPersistor")
@RequiredArgsConstructor
public class ExecuteAwbPersistor {

    private final AwbDAO awbDAO;

    public void persist(AwbValidationVO awbValidationVO) {
        var possibleAwb = awbDAO.findAwbByShipmentKey(
                new ShipmentKey(awbValidationVO.getShipmentPrefix(),
                        awbValidationVO.getMasterDocumentNumber()));
        if (possibleAwb.isPresent()) {
            log.info("Changed AWB status to \"E\"");
            var awb = possibleAwb.get();
            awb.setAwbStatus(AwbStatusEnum.EXECUTED.getValue());
            awbDAO.saveAwb(awb);
        }
    }
}