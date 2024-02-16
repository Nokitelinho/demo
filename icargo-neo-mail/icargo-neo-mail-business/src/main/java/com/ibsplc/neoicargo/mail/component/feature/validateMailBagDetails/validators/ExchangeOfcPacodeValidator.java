package com.ibsplc.neoicargo.mail.component.feature.validateMailBagDetails.validators;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.orchestration.Validator;
import com.ibsplc.neoicargo.mail.errorhandling.exception.MailHHTBusniessException;
import com.ibsplc.neoicargo.mail.exception.MailMLDBusniessException;
import com.ibsplc.neoicargo.mail.util.ScannerValidationUtils;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.mail.vo.ScannedMailDetailsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component("exchangeOfcPacodeValidator")
public class ExchangeOfcPacodeValidator extends Validator<ScannedMailDetailsVO> {
    @Autowired
    ScannerValidationUtils scannerValidationUtils;
    @Override
    public void validate(ScannedMailDetailsVO scannedMailDetailsVO) throws BusinessException {
        if(scannedMailDetailsVO!=null&&!scannedMailDetailsVO.getMailDetails().isEmpty()){
            for(MailbagVO mailbagVO :scannedMailDetailsVO.getMailDetails()) {
                if (mailbagVO.getMailbagId() != null && mailbagVO.getMailbagId().length() == 29) {
                    if (mailbagVO.getOrigin() == null) {
                        scannerValidationUtils.constructAndRaiseException(MailMLDBusniessException.INVALID_OFFICEOFEXCHANGE,
                                MailHHTBusniessException.INVALID_OFFICEOFEXCHANGE, scannedMailDetailsVO);
                    } else if (mailbagVO.getDestination() == null) {
                        scannerValidationUtils.constructAndRaiseException(MailMLDBusniessException.INVALID_OFFICEOFEXCHANGE,
                                MailHHTBusniessException.INVALID_OFFICEOFEXCHANGE, scannedMailDetailsVO);
                    }
                } else if (mailbagVO.getOoe() == null&&mailbagVO.getOrigin()!=null) {
                    throw new MailHHTBusniessException(
                            MailHHTBusniessException.OOE_NOT_CONFIGURED_FOR_GPA_AGAINST_ORG + " " + mailbagVO.getOrigin());
                } else if (mailbagVO.getDoe() == null&&mailbagVO.getDestination()!=null) {
                    throw new MailHHTBusniessException(
                            MailHHTBusniessException.DOE_NOT_CONFIGURED_FOR_GPA_AGAINST_DEST + " " + mailbagVO.getDestination());
                }
                if (mailbagVO.getPaCode() == null || mailbagVO.getPaCode().isEmpty()) {
                    scannerValidationUtils.constructAndRaiseException(MailMLDBusniessException.INVALID_PA,
                            MailHHTBusniessException.INVALID_PA, scannedMailDetailsVO);
                }
            }
            }
        }
    }
