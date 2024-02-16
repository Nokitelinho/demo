package com.ibsplc.neoicargo.cca.component.feature.savecca.validator;

import com.ibsplc.neoicargo.cca.exception.CcaBusinessException;
import com.ibsplc.neoicargo.cca.exception.CcaErrors;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorType;
import com.ibsplc.neoicargo.framework.orchestration.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.ibsplc.neoicargo.cca.exception.CcaErrors.constructErrorVO;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Slf4j
@Component
public class CcaServiceCargoValidator extends Validator<CCAMasterVO> {

    @Override
    public void validate(final CCAMasterVO ccaMasterVO) throws BusinessException {
        log.info("Save CCA CcaServiceCargoValidator {}-{}:{}", ccaMasterVO.getShipmentPrefix(),
                ccaMasterVO.getMasterDocumentNumber(),
                ccaMasterVO.getCcaReferenceNumber());
        final var revisedShipmentVO = ccaMasterVO.getRevisedShipmentVO();
        for (final var rate : revisedShipmentVO.getAwbRates()) {
            if (isNotBlank(revisedShipmentVO.getServiceCargoClass())
                    && BigDecimal.ZERO.compareTo(rate.getCharge().getAmount()) != 0) {
                throw new CcaBusinessException(constructErrorVO(CcaErrors.NEO_CCA_013.getErrorCode(),
                        CcaErrors.NEO_CCA_013.getErrorMessage(), ErrorType.ERROR));
            }
        }
    }
}
