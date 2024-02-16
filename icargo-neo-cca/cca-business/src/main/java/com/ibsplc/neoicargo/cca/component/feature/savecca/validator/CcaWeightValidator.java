package com.ibsplc.neoicargo.cca.component.feature.savecca.validator;

import com.ibsplc.neoicargo.cca.exception.CcaBusinessException;
import com.ibsplc.neoicargo.cca.exception.CcaErrors;
import com.ibsplc.neoicargo.cca.util.CcaUtil;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CcaRatingDetailVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorType;
import com.ibsplc.neoicargo.framework.orchestration.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.ibsplc.neoicargo.cca.exception.CcaErrors.constructErrorVO;

@Slf4j
@Component
public class CcaWeightValidator extends Validator<CCAMasterVO> {

    @Override
    public void validate(final CCAMasterVO ccaMasterVO) throws BusinessException {
        log.info("Save CCA CcaWeightValidator {}-{}:{}", ccaMasterVO.getShipmentPrefix(),
                ccaMasterVO.getMasterDocumentNumber(),
                ccaMasterVO.getCcaReferenceNumber());
        var revisedShipmentVO = ccaMasterVO.getRevisedShipmentVO();
        final var ratingDetails = revisedShipmentVO.getRatingDetails();
        if (!CcaUtil.isNullOrEmpty(ratingDetails)) {
            for (CcaRatingDetailVO ccaRatingDetailVO : ratingDetails) {
                if (BigDecimal.ZERO.equals(ccaRatingDetailVO.getWeightOfShipment().getValue())) {
                    throw new CcaBusinessException(constructErrorVO(CcaErrors.NEO_CCA_007.getErrorCode(),
                            CcaErrors.NEO_CCA_007.getErrorMessage(), ErrorType.ERROR));
                }
                if (BigDecimal.ZERO.equals(ccaRatingDetailVO.getChargeableWeight().getValue())) {
                    throw new CcaBusinessException(constructErrorVO(CcaErrors.NEO_CCA_008.getErrorCode(),
                            CcaErrors.NEO_CCA_008.getErrorMessage(), ErrorType.ERROR));
                }
            }
        }
    }
}
