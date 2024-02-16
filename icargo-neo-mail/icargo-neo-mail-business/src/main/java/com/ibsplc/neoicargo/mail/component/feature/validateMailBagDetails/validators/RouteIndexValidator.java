package com.ibsplc.neoicargo.mail.component.feature.validateMailBagDetails.validators;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.orchestration.Validator;
import com.ibsplc.neoicargo.mail.component.feature.validateMailBagDetails.ValidateMailBagDetailsFeatureConstants;
import com.ibsplc.neoicargo.mail.errorhandling.exception.MailHHTBusniessException;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.mail.vo.ScannedMailDetailsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component("routeIndexValidator")
public class RouteIndexValidator extends Validator<ScannedMailDetailsVO>
{
    @Autowired
    private ContextUtil contextUtil;
    @Override
    public void validate(ScannedMailDetailsVO scannedMailDetailsVO) throws BusinessException {
        String noOfChar=contextUtil.getTxContext(ValidateMailBagDetailsFeatureConstants.NO_OF_CHAR_ALLOWED_FOR_MAILTAG);
        if (scannedMailDetailsVO != null && !scannedMailDetailsVO.getMailDetails().isEmpty()) {
            for (MailbagVO mailbagVO : scannedMailDetailsVO.getMailDetails()) {
                if((mailbagVO.getOrigin()==null||mailbagVO.getOrigin().isEmpty())
                        &&"NA".equalsIgnoreCase(contextUtil.getTxContext(ValidateMailBagDetailsFeatureConstants.DEST_FOR_CDT_MISSING_DOM_MAL))
                        &&mailbagVO.getMailbagId()!=null&&noOfChar.contains(String.valueOf(mailbagVO.getMailbagId().length()))) {
                    if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
                            && mailbagVO.isDeliveryNeeded()) {
                        throw new MailHHTBusniessException(
                                MailHHTBusniessException.PLAN_ROUTE_MISSING_FOR_DLV,
                                MailHHTBusniessException.PLAN_ROUTE_MISSING);
                    }
                    throw new MailHHTBusniessException(MailHHTBusniessException.PLAN_ROUTE_MISSING,
                            MailHHTBusniessException.PLAN_ROUTE_MISSING);
                }
            }
        }
    }
}
