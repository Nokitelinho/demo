package com.ibsplc.neoicargo.mail.util;

import com.ibsplc.neoicargo.mail.errorhandling.exception.MailHHTBusniessException;
import com.ibsplc.neoicargo.mail.exception.ForceAcceptanceException;
import com.ibsplc.neoicargo.mail.exception.MailMLDBusniessException;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.mail.vo.ScannedMailDetailsVO;
import org.springframework.stereotype.Component;

@Component("scannerValidationUtils")
public class ScannerValidationUtils {
    public void constructAndRaiseException(String errorCode, String errorDescriptionForHHT,
                                           ScannedMailDetailsVO scannedMailDetailsVO) throws MailMLDBusniessException,
            MailHHTBusniessException, ForceAcceptanceException {
        if (errorDescriptionForHHT!=null && errorDescriptionForHHT.length() > 90) {
            errorDescriptionForHHT = errorDescriptionForHHT.substring(0, 90);
        }
        else if (errorDescriptionForHHT == null || errorDescriptionForHHT.isEmpty()) {

            errorDescriptionForHHT = "Exception";
        }
        if (scannedMailDetailsVO!=null && scannedMailDetailsVO.isForceAcpAfterErr()){
            //doAcceptanceAfterErrors(scannedMailDetailsVO);
            throw new ForceAcceptanceException(errorCode,errorDescriptionForHHT);
        }

        if (scannedMailDetailsVO !=null && scannedMailDetailsVO.getMailSource() !=null && MailConstantsVO.MLD
                .equals(scannedMailDetailsVO.getMailSource())) {
            throw new MailMLDBusniessException(errorCode);
        }
        if(scannedMailDetailsVO !=null){
            scannedMailDetailsVO.setErrorDescription(errorDescriptionForHHT);

        }
        throw new MailHHTBusniessException(errorCode,errorDescriptionForHHT);
    }
    public ScannedMailDetailsVO constructAndroidException(String errorCode, String errorDescriptionForHHT,
                                                          ScannedMailDetailsVO scannedMailDetailsVO) throws MailMLDBusniessException,
            MailHHTBusniessException {

        if (errorDescriptionForHHT == null) {
            errorDescriptionForHHT = "Exception";
        }
        if (scannedMailDetailsVO != null) {
            MailbagVO mailbagVO = new MailbagVO();
            mailbagVO.setErrorCode(errorCode);
            mailbagVO.setErrorType("Warning");
            if(MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getContainerAsSuchArrOrDlvFlag())){
                mailbagVO.setErrorType("E");
            }else if(MailHHTBusniessException.INVALID_OFFICEOFEXCHANGE.equals(errorCode)){
                mailbagVO.setErrorType("Error");
            }else if(MailConstantsVO.INVALID_DELIVERY_PORT.equals(errorCode)){
                mailbagVO.setErrorType("Error");
            }else if(MailConstantsVO.CONTAINER_REASSIGN_ERROR.equals(errorCode)){
                mailbagVO.setErrorType("Error");
            }
            mailbagVO.setErrorDescription(errorDescriptionForHHT);
            scannedMailDetailsVO.getErrorMailDetails().add(mailbagVO);
        }
        return scannedMailDetailsVO;
    }
}
