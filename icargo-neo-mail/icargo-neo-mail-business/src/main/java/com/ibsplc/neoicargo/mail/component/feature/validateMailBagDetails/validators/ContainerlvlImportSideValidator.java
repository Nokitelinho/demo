package com.ibsplc.neoicargo.mail.component.feature.validateMailBagDetails.validators;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.orchestration.Validator;
import com.ibsplc.neoicargo.mail.errorhandling.exception.MailHHTBusniessException;
import com.ibsplc.neoicargo.mail.util.ScannerValidationUtils;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.ScannedMailDetailsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("containerlvlImportSideValidator")
public class ContainerlvlImportSideValidator extends Validator<ScannedMailDetailsVO> {
    @Autowired
    ScannerValidationUtils scannerValidationUtils;

    @Override
    public void validate(ScannedMailDetailsVO scannedMailDetailsVO) throws BusinessException {
        if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())) {
            if (scannedMailDetailsVO.getLatestContainerAssignmentVO()!=null &&"-1".equals(scannedMailDetailsVO.getLatestContainerAssignmentVO().getFlightNumber())
                    && MailConstantsVO.DESTN_FLT == scannedMailDetailsVO.getLatestContainerAssignmentVO().getFlightSequenceNumber()
                    && MailConstantsVO.ULD_TYPE.equals(scannedMailDetailsVO.getLatestContainerAssignmentVO().getContainerType())
                    && (scannedMailDetailsVO.getMailDetails() == null
                    || scannedMailDetailsVO.getMailDetails().isEmpty())) {
                scannerValidationUtils.constructAndRaiseException(MailHHTBusniessException.CONTAINER_CANNOT_ASSIGN,
                        MailHHTBusniessException.CONTAINER_CANNOT_ASSIGN, scannedMailDetailsVO);
            }
        }
    }
}
