package com.ibsplc.neoicargo.awb.component.feature.deleteawb;

import com.ibsplc.neoicargo.awb.component.feature.deleteawb.persistor.DeleteAwbPersistor;
import com.ibsplc.neoicargo.awb.vo.AwbValidationVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@FeatureConfigSource("feature/awb/deleteawb")
@Component("deleteAwbFeature")
public class DeleteAwbFeature extends AbstractFeature<AwbValidationVO> {

    private final DeleteAwbPersistor deleteAwbPersistor;

    @Override
    public Void perform(AwbValidationVO awbValidationVO) {
        log.info("Invoked delete awb Feature!");
        deleteAwbPersistor.persist(awbValidationVO);
        return null;
    }

    @Override
    public void postInvoke(AwbValidationVO awbValidationVO) throws BusinessException {
        log.info("Postinvoking delete shipment after DeleteAwb");
        invoke("deleteShipmentInvoker", awbValidationVO);
    }
}
