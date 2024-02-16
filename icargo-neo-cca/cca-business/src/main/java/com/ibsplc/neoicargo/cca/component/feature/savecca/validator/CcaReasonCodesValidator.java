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
public class CcaReasonCodesValidator extends Validator<CCAMasterVO> {

    @Override
    public void validate(final CCAMasterVO ccaMasterVO) throws BusinessException {
        log.info("Save CCA CcaReasonCodesValidator {}-{}:{}", ccaMasterVO.getShipmentPrefix(),
                ccaMasterVO.getMasterDocumentNumber(),
                ccaMasterVO.getCcaReferenceNumber());
        if (isBlank(ccaMasterVO.getCcaReason())) {
            throw new CcaBusinessException(constructErrorVO(CcaErrors.NEO_CCA_012.getErrorCode(),
                    CcaErrors.NEO_CCA_012.getErrorMessage(), ErrorType.ERROR));
        }
    }
}
