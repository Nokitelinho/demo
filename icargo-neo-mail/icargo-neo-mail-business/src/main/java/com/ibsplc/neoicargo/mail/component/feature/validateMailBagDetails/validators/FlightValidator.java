package com.ibsplc.neoicargo.mail.component.feature.validateMailBagDetails.validators;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.orchestration.Validator;
import com.ibsplc.neoicargo.mail.component.feature.validateMailBagDetails.ValidateMailBagDetailsFeatureConstants;
import com.ibsplc.neoicargo.mail.component.proxy.FlightOperationsProxy;
import com.ibsplc.neoicargo.mail.errorhandling.exception.MailHHTBusniessException;
import com.ibsplc.neoicargo.mail.exception.ForceAcceptanceException;
import com.ibsplc.neoicargo.mail.exception.MailMLDBusniessException;
import com.ibsplc.neoicargo.mail.util.FlightValidationUtils;
import com.ibsplc.neoicargo.mail.util.NeoMastersServiceUtils;
import com.ibsplc.neoicargo.mail.util.ScannerValidationUtils;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.ScannedMailDetailsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
@Component("flightValidator")
public class FlightValidator extends Validator<ScannedMailDetailsVO> {
    @Autowired
    private NeoMastersServiceUtils neoMastersServiceUtils;
    @Autowired
    ScannerValidationUtils scannerValidationUtils;

    @Override
    public void validate(ScannedMailDetailsVO scannedMailDetailsVO) throws BusinessException {
        Collection<FlightValidationVO> flightDetailsVOs = null;
        AirlineValidationVO airlineValidationVO = null;
        if (scannedMailDetailsVO.getFlightNumber() != null && !"null".equals(scannedMailDetailsVO.getFlightNumber())
                && scannedMailDetailsVO.getFlightNumber().trim().length() > 0
                && !MailConstantsVO.DESTN_FLT_STR.equals(scannedMailDetailsVO.getFlightNumber())) {
            flightDetailsVOs = scannedMailDetailsVO.getFlightValidationVOS();
            if (flightDetailsVOs==null||flightDetailsVOs.isEmpty()) {
                if (!(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())
                        && scannedMailDetailsVO.getMailDetails() != null
                        && !scannedMailDetailsVO.getMailDetails().isEmpty())) {
                    scannerValidationUtils.constructAndRaiseException(MailConstantsVO.MLD_MSG_ERR_INVALID_FLIGHT,
                            MailHHTBusniessException.INVALID_FLIGHT, scannedMailDetailsVO);
                }
            } else {
                String validateImportHandling =neoMastersServiceUtils.findSystemParameterValue(ValidateMailBagDetailsFeatureConstants.SYSPAR_IMPORT_HANDL_VALIDATION);
                Collection<String> parCodes = new ArrayList<>();
                parCodes.add(ValidateMailBagDetailsFeatureConstants.SHARED_ARPPAR_ONLARP);
                Map<String, String> arpMap = neoMastersServiceUtils.findParametersForAirport(scannedMailDetailsVO.getCompanyCode(),
                        flightDetailsVOs.iterator().next().getLegOrigin(), parCodes);
                if(arpMap!=null) {
                    String onlineArpParamater = arpMap.get(ValidateMailBagDetailsFeatureConstants.SHARED_ARPPAR_ONLARP);
                    if ("Y".equals(validateImportHandling) && ("Y").equals(onlineArpParamater)
                            && flightDetailsVOs.iterator().next().getAtd() == null) {
                        scannerValidationUtils.constructAndRaiseException(MailConstantsVO.MAIL_OPERATIONS_ATD_MISSING,
                                MailHHTBusniessException.MAIL_OPERATIONS_ATD_MISSING, scannedMailDetailsVO);
                    }
                }
            }
        }
        if (scannedMailDetailsVO.getCarrierCode() != null
                && scannedMailDetailsVO.getCarrierCode().trim().length() != 0) {
            try {
                airlineValidationVO = neoMastersServiceUtils.validateAlphaCode(
                        scannedMailDetailsVO.getCompanyCode(),scannedMailDetailsVO.getCarrierCode());
                if (airlineValidationVO == null) {
                    scannerValidationUtils.constructAndRaiseException(MailMLDBusniessException.INVALID_CARRIERCODE_EXCEPTION,
                            MailHHTBusniessException.INVALID_CARRIERCODE_EXCEPTION, scannedMailDetailsVO);
                } else {
                    scannedMailDetailsVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
                }
            } finally {
            }
        }
        if (scannedMailDetailsVO.getCarrierCode() == null
                || scannedMailDetailsVO.getCarrierCode().trim().length() == 0) {
            scannerValidationUtils.constructAndRaiseException(MailMLDBusniessException.INVALID_CARRIERCODE_EXCEPTION,
                    MailHHTBusniessException.INVALID_CARRIERCODE_EXCEPTION, scannedMailDetailsVO);
        }
        if (flightDetailsVOs != null && !flightDetailsVOs.isEmpty()) {
            scannedMailDetailsVO
                    .setFlightSequenceNumber(flightDetailsVOs.iterator().next().getFlightSequenceNumber());
        }
    }

}
