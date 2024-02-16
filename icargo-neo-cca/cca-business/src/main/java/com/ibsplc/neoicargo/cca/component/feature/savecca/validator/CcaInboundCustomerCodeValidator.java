package com.ibsplc.neoicargo.cca.component.feature.savecca.validator;

import com.ibsplc.neoicargo.cca.exception.CcaBusinessException;
import com.ibsplc.neoicargo.cca.exception.CcaErrors;
import com.ibsplc.neoicargo.cca.util.awb.AirportUtil;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorType;
import com.ibsplc.neoicargo.framework.orchestration.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.ibsplc.neoicargo.cca.component.feature.savecca.helper.CcaBillingTypeHelper.CC_PAYMENT_TYPES;
import static com.ibsplc.neoicargo.cca.exception.CcaErrors.constructErrorVO;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
@Component
@RequiredArgsConstructor
public class CcaInboundCustomerCodeValidator extends Validator<CCAMasterVO> {

    private final AirportUtil airportUtil;

    @Override
    public void validate(final CCAMasterVO ccaMasterVO) throws BusinessException {
        log.info("Save CCA CcaInboundCustomerCodeValidator {}-{}:{}", ccaMasterVO.getShipmentPrefix(),
                ccaMasterVO.getMasterDocumentNumber(),
                ccaMasterVO.getCcaReferenceNumber());
        var revisedShipmentVO = ccaMasterVO.getRevisedShipmentVO();
        if (CC_PAYMENT_TYPES.contains(revisedShipmentVO.getPayType())
                && airportUtil.isLastOwnFlownRoute(revisedShipmentVO)
                && isBlank(revisedShipmentVO.getInboundCustomerCode())) {
            throw new CcaBusinessException(constructErrorVO(CcaErrors.NEO_CCA_010, ErrorType.ERROR));
        }
    }
}
