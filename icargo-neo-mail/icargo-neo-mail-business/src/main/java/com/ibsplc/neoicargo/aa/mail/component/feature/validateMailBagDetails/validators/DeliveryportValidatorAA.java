package com.ibsplc.neoicargo.ibs.mail.component.feature.validateMailBagDetails.validators;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.orchestration.Validator;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.mail.component.MailController;
import com.ibsplc.neoicargo.mail.component.MailUploadController;
import com.ibsplc.neoicargo.mail.component.Mailbag;
import com.ibsplc.neoicargo.mail.errorhandling.exception.MailHHTBusniessException;
import com.ibsplc.neoicargo.mail.util.NeoMastersServiceUtils;
import com.ibsplc.neoicargo.mail.util.ScannerValidationUtils;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.mail.vo.ScannedMailDetailsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
@Slf4j
@Component("deliveryportValidatorAA")
public class DeliveryportValidatorAA extends Validator<ScannedMailDetailsVO> {
    @Autowired
    private LocalDate localDateUtil;
    @Autowired
    private ContextUtil contextUtil;
    @Autowired
    ScannerValidationUtils scannerValidationUtils;
    @Autowired
    private NeoMastersServiceUtils neoMastersServiceUtils;
    @Autowired
    private MailUploadController mailuploadController;

    @Override
    public void validate(ScannedMailDetailsVO scannedMailDetailsVO) throws BusinessException {
        boolean coTerminusDelivery;
        String poaCode = null;
        String isCoterminusConfigured = mailuploadController.findSystemParameterValue(MailConstantsVO.IS_COTERMINUS_CONFIGURED);
        log.debug("DeliveryportValidator" + " : " + "doSpecificValidations" + " Entering");
        Mailbag mailBag = null;
        if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0) {

            for (MailbagVO scannedMailbagVO : scannedMailDetailsVO.getMailDetails()) {
                ZonedDateTime dspDate = localDateUtil.getLocalDate(scannedMailbagVO.getScannedPort(), true);
                if (scannedMailbagVO.getConsignmentDate() != null) {
                    dspDate = localDateUtil.getLocalDateTime(scannedMailbagVO.getConsignmentDate(), null);
                }
                poaCode=scannedMailbagVO.getPaCode();
                if (("DLV".equals(scannedMailDetailsVO.getProcessPoint())
                        || "Y".equals(scannedMailbagVO.getDeliveredFlag())) && scannedMailbagVO.getDestination() != null
                        && scannedMailDetailsVO.getAirportCode() != null
                        && (!(scannedMailbagVO.getDestination().equals(scannedMailDetailsVO.getAirportCode())))
                        && isCoterminusConfigured != null && "Y".equals(isCoterminusConfigured)) {
                    coTerminusDelivery = new MailController().validateCoterminusairports(
                            scannedMailbagVO.getDestination(), scannedMailDetailsVO.getAirportCode(),
                            MailConstantsVO.RESDIT_DELIVERED, poaCode, dspDate);
                    if (!(coTerminusDelivery || mailuploadController.isCommonCityForAirport(scannedMailDetailsVO.getCompanyCode(),
                            scannedMailDetailsVO.getAirportCode(), scannedMailbagVO.getDestination()))) {
                        scannedMailDetailsVO = scannerValidationUtils.constructAndroidException(MailConstantsVO.INVALID_DELIVERY_PORT,
                                MailHHTBusniessException.INVALID_DELIVERY_EXCEPTION, scannedMailDetailsVO);
                    }
                }
            }
        }
        log.debug("MailUploadController" + " : " + "doSpecificValidations" + " Exiting");
    }
}
