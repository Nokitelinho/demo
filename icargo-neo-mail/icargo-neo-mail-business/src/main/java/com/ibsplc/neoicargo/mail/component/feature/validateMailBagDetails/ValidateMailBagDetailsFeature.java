package com.ibsplc.neoicargo.mail.component.feature.validateMailBagDetails;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import com.ibsplc.neoicargo.framework.orchestration.FeatureContextUtilThreadArray;
import com.ibsplc.neoicargo.mail.component.MailController;
import com.ibsplc.neoicargo.mail.component.feature.validateMailBagDetails.validators.SecurityScreeningValidator;
import com.ibsplc.neoicargo.mail.errorhandling.exception.MailHHTBusniessException;
import com.ibsplc.neoicargo.mail.exception.ForceAcceptanceException;
import com.ibsplc.neoicargo.mail.exception.MailMLDBusniessException;
import com.ibsplc.neoicargo.mail.util.FlightValidationUtils;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.ScannedMailDetailsVO;
import com.ibsplc.xibase.util.log.Log;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("validateMailBagDetailsFeature")
@FeatureConfigSource("feature/validateMailBagDetailsFeature")
@Slf4j
public class ValidateMailBagDetailsFeature extends AbstractFeature<ScannedMailDetailsVO> {
    @Autowired
    private ContextUtil contextUtil;

    @Autowired
    SecurityScreeningValidator securityScreeningValidator;
    @Override
    protected ScannedMailDetailsVO perform(ScannedMailDetailsVO scannedMailDetailsVO) throws BusinessException {
        log.debug("inside perform");
        return scannedMailDetailsVO;
    }

    @Override
    protected void validate(ScannedMailDetailsVO scannedMailDetailsVO) throws BusinessException {
        securityScreeningValidator.validate(scannedMailDetailsVO);
    }
}
