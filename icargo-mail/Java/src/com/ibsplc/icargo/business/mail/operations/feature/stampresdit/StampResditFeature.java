package com.ibsplc.icargo.business.mail.operations.feature.stampresdit;


import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.feature.stampresdit.persistors.MailResditPersistor;
import com.ibsplc.icargo.business.mail.operations.vo.MailResditVO;
import com.ibsplc.icargo.framework.feature.AbstractFeature;
import com.ibsplc.icargo.framework.feature.Feature;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.vo.FeatureConfigVO;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@FeatureComponent(StampResditFeatureConstants.STAMP_RESDIT_FEATURE)
@Feature(exception = MailTrackingBusinessException.class)
public class StampResditFeature extends AbstractFeature<MailResditVO> {
	
	private static final Log LOGGER = LogFactory.getLogger(StampResditFeatureConstants.MODULE_SUBMODULE);

	@Override
	protected FeatureConfigVO fetchFeatureConfig(MailResditVO mailResditVO) {
		FeatureConfigVO featureConfigVO = new FeatureConfigVO();
		List<String> validatorIds = new ArrayList<>();
		validatorIds.add(StampResditFeatureConstants.STAMP_RESDIT_VALIDATOR);
		featureConfigVO.setValidatorIds(validatorIds);
		return featureConfigVO;
	}

	@Override
	protected Void perform(MailResditVO mailResditVO) throws SystemException, BusinessException {
		LOGGER.entering(getClass().getSimpleName(), "perform");
		new MailResditPersistor().persist(mailResditVO);
		return null;
	}

}
