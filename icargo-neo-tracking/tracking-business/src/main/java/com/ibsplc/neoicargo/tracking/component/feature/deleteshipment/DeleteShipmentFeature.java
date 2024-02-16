package com.ibsplc.neoicargo.tracking.component.feature.deleteshipment;

import com.ibsplc.neoicargo.tracking.component.feature.deleteshipment.persistor.DeleteShipmentPersistor;
import com.ibsplc.neoicargo.awb.vo.AwbValidationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("deleteShipmentFeature")
@RequiredArgsConstructor
public class DeleteShipmentFeature {
    private final DeleteShipmentPersistor deleteShipmentPersistor;

    public void perform(AwbValidationVO awbValidationVO) {
        deleteShipmentPersistor.persist(awbValidationVO);
    }
}
