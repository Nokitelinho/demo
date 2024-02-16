package com.ibsplc.icargo.business.mail.operations.feature.autoattachawbdetails.validators;

import java.util.Collection;
import java.util.Objects;
import com.ibsplc.icargo.business.mail.operations.OfficeOfExchange;
import com.ibsplc.icargo.business.mail.operations.feature.autoattachawbdetails.AutoAttachAWBDetailsFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.feature.saveconsignmentupload.SaveConsignmentUploadFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.Validator;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@FeatureComponent(AutoAttachAWBDetailsFeatureConstants.AGENT_CODE_VALIDATOR)
public class AgentCodeValidator extends Validator<MailManifestDetailsVO> {

	private static final Log LOGGER = LogFactory.getLogger(SaveConsignmentUploadFeatureConstants.MODULE_SUBMODULE);

	@Override
	public void validate(MailManifestDetailsVO mailManifestDetailsVO) throws BusinessException, SystemException {
		LOGGER.entering(getClass().getSimpleName(), "validate");
		OperationalFlightVO operationalFlightVO = mailManifestDetailsVO.getOperationalFlightVO();
		if(Objects.isNull(operationalFlightVO)){
			return;
		}
		String companyCode = operationalFlightVO.getCompanyCode();
		Collection<MailbagVO> mailbagVOs = mailManifestDetailsVO.getMailbagVOs();
		String agentCode = null;
		if (mailbagVOs != null && !mailbagVOs.isEmpty()) {
			String shipper = mailbagVOs.iterator().next().getPaCode();
			agentCode = findAgentCodeForPA(companyCode, shipper);
		}

		addToContext(AutoAttachAWBDetailsFeatureConstants.SAVE_AGENTCODE, agentCode);

		LOGGER.exiting(getClass().getSimpleName(), "validate");
	}

	private String findAgentCodeForPA(String companyCode, String officeOfExchange) throws SystemException {

		return OfficeOfExchange.findAgentCodeForPA(companyCode, officeOfExchange);
	}

}
