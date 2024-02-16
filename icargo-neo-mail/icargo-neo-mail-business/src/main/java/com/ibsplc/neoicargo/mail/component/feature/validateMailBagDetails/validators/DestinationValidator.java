package com.ibsplc.neoicargo.mail.component.feature.validateMailBagDetails.validators;

import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.ebl.api.ServiceException;
import com.ibsplc.neoicargo.framework.orchestration.Validator;
import com.ibsplc.neoicargo.mail.errorhandling.exception.MailHHTBusniessException;
import com.ibsplc.neoicargo.mail.util.NeoMastersServiceUtils;
import com.ibsplc.neoicargo.mail.util.ScannerValidationUtils;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.ScannedMailDetailsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("destinationValidator")
public class DestinationValidator extends Validator<ScannedMailDetailsVO> {
    @Autowired
    private NeoMastersServiceUtils neoMastersServiceUtils;
    @Autowired
    private ContextUtil contextUtil;
    @Autowired
    ScannerValidationUtils scannerValidationUtils;

    @Override
    public void validate(ScannedMailDetailsVO scannedMailDetailsVO) throws BusinessException {
        AirportValidationVO airportValidationVO = new AirportValidationVO();
        LoginProfile logonAttributes = contextUtil.callerLoginProfile();
        if (scannedMailDetailsVO.getContainerNumber() != null
                && scannedMailDetailsVO.getContainerNumber().trim().length() != 0
                && scannedMailDetailsVO.getDestination() != null
                && scannedMailDetailsVO.getDestination().trim().length() != 0) {
            try {
                airportValidationVO = neoMastersServiceUtils.validateAirportCode(logonAttributes.getCompanyCode(),
                        scannedMailDetailsVO.getDestination());
            }catch(Exception e){
                airportValidationVO=null;
            }
            if (airportValidationVO == null) {
                scannerValidationUtils.constructAndRaiseException(MailConstantsVO.ANDROID_MSG_ERR_INVALID_DESTINATION,
                        MailHHTBusniessException.INVALID_POU_DESTINATION, scannedMailDetailsVO);
            }
        }
    }
}
