package com.ibsplc.neoicargo.mail.component.feature.validateMailBagDetails.validators;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.orchestration.Validator;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.mail.errorhandling.exception.MailHHTBusniessException;
import com.ibsplc.neoicargo.mail.util.ScannerValidationUtils;
import com.ibsplc.neoicargo.mail.vo.ContainerAssignmentVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.ScannedMailDetailsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("containerReassignmentValidator")
public class ContainerReassignmentValidator extends Validator<ScannedMailDetailsVO> {
    @Autowired
    private LocalDate localDateUtil;
    @Autowired
    ScannerValidationUtils scannerValidationUtils;

    @Override
    public void validate(ScannedMailDetailsVO scannedMailDetailsVO) throws BusinessException {
        if (scannedMailDetailsVO.getProcessPoint() != null
                && MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
                && !MailConstantsVO.WS.equals(scannedMailDetailsVO.getMailSource())
                && scannedMailDetailsVO.getContainerNumber() != null
                && scannedMailDetailsVO.getContainerNumber().trim().length() > 0) {
            ContainerAssignmentVO containerAssignmentVO = scannedMailDetailsVO.getLatestContainerAssignmentVO();

            if (containerAssignmentVO != null && MailConstantsVO.FLAG_YES.equals(containerAssignmentVO.getTransitFlag())
                    && MailConstantsVO.FLAG_NO.equals(containerAssignmentVO.getArrivalFlag())
                    && containerAssignmentVO.getAirportCode() != null
                    && containerAssignmentVO.getAirportCode().equalsIgnoreCase(scannedMailDetailsVO.getAirportCode())
                    && containerAssignmentVO.getContainerType() != null && scannedMailDetailsVO.getContainerType() != null
                    && containerAssignmentVO.getContainerType().equals(scannedMailDetailsVO.getContainerType())) {
                if (containerAssignmentVO.getAssignedDate() != null && scannedMailDetailsVO.getDeviceDateAndTime() != null
                        && localDateUtil.toUTCTime(containerAssignmentVO.getAssignedDate())
                        .isAfter(localDateUtil.toUTCTime(scannedMailDetailsVO.getDeviceDateAndTime()))) {
                    scannerValidationUtils.constructAndroidException(MailConstantsVO.CONTAINER_REASSIGN_ERROR,
                            MailHHTBusniessException.CONTAINER_REASSIGN_ERROR, scannedMailDetailsVO);
                } else {
                    if (containerAssignmentVO.getFlightNumber() != null
                            && !MailConstantsVO.DESTN_FLT_STR.equals(containerAssignmentVO.getFlightNumber())
                            && containerAssignmentVO.getFlightSequenceNumber() != -1
                            && (scannedMailDetailsVO.getFlightNumber() == null
                            || scannedMailDetailsVO.getFlightNumber().trim().length() == 0)) {
                        String errorDescription = new StringBuilder()
                                .append(MailHHTBusniessException.CONTAINER_REASSIGN_FLIGHT)
                                .append(containerAssignmentVO.getCarrierCode()).append(" ")
                                .append(containerAssignmentVO.getFlightNumber()).toString();
                        scannerValidationUtils.constructAndroidException(
                                MailConstantsVO.CONTAINER_REASSIGN_WARNING, errorDescription, scannedMailDetailsVO);
                    }
                }
            }
        }
    }
}
