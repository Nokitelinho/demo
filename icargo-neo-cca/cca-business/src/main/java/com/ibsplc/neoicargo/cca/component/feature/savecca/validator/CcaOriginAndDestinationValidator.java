package com.ibsplc.neoicargo.cca.component.feature.savecca.validator;

import com.ibsplc.neoicargo.cca.exception.CcaBusinessException;
import com.ibsplc.neoicargo.cca.exception.CcaErrors;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorType;
import com.ibsplc.neoicargo.framework.orchestration.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.ibsplc.neoicargo.cca.exception.CcaErrors.constructErrorVO;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
@Component
public class CcaOriginAndDestinationValidator extends Validator<CCAMasterVO> {

    @Override
    public void validate(final CCAMasterVO ccaMasterVO) throws BusinessException {
        log.info("Save CCA CcaOriginAndDestinationValidator {}-{}:{}", ccaMasterVO.getShipmentPrefix(),
                ccaMasterVO.getMasterDocumentNumber(),
                ccaMasterVO.getCcaReferenceNumber());
        var revisedShipmentVO = ccaMasterVO.getRevisedShipmentVO();
        if (isBlank(revisedShipmentVO.getOrigin())) {
            throw new CcaBusinessException(constructErrorVO(CcaErrors.NEO_CCA_005.getErrorCode(),
                    CcaErrors.NEO_CCA_005.getErrorMessage(), ErrorType.ERROR));
        }
        if (isBlank(revisedShipmentVO.getDestination())) {
            throw new CcaBusinessException(constructErrorVO(CcaErrors.NEO_CCA_006.getErrorCode(),
                    CcaErrors.NEO_CCA_006.getErrorMessage(), ErrorType.ERROR));
        }
        if (revisedShipmentVO.getOrigin().equals(revisedShipmentVO.getDestination())) {
            throw new CcaBusinessException(constructErrorVO(CcaErrors.NEO_CCA_004.getErrorCode(),
                    CcaErrors.NEO_CCA_004.getErrorMessage(), ErrorType.ERROR));
        }

    }

}
