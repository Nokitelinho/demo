package com.ibsplc.neoicargo.mail.component.feature.autoattachawbdetails.validators;

import com.ibsplc.neoicargo.framework.orchestration.FeatureContextUtilThreadArray;
import com.ibsplc.neoicargo.framework.orchestration.Validator;
import com.ibsplc.neoicargo.mail.component.MailController;
import com.ibsplc.neoicargo.mail.component.feature.autoattachawbdetails.AutoAttachAWBDetailsFeatureConstants;
import com.ibsplc.neoicargo.mail.vo.MailManifestDetailsVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.mail.vo.OperationalFlightVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;

@Slf4j
@Component(AutoAttachAWBDetailsFeatureConstants.PA_CODE_VALIDATOR)
public class PostalAuthorityCodeValidator extends Validator<MailManifestDetailsVO> {

	@Autowired
	private MailController mailController;

	@Override
	public void validate(MailManifestDetailsVO mailManifestDetailsVO)  {
		log.info("Entering {} {}",getClass().getSimpleName(), "validate");
		OperationalFlightVO operationalFlightVO = mailManifestDetailsVO.getOperationalFlightVO();
		if(Objects.isNull(operationalFlightVO)){
			return;
		}
		String companyCode = operationalFlightVO.getCompanyCode();
		Collection<MailbagVO> mailbagVOs = mailManifestDetailsVO.getMailbagVOs();
		String consignee = null;
		if (mailbagVOs != null && !mailbagVOs.isEmpty()) {
			String doe = mailbagVOs.iterator().next().getDoe();
			consignee = findPAForOfficeOfExchange(companyCode, doe);
		}
		addtoFeatureContext(AutoAttachAWBDetailsFeatureConstants.SAVE_PACODE, consignee);

		log.info("Exiting {} {}",getClass().getSimpleName(), "validate");
	}



	private String findPAForOfficeOfExchange(String companyCode, String officeOfExchange) {
		return mailController.findPAForOfficeOfExchange(companyCode, officeOfExchange);
	}

}
