package com.ibsplc.neoicargo.mail.component.feature.validateMailBagDetails.validators;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.orchestration.Validator;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.mail.component.MailController;
import com.ibsplc.neoicargo.mail.component.MailUploadController;
import com.ibsplc.neoicargo.mail.component.Mailbag;
import com.ibsplc.neoicargo.mail.component.MailbagPK;
import com.ibsplc.neoicargo.mail.component.feature.validateMailBagDetails.ValidateMailBagDetailsFeatureConstants;
import com.ibsplc.neoicargo.mail.errorhandling.exception.MailHHTBusniessException;
import com.ibsplc.neoicargo.mail.util.NeoMastersServiceUtils;
import com.ibsplc.neoicargo.mail.util.ScannerValidationUtils;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.mail.vo.OfficeOfExchangeVO;
import com.ibsplc.neoicargo.mail.vo.ScannedMailDetailsVO;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Component("lATValidator")
public class LATValidator extends Validator<ScannedMailDetailsVO> {

    @Autowired
    private NeoMastersServiceUtils neoMastersServiceUtils;
    @Autowired
    private MailController mailController;
    @Autowired
    private MailUploadController mailuploadController;
    @Autowired
    ScannerValidationUtils scannerValidationUtils;
    @Autowired
    private LocalDate localDateUtil;
    @Autowired
    private ContextUtil contextUtil;
    @Override
    public void validate(ScannedMailDetailsVO scannedMailDetailsVO) throws BusinessException, SystemException {
        ScannedMailDetailsVO scannedMailVO=new ScannedMailDetailsVO();
        Collection<MailbagVO> mailBagVOs = scannedMailDetailsVO
                .getMailDetails();
        String paCode=null;
        String paCode_int= null;
        String paCode_dom= null;
        String scanPort=null;
        Collection<MailbagVO> newMailbagVOs=new ArrayList<MailbagVO>();
        paCode_int= (String) contextUtil.getInstance().getTxContext(ValidateMailBagDetailsFeatureConstants.USPS_INTERNATIONAL_PA);
        paCode_dom = (String) ContextUtil.getInstance().getTxContext(ValidateMailBagDetailsFeatureConstants.DOMESTICMRA_USPS);
        boolean latValidation = false;
        boolean coterminusAvalilable=false;
        if (mailBagVOs != null && mailBagVOs.size() > 0) {
            for (MailbagVO mailBagvo : mailBagVOs) {
                    paCode=mailBagvo.getPaCode();
                scanPort=mailBagvo.getScannedPort();
                if(mailBagvo.getOrigin()!=null&&paCode!=null&& paCode.trim().length()>0
                        && ((paCode.equals(paCode_int)||(paCode.equals(paCode_dom))
                        && scannedMailDetailsVO.getProcessPoint().equals("ACP"))))
                {
                    mailBagvo.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
                    mailBagvo.setFlightDate(scannedMailDetailsVO.getFlightDate());
                    mailBagvo.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
                    mailBagvo.setFlightDate(scannedMailDetailsVO.getFlightDate());
                    mailBagvo.setCarrierCode(scannedMailDetailsVO.getCarrierCode());
                    mailBagvo.setCarrierId(scannedMailDetailsVO.getCarrierId());
                    mailBagvo.setPol(scannedMailDetailsVO.getPol());
                    mailBagvo.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
                    newMailbagVOs.add(mailBagvo);
                }
                if(MailConstantsVO.FLAG_YES.equals(mailBagvo.getLatValidationNeeded())){
                    ZonedDateTime dspDate = localDateUtil.getLocalDate(scanPort, true);
                    if (mailBagvo.getConsignmentDate() != null) {
                        dspDate = localDateUtil.getLocalDateTime(mailBagvo.getConsignmentDate(), null);
                    }
                    coterminusAvalilable= mailController.validateCoterminusairports(mailBagvo.getOrigin(), scannedMailDetailsVO.getAirportCode(),
                            MailConstantsVO.RESDIT_RECEIVED, paCode,dspDate);
                    if(mailBagvo.getOrigin()!=null&&mailBagvo.getOrigin().equals(scannedMailDetailsVO.getAirportCode()) || coterminusAvalilable){
                        latValidation=true;
                    }
                }
            }
        }
        if(newMailbagVOs!=null && newMailbagVOs.size()>0&& scannedMailDetailsVO.getProcessPoint().equals("ACP"))	//added by A-7371 as part of ICRD-297488
            if(latValidation){
                scannedMailVO= mailController.doLATValidation(newMailbagVOs, true);
            }
        if(scannedMailVO.getErrorDescription()!=null)
        {
            if(scannedMailVO.getErrorDescription().equals(ValidateMailBagDetailsFeatureConstants.LAT_VIOLATED_ERR)){
                scannerValidationUtils.constructAndroidException(MailConstantsVO.LAT_VIOLATED_ERR, MailHHTBusniessException.LAT_ERROR, scannedMailDetailsVO);
                scannerValidationUtils.constructAndRaiseException(MailConstantsVO.LAT_VIOLATED_ERR, MailHHTBusniessException.LAT_ERROR, scannedMailDetailsVO);
            }
            else{
                scannerValidationUtils.constructAndroidException(MailConstantsVO.LAT_VIOLATED_WAR, MailHHTBusniessException.LAT_WARNING
                        , scannedMailDetailsVO);
            }
        }
    }
}
