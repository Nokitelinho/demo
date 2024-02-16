package com.ibsplc.icargo.business.mail.operations.feature.autoattachawbdetails.validators;

import java.util.Collection;
import java.util.Objects;
import com.ibsplc.icargo.business.mail.operations.cache.OfficeOfExchangeCache;
import com.ibsplc.icargo.business.mail.operations.feature.autoattachawbdetails.AutoAttachAWBDetailsFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.feature.saveconsignmentupload.SaveConsignmentUploadFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.Validator;
import com.ibsplc.xibase.server.framework.cache.CacheException;
import com.ibsplc.xibase.server.framework.cache.CacheFactory;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@FeatureComponent(AutoAttachAWBDetailsFeatureConstants.PA_CODE_VALIDATOR)
public class PostalAuthorityCodeValidator extends Validator<MailManifestDetailsVO> {

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
		String consignee = null;
		if (mailbagVOs != null && !mailbagVOs.isEmpty()) {
			String doe = mailbagVOs.iterator().next().getDoe();
			consignee = findPAForOfficeOfExchange(companyCode, doe);
		}

		addToContext(AutoAttachAWBDetailsFeatureConstants.SAVE_PACODE, consignee);

		LOGGER.exiting(getClass().getSimpleName(), "validate");
	}

	private String findPAForOfficeOfExchange(String companyCode, String officeOfExchange) throws CacheException {
		String paCode = null;
		Page<OfficeOfExchangeVO> officeOfExchangePage = null;
		CacheFactory factory = CacheFactory.getInstance();
		OfficeOfExchangeCache cache = factory.getCache(OfficeOfExchangeCache.ENTITY_NAME);
		officeOfExchangePage = cache.findOfficeOfExchange(companyCode, officeOfExchange, 1);
		if (officeOfExchangePage != null && !officeOfExchangePage.isEmpty()
				&& officeOfExchangePage.iterator().next().getPoaCode() != null
				&& officeOfExchangePage.iterator().next().isActive()) {
			paCode = officeOfExchangePage.iterator().next().getPoaCode();
		}
		return paCode;
	}

}
