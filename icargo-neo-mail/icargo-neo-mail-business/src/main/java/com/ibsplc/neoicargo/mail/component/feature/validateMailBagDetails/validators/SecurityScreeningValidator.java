package com.ibsplc.neoicargo.mail.component.feature.validateMailBagDetails.validators;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.orchestration.Validator;
import com.ibsplc.neoicargo.mail.component.MailController;
import com.ibsplc.neoicargo.mail.component.MailOperationsValidator;
import com.ibsplc.neoicargo.mail.errorhandling.exception.MailHHTBusniessException;
import com.ibsplc.neoicargo.mail.exception.ForceAcceptanceException;
import com.ibsplc.neoicargo.mail.exception.MailMLDBusniessException;
import com.ibsplc.neoicargo.mail.util.ScannerValidationUtils;
import com.ibsplc.neoicargo.mail.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
@Slf4j
@Component("securityScreeningValidator")

public class SecurityScreeningValidator extends Validator<ScannedMailDetailsVO> {
    @Autowired
    private MailController mailController;
    @Autowired
    ScannerValidationUtils scannerValidationUtils;
    @Override
    public void validate(ScannedMailDetailsVO scannedMailDetailsVO) throws BusinessException {
        if (scannedMailDetailsVO.getMailDetails() != null && !scannedMailDetailsVO.getMailDetails().isEmpty()) {
            if (!MailConstantsVO.WS.equals(scannedMailDetailsVO.getMailSource())
                    && !scannedMailDetailsVO.isNotReqSecurityValAtDel()) {
                checkSecurityScreeningValidationAtMailbagLevel(scannedMailDetailsVO);
            }
        } else {
            if (scannedMailDetailsVO.getLatestContainerAssignmentVO() != null && !MailConstantsVO.WS.equals(scannedMailDetailsVO.getMailSource())
                    && !(MailConstantsVO.FLAG_YES).equals(scannedMailDetailsVO.getOverrideValidations())) {
                checkSecurityScreeningValidationAtContainerLevel(scannedMailDetailsVO);
            }
        }
        log.debug("MailUploadController  " + " : " + "doSecurityScreeningValidations" + " Exiting");
    }
    private void checkSecurityScreeningValidationAtMailbagLevel(ScannedMailDetailsVO scannedMailDetailsVO)
            throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
        Collection<SecurityScreeningValidationVO> securityScreeningValidationVOs = null;
        MailbagVO mailbagVo = scannedMailDetailsVO.getMailDetails().iterator().next();
        SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO = populateSecurityScreeningValidationFilterVO(
                scannedMailDetailsVO, mailbagVo);
        if (securityScreeningValidationFilterVO.isAppRegValReq()) {
            mailController.populateApplicableRegulationFlagValidationDetails(securityScreeningValidationFilterVO,
                    mailbagVo.getMailSequenceNumber());
        }
        if (!securityScreeningValidationFilterVO.isAppRegValReq()
                && securityScreeningValidationFilterVO.isSecurityValNotReq()) {
            return;
        }
        try {
            securityScreeningValidationVOs = mailController
                    .checkForSecurityScreeningValidation(securityScreeningValidationFilterVO);
        } catch (BusinessException e) {
            log.info("Exception :", e);
        }
        if (securityScreeningValidationVOs != null && !securityScreeningValidationVOs.isEmpty()) {
            for (SecurityScreeningValidationVO securityScreeningValidationVO : securityScreeningValidationVOs) {
                securityScreeningValidationVO.setMailbagID(mailbagVo.getMailbagId());
                if (checkForSecurityWarningOrError(scannedMailDetailsVO, securityScreeningValidationVO)) {
                    break;
                }
            }
        }
    }
    /**
     * @author A-8353
     * @param scannedMailDetailsVO
     * @param mailbagVo
     * @return
     * @throws SystemException
     */
    private SecurityScreeningValidationFilterVO populateSecurityScreeningValidationFilterVO(
            ScannedMailDetailsVO scannedMailDetailsVO,
            MailbagVO mailbagVo) {
        SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO = new SecurityScreeningValidationFilterVO();
        securityScreeningValidationFilterVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
        securityScreeningValidationFilterVO.setOriginAirport(mailbagVo.getOrigin());
        securityScreeningValidationFilterVO.setDestinationAirport(mailbagVo.getDestination());
        securityScreeningValidationFilterVO.setTransactionAirport(scannedMailDetailsVO.getAirportCode());
        if (mailbagVo.getSecurityStatusCode() != null) {
            securityScreeningValidationFilterVO.setSecurityStatusCode(mailbagVo.getSecurityStatusCode());
        } else {
            securityScreeningValidationFilterVO.setSecurityStatusCode(MailConstantsVO.SECURITY_STATUS_CODE_NSC);
        }
        if (scannedMailDetailsVO.isScreeningPresent()) {
            securityScreeningValidationFilterVO.setSecurityValNotReq(true);
            securityScreeningValidationFilterVO.setSecurityValNotRequired(MailConstantsVO.FLAG_YES);
        }
        securityScreeningValidationFilterVO.setSubClass(mailbagVo.getMailSubclass());
        if (scannedMailDetailsVO.getFlightValidationVOS() != null && !scannedMailDetailsVO.getFlightValidationVOS().isEmpty()) {
            securityScreeningValidationFilterVO.setFlightType(scannedMailDetailsVO.getFlightValidationVOS().iterator().next().getFlightType());
        }
        if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())) {
            securityScreeningValidationFilterVO.setApplicableTransaction(MailConstantsVO.MAIL_STATUS_ARRIVED);
        } else if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
                && scannedMailDetailsVO.getFlightValidationVOS() != null && !scannedMailDetailsVO.getFlightValidationVOS().isEmpty()) {
            securityScreeningValidationFilterVO.setApplicableTransaction(MailConstantsVO.MAIL_STATUS_ASSIGNED);
            securityScreeningValidationFilterVO.setAppRegValReq(true);
            securityScreeningValidationFilterVO.setTransistAirport(scannedMailDetailsVO.getPou());
        } else {
            if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())) {
                securityScreeningValidationFilterVO.setApplicableTransaction(MailConstantsVO.MAIL_STATUS_ACCEPTED);
                securityScreeningValidationFilterVO.setAppRegValReq(true);
            }
        }
        if (scannedMailDetailsVO.getTransferFromCarrier() != null
                && scannedMailDetailsVO.getTransferFrmFlightNum() == null
                && !mailController.checkIfPartnerCarrier(scannedMailDetailsVO.getAirportCode(),
                scannedMailDetailsVO.getTransferFromCarrier())) {
            securityScreeningValidationFilterVO.setTransferInTxn(true);
        }
        mailController.findCountryCodesForSecurityScreeningValidation(securityScreeningValidationFilterVO);
        return securityScreeningValidationFilterVO;
    }
    /**
     * @author A-8353
     * @param scannedMailDetailsVO
     * @throws MailMLDBusniessException
     * @throws MailHHTBusniessException
     * @throws ForceAcceptanceException
     */
    private void checkSecurityScreeningValidationAtContainerLevel(ScannedMailDetailsVO scannedMailDetailsVO)
            throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
        if (scannedMailDetailsVO.getProcessPoint() != null
                && MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint())
                && scannedMailDetailsVO.getContainerNumber() != null
                && scannedMailDetailsVO.getContainerNumber().trim().length() > 0) {
            if ((MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getLatestContainerAssignmentVO().getTransitFlag())
                    && MailConstantsVO.FLAG_NO.equals(scannedMailDetailsVO.getLatestContainerAssignmentVO().getArrivalFlag()))
                    && scannedMailDetailsVO.getLatestContainerAssignmentVO().getContainerType() != null
                    && scannedMailDetailsVO.getLatestContainerAssignmentVO().getContainerType().equals(scannedMailDetailsVO.getContainerType())) {
                checkForSecurityScreeningForContainerAtExport(scannedMailDetailsVO);
            }
        } else {
            if ((scannedMailDetailsVO.getProcessPoint() != null
                    && MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
                    && MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getLatestContainerAssignmentVO().getTransitFlag())
                    && MailConstantsVO.FLAG_NO.equals(scannedMailDetailsVO.getLatestContainerAssignmentVO().getArrivalFlag())
                    && scannedMailDetailsVO.getAirportCode().equals(scannedMailDetailsVO.getLatestContainerAssignmentVO().getPou()))) {
                checkforForSecurityScreeningValidationForContainer(scannedMailDetailsVO,
                        MailConstantsVO.MAIL_STATUS_ARRIVED);
            }
        }
    }
    /**
     * @author A-8353
     * @param scannedMailDetailsVO
     * @throws MailMLDBusniessException
     * @throws MailHHTBusniessException
     * @throws ForceAcceptanceException
     */
    private void checkForSecurityScreeningForContainerAtExport(ScannedMailDetailsVO scannedMailDetailsVO)
            throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
        if(scannedMailDetailsVO.getAirportCode().equalsIgnoreCase(scannedMailDetailsVO.getLatestContainerAssignmentVO().getAirportCode())){
            checkforForSecurityScreeningValidationForContainer(scannedMailDetailsVO, MailConstantsVO.MAIL_STATUS_ACCEPTED);
        }
        else {
            if ((scannedMailDetailsVO.getAirportCode().equals(scannedMailDetailsVO.getLatestContainerAssignmentVO().getPou()) &&
                    MailConstantsVO.ULD_TYPE.equals(scannedMailDetailsVO.getContainerType()))){
                checkforForSecurityScreeningValidationForContainer(scannedMailDetailsVO,  MailConstantsVO.MAIL_STATUS_TRANSFERRED);
            }

        }
    }
    /**
     * @author A-8353
     * @param scannedMailDetailsVO
     * @param transactionType
     * @throws MailMLDBusniessException
     * @throws MailHHTBusniessException
     * @throws ForceAcceptanceException
     */
    private void checkforForSecurityScreeningValidationForContainer(ScannedMailDetailsVO scannedMailDetailsVO,String transactionType)
            throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
        OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
        operationalFlightVO.setFlightNumber(scannedMailDetailsVO.getLatestContainerAssignmentVO().getFlightNumber());
        operationalFlightVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
        operationalFlightVO.setPol(scannedMailDetailsVO.getAirportCode());
        if (scannedMailDetailsVO.getFlightValidationVOS() != null && !scannedMailDetailsVO.getFlightValidationVOS().isEmpty()) {
            operationalFlightVO.setFlightType(scannedMailDetailsVO.getFlightValidationVOS().iterator().next().getFlightType());
            operationalFlightVO.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
        } else {
            operationalFlightVO.setFlightNumber(MailConstantsVO.DESTN_FLT_STR);
        }
        Collection<ContainerVO> selectedContainerVOs = new ArrayList<>();
        ContainerVO containerVO = new ContainerVO();
        containerVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
        containerVO.setFlightNumber(scannedMailDetailsVO.getLatestContainerAssignmentVO().getFlightNumber());
        containerVO.setFlightSequenceNumber(scannedMailDetailsVO.getLatestContainerAssignmentVO().getFlightSequenceNumber());
        containerVO.setCarrierId(scannedMailDetailsVO.getLatestContainerAssignmentVO().getCarrierId());
        containerVO.setLegSerialNumber(scannedMailDetailsVO.getLatestContainerAssignmentVO().getLegSerialNumber());
        containerVO.setAssignedPort(scannedMailDetailsVO.getLatestContainerAssignmentVO().getAirportCode());
        containerVO.setContainerNumber(scannedMailDetailsVO.getLatestContainerAssignmentVO().getContainerNumber());
        containerVO.setType(scannedMailDetailsVO.getLatestContainerAssignmentVO().getContainerType());
        containerVO.setTransactionCode(transactionType);
        containerVO.setPou(scannedMailDetailsVO.getPou());
        selectedContainerVOs.add(containerVO);
        SecurityScreeningValidationVO securityScreeningValidationVO = new SecurityScreeningValidationVO();
        try {
            securityScreeningValidationVO = mailController
                    .doSecurityAndScreeningValidationAtContainerLevel(operationalFlightVO, selectedContainerVOs);
            if (securityScreeningValidationVO != null) {
                checkForSecurityWarningOrError(scannedMailDetailsVO, securityScreeningValidationVO);
            }
        } finally {
        }
    }
    /**
     * @author A-8353
     * @param scannedMailDetailsVO
     * @param securityScreeningValidationVO
     * @return
     * @throws MailMLDBusniessException
     * @throws MailHHTBusniessException
     * @throws ForceAcceptanceException
     */
    private boolean checkForSecurityWarningOrError(ScannedMailDetailsVO scannedMailDetailsVO,
                                                   SecurityScreeningValidationVO securityScreeningValidationVO)
            throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
        StringBuilder errorString = new StringBuilder();
        String error = errorString.append("Security screening for the particular mailbag is not done  ")
                .append("MailbagID:").append(securityScreeningValidationVO.getMailbagID()).toString();
        if (MailConstantsVO.WARNING.equals(securityScreeningValidationVO.getErrorType())) {
            new MailOperationsValidator().constructAndroidException(error,
                    MailConstantsVO.SECURITY_WARNING_VALIDATION, scannedMailDetailsVO);
            return true;
        }
        if (MailConstantsVO.ERROR.equals(securityScreeningValidationVO.getErrorType())) {
            if ("AR".equals(securityScreeningValidationVO.getValidationType())) {
                scannerValidationUtils.constructAndRaiseException(MailConstantsVO.APPLICABLE_REGULATION_ERROR,
                        MailHHTBusniessException.APPLICABLE_REGULATION_ERROR_DESC, scannedMailDetailsVO);
            } else {
                scannerValidationUtils.constructAndRaiseException(error, MailConstantsVO.SECURITY_ERROR_VALIDATION, scannedMailDetailsVO);
            }
        }
        return false;
    }
}
