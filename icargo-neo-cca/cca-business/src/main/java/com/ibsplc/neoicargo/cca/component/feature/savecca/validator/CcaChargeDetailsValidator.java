package com.ibsplc.neoicargo.cca.component.feature.savecca.validator;

import com.ibsplc.neoicargo.cca.exception.CcaBusinessException;
import com.ibsplc.neoicargo.cca.exception.CcaErrors;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CcaChargeDetailsVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorType;
import com.ibsplc.neoicargo.framework.orchestration.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.ibsplc.neoicargo.cca.exception.CcaErrors.constructErrorVO;

@Slf4j
@Component
public class CcaChargeDetailsValidator extends Validator<CCAMasterVO> {
    @Override
    public void validate(final CCAMasterVO ccaMasterVO) throws BusinessException {
        validateDuplicates(ccaMasterVO);
        validateUpdateExisting(ccaMasterVO);
    }

    private void validateDuplicates(final CCAMasterVO ccaMasterVO) throws CcaBusinessException {
        validateCharges(ccaMasterVO.getOriginalShipmentVO().getAwbCharges());
        validateCharges(ccaMasterVO.getRevisedShipmentVO().getAwbCharges());
    }

    private void validateCharges(Collection<CcaChargeDetailsVO> charges) throws CcaBusinessException {
        final var keys = new HashSet<String>();
        for (final var charge : charges) {
            if (!keys.add(charge.businessKey())) {
                throw new CcaBusinessException(constructErrorVO(CcaErrors.NEO_CCA_001.getErrorCode(),
                        CcaErrors.NEO_CCA_001.getErrorMessage(), ErrorType.ERROR));
            }
        }
    }

    private void validateUpdateExisting(CCAMasterVO ccaMasterVO) throws CcaBusinessException {
        final var originalCharges = ccaMasterVO.getOriginalShipmentVO().getAwbCharges().stream()
                .collect(Collectors.toMap(CcaChargeDetailsVO::businessKey, Function.identity()));
        final var revisedCharges = ccaMasterVO.getRevisedShipmentVO().getAwbCharges();
        for (final var charge : revisedCharges) {
            final var originalCharge = originalCharges.get(charge.businessKey());
            if (originalCharge == null && charge.getCharge().getAmount().doubleValue() == 0) {
                throw new CcaBusinessException(constructErrorVO(CcaErrors.NEO_CCA_002.getErrorCode(),
                        CcaErrors.NEO_CCA_002.getErrorMessage(), ErrorType.ERROR));
            }
        }
    }
}
